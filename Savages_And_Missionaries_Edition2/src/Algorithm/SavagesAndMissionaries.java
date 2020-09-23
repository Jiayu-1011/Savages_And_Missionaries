package Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Future;

import static java.lang.Math.abs;

public class SavagesAndMissionaries {

    public static Future<ExecResult> solve(int n, int k) {
        return ExecResult.executor.submit(() -> {
            Search ass = new Search(n, k);
            State resultState = ass.Search();

            if (resultState == null)
                return ExecResult.FAILURE;

            ArrayList<int[]> rounds = new ArrayList<>();
            State currState = new State(resultState.getShips() + 1, resultState.getSavages(), resultState.getMissionaries(),
                    resultState);
            while (currState.getFather() != null) {
                int[] round = {currState.getFather().getSavages(), currState.getFather().getMissionaries(),
                        n - currState.getFather().getSavages(), n - currState.getFather().getMissionaries(),
                        abs(currState.getSavages() - currState.getFather().getSavages()),
                        abs(currState.getMissionaries() - currState.getFather().getMissionaries())};
                rounds.add(round);
                currState = currState.getFather();
            }
            Collections.reverse(rounds);

            return ExecResult.SUCCESS.set(rounds);
        });

//        // 返回结果
//        try {
//            Algorithm.ExecResult res = resultInFuture.get(timeout, TimeUnit.MILLISECONDS);
//            return res;
//        } catch (Exception e) {
//            return Algorithm.ExecResult.TIMEOUT;
//        }
    }

    public static void main(String[] args) {
        int n = 3;
        int k = 3;

//        ArrayList<int[]> rounds = solve(n, k).get().data;
//        if (rounds == null) {
//            System.out.println("No solution for the input.");
//        }
//        else {
//            for (int[] row : rounds) {
//                System.out.println(Arrays.toString(row));
//            }
//        }
    }

}
