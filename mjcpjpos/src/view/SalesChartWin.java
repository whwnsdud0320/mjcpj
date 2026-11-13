package view;

import javax.swing.*;
import java.awt.*;

public class SalesChartWin extends JPanel {
    public SalesChartWin() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("조회 기간: "));
        topPanel.add(new JComboBox<>(new String[]{"2026-11-01 ~ 2026-11-12"}));
        JButton btnQuery = new JButton("통계 갱신");
        topPanel.add(btnQuery);
        add(topPanel, BorderLayout.NORTH);

        // 시간대별 매출 막대 그래프 커스텀 패널 (JProgressBar / Custom Paint 활용)
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawString("【 시간대별 매출 막대 그래프 통계 영역 】", 100, 100);
                g.drawRect(50, 130, 120, 200);
                g.fillRect(50, 180, 120, 150);
                g.drawString("점심 피크 (11~14시)", 50, 350);
            }
        };
        add(chartPanel, BorderLayout.CENTER);
    }
}