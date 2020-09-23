package Algorithm;

/*
表示当前所处状态：
1. savages表示当前左岸野人个数
2. missionaries表示当前左岸传教士数目
3. ships表示船已经运行的趟数。并且当ships为偶数时，表示船在左岸；ships为奇数时表示船在右岸。
 */
public class State {

    private int ships;
    private int savages;
    private int missionaries;
    private State father;

    public State(int ship, int savages, int missionaries, State father) {
        this.ships = ship;
        this.savages = savages;
        this.missionaries = missionaries;
        this.father = father;
    }

    public int getShips() {
        return ships;
    }

    public void setShips(int ship) {
        this.ships = ship;
    }

    public int getSavages() {
        return savages;
    }

    public State getFather() {
        return father;
    }

    public void setSavages(int savages) {
        this.savages = savages;
    }

    public int getMissionaries() {
        return missionaries;
    }

    public void setMissionaries(int missionaries) {
        this.missionaries = missionaries;
    }

    public void setFather(State father) {
        this.father = father;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return this.isLeftSide() == state.isLeftSide() &&
                this.savages == state.savages &&
                this.missionaries == state.missionaries;
    }

    public boolean isLeftSide() {
        return ships % 2 == 0;
    }

}
