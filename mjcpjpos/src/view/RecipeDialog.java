package view;

import javax.swing.*;
import java.awt.*;

public class RecipeDialog extends JDialog {
    public RecipeDialog(JFrame parent) {
        super(parent, "메뉴별 원자재 레시피 관리", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        add(new JLabel("메뉴별 1잔당 원자재 소모량을 관리합니다.", SwingConstants.CENTER), BorderLayout.NORTH);
        
        String[] cols = {"원자재명", "1잔당 소모량", "단위"};
        Object[][] data = {{"에스프레소 원두", "20", "g"}};
        add(new JScrollPane(new JTable(data, cols)), BorderLayout.CENTER);

        JButton btnClose = new JButton("닫기");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);
    }
}