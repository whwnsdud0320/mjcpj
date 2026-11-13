package view;

import javax.swing.*;
import shared.CallBoard;
import java.awt.*;

public class PickupBoardWin extends JFrame {
    private JLabel lblNumber;

    public PickupBoardWin(CallBoard board) {
        setTitle("스마트 카페 - 주문번호 호출 화면");
        setSize(700, 500);
        setLocation(1000, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel pnlHeader = new JPanel();
        JLabel lblTitle = new JLabel("음료가 준비되었습니다", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitle.setForeground(new Color(15, 118, 110));
        pnlHeader.add(lblTitle);
        add(pnlHeader, BorderLayout.NORTH);

        lblNumber = new JLabel("--- 번", SwingConstants.CENTER);
        lblNumber.setFont(new Font("SansSerif", Font.BOLD, 120));
        add(lblNumber, BorderLayout.CENTER);

        // 호출 대기열 수신 및 화면 갱신 스레드
        new Thread(() -> {
            while (true) {
                int orderNo = board.getCallOrder();
                if (orderNo != -1) {
                    SwingUtilities.invokeLater(() -> lblNumber.setText(orderNo + " 번"));
                }
            }
        }).start();
    }
}