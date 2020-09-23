package Display;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StoppableDisplay {
    Display display;

    private ExecutorService execService;

    public void stop() {
        display.setStopped(true);
    }

    public void start(int n, int k, int[][] arr) {
        execService = Executors.newSingleThreadExecutor();
        execService.execute(new Runnable() {
            @Override
            public void run() {
                display = new Display(n, k, arr);
                display.initAndDraw();
            }
        });
    }
}
