package view;

import javax.swing.*;
import shared.*;
import model.Staff;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StaffPosWin extends JFrame {
    private JLabel lblClock, lblStaffInfo;
    private DefaultListModel<String> orderQueueModel;
    private JList<String> orderQueueList;

    public StaffPosWin(OrderBuffer ob, OrderNumber on, StockManager sm, StampManager stm, Staff staff) {
        setTitle("스마트 카페 - 카운터 직원 POS (" + staff.getName() + "님)");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // TOP: 툴바 및 직원 정보, 실시간 시계 스레드
        JToolBar toolBar = new JToolBar();
        lblStaffInfo = new JLabel(" 근무자: " + staff.getName() + " (" + staff.getStaffId() + ") | ");
        lblClock = new JLabel();
        toolBar.add(lblStaffInfo);
        toolBar.add(lblClock);
        
        JButton btnAttendanceIn = new JButton("출근 체크");
        btnAttendanceIn.addActionListener(e -> JOptionPane.showMessageDialog(this, "출근이 기록되었습니다."));
        JButton btnAttendanceOut = new JButton("퇴근 체크");
        btnAttendanceOut.addActionListener(e -> JOptionPane.showMessageDialog(this, "퇴근 및 근무 시간이 계산되었습니다."));
        toolBar.add(btnAttendanceIn);
        toolBar.add(btnAttendanceOut);
        
        add(toolBar, BorderLayout.NORTH);

        // CENTER: 실시간 주문 대기열 (JSplitPane)
        orderQueueModel = new DefaultListModel<>();
        orderQueueList = new JList<>(orderQueueModel);
        JScrollPane scrollQueue = new JScrollPane(orderQueueList);
        scrollQueue.setBorder(BorderFactory.createTitledBorder("실시간 주방 주문 대기열 (생산자-소비자 연동)"));

        JPanel pnlAction = new JPanel(new GridLayout(2, 1));
        JButton btnStartCook = new JButton("제조 시작");
        JButton btnCompleteCook = new JButton("제조 완료");
        pnlAction.add(btnStartCook);
        pnlAction.add(btnCompleteCook);

        JSplitPane splitCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollQueue, pnlAction);
        splitCenter.setDividerLocation(750);
        add(splitCenter, BorderLayout.CENTER);

        // POS 시계 스레드 구동 (3주차 수업 내용)
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    SwingUtilities.invokeLater(() -> lblClock.setText("현재 시각: " + time));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

        // 백그라운드 주문 수신 스레드 실행 (OrderReceiver)
        new Thread(() -> {
            while (true) {
                OrderInfo info = ob.getOrder();
                if (info != null) {
                    SwingUtilities.invokeLater(() -> orderQueueModel.addElement("[주문 #" + info.orderNo + "] " + info.summary));
                }
            }
        }).start();
    }
}