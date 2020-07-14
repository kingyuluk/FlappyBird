package com.bird.main;

import static com.bird.util.Constant.FRAME_HEIGHT;
import static com.bird.util.Constant.FRAME_WIDTH;
import static com.bird.util.Constant.FRAME_X;
import static com.bird.util.Constant.FRAME_Y;
import static com.bird.util.Constant.GAME_INTERVAL;
import static com.bird.util.Constant.GAME_TITLE;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;


/**
 * 主窗口类，游戏窗口和绘制的相关内容
 *
 * @author Kingyu
 */

public class GameFrame extends Frame implements Runnable {
    private static final long serialVersionUID = 1L; // 保持版本的兼容性

    private static int gameState; // 游戏状态
    public static final int STATE_READY = 0; // 游戏未开始
    public static final int STATE_START = 1; // 游戏开始
    public static final int STATE_OVER = 2; // 游戏结束

    private GameBackground background; // 游戏背景对象
    private GameForeground foreground; // 游戏前景对象
    private Bird bird; // 小鸟对象
    private GameElementLayer gameElement; // 游戏元素对象
    private GameReady ready; // 游戏未开始时对象

    // 在构造器中初始化
    public GameFrame() {
        initFrame(); // 初始化游戏窗口
        setVisible(true); // 窗口默认为不可见，设置为可见
        initGame(); // 初始化游戏对象
    }

    // 初始化游戏窗口
    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // 设置窗口大小
        setTitle(GAME_TITLE); // 设置窗口标题
        setLocation(FRAME_X, FRAME_Y); // 窗口初始位置
        setResizable(false); // 设置窗口大小不可变
        // 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // 结束程序
            }
        });
        addKeyListener(new BirdKeyListener()); // 添加按键监听
    }

    // 用于接收按键事件的对象的内部类
    class BirdKeyListener implements KeyListener {
        // 按键按下，根据游戏当前的状态调用不同的方法
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyChar();
            switch (gameState) {
                case STATE_READY:
                    if (keycode == KeyEvent.VK_SPACE) {
                        // 游戏启动界面时按下空格，小鸟振翅一次并开始受重力影响
                        bird.birdUp();
                        bird.birdDown();
                        setGameState(STATE_START); // 游戏状态改变
                    }
                    break;
                case STATE_START:
                    if (keycode == KeyEvent.VK_SPACE) {
                        //游戏过程中按下空格则振翅一次，并持续受重力影响
                        bird.birdUp();
                        bird.birdDown();
                    }
                    break;
                case STATE_OVER:
                    if (keycode == KeyEvent.VK_SPACE) {
                        //游戏结束时按下空格，重新开始游戏
                        resetGame();
                    }
                    break;
            }
        }

        // 重新开始游戏
        private void resetGame() {
            setGameState(STATE_READY);
            gameElement.reset();
            bird.reset();
        }

        // 按键松开，更改按键状态标志
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyChar();
            if (keycode == KeyEvent.VK_SPACE) {
                bird.keyReleased();
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    // 初始化游戏中的各个对象
    private void initGame() {
        background = new GameBackground();
        gameElement = new GameElementLayer();
        foreground = new GameForeground();
        ready = new GameReady();
        bird = new Bird();
        setGameState(STATE_READY);

        // 启动用于刷新窗口的线程
        new Thread(this).start();
    }

    // 项目中存在两个线程：系统线程，自定义的线程：调用repaint()。
    // 系统线程：屏幕内容的绘制，窗口事件的监听与处理
    // 两个线程会抢夺系统资源，可能会出现一次刷新周期所绘制的内容，并没有在一次刷新周期内完成
    // （双缓冲）单独定义一张图片，将需要绘制的内容绘制到这张图片，再一次性地将图片绘制到窗口
    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    /**
     * 绘制游戏内容 当repaint()方法被调用时，JVM会调用update()，参数g是系统提供的画笔，由系统进行实例化
     * 单独启动一个线程，不断地快速调用repaint()，让系统对整个窗口进行重绘
     */
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics(); // 获得图片画笔
        // 使用图片画笔将需要绘制的内容绘制到图片

        background.draw(bufG, bird); // 背景层
        foreground.draw(bufG, bird); // 前景层

        // 鸟
        if (gameState == STATE_READY) { // 游戏未开始
            ready.draw(bufG);
        } else { // 游戏结束
            gameElement.draw(bufG, bird); // 游戏元素层
        }
        bird.draw(bufG); // 鸟
        g.drawImage(bufImg, 0, 0, null); // 一次性将图片绘制到屏幕上
    }

    // TODO: 不建议在while循环中使用sleep
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            repaint(); // 通过调用repaint(),让JVM调用update()
            try {
                Thread.sleep(GAME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setGameState(int gameState) {
        GameFrame.gameState = gameState;
    }

}
