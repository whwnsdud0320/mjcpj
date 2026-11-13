package thread;
public class KioskTimer implements Runnable {
    private int sec = 60;
    private boolean run = true;
    public synchronized void reset() { sec = 60; }
    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(1000); sec--;
                if (sec <= 0) break;
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
    }
    public void stopTimer() { run = false; }
}