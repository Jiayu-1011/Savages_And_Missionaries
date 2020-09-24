package Algorithm;

import java.util.ArrayList;

public enum ExecResult {
    SUCCESS, FAILURE, TIMEOUT, INTERRUPTED;

    public ArrayList<int[]> data;

    public ExecResult set(ArrayList<int[]> data) {
        this.data = data;
        return this;
    }
}
