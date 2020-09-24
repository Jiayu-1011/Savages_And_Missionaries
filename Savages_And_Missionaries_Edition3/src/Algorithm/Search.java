package Algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Search {

    private int n;
    private int k;

    public Search(int n, int k) {
        this.n = n;
        this.k = k;
    }

    private boolean isSafe(State state) {  // 判断当前状态是否安全
        if ((state.getMissionaries() != 0) && (state.getMissionaries() != this.n))
            return state.getMissionaries() == state.getSavages();
        return true;
    }

    private boolean isLegal(State stateBefore, State stateAfter) {  // 判断当前传输动作是否合法
        if (stateBefore.isLeftSide())
            return ((stateAfter.getSavages() - stateBefore.getSavages()) + (stateAfter.getMissionaries() - stateBefore.getMissionaries()) < 0 &&
                    (stateBefore.getSavages() - stateAfter.getSavages()) + (stateBefore.getMissionaries() - stateAfter.getMissionaries()) <= this.k &&
                    (stateAfter.getSavages() >= 0) && (stateAfter.getMissionaries() >= 0) && isSafe(stateAfter));
        else
            return ((stateAfter.getSavages() - stateBefore.getSavages()) + (stateAfter.getMissionaries() - stateBefore.getMissionaries()) > 0 &&
                    (stateAfter.getSavages() - stateBefore.getSavages()) + (stateAfter.getMissionaries() - stateBefore.getMissionaries()) <= this.k &&
                    (stateAfter.getSavages() <= this.n) && (stateAfter.getMissionaries() <= this.n) && isSafe(stateAfter));
    }

    private int g(State s) {
        return s.getShips();
    }

    private int h(State s) {
        return s.getSavages() + s.getMissionaries() - 2 * (1 - (s.getShips() % 2));
    }
    
    private int f(State s) {
        return g(s) + h(s);
    }

    public State Search() {
        State startState = new State(0, this.n, this.n, null);
        if (this.k >= 2 * this.n) {
            return new State(1, 0, 0, startState);
        }

        Comparator<State> less = new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                return f(o1) - f(o2);
            }
        };
        PriorityQueue<State> openList = new PriorityQueue<>(less);
        ArrayList<State> closedList = new ArrayList<>();
        openList.add(startState);
        State targetState = new State(1, 0, 0, null);

        while (!openList.isEmpty()) {
            State s = openList.peek();
            openList.poll();
            closedList.add(s);
            int savagesOnAshore = s.isLeftSide() ? s.getSavages() : (this.n - s.getSavages());
            int missionariesOnAshore = s.isLeftSide() ? s.getMissionaries() : (this.n - s.getMissionaries());
            int savagesCanMove = (savagesOnAshore < this.k) ? savagesOnAshore : this.k;
            int missionariesCanMove = (missionariesOnAshore < this.k) ? missionariesOnAshore : this.k;
            for (int savagesMove = savagesCanMove; savagesMove >= 0; savagesMove--)
                for (int missionariesMove = missionariesCanMove; missionariesMove >= 0; missionariesMove--) {
                    State stateAfter = new State(s.getShips() + 1, s.getSavages() + savagesMove * (s.isLeftSide() ? -1 : 1),
                            s.getMissionaries() + missionariesMove * (s.isLeftSide() ? -1 : 1), s);
                    if (isLegal(s, stateAfter) && !closedList.contains(stateAfter) && !openList.contains(stateAfter)) {
                        openList.add(stateAfter);
                        if (stateAfter.equals(targetState))
                            return stateAfter;
                    }
                }
        }

        return null;
    }

}
