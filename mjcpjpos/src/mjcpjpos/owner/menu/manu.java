package mjcpjpos.owner.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class manu extends JPanel{
    public manu() {
        setBackground(Color.WHITE);

        setLayout(new GridBagLayout());
        
        // 화면에 띄울 재고발주 텍스트 레이블 생성
        JLabel txtLabel = new JLabel("메뉴확인 화면");
        txtLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30)); // 글자 크기 키우기
        txtLabel.setForeground(Color.BLACK);
        
        // 패널에 레이블 추가 (GridBagLayout 기본값에 의해 정중앙에 배치됨)
        add(txtLabel);
    }
}
