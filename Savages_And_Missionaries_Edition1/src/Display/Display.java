package Display;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import Model.*;


import java.awt.*;
import java.util.ArrayList;

public class Display {
    private final static Color MISSIONARY_COLOR = Color.RED;
    private final static Color SAVAGE_COLOR = Color.BLUE;

    private final static int ROUND_TIME = 1000;//停顿时间
    private final static int INITIAL_SHIP_LENGTH = 40;//船的尺寸，后续根据K值改变
    private final static int INITIAL_PERSON_INTERVAL = 6;//船上人物间隔
    private final static int TWINKLING_TIMES = 50;//过河时闪烁次数（间接帧率）
    private final static int SHIP_LEFT_X = -130;
    private final static int SHIP_RIGHT_X = -SHIP_LEFT_X;
    private final static int COAST_LEFT_X = -150;
    private final static int COAST_RIGHT_X = -COAST_LEFT_X;
    private final static int COAST_UP_Y = 150;
    private final static int COAST_DOWN_Y = -COAST_UP_Y;

    ArrayList<int[]> rounds;
    int ms;
    int N, K;
    double ship_length, person_interval_on_ship, person_interval_on_coast;//根据K值计算后的船实际长度,人间距
    Color missionaryColor;
    Color savageColor;


    public Display(int N, int K, ArrayList<int[]> rounds) {
        if (rounds == null){
            System.out.println("N = " + N + ",K = " + K + ",此情况无解！");
            System.exit(0);
        }
        else {
            this.rounds = rounds;
        }
        this.ms = ROUND_TIME;
        this.N = N;
        this.K = K;
        this.missionaryColor = MISSIONARY_COLOR;
        this.savageColor = SAVAGE_COLOR;
        this.ship_length = INITIAL_SHIP_LENGTH + K * 10;
        this.person_interval_on_ship = (this.ship_length * 0.95 - 25 * K) / (K - 1);//根据人数调整间距
        this.person_interval_on_coast = (170.0 / rounds.get(0)[0]);
    }

    private void Drawing(int[] state, int round_num, boolean isDeparting) {
        if (state.length != 6) {
            throw new IllegalArgumentException();
        }

        //左岸状态初始化
        //state[0]为留在左岸的野人数量
        //state[1]为留在左岸的传教士数量
        for (int i = 0; i < state[0]; i++) {
//            StdDraw.circle(-180, -5 - span * i, 4);
            Savage sav = new Savage(-180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
            sav.draw();
        }
        for (int i = 0; i < state[1]; i++) {
//            StdDraw.filledRectangle(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
            Missionary mis = new Missionary(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
            mis.draw();
        }


        //右岸状态初始化
        //state[2]为留在左岸的野人数量
        //state[3]为留在左岸的传教士数量
        for (int i = 0; i < state[2]; i++) {
//            StdDraw.circle(180, -5 - span * i, 4);
            Savage sav = new Savage(180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
            sav.draw();
        }
        for (int i = 0; i < state[3]; i++) {
//            StdDraw.filledRectangle(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
            Missionary mis = new Missionary(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
            mis.draw();
        }

        //船状态初始化
        //船偶数轮开始停在左岸岸边,奇数轮开始停在右岸岸边
        Ship sh = new Ship(isDeparting? SHIP_LEFT_X : SHIP_RIGHT_X, 0, ship_length, this.K);
        sh.draw();

        //岸际线初始化
        StdDraw.setPenColor(Color.gray);
        StdDraw.line(COAST_LEFT_X, COAST_UP_Y, COAST_LEFT_X, COAST_DOWN_Y);//左岸际线
        StdDraw.line(COAST_RIGHT_X, COAST_UP_Y, COAST_RIGHT_X, COAST_DOWN_Y);//右岸际线


        StdDraw.show();
        StdDraw.pause(ms/2);
        StdDraw.clear();

        //过河状态（含左岸、右岸、船、人）
        //state[4]为上船的野人数量
        //state[5]为上船的传教士数量
//        if(state[0] != 0) {
//            span = 20.0 / state[0];
//        }
        for(int sailing_length = 0; sailing_length < TWINKLING_TIMES; sailing_length++) {
            //基础模型重建
            StdDraw.setPenColor(Color.black);
            StdDraw.text(-180, 180, "左岸");
            StdDraw.text(180, 180, "右岸");
            StdDraw.text(0, 180, "第" + round_num + "轮");
            StdDraw.setPenColor(Color.gray);
            StdDraw.line(COAST_LEFT_X, COAST_UP_Y, COAST_LEFT_X, COAST_DOWN_Y);//左岸际线
            StdDraw.line(COAST_RIGHT_X, COAST_UP_Y, COAST_RIGHT_X, COAST_DOWN_Y);//右岸际线

            //如果是左岸->右岸，左岸要减去上船人数
            //state[0] - state[4]代表左岸剩余野人数
            //state[2]表示右岸剩余野人数
            if(isDeparting) {
                //左岸
                for (int i = 0; i < state[0] - state[4]; i++) {
//                    StdDraw.filledRectangle(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
                    Savage sav = new Savage(-180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
                    sav.draw();

                }
                for (int i = 0; i < state[1] - state[5]; i++) {
//                    StdDraw.circle(-180, -5 - span * i, 4);
                    Missionary mis = new Missionary(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
                    mis.draw();
                }

                //右岸
                for (int i = 0; i < state[2]; i++) {
//                    StdDraw.circle(180, -5 - span * i, 4);
                    Savage sav = new Savage(180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
                    sav.draw();
                }
                for (int i = 0; i < state[3]; i++) {
//                    StdDraw.filledRectangle(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
                    Missionary mis = new Missionary(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
                    mis.draw();
                }
            }
            //如果是左岸<-右岸，则右岸需要减去上船人数
            //state[2] - state[4]为右岸剩余野人数
            //state[0]为左岸剩余野人数
            else {
                //左岸
                for (int i = 0; i < state[0]; i++) {
//                    StdDraw.circle(-180, -5 - span * i, 4);
                    Savage sav = new Savage(-180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
                    sav.draw();
                }
                for (int i = 0; i < state[1]; i++) {
//                    StdDraw.filledRectangle(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
                    Missionary mis = new Missionary(-180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
                    mis.draw();
                }

                //右岸
                for (int i = 0; i < state[2] - state[4]; i++) {
//                    StdDraw.circle(180, -5 - span * i, 4);
                    Savage sav = new Savage(180, -0.5 * person_interval_on_coast - person_interval_on_coast * i);
                    sav.draw();
                }
                for (int i = 0; i < state[3] - state[5]; i++) {
//                    StdDraw.filledRectangle(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
                    Missionary mis = new Missionary(180, 0.5 * person_interval_on_coast + person_interval_on_coast * i);
                    mis.draw();
                }
            }


            //过河动态
            //船+人
            if(isDeparting) {
//                StdDraw.setPenColor(savageColor);
//                for (int i = 0; i < state[4]; i++) {
//                    StdDraw.filledRectangle(-150 + sailing_length * 5, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
//                }
//                StdDraw.setPenColor(missionaryColor);
//                for (int i = 0; i < state[5]; i++) {
//                    StdDraw.circle(-150 + sailing_length * 5, -5 - span * i, 4);
//                }

                sh.setPos(SHIP_LEFT_X + sailing_length * 5, 0);
                for (int i = 0; i < state[4]; i++) {
                    Savage s = new Savage(0,0);
                    sh.addPerson(s, -0.5 * person_interval_on_ship - i * person_interval_on_ship);//野人上船
                }
                for (int i = 0; i < state[5]; i++) {
                    Missionary m = new Missionary(0,0);
                    sh.addPerson(m, 0.5 * person_interval_on_ship + i * person_interval_on_ship);//传教士上船
                }
                sh.draw();

            }
            else {
//                StdDraw.setPenColor(savageColor);
//                for (int i = 0; i < state[4]; i++) {
//                    StdDraw.filledRectangle(150 - sailing_length * 5, 0.5 * person_interval_on_coast + person_interval_on_coast * i, 4, 4);
//                }
//                StdDraw.setPenColor(missionaryColor);
//                for (int i = 0; i < state[5]; i++) {
//                    StdDraw.circle(150 - sailing_length * 5, -5 - span * i, 4);
//                }

                if(state[0] + state[1] == 0) {
                    StdDraw.text(0,0,"过河已完成！");
                    StdDraw.show();
                    StdDraw.pause(2 * ms);
                    return;
                }
                else {
                    sh.setPos(SHIP_RIGHT_X - sailing_length * 5, 0);
                    for (int i = 0; i < state[4]; i++) {
                        Savage s = new Savage(0, 0);
                        sh.addPerson(s, -0.5 * person_interval_on_ship - i * person_interval_on_ship);//野人上船
                    }
                    for (int i = 0; i < state[5]; i++) {
                        Missionary m = new Missionary(0, 0);
                        sh.addPerson(m, 0.5 * person_interval_on_ship + i * person_interval_on_ship);//传教士上船
                    }
                    sh.draw();
                }
            }
            StdDraw.show();
            StdDraw.pause(ms/24);
            StdDraw.clear();
        }

    }

    public void reset() {
        StdDraw.setScale(-200, 200);
        StdDraw.clear();
//        StdDraw.enableDoubleBuffering();
    }

    public void draw() {
//        StdDraw.picture(0, 0, "img/dog.jpg");
//        StdDraw.show();
//        StdDraw.pause(ms);
//        StdDraw.clear();

        boolean isDeparting = true;
        for (int round_num=0; round_num<rounds.size(); round_num++) {
            int []state = rounds.get(round_num);
            StdDraw.clear();
            StdDraw.setPenColor(Color.black);
            StdDraw.text(-180, 180, "左岸");
            StdDraw.text(180, 180, "右岸");
            StdDraw.text(0, 180, "第" + round_num + "轮");
            StdDraw.enableDoubleBuffering();
            StdDraw.show();

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
