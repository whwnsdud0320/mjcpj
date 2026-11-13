package view;

import shared.*;

public class MakeWindow {
    public static void main(String[] args) {
        // 1. 공유 자원 객체 생성 (프로그램 전체가 공유)
        OrderBuffer orderBuffer = new OrderBuffer();
        OrderNumber orderNumber = new OrderNumber();
        StockManager stockManager = new StockManager();
        StampManager stampManager = new StampManager();
        CallBoard callBoard = new CallBoard();

        // 2. 키오스크 창, 로그인 창, 주문번호 호출 화면을 동시에 구동
        javax.swing.SwingUtilities.invokeLater(() -> {
            new KioskWin(orderBuffer, orderNumber, stockManager, stampManager).setVisible(true);
            new LoginWin(orderBuffer, orderNumber, stockManager, stampManager, callBoard).setVisible(true);
            new PickupBoardWin(callBoard).setVisible(true);
        });
    }
}