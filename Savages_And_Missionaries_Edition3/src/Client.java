import Algorithm.ExecResult;
import Algorithm.SavagesAndMissionaries;
import Display.DisplayDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Client {

    private static Future<ExecResult> execResult;
    private static DisplayDelegate displayer = new DisplayDelegate();

    static void globalInit() {
        JFrame jf = new JFrame("野人与传教士");
        Dimension jfSize = new Dimension(480, 360);
        jf.setMinimumSize(jfSize);
        jf.setMaximumSize(jfSize);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
            1. 创建界面组件
         */
        // 创建说明的标签
        JLabel Descriptionlable = new JLabel(
                "<html>" +
                "程序说明：<br>" +
                "某条河流的岸边有N个传教士、N个野人和一条船，传教士们想用这条船把所有人都运到河的对岸去，但有以下条件限制：<br>" +
                "1. 传教士和野人都会划船，但船每次最多只能运K个人；<br>" +
                "2. 在任何时刻保证传教士安全。野人数目如果超过传教士，则传教士会被野人吃掉。<br>" +
                "假设野人会服从任何一种过河安排，请规划出一个确保传教士以及野人安全过河的计划。<br>" +
                "Timeout中请输入你所期望的完成时间上限。" +
                "</html>"
        );
        Descriptionlable.setSize(400, 250);
        Descriptionlable.setHorizontalAlignment(JLabel.CENTER);
        // 创建N的文本框
        final JTextField NHolder = new JTextField(8);
        NHolder.setFont(new Font(null, Font.PLAIN, 14));
        NHolder.setHorizontalAlignment(JTextField.CENTER);
        // 创建N的标签
        JLabel Nlabel = new JLabel("            N:            ");

        // 创建K的文本框
        final JTextField KHolder = new JTextField(8);
        KHolder.setFont(new Font(null, Font.PLAIN, 14));
        KHolder.setHorizontalAlignment(JTextField.CENTER);
        // 创建K的标签
        JLabel Klabel = new JLabel("            K:            ");

        // 创建超时设置的文本框
        final JTextField timeoutField = new JTextField(8);
        timeoutField.setFont(new Font(null, Font.PLAIN, 14));
        timeoutField.setHorizontalAlignment(JTextField.CENTER);
        // 创建超时的标签
        JLabel timeoutLabel = new JLabel("Timeout (in ms): ");

        // 创建提示信息
        final JLabel feedbackLbl = new JLabel(" ");
        feedbackLbl.setForeground(Color.red);

        // 创建开始按钮
        JButton startBtn = new JButton("Start");
        startBtn.setFont(new Font(null, Font.PLAIN, 14));
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startBtn.setEnabled(false);
                try {
                    int n = Integer.parseInt(NHolder.getText());
                    int k = Integer.parseInt(KHolder.getText());
                    int timeout = Integer.parseInt(timeoutField.getText());
                    //--Mark: 调试使用
                    System.out.println("n is " + n + ", k is " + k + ", timeout is " + timeout);

                    // 验证整数范围
                    if (n <= 0 || k <= 0 || timeout <= 0) {
                        feedbackLbl.setText("N and K must be integers which are greater than 0!");
                        return;
                    }

                    feedbackLbl.setText("Sloving...");

                    // 创建工人来执行计算
                    new SwingWorker() {
                        String infoString = "";

                        @Override
                        protected void done() {
                            startBtn.setEnabled(true);
                            feedbackLbl.setText(infoString);
                        }

                        @Override
                        protected Void doInBackground() throws Exception {
                            execResult = SavagesAndMissionaries.solve(n, k);
                            ExecResult res = null;
                            try {
                                res = execResult.get(timeout, TimeUnit.MILLISECONDS);
                            } catch (TimeoutException te) {
                                res = ExecResult.TIMEOUT;
                            } catch (Exception ie) {
                                res = ExecResult.INTERRUPTED;
                            }
                            // 处理结果
                            if (res == ExecResult.TIMEOUT)
                                infoString = "Timeout: Can't solve the case in given time.";
                            else if (res == ExecResult.FAILURE)
                                infoString = "Failure: Unsolvable case.";
                            else if (res == ExecResult.INTERRUPTED)
                                infoString = "Stopped.";
                            else {              // --> succeeded
                                SwingUtilities.invokeLater(() -> {
                                    feedbackLbl.setText("Sucess: New drawing process started.");
                                });
                                int[][] arr = new int[res.data.size()][6];
                                for (int i = 0; i < res.data.size(); ++i)
                                    arr[i] = res.data.get(i);
                                displayer.start(n, k, arr);
                                infoString = "Drawing process finished.";
                            }
                            return null;
                        }
                    }.execute();
                } catch (Exception parseException) {
                    // ??
                }
            }
        });

        // 创建停止按钮
        JButton stopBtn = new JButton("Stop");
        stopBtn.setFont(new Font(null, Font.PLAIN, 14));
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (execResult != null)
                    execResult.cancel(true);
                displayer.stop();
            }
        });

        /*
            2. 创建组件布局
         */

        // 创建垂直布局
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalGlue());

        // 创建说明的水平容器
        Box hBoxForDescription = Box.createHorizontalBox();
        hBoxForDescription.add(Descriptionlable);
        vBox.add(hBoxForDescription);

        vBox.add(Box.createVerticalStrut(16));

        // 创建N的水平容器
        Box hBoxForN = Box.createHorizontalBox();
        hBoxForN.add(Nlabel);
        hBoxForN.add(NHolder);
        vBox.add(hBoxForN);

        vBox.add(Box.createVerticalStrut(8));

        // 创建K的水平容器
        Box hBoxForK = Box.createHorizontalBox();
        hBoxForK.add(Klabel);
        hBoxForK.add(KHolder);
        vBox.add(hBoxForK);

        vBox.add(Box.createVerticalStrut(8));

        // 创建超时的水平容器
        Box hBoxForTimeout = Box.createHorizontalBox();
        hBoxForTimeout.add(timeoutLabel);
        hBoxForTimeout.add(timeoutField);
        vBox.add(hBoxForTimeout);

        vBox.add(Box.createVerticalGlue());

        // 包裹按钮
        Box hBox1 = Box.createHorizontalBox();
        hBox1.add(Box.createHorizontalGlue());
        hBox1.add(startBtn);
        hBox1.add(Box.createHorizontalStrut(16));
        hBox1.add(stopBtn);
        hBox1.add(Box.createHorizontalGlue());
        // 将按钮加入界面
        vBox.add(hBox1);

        vBox.add(Box.createVerticalGlue());

        // 包裹提示信息
        Box hBox2 = Box.createHorizontalBox();
        hBox2.add(Box.createHorizontalGlue());
        hBox2.add(feedbackLbl);
        hBox2.add(Box.createHorizontalGlue());
        // 将提示信息加入界面
        vBox.add(hBox2);

        vBox.add(Box.createVerticalGlue());

        // 重设组件尺寸
        Dimension textFieldSize = new Dimension(160, 24);
        Dimension descriptionSize = new Dimension(400, 250);
        Descriptionlable.setMaximumSize(descriptionSize);
        Descriptionlable.setMinimumSize(descriptionSize);
        NHolder.setMaximumSize(textFieldSize);
        NHolder.setMinimumSize(textFieldSize);
        KHolder.setMaximumSize(textFieldSize);
        KHolder.setMinimumSize(textFieldSize);
        timeoutField.setMaximumSize(textFieldSize);
        timeoutField.setMinimumSize(textFieldSize);

        jf.setContentPane(vBox);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

    public static void main(String[] args) {
        globalInit();
    }
}
