package view;

import javax.swing.*;
import model.Menu;
import java.awt.*;

public class OptionDialog extends JDialog {
    private boolean confirmed = false;
    private int quantity = 1;

    public OptionDialog(JFrame parent, Menu menu) {
        super(parent, "옵션 선택 - " + menu.getMenuName(), true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 10, 10));

        add(new JLabel("메뉴: " + menu.getMenuName() + " (" + menu.getPrice() + "원)", SwingConstants.CENTER));
        
        JPanel tempPanel = new JPanel();
        JRadioButton rbIce = new JRadioButton("ICE", true);
        JRadioButton rbHot = new JRadioButton("HOT");
        ButtonGroup bgTemp = new ButtonGroup();
        bgTemp.add(rbIce); bgTemp.add(rbHot);
        tempPanel.add(rbIce); tempPanel.add(rbHot);
        add(tempPanel);

        JPanel qtyPanel = new JPanel();
        JButton btnMinus = new JButton("-");
        JLabel lblQty = new JLabel(" 1 ");
        JButton btnPlus = new JButton("+");
        btnPlus.addActionListener(e -> { quantity++; lblQty.setText(" " + quantity + " "); });
        btnMinus.addActionListener(e -> { if(quantity > 1) quantity--; lblQty.setText(" " + quantity + " "); });
        qtyPanel.add(new JLabel("수량:")); qtyPanel.add(btnMinus); qtyPanel.add(lblQty); qtyPanel.add(btnPlus);
        add(qtyPanel);

        JButton btnOk = new JButton("선택 완료");
        btnOk.addActionListener(e -> { confirmed = true; dispose(); });
        add(btnOk);
    }

    public boolean isConfirmed() { return confirmed; }
    public int getQuantity() { return quantity; }
}