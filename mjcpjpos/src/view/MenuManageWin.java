package view;

import javax.swing.*;
import java.awt.*;

public class MenuManageWin extends JPanel {
    public MenuManageWin() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("카테고리 선택: "));
        JComboBox<String> cbCategory = new JComboBox<>(new String[]{"전체", "커피", "라떼", "에이드", "디저트"});
        topPanel.add(cbCategory);
        JButton btnAddCat = new JButton("카테고리 추가");
        topPanel.add(btnAddCat);
        add(topPanel, BorderLayout.NORTH);

        String[] cols = {"번호", "카테고리", "메뉴명", "가격", "시즌", "판매상태"};
        Object[][] data = {
            {1, "커피", "아메리카노", "4,000원", "ALL", "판매중"},
            {2, "커피", "카페라떼", "4,500원", "ALL", "판매중"}
        };
        JTable table = new JTable(data, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}