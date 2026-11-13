package thread;
public class LoginLockTimer extends Thread {
    @Override
    public void run() {
        try { Thread.sleep(30000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}