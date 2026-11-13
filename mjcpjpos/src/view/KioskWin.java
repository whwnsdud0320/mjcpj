package view;

import javax.swing.*;
import shared.*;
import db.*;
import model.Menu;
import exception.OutOfStockException;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KioskWin extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainCardPanel;
    private OrderBuffer orderBuffer;
    private OrderNumber orderNumber;
    private StockManager stockManager;
    private StampManager stampManager;
    private String orderType = "매장"; // "매장" 또는 "포장"
    private DefaultListModel<String> cartListModel;
    private JTable cartTable;
    private JLabel lblTotalPrice;
    private Map<Integer, Integer> currentCartItems = new HashMap<>(); // MenuId -> Quantity

    public KioskWin(OrderBuffer ob, OrderNumber on, StockManager sm, StampManager stm) {
        this.orderBuffer = ob;
        this.orderNumber = on;
        this.stockManager = sm;
        this.stampManager = stm;

        setTitle("스마트 카페 - 무인 키오스크");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(50, 50);

        cardLayout = new CardLayout();
        mainCardPanel = new JPanel(cardLayout);

        mainCardPanel.add(createFirstCard(), "CARD1");
        mainCardPanel.add(createOrderCard(), "CARD2");

        add(mainCardPanel);
        cardLayout.show(mainCardPanel, "CARD1");
    }

    // 카드 1: 첫 화면 (매장/포장 선택)
    private JPanel createFirstCard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel("어서 오세요! 어디에서 드시나요?", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(lblTitle, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JButton btnDineIn = new JButton("매장 식사");
        btnDineIn.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnDineIn.addActionListener(e -> {
            orderType = "매장";
            cardLayout.show(mainCardPanel, "CARD2");
        });

        JButton btnTakeout = new JButton("포장하기");
        btnTakeout.setFont(new Font("SansSerif", Font.BOLD, 22));
        btnTakeout.addActionListener(e -> {
            orderType = "포장";
            cardLayout.show(mainCardPanel, "CARD2");
        });

        btnPanel.add(btnDineIn);
        btnPanel.add(btnTakeout);
        panel.add(btnPanel, BorderLayout.CENTER);
        return panel;
    }

    // 카드 2: 메뉴 주문 화면
    private JPanel createOrderCard() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 상단 안내
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnFirst = new JButton("처음으로");
        btnFirst.addActionListener(e -> cardLayout.show(mainCardPanel, "CARD1"));
        topPanel.add(btnFirst);
        topPanel.add(new JLabel("  [주문 모드: 키오스크]"));
        panel.add(topPanel, BorderLayout.NORTH);

        // 중앙: 카테고리 탭 및 메뉴 버튼
        JTabbedPane tabbedPane = new JTabbedPane();
        List<Menu> coffeeList = new MenuDB().getMenuList(1); // 예시: 카테고리 1 (커피)
        JPanel coffeePanel = new JPanel(new GridLayout(3, 3, 10, 10));
        for (Menu m : coffeeList) {
            JButton btnMenu = new JButton("<html>" + m.getMenuName() + "<br>" + m.getPrice() + "원</html>");
            btnMenu.addActionListener(e -> {
                if (m.isSoldOut()) {
                    JOptionPane.showMessageDialog(this, "품절된 메뉴입니다.");
                    return;
                }
                OptionDialog optDlg = new OptionDialog(this, m);
                optDlg.setVisible(true);
                if (optDlg.isConfirmed()) {
                    currentCartItems.put(m.getMenuId(), currentCartItems.getOrDefault(m.getMenuId(), 0) + optDlg.getQuantity());
                    refreshCartTable();
                }
            });
            coffeePanel.add(btnMenu);
        }
        tabbedPane.addTab("커피", new JScrollPane(coffeePanel));
        panel.add(tabbedPane, BorderLayout.CENTER);

        // 오른쪽: 장바구니 및 결제 패널
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(320, 700));
        rightPanel.setBorder(BorderFactory.createTitledBorder("장바구니"));

        cartListModel = new DefaultListModel<>();
        cartTable = new JTable(new Object[][]{}, new String[]{"상품명", "수량", "금액"});
        rightPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        JPanel payPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        lblTotalPrice = new JLabel("총 결제금액: 0원", SwingConstants.RIGHT);
        lblTotalPrice.setFont(new Font("SansSerif", Font.BOLD, 16));
        payPanel.add(lblTotalPrice);

        JButton btnStamp = new JButton("스탬프 조회·사용");
        btnStamp.addActionListener(e -> new KeypadDialog(this, stampManager).setVisible(true));
        payPanel.add(btnStamp);

        JButton btnPay = new JButton("신용카드 결제");
        btnPay.setBackground(new Color(15, 118, 110));
        btnPay.setForeground(Color.WHITE);
        btnPay.addActionListener(e -> processCheckout());
        payPanel.add(btnPay);

        rightPanel.add(payPanel, BorderLayout.SOUTH);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void refreshCartTable() {
        // 장바구니 UI 갱신 로직
    }

    private void processCheckout() {
        if (currentCartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "장바구니가 비어 있습니다.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 1. 재고 검사 및 차감 (재고 부족 시 OutOfStockException 발생 및 전파)
            stockManager.checkAndDecreaseStock(conn, currentCartItems);

            // 2. 주문번호 발급 및 결제 정보 저장 (SELL 테이블)
            int orderNo = orderNumber.getNextOrderNo();
            new SellDB().insertSell(conn, null, null, orderNo, orderType, 10000, 10000, 0, 0);

            conn.commit(); // 커밋

            // 3. POS 수신 스레드로 주문 대기열 전달 (생산자-소비자 통신)
            orderBuffer.putOrder(new OrderInfo(orderNo, "아메리카노 외"));

            JOptionPane.showMessageDialog(this, "결제가 완료되었습니다!\n주문번호: " + orderNo + "번");
            currentCartItems.clear();
            cardLayout.show(mainCardPanel, "CARD1");

        } catch (OutOfStockException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "결제 오류", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}