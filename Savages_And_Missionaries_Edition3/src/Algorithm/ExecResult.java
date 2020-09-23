package Algorithm;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ExecResult {
    SUCCESS, FAILURE, TIMEOUT, INTERRUPTED;

    public ArrayList<int[]> data;

    public ExecResult set(ArrayList<int[]> data) {
        this.data = data;
        return this;
    }
}
