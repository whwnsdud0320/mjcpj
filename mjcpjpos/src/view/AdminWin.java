package view;

import javax.swing.*;
import shared.*;
import java.awt.*;

public class AdminWin extends JFrame {
    public AdminWin(OrderBuffer ob, OrderNumber on, StockManager sm, StampManager stm, CallBoard cb) {
        setTitle("스마트 카페 - 점주 관리자 모드");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // 탭 1: 메뉴·카테고리 관리
        tabbedPane.addTab("메뉴·카테고리", new MenuManageWin());
        // 탭 2: 재고·발주 관리
        tabbedPane.addTab("재고·발주", new StockManageWin(sm));
        // 탭 3: 직원·근태 관리
        tabbedPane.addTab("직원·근태", new StaffManageWin());
        // 탭 4: 매출 분석 통계
        tabbedPane.addTab("매출 분석", new SalesChartWin());

        add(tabbedPane, BorderLayout.CENTER);
    }
}