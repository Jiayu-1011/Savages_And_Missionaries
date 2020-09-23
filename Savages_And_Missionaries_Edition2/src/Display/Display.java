package Display;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import Model.*;

import java.awt.*;

public class Display extends Display_Initial{
    int[][] rounds;
    int ms;
    int N, K;
    double ship_length, person_interval_on_ship, person_interval_on_coast;//根据K值计算后的船实际长度,人间距
    Color missionaryColor;
    Color savageColor;

    /*
        加入与停止有关的信号
     */
    private boolean stopped = false;

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Display(int N, int K, int[][] rounds) {
        super(N, K, rounds);
        this.rounds = rounds;
        this.ms = ROUND_TIME;
        this.N = N;
        this.K = K;
        this.missionaryColor = MISSIONARY_COLOR;
        this.savageColor = SAVAGE_COLOR;
        this.ship_length = INITIAL_SHIP_LENGTH;
//        this.person_interval_on_ship = (this.ship_length * 0.95 - 25 * K) / (K - 1);//根据人数调整间距
        this.person_interval_on_coast = (170.0 / rounds[0][0]);
    }

    //Draw就完事了
    private void Drawing(int[] state, int round_num, boolean isDeparting) {
        if (state.length != 6) {
            throw new IllegalArgumentException();
        }
//        for(int i:state) System.out.print(i);
//        System.out.println();

        //每轮开始航行前初始化
        InitialStateBar(round_num);//状态栏初始化
        InitialBothCoast(state);//初始化两岸状态
        Ship sh = InitialShip(isDeparting);//初始化船只

        StdDraw.show();
        StdDraw.pause(ms/2);
        StdDraw.clear();

        //过河状态（含左岸、右岸、船、人）
        //state[4]为上船的野人数量
        //state[5]为上船的传教士数量
        for(int sailing_length = 0; !stopped && sailing_length < TWINKLING_TIMES; sailing_length++) {
            //基础模型重建
            InitialStateBar(round_num);

            if(isDeparting) {
                InitialBothCoast_Depart(state);
                //过河动态
                //船+人 Departing
                sh.setPos(SHIP_LEFT_X + sailing_length * 5, 0);
            }
            else {
                InitialBothCoast_Arrive(state);
                if(state[0] + state[1] == 0) {
                    StdDraw.text(0,0,"过河已完成！");
                    StdDraw.show();
                    StdDraw.pause(2 * ms);
                    return;
                }
                //Arriving
                else { sh.setPos(SHIP_RIGHT_X - sailing_length * 5, 0); }
            }


            //野人上船
            for (int i = 0; i < state[4]; i++) {
                Savage s = new Savage(0,0);
                sh.addPerson(s, -30);
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.text(sh.getX() - 10, sh.getY() + 2, "× " + state[4]);
            }
            //传教士上船
            for (int i = 0; i < state[5]; i++) {
                Missionary m = new Missionary(0,0);
                sh.addPerson(m, 15);
                StdDraw.setPenColor(Color.RED);
                StdDraw.text(sh.getX() + 30, sh.getY() + 2, "× " + state[5]);
            }
            sh.draw();

            StdDraw.show();
            StdDraw.pause(ms/24);
            StdDraw.clear();
        }

    }

    public void reset() {
        StdDraw.setScale(-200, 200);
        StdDraw.clear();
        StdDraw.enableDoubleBuffering();
    }

    public void draw() {
//        StdDraw.picture(0, 0, "img/dog.jpg");
//        StdDraw.show();
//        StdDraw.pause(ms);
//        StdDraw.clear();

        boolean isDeparting = true;
        for (int round_num=0; round_num<rounds.length && !stopped; round_num++) {
            int []state = rounds[round_num];
            StdDraw.clear();

            Drawing(state, round_num, isDeparting);
            isDeparting = !isDeparting;
        }
        StdOut.print("过河已完成！");
        StdOut.print("Entire process drawn.");
    }

    public void initAndDraw() {
        reset();
        draw();
    }
}
