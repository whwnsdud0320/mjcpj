package view;

import javax.swing.*;
import shared.StampManager;
import java.awt.*;

public class KeypadDialog extends JDialog {
    private JTextField txtPhone;

    public KeypadDialog(JFrame parent, StampManager sm) {
        super(parent, "스탬프 적립 - 휴대폰 번호 입력", true);
        setSize(350, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        txtPhone = new JTextField("010-");
        txtPhone.setFont(new Font("SansSerif", Font.BOLD, 18));
        txtPhone.setHorizontalAlignment(JTextField.CENTER);
        add(txtPhone, BorderLayout.NORTH);

        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.addActionListener(e -> txtPhone.setText(txtPhone.getText() + e.getActionCommand()));
            keypadPanel.add(btn);
        }
        JButton btnClear = new JButton("지우기");
        btnClear.addActionListener(e -> txtPhone.setText("010-"));
        JButton btnZero = new JButton("0");
        btnZero.addActionListener(e -> txtPhone.setText(txtPhone.getText() + "0"));
        JButton btnConfirm = new JButton("확인");
        btnConfirm.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "스탬프가 조회/적립되었습니다.");
            dispose();
        });

        keypadPanel.add(btnClear);
        keypadPanel.add(btnZero);
        keypadPanel.add(btnConfirm);
        add(keypadPanel, BorderLayout.CENTER);
    }
}