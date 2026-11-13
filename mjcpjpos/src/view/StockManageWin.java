package view;

import javax.swing.*;
import shared.StockManager;
import java.awt.*;

public class StockManageWin extends JPanel {
    public StockManageWin(StockManager sm) {
        setLayout(new BorderLayout());

        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        summaryPanel.add(new JLabel("관리 품목 수: 4개", SwingConstants.CENTER));
        summaryPanel.add(new JLabel("정상 품목: 3개", SwingConstants.CENTER));
        JLabel lblDanger = new JLabel("안전재고 미달: 1개", SwingConstants.CENTER);
        lblDanger.setForeground(Color.RED);
        summaryPanel.add(lblDanger);
        add(summaryPanel, BorderLayout.NORTH);

        String[] cols = {"코드", "품목명", "분류", "현재재고", "안전재고", "단위"};
        Object[][] data = {
            {"S01", "에스프레소 원두", "원자재", "15 kg", "5 kg", "kg"},
            {"S02", "서울우유 1L", "유제품", "4 팩", "10 팩", "팩"}
        };
        JTable table = new JTable(data, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout());
        JButton btnOrder = new JButton("발주 승인 및 재고 가산");
        JButton btnRecipe = new JButton("레시피 관리");
        btnRecipe.addActionListener(e -> new RecipeDialog((JFrame)SwingUtilities.getWindowAncestor(this)).setVisible(true));
        pnlBottom.add(btnOrder);
        pnlBottom.add(btnRecipe);
        add(pnlBottom, BorderLayout.SOUTH);
    }
}