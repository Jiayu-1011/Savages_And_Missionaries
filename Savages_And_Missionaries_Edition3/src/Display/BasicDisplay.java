package Display;

import Model.Missionary;
import Model.Savage;
import Model.Ship;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class BasicDisplay {
    protected final static int NUM_UPPER_LIMIT = 5;
    protected final static Color MISSIONARY_COLOR = Color.RED;
    protected final static Color SAVAGE_COLOR = Color.BLUE;

    protected final static int ROUND_TIME = 1000;//停顿时间
    protected final static int INITIAL_SHIP_LENGTH = 80;//船的尺寸，后续根据K值改变
    protected final static int INITIAL_PERSON_INTERVAL = 20;//人物间隔
    protected final static int TWINKLING_TIMES = 50;//过河时闪烁次数（间接帧率）
    protected final static int SHIP_LEFT_X = -120;
    protected final static int SHIP_RIGHT_X = -SHIP_LEFT_X;
    protected final static int COAST_LEFT_X = -150;
    protected final static int COAST_RIGHT_X = -COAST_LEFT_X;
    protected final static int COAST_UP_Y = 150;
    protected final static int COAST_DOWN_Y = -COAST_UP_Y;
    protected final static int PERSON_LEFT_X = -190;
    protected final static int PERSON_RIGHT_X = -PERSON_LEFT_X - 35;
    protected final static double PER_NUM_INTERVAL = 0.02;

    int[][] rounds;
    int ms;
    int N, K;
    double ship_length, person_interval_on_ship, person_interval_on_coast;//根据K值计算后的船实际长度,人间距
    Color missionaryColor;
    Color savageColor;
    double left_savage_arrive_interval, left_missionary_arrive_interval, right_savage_arrive_interval, right_missionary_arrive_interval;
    double left_savage_depart_interval, left_missionary_depart_interval, right_savage_depart_interval, right_missionary_depart_interval;
    double savage_on_ship_interval, missionary_on_ship_interval;

    public BasicDisplay(int N,int K,int[][] rounds){
        this.rounds = rounds;
        this.ms = ROUND_TIME;
        this.N = N;
        this.K = K;
        this.missionaryColor = MISSIONARY_COLOR;
        this.savageColor = SAVAGE_COLOR;
        this.ship_length = INITIAL_SHIP_LENGTH;
//        this.person_interval_on_ship = (this.ship_length * 0.95 - 25 * K) / (K - 1);//根据人数调整间距
        if(rounds[0][0] < 5) {
            this.person_interval_on_coast = (170.0 / rounds[0][0]);
        } else {
            this.person_interval_on_coast = 170.0 / 5;
        }
        this.left_savage_arrive_interval = 0;
        this.left_missionary_arrive_interval = 0;
        this.right_savage_arrive_interval = 0;
        this.right_missionary_arrive_interval = 0;
        this.left_savage_depart_interval = 0;
        this.left_missionary_depart_interval = 0;
        this.right_savage_depart_interval = 0;
        this.right_missionary_depart_interval = 0;
        this.savage_on_ship_interval = 0;
        this.missionary_on_ship_interval = 0;
    }

    public void InitialOnCoastInterval(int[] state){
        this.left_savage_arrive_interval = 0;
        this.left_missionary_arrive_interval = 0;
        this.right_savage_arrive_interval = 0;
        this.right_missionary_arrive_interval = 0;
        this.left_savage_depart_interval = 0;
        this.left_missionary_depart_interval = 0;
        this.right_savage_depart_interval = 0;
        this.right_missionary_depart_interval = 0;
        this.savage_on_ship_interval = 0;
        this.missionary_on_ship_interval = 0;

        double left_savage_arrive_div = state[0];
        double left_missionary_arrive_div = state[1];
        double right_savage_arrive_div = state[2] - state[4];
        double right_missionary_arrive_div = state[3] - state[5];
        double left_savage_depart_div = state[0] - state[4];
        double left_missionary_depart_div = state[1] -state[5];
        double right_savage_depart_div = state[2];
        double right_missionary_depart_div = state[3];

        while(left_savage_arrive_div != 0){
            left_savage_arrive_div /= 10;
            this.left_savage_arrive_interval += PER_NUM_INTERVAL;
        }
        while(left_missionary_arrive_div != 0){
            left_missionary_arrive_div /= 10;
            this.left_missionary_arrive_interval += PER_NUM_INTERVAL;
        }
        while(right_savage_arrive_div != 0){
            right_savage_arrive_div /= 10;
            this.right_savage_arrive_interval += PER_NUM_INTERVAL;
        }
        while(right_missionary_arrive_div != 0){
            right_missionary_arrive_div /= 10;
            this.right_missionary_arrive_interval += PER_NUM_INTERVAL;
        }
        while(left_savage_depart_div != 0){
            left_savage_depart_div /= 10;
            this.left_savage_depart_interval += PER_NUM_INTERVAL;
        }
        while(left_missionary_depart_div != 0){
            left_missionary_depart_div /= 10;
            this.left_missionary_depart_interval += PER_NUM_INTERVAL;
        }
        while(right_savage_depart_div != 0){
            right_savage_depart_div /= 10;
            this.right_savage_depart_interval += PER_NUM_INTERVAL;
        }
        while(right_missionary_depart_div != 0){
            right_missionary_depart_div /= 10;
            this.right_missionary_depart_interval += PER_NUM_INTERVAL;
        }

    }

    public void InitialOnShipInterval(int[] state){
        //根据数字位数调整微距离
        this.savage_on_ship_interval = 0;
        this.missionary_on_ship_interval = 0;

        double savage_on_ship_div = state[4];
        double missionary_on_ship_div = state[5];

        while(savage_on_ship_div != 0){
            savage_on_ship_div /= 10;
            this.savage_on_ship_interval += PER_NUM_INTERVAL;
        }
        while(missionary_on_ship_div != 0){
            missionary_on_ship_div /= 10;
            this.missionary_on_ship_interval += PER_NUM_INTERVAL;
        }
    }


    /*----------------------------------------------------------------------------------------*/
    /*--------------------------------Initial部分 start----------------------------------------*/

    public void InitialStateBar(int round_num){
        StdDraw.setPenColor(Color.black);
        StdDraw.text(COAST_LEFT_X - 20, PERSON_RIGHT_X, "左岸");
        StdDraw.text(COAST_RIGHT_X + 20, PERSON_RIGHT_X, "右岸");
        StdDraw.line(COAST_LEFT_X, COAST_UP_Y, COAST_LEFT_X, COAST_DOWN_Y);//左岸际线
        StdDraw.line(COAST_RIGHT_X, COAST_UP_Y, COAST_RIGHT_X, COAST_DOWN_Y);//右岸际线
        StdDraw.text(0, PERSON_RIGHT_X, "第" + round_num + "轮");
        new Savage(-20, PERSON_RIGHT_X - 28).draw();
        StdDraw.text(5, PERSON_RIGHT_X - 25, ": 野人");
        new Missionary(-20, PERSON_RIGHT_X - 50).draw();
        StdDraw.text(10, PERSON_RIGHT_X - 45, ": 传教士");
    }


    /* ----------------Arriving部分 左岸<-右岸--------------------*/
    public void InitialLeftCoastSavage_Arrive_Mix(int[] state){
        //state[0]为留在左岸的野人数量
        new Savage(PERSON_LEFT_X, INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_LEFT_X + 15 + this.left_savage_arrive_interval, INITIAL_PERSON_INTERVAL, "× " + state[0]);
    }

    public void InitialLeftCoastSavage_Arrive_Separate(int[] state){
        for (int i = 0; i < state[0]; i++) {
            new Savage(PERSON_LEFT_X, 0.5 * person_interval_on_coast + person_interval_on_coast * i).draw();
        }
    }

    public void InitialLeftCoastMissionary_Arrive_Mix(int[] state){
        //state[1]为留在左岸的传教士数量
        new Missionary(PERSON_LEFT_X, -INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_LEFT_X + 15 + this.left_missionary_arrive_interval, -INITIAL_PERSON_INTERVAL, "× " + state[1]);
    }

    public void InitialLeftCoastMissionary_Arrive_Separate(int[] state){
        for (int i = 0; i < state[1]; i++) {
            new Missionary(PERSON_LEFT_X, -0.5 * person_interval_on_coast - person_interval_on_coast * i).draw();
        }
    }

    public void InitialRightCoastSavage_Arrive_Mix(int[] state){
        //state[2] - state[4]为右岸剩余野人数
        new Savage(PERSON_RIGHT_X, INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_RIGHT_X + 15 + this.right_savage_arrive_interval, INITIAL_PERSON_INTERVAL, "× " + (state[2] - state[4]));
    }

    public void InitialRightCoastSavage_Arrive_Separate(int[] state){
        for (int i = 0; i < state[2] - state[4]; i++) {
            new Savage(-PERSON_LEFT_X, 0.5 * person_interval_on_coast + person_interval_on_coast * i).draw();
        }
    }

    public void InitialRightCoastMissionary_Arrive_Mix(int[] state){
        new Missionary(PERSON_RIGHT_X, -INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_RIGHT_X + 15 + this.right_missionary_arrive_interval, -INITIAL_PERSON_INTERVAL, "× " + (state[3] - state[5]));
    }

    public void InitialRightCoastMissionary_Arrive_Separate(int[] state){
        for (int i = 0; i < state[3] - state[5]; i++) {
            new Missionary(-PERSON_LEFT_X, -0.5 * person_interval_on_coast - person_interval_on_coast * i).draw();
        }
    }


    /*------------------Departing部分 左岸->右岸------------------*/
    public void InitialLeftCoastSavage_Depart_Mix(int[] state){
        //state[0] - state[4]代表左岸剩余野人数
        new Savage(PERSON_LEFT_X, INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_LEFT_X + 15 + this.left_savage_depart_interval, INITIAL_PERSON_INTERVAL, "× " + (state[0] - state[4]));
    }

    public void InitialLeftCoastSavage_Depart_Separate(int[] state){
        for (int i = 0; i < state[0] - state[4]; i++) {
            new Savage(PERSON_LEFT_X, 0.5 * person_interval_on_coast + person_interval_on_coast * i).draw();
        }
    }

    public void InitialLeftCoastMissionary_Depart_Mix(int[] state){
        new Missionary(PERSON_LEFT_X, -INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_LEFT_X + 15 + this.left_missionary_depart_interval, -INITIAL_PERSON_INTERVAL, "× " + (state[1] - state[5]));
    }

    public void InitialLeftCoastMissionary_Depart_Separate(int[] state){
        for (int i = 0; i < state[1] - state[5]; i++) {
            new Missionary(PERSON_LEFT_X, -0.5 * person_interval_on_coast - person_interval_on_coast * i).draw();
        }
    }

    public void InitialRightCoastSavage_Depart_Mix(int[] state){
        new Savage(PERSON_RIGHT_X, INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_RIGHT_X + 15 + this.right_savage_depart_interval, INITIAL_PERSON_INTERVAL, "× " + state[2]);
    }

    public void InitialRightCoastSavage_Depart_Separate(int[] state){
        for (int i = 0; i < state[2]; i++) {
            new Savage(-PERSON_LEFT_X, 0.5 * person_interval_on_coast + person_interval_on_coast * i).draw();
        }
    }

    public void InitialRightCoastMissionary_Depart_Mix(int[] state){
        new Missionary(PERSON_RIGHT_X, -INITIAL_PERSON_INTERVAL).draw();
        StdDraw.text(PERSON_RIGHT_X + 15 + this.right_missionary_depart_interval, -INITIAL_PERSON_INTERVAL, "× " + state[3]);
    }

    public void InitialRightCoastMissionary_Depart_Separate(int[] state){
        for (int i = 0; i < state[3]; i++) {
            new Missionary(-PERSON_LEFT_X, -0.5 * person_interval_on_coast - person_interval_on_coast * i).draw();
        }
    }

    //上船前初始化两岸状态
    public void InitialBothCoast(int[] state) {
        if (state[0] < NUM_UPPER_LIMIT) {
            InitialLeftCoastSavage_Arrive_Separate(state);
        } else {
            InitialLeftCoastSavage_Arrive_Mix(state);
        }
        if (state[1] < NUM_UPPER_LIMIT) {
            InitialLeftCoastMissionary_Arrive_Separate(state);
        } else {
            InitialLeftCoastMissionary_Arrive_Mix(state);
        }
        if (state[2] < NUM_UPPER_LIMIT) {
            InitialRightCoastSavage_Depart_Separate(state);
        } else {
            InitialRightCoastSavage_Depart_Mix(state);
        }
        if (state[3] < NUM_UPPER_LIMIT) {
            InitialRightCoastMissionary_Depart_Separate(state);
        } else {
            InitialRightCoastMissionary_Depart_Mix(state);
        }
    }

    public void InitialBothCoast_Depart(int[] state){
        if(state[0] - state[4] < NUM_UPPER_LIMIT) {
            InitialLeftCoastSavage_Depart_Separate(state);
        } else {
            InitialLeftCoastSavage_Depart_Mix(state);
        }
        if(state[1] - state[5] < NUM_UPPER_LIMIT) {
            InitialLeftCoastMissionary_Depart_Separate(state);
        } else {
            InitialLeftCoastMissionary_Depart_Mix(state);
        }
        if(state[2] < NUM_UPPER_LIMIT) {
            InitialRightCoastSavage_Depart_Separate(state);
        } else {
            InitialRightCoastSavage_Depart_Mix(state);
        }
        if(state[3] < NUM_UPPER_LIMIT) {
            InitialRightCoastMissionary_Depart_Separate(state);
        } else {
            InitialRightCoastMissionary_Depart_Mix(state);
        }
    }

    public void InitialBothCoast_Arrive(int[] state){
        if(state[0] < NUM_UPPER_LIMIT) {
            InitialLeftCoastSavage_Arrive_Separate(state);
        } else {
            InitialLeftCoastSavage_Arrive_Mix(state);
        }
        if(state[1] < NUM_UPPER_LIMIT) {
            InitialLeftCoastMissionary_Arrive_Separate(state);
        } else {
            InitialLeftCoastMissionary_Arrive_Mix(state);
        }
        if(state[2] - state[4] < NUM_UPPER_LIMIT) {
            InitialRightCoastSavage_Arrive_Separate(state);
        } else {
            InitialRightCoastSavage_Arrive_Mix(state);
        }
        if(state[3] - state[5] < NUM_UPPER_LIMIT) {
            InitialRightCoastMissionary_Arrive_Separate(state);
        } else {
            InitialRightCoastMissionary_Arrive_Mix(state);
        }
    }

    public Ship InitialShip(boolean isDeparting){
        //船状态初始化
        //船偶数轮开始停在左岸岸边,奇数轮开始停在右岸岸边
        Ship sh = new Ship(isDeparting? SHIP_LEFT_X : SHIP_RIGHT_X, 0, ship_length, this.K);
        sh.draw();
        return sh;
    }

    public void InitialSavageOnShip(int[] state, Ship sh){
        if(state[4] != 0) {
            Savage s = new Savage(0, 0);
            sh.addPerson(s, -35);
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.text(sh.getX() - 20 + this.savage_on_ship_interval, sh.getY() + 2, "×" + state[4]);
        }
    }

    public void InitialMissionaryOnShip(int[] state, Ship sh){
        if(state[5] != 0) {
            Missionary m = new Missionary(0,0);
            sh.addPerson(m, 15);
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(sh.getX() + 30 + this.missionary_on_ship_interval, sh.getY() + 2, "×" + state[5]);
        }
    }

    /*--------------------------------Initial部分 end------------------------------------------*/
    /*----------------------------------------------------------------------------------------*/

}
