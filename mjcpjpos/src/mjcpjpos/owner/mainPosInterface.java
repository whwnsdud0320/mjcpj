package mjcpjpos.owner;

import javax.swing.*;
import mjcpjpos.owner.logout.logout;
import mjcpjpos.owner.menu.manu; // 명칭 주의: 소문자 manu 패널
import mjcpjpos.owner.order.order;
import mjcpjpos.owner.statistics.statistics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BordeLayoutOwnerPos extends JFrame implements ActionListener {
	
	Container ct = getContentPane();
	JPanel l1 = new JPanel(); // 서쪽 사이드바 패널
	JPanel l2 = new JPanel(); // 중앙 패널 (화면 교체용)
	
	// 패널 인스턴스들을 매번 새로 만들지 않고 미리 하나씩 생성
	manu mn = new manu();
	order od = new order();
	statistics st = new statistics();
	logout lg = new logout();
	
	BordeLayoutOwnerPos() {
		// 중앙 패널을 BorderLayout으로 설정
		l2.setLayout(new BorderLayout());
		
		// 세로 정렬(Y_AXIS)을 위한 BoxLayout 설정
		l1.setLayout(new BoxLayout(l1, BoxLayout.Y_AXIS));
		l1.setBackground(Color.GRAY);
		
		// 사이드바의 가로 크기 기준 설정
		int sidebarWidth = 200;
		
		JTextField f1 = new JTextField("ADMIN SMARTCAFE");
		f1.setHorizontalAlignment(JTextField.CENTER);
		f1.setEditable(false);
		f1.setMaximumSize(new Dimension(sidebarWidth, 40));
		
		// 버튼 생성 및 크기 고정
		JButton b1 = new JButton("메뉴-카테고리");
		JButton b2 = new JButton("재고-발주");
		JButton b3 = new JButton("근태-매출통계");
		JButton b4 = new JButton("로그아웃");
		
		Dimension buttonSize = new Dimension(sidebarWidth, 50);
		b1.setMaximumSize(buttonSize);
		b2.setMaximumSize(buttonSize);
		b3.setMaximumSize(buttonSize);
		b4.setMaximumSize(buttonSize);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		
		l1.add(f1);
		l1.add(Box.createVerticalStrut(20));
		l1.add(b1);
		l1.add(Box.createVerticalStrut(10));
		l1.add(b2);
		l1.add(Box.createVerticalStrut(10));
		l1.add(b3);
		l1.add(Box.createVerticalStrut(10));
		l1.add(b4);
		
		// 남은 하단 공간을 밀어 올려주는 Glue 추가
		l1.add(Box.createVerticalGlue());
		
		// 전체 레이아웃 배치
		ct.add(l1, BorderLayout.WEST);
		ct.add(l2, BorderLayout.CENTER);
		
		// 초기 첫 화면으로 메뉴 화면
		l2.add(mn, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
        
		l2.removeAll();
        
		if (s.equals("메뉴-카테고리")) {
			l2.add(mn, BorderLayout.CENTER); // 패널 배치 시 레이아웃 구역 명시
		} else if (s.equals("재고-발주")) {
			l2.add(od, BorderLayout.CENTER);
		} else if (s.equals("근태-매출통계")) {
			l2.add(st, BorderLayout.CENTER);
		} else if (s.equals("로그아웃")) {
			l2.add(lg, BorderLayout.CENTER);
		}
		
		l2.revalidate();
		l2.repaint();
	}
}

public class mainPosInterface {
    public void mainPos() {
        BordeLayoutOwnerPos onwerPos = new BordeLayoutOwnerPos();
        onwerPos.setTitle("관리자 포스");
        onwerPos.setExtendedState(JFrame.MAXIMIZED_BOTH);
        onwerPos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 정상 종료 추가
        onwerPos.setVisible(true);
    }
}
