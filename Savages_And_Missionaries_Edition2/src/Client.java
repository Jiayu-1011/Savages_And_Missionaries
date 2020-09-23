import Algorithm.ExecResult;
import Algorithm.SavagesAndMissionaries;
import Display.StoppableDisplay;
import edu.princeton.cs.algs4.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class Client {

    private static Future<ExecResult> execResult;
    private static ExecutorService startBtnExecServ = Executors.newSingleThreadExecutor();
    private static StoppableDisplay displayer = new StoppableDisplay();

    static void globalInit() {
        JFrame jf = new JFrame("野人与传教士");
        Dimension jfSize = new Dimension(360, 240);
        jf.setMinimumSize(jfSize);
        jf.setMaximumSize(jfSize);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 创建垂直布局
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalGlue());

        // 创建N的文本框
        Dimension textFieldSize = new Dimension(160, 24);
        final JTextField NHolder = new JTextField(8);
        NHolder.setFont(new Font(null, Font.PLAIN, 14));
        NHolder.setMaximumSize(textFieldSize);
        NHolder.setMinimumSize(textFieldSize);
        NHolder.setHorizontalAlignment(JTextField.CENTER);
        // 创建N的标签
        JLabel Nlabel = new JLabel("N: ");
        // 创建N的水平容器
        Box hBoxForN = Box.createHorizontalBox();
        hBoxForN.add(Box.createHorizontalStrut(137));
        hBoxForN.add(Nlabel);
        hBoxForN.add(NHolder);
        hBoxForN.add(Box.createHorizontalStrut(46));
        hBoxForN.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        vBox.add(hBoxForN);

//        vBox.add(Box.createVerticalStrut(4));

        // 创建K的文本框
        final JTextField KHolder = new JTextField(8);
        KHolder.setFont(new Font(null, Font.PLAIN, 14));
        KHolder.setMaximumSize(textFieldSize);
        KHolder.setMinimumSize(textFieldSize);
        KHolder.setHorizontalAlignment(JTextField.CENTER);
        // 创建K的标签
        JLabel Klabel = new JLabel("K: ");
        // 创建K的水平容器
        Box hBoxForK = Box.createHorizontalBox();
        hBoxForK.add(Box.createHorizontalStrut(138));
        hBoxForK.add(Klabel);
        hBoxForK.add(KHolder);
        hBoxForK.add(Box.createHorizontalStrut(46));
        vBox.add(hBoxForK);

        vBox.add(Box.createVerticalStrut(4));

        // 创建超时设置的文本框
        final JTextField timeoutField = new JTextField(8);
        timeoutField.setFont(new Font(null, Font.PLAIN, 14));
        timeoutField.setMaximumSize(textFieldSize);
        timeoutField.setMinimumSize(textFieldSize);
        timeoutField.setHorizontalAlignment(JTextField.CENTER);
        vBox.add(timeoutField);
        // 创建超时的标签
        JLabel timeoutLabel = new JLabel("Timeout (in ms): ");
        // 创建超时的水平容器
        Box hBoxForTimeout = Box.createHorizontalBox();
        hBoxForTimeout.add(timeoutLabel);
        hBoxForTimeout.add(timeoutField);
        vBox.add(hBoxForTimeout);

        vBox.add(Box.createVerticalGlue());

        // 创建提示信息
        final JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setForeground(Color.red);

        // 创建开始按钮
        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font(null, Font.PLAIN, 14));
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.parseInt(NHolder.getText());
                    int k = Integer.parseInt(KHolder.getText());
                    int timeout = Integer.parseInt(timeoutField.getText());
                    if (n <= 0 || k <= 0 || timeout <= 0) {
                        throw new IllegalArgumentException("All inputs must be numbers which are greater than 0!");
                    }
                    feedbackLbl.setText("Sloving...");
                    // 开启新线程执行
                    startBtnExecServ.execute(new Runnable() {
                        @Override
                        public void run() {
                            startBtn.setEnabled(false);
                            // 执行计算
                            execResult = SavagesAndMissionaries.solve(n, k);
                            ExecResult res = null;
                            try {
                                res = execResult.get(timeout, TimeUnit.MILLISECONDS);
                            } catch (TimeoutException te) {
                                res = ExecResult.TIMEOUT;
                            } catch (Exception e) {
                                res = ExecResult.INTERRUPTED;
                                feedbackLbl.setText("Stopped.");
                                startBtn.setEnabled(true);
                            }

                            switch (res) {
                                case TIMEOUT:
                                    feedbackLbl.setText("Timeout: Can't solve the case in given time.");;
                                    break;
                                case FAILURE:
                                    feedbackLbl.setText("Failure: Unsolvable case.");
                                    break;
                                case SUCCESS:
                                    feedbackLbl.setText("Sucess: New drawing process started.");
                                    /*
                                        启动绘图
                                     */
                                    int[][] arr = new int[res.data.size()][6];
                                    for (int i = 0; i < res.data.size(); ++i) {
                                        arr[i] = res.data.get(i);
                                    }
                                    displayer.start(n, k, arr);
                                    break;
                            }
                            startBtn.setEnabled(true);
                        }
                    });
                } catch (Exception exception) {
                    feedbackLbl.setText("N and K must be integers which are greater than 0!");
                }
            }
        });

        // 创建stop按钮
        JButton stopBtn = new JButton("Stop");
        stopBtn.setFont(new Font(null, Font.PLAIN, 14));
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execResult.cancel(true);
                /*
                    停止绘图
                 */
                displayer.stop();
            }
        });

        // 创建横向容器来包裹按钮
        Box hBox1 = Box.createHorizontalBox();
        hBox1.add(Box.createHorizontalGlue());
        hBox1.add(startBtn);
        hBox1.add(Box.createHorizontalStrut(16));
        hBox1.add(stopBtn);
        hBox1.add(Box.createHorizontalGlue());

        vBox.add(hBox1);
        vBox.add(Box.createVerticalGlue());

        // 创建横向容器来包裹提示信息
        Box hBox2 = Box.createHorizontalBox();
        hBox2.add(Box.createHorizontalGlue());
        hBox2.add(feedbackLbl);
        hBox2.add(Box.createHorizontalGlue());
        vBox.add(hBox2);

        vBox.add(Box.createVerticalGlue());

        jf.setContentPane(vBox);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        globalInit();
    }
}
