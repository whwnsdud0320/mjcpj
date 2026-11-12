package mjcpjpos.owner;

import javax.swing.*;

import mjcpjpos.owner.logout.logout;
import mjcpjpos.owner.menu.manu;
import mjcpjpos.owner.order.order;
import mjcpjpos.owner.statistics.statistics;

import java.awt.*;

public class mainPosInterface {
    // 클래스 범위에서 centerPanel을 관리하기 위해 별도로 보관할 변수 선언 가능하지만, 
    // 여기서는 기존 frame을 기반으로 쉽게 교체하도록 구현하겠습니다.
    private JPanel currentCenterPanel; // 현재 우측에 보여지고 있는 패널을 추적하기 위한 변수

    public void mainPos() {
        manu mn = new manu();
        order od = new order(); // 재고발주 패널 인스턴스 생성 (위에서 생성자로 변경했으므로 자동 세팅됨)
        statistics st = new statistics();
        logout lg = new logout();
        
        JFrame frame = new JFrame("pos기 메인화면");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 전체화면

        // 1. 모니터의 실제 해상도(스크린 크기) 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;   
        int screenHeight = screenSize.height; 

        // 2. 가로 20% 크기 계산하기
        int sidebarWidth = (int) (screenWidth * 0.20);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.GRAY);
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));

        Dimension sidebarSize = new Dimension(sidebarWidth, screenHeight);
        sidebarPanel.setPreferredSize(sidebarSize);
        sidebarPanel.setMinimumSize(sidebarSize);
        sidebarPanel.setMaximumSize(sidebarSize);

        // --- [서브 패널 1: 정보 패널] ---
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("SMART CAFE POS");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        
        JLabel loginLabel = new JLabel("로그인: 홍길동");
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(loginLabel);

        // --- [서브 패널 2: 버튼 패널] ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));
        Dimension btnSize = new Dimension(150, 40);
        
        // 버튼 이름 매칭
        JButton btn1 = new JButton("메뉴, 카테고리 관리");
        JButton btn2 = new JButton("재고, 발주 관리");
        JButton btn3 = new JButton("근태, 매출 통계 관리");
        JButton btn4 = new JButton("로그아웃");

        // BoxLayout 컴포넌트 가로정렬
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn2.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn3.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn4.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn1.setMaximumSize(btnSize); btn1.setPreferredSize(btnSize);
        btn2.setMaximumSize(btnSize); btn2.setPreferredSize(btnSize);
        btn3.setMaximumSize(btnSize); btn3.setPreferredSize(btnSize);
        btn4.setMaximumSize(btnSize); btn4.setPreferredSize(btnSize);

        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(btn1);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btn2);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btn3);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btn4);

        sidebarPanel.add(infoPanel);
        sidebarPanel.add(buttonPanel);

        //메인 프레임 레이아웃 배치
        frame.setLayout(new BorderLayout());
        frame.add(sidebarPanel, BorderLayout.WEST);

        // 기본 오른쪽 구역 초기화
        currentCenterPanel = new JPanel();
        currentCenterPanel.setBackground(Color.WHITE);
        currentCenterPanel.add(new JLabel("여기에 주문 내역이나 테이블 목록이 들어갑니다 (80% 구역)"));
        frame.add(currentCenterPanel, BorderLayout.CENTER);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        btn1.addActionListener(e -> {
            frame.remove(currentCenterPanel);
            currentCenterPanel = mn;
            frame.add(currentCenterPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        btn2.addActionListener(e -> {
            frame.remove(currentCenterPanel);
            currentCenterPanel = od;
            frame.add(currentCenterPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        btn3.addActionListener(e -> {
            frame.remove(currentCenterPanel);
            currentCenterPanel = st;
            frame.add(currentCenterPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

        btn4.addActionListener(e -> {
            frame.remove(currentCenterPanel);
            currentCenterPanel = lg;
            frame.add(currentCenterPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
    }
}
