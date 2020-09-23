package Display;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class DisplayDelegate {
    private Display display;
    private ReentrantLock lock = new ReentrantLock();
    private ExecutorService execService;

    public void stop() {
        if (display != null)
            display.setStopped(true);
    }

    public void start(int n, int k, int[][] arr, Runnable callback) {
        execService = Executors.newSingleThreadExecutor();
        execService.execute(() -> {
            if (lock.tryLock() == false)
                return;
            display = new Display(n, k, arr);
            display.initAndDraw();
            if (callback != null)
                callback.run();
            lock.unlock();
        });
    }

    /**
     * 这个版本没有回调，是阻塞型的
     * @param n
     * @param k
     * @param arr
     */
    public void start(int n, int k, int[][] arr) {
        if (lock.tryLock() == false)
            return;
        display = new Display(n, k, arr);
        display.initAndDraw();
        lock.unlock();
    }
}
