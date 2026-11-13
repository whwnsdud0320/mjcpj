package thread;
import shared.CallBoard;

public class CallDisplay extends Thread {
    private CallBoard board;
    public CallDisplay(CallBoard board) { this.board = board; }
    @Override
    public void run() {
        while (!isInterrupted()) {
            int no = board.getCallOrder();
            if (no != -1) { System.out.println("호출 번호 표시: " + no); }
        }
    }
}