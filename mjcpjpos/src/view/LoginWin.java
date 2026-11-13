package view;

import javax.swing.*;
import shared.*;
import db.StaffDB;
import model.Staff;
import java.awt.*;

public class LoginWin extends JFrame {
    private JTextField txtId;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private JLabel lblStatus;

    public LoginWin(OrderBuffer ob, OrderNumber on, StockManager sm, StampManager stm, CallBoard cb) {
        setTitle("스마트 카페 - 공통 로그인");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // WEST: 브랜드 안내 패널
        JPanel pnlWest = new JPanel();
        pnlWest.setBackground(new Color(44, 34, 28));
        pnlWest.setPreferredSize(new Dimension(180, 300));
        pnlWest.setLayout(new GridBagLayout());
        JLabel lblBrand = new JLabel("<html><center><font color='white' size='5'><b>SMART CAFE</b></font><br><font color='#aaaaaa' size='2'>카운터·주방·관리 통합</font></center></html>");
        pnlWest.add(lblBrand);
        add(pnlWest, BorderLayout.WEST);

        // CENTER: 로그인 입력 패널 (GridLayout)
        JPanel pnlCenter = new JPanel(new GridLayout(4, 1, 5, 5));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel pnlId = new JPanel(new BorderLayout(5, 5));
        pnlId.add(new JLabel("사원번호"), BorderLayout.NORTH);
        txtId = new JTextField();
        pnlId.add(txtId, BorderLayout.CENTER);
        pnlCenter.add(pnlId);

        JPanel pnlPass = new JPanel(new BorderLayout(5, 5));
        pnlPass.add(new JLabel("비밀번호"), BorderLayout.NORTH);
        txtPass = new JPasswordField();
        pnlPass.add(txtPass, BorderLayout.CENTER);
        pnlCenter.add(pnlPass);

        btnLogin = new JButton("시스템 로그인");
        btnLogin.setBackground(new Color(15, 118, 110));
        btnLogin.setForeground(Color.WHITE);
        pnlCenter.add(btnLogin);

        lblStatus = new JLabel("직원·관리자 계정으로 로그인하세요.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("SansSerif", Font.PLAIN, 11));
        pnlCenter.add(lblStatus);

        add(pnlCenter, BorderLayout.CENTER);

        // 이벤트 처리
        btnLogin.addActionListener(e -> processLogin(ob, on, sm, stm, cb));
        txtPass.addActionListener(e -> processLogin(ob, on, sm, stm, cb)); // 엔터 키 로그인
    }

    private void processLogin(OrderBuffer ob, OrderNumber on, StockManager sm, StampManager stm, CallBoard cb) {
        try {
            int staffId = Integer.parseInt(txtId.getText().trim());
            String password = new String(txtPass.getPassword());

            Staff staff = new StaffDB().getStaffById(staffId);
            if (staff != null && staff.getPassword().equals(password)) {
                if (staff.getStatus() == 0) {
                    JOptionPane.showMessageDialog(this, "퇴사 처리된 직원 계정입니다.");
                    return;
                }
                dispose();
                if ("ADMIN".equals(staff.getRole())) {
                    new AdminWin(ob, on, sm, stm, cb).setVisible(true);
                } else {
                    new StaffPosWin(ob, on, sm, stm, staff).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "사원번호 또는 비밀번호가 일치하지 않습니다.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "사원번호를 올바른 숫자로 입력하세요.");
        }
    }
}