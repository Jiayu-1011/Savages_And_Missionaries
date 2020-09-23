import Algorithm.Search;
import Algorithm.State;
import Display.Display;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;


public class Client {
//    public static int ROUND_TIME = 1000;
    /*
      rounds[][]数组定义如下：
      对于rounds[i],六个元素分别对应该轮的
      {左岸的野人数量, 左岸的传教士数量, 右岸的野人数量, 右岸的传教士数量, 上船的野人数量, 上船的传教士数量}

      偶数轮表示原岸状态，奇数轮表示彼岸状态
     */
     /*
      假定N = 3,K = 2,正确的移动方法如下：
      初始：左岸:【野人3人，传教士3人】---右岸：【野人0人，传教士0人】
*第1步:将 2 个野人和 0 个传教士移动到右岸。---> {3, 3, 0, 0, 2, 0}
      更新：左岸:【野人1人，传教士3人】---右岸：【野人2人，传教士0人】
*第2步:将 1 个野人和 0 个传教士移动到左岸。<--- {1, 3, 2, 0, 1, 0}
      更新：左岸:【野人2人，传教士3人】---右岸：【野人1人，传教士0人】
*第3步:将 2 个野人和 0 个传教士移动到右岸。---> {2, 3, 1, 0, 2, 0}
      更新：左岸:【野人0人，传教士3人】---右岸：【野人3人，传教士0人】
*第4步:将 1 个野人和 0 个传教士移动到左岸。<--- {0, 3, 3, 0, 1, 0}
      更新：左岸:【野人1人，传教士3人】---右岸：【野人2人，传教士0人】
*第5步:将 0 个野人和 2 个传教士移动到右岸。---> {1, 3, 2, 0, 0, 2}
      更新：左岸:【野人1人，传教士1人】---右岸：【野人2人，传教士2人】
*第6步:将 1 个野人和 1 个传教士移动到左岸。<--- {1, 1, 2, 2, 1, 1}
      更新：左岸:【野人2人，传教士2人】---右岸：【野人1人，传教士1人】
*第7步:将 0 个野人和 2 个传教士移动到右岸。---> {2, 2, 1, 1, 0, 2}
      更新：左岸:【野人2人，传教士0人】---右岸：【野人1人，传教士3人】
*第8步:将 1 个野人和 0 个传教士移动到左岸。<--- {2, 0, 1, 3, 1, 0}
      更新：左岸:【野人3人，传教士0人】---右岸：【野人0人，传教士3人】
*第9步:将 2 个野人和 0 个传教士移动到右岸。---> {3, 0, 0, 3, 2, 0}
      更新：左岸:【野人1人，传教士0人】---右岸：【野人2人，传教士3人】
*第10步:将 0 个野人和 1 个传教士移动到左岸。<--- {1, 0, 2, 3, 0, 1}
      更新：左岸:【野人1人，传教士1人】---右岸：【野人2人，传教士2人】
*第11步:将 1 个野人和 1 个传教士移动到右岸。---> {1, 1, 2, 2, 1, 1}
      更新：左岸:【野人0人，传教士0人】---右岸：【野人3人，传教士3人】
      <-- {0, 0, 3, 3, 0, 0}
      */
    //对应的rounds数组如下

//    //N = 3, K = 2
//    public static int rounds1[][] = {
//            {3, 3, 0, 0, 2, 0},//-->
//            {1, 3, 2, 0, 1, 0},//<--
//            {2, 3, 1, 0, 2, 0},//-->
//            {0, 3, 3, 0, 1, 0},//<--
//            {1, 3, 2, 0, 0, 2},//-->
//            {1, 1, 2, 2, 1, 1},//<--
//            {2, 2, 1, 1, 0, 2},//-->
//            {2, 0, 1, 3, 1, 0},//<--
//            {3, 0, 0, 3, 2, 0},//-->
//            {1, 0, 2, 3, 0, 1},//<--
//            {1, 1, 2, 2, 1, 1},//-->
//            {0, 0, 3, 3, 0, 0}//<--
//    };
//
//    //N = 2,K = 2
//    public static int[][] rounds2 = {
//            {2, 2, 0, 0, 2, 0},
//            {0, 2, 2, 0, 1, 0},
//            {1, 2, 1, 0, 0, 2},
//            {1, 0, 1, 2, 0, 1},
//            {1, 1, 1, 1, 1, 1},
//            {0, 0, 2, 2, 0, 0}
//    };
//
//    //N = 6, K = 4
//    public static ArrayList<int[]> rounds3 = {
//            {6, 6, 0, 0, 4, 0},
//            {2, 6, 4, 0, 1, 0},
//            {3, 6, 3, 0, 3, 0},
//            {0, 6, 6, 0, 2, 0},
//            {2, 6, 4, 0, 0, 4},
//            {2, 2, 4, 4, 1, 1},
//            {3, 3, 3, 3, 1, 3},
//            {2, 0, 4, 6, 1, 0},
//            {3, 0, 3, 6, 3, 0},
//            {0, 0, 6, 6, 0, 0}
//    };

    public static ArrayList<int[]> savages_and_missionaries(int n, int k) {
        Search ass = new Search(n, k);
        State resultState = ass.Search();

        if (resultState == null)
            return null;

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

        return rounds;
    }

    public static void main(String[] args) {
        int N = 6;
        int K = 5;

        ArrayList<int[]> rounds = savages_and_missionaries(N, K);
        new Display(N, K, rounds).initAndDraw();
    }
}
