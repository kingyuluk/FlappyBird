package com.bird.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;
import com.bird.util.MusicUtil;

/**
 * 小鸟类，小鸟的绘制与飞行逻辑都在此类
 *
 * @author Kingyu
 */
public class Bird {
    public static final int IMG_COUNT = 8; // 图片数量
    public static final int STATE_COUNT = 4; // 状态数
    private final BufferedImage[][] birdImages; // 小鸟的图片数组对象
    private final int x;
    private int y; // 小鸟的坐标

    int wingState; // 翅膀状态

    // 图片资源
    private BufferedImage image; // 实时的小鸟图片
    private BufferedImage scoreImg; // 计分牌
    private BufferedImage overImg; // 结束标志
    private BufferedImage againImg; // 继续标志

    // 小鸟的状态
    private int state;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_UP = 1;
    public static final int STATE_DOWN = 2;
    public static final int STATE_FALL = 3;
    public static final int STATE_DEAD = 4;

    private final Rectangle birdRect; // 碰撞矩形
    public static final int RECT_DESCALE = 2; // 补偿碰撞矩形宽高的参数

    private final GameScore countScore; // 计分器

    // 在构造器中对资源初始化
    public Bird() {
        countScore = GameScore.getInstance(); // 计分器

        // 读取小鸟图片资源
        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for (int j = 0; j < STATE_COUNT; j++) {
            for (int i = 0; i < IMG_COUNT; i++) {
                birdImages[j][i] = GameUtil.loadBufferedImage(Constant.BIRDS_IMG_PATH[j][i]);
            }
        }

        // 初始化小鸟的坐标
        x = Constant.FRAME_WIDTH >> 2;
        y = Constant.FRAME_HEIGHT >> 1;

        int ImgWidth = birdImages[state][0].getWidth();
        int ImgHeight = birdImages[state][0].getHeight();

        // 初始化碰撞矩形
        int rectX = x - ImgWidth / 2;
        int rectY = y - ImgHeight / 2;
        birdRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, ImgWidth - RECT_DESCALE * 3,
                ImgHeight - RECT_DESCALE * 4); // 碰撞矩形的坐标与小鸟相同
    }

    // 绘制方法
    public void draw(Graphics g) {
        fly();
        int state_index = Math.min(state, STATE_FALL); // 图片资源索引
        // 小鸟中心点计算
        int halfImgWidth = birdImages[state_index][0].getWidth() >> 1;
        int halfImgHeight = birdImages[state_index][0].getHeight() >> 1;
        if (speed > 0)
            image = birdImages[STATE_UP][0];
        g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null); // x坐标于窗口1/4处，y坐标位窗口中心

        if (state == STATE_DEAD)
            drawGameOver(g);
        else if (state != STATE_FALL)
            drawScore(g);
        // 绘制矩形
//      g.setColor(Color.black);
//      g.drawRect((int) birdRect.getX(), (int) birdRect.getY(), (int) birdRect.getWidth(), (int) birdRect.getHeight());
    }

    public static final int SPEED_UP = 32; // 小鸟向上的速度
    public static final double g = 9.8; // 重力加速度
    public static final double T = 0.2; // 小鸟的下落函数执行的时间

    private double speed = 0; // 小鸟的初速度

    private boolean keyFlag = true; // 按键状态，true为已释放，使当按住按键时不会重复调用方法

    public void keyPressed() {
        keyFlag = false;
    }

    public void keyReleased() {
        keyFlag = true;
    }

    public boolean keyIsReleased() {
        return keyFlag;
    }

    // 小鸟的飞行逻辑
    private void fly() {
        // 翅膀状态，实现小鸟振翅飞行
        wingState++;
        image = birdImages[Math.min(state, STATE_FALL)][wingState / 10 % IMG_COUNT];

        // 下方边界: 窗口的高度 - 地面的高度 - 小鸟图片的高度
        final int bottomBoundary = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImages[0][0].getHeight() >> 1);
        final int topBoundary = -50;

        switch (state) {
            case STATE_DOWN:
                // 自由落体
                speed -= g * T;
                double h = speed * T - g * T * T / 2;
                y = Math.min((int) (y - h), bottomBoundary);
                birdRect.y = Math.min((int) (birdRect.y - h), bottomBoundary);
                if (birdRect.y == bottomBoundary) {
                    MusicUtil.playCrash();
                    birdDead();
                }
                break;

            case STATE_FALL:
                // 自由落体
                speed -= g * T;
                h = speed * T - g * T * T / 2;
                y = Math.min((int) (y - h), bottomBoundary);
                birdRect.y = Math.min((int) (birdRect.y - h), bottomBoundary);
                if (birdRect.y == bottomBoundary)
                    birdDead();
                break;

            case STATE_DEAD:
                GameFrame.setGameState(GameFrame.STATE_OVER);
                break;

            case STATE_NORMAL:
            case STATE_UP:
                break;
        }

        // 控制上方边界
        if (birdRect.y < topBoundary) {
            birdRect.y = topBoundary;
            y = topBoundary;
        }

    }

    // 小鸟振翅
    public void birdUp() {
        if (keyIsReleased()) { // 如果按键已释放
            if (state == STATE_DEAD || state == STATE_UP || state == STATE_FALL)
                return; // 小鸟死亡或坠落时返回
            MusicUtil.playFly(); // 播放音效
            state = STATE_UP;
            speed = SPEED_UP; // 每次振翅将速度改为上升速度
            wingState = 0; // 重置翅膀状态
            keyPressed();
        }
    }

    // 小鸟下降
    public void birdDown() {
        if (state == STATE_DEAD || state == STATE_FALL)
            return; // 小鸟死亡或坠落时返回
        state = STATE_DOWN;
    }

    // 小鸟坠落（已死）
    public void birdFall() {
        state = STATE_FALL;
        MusicUtil.playCrash(); // 播放音效
        speed = 0;  // 速度置0，防止小鸟继续上升与水管重叠
        // 死后画面静止片刻
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 小鸟死亡
    public void birdDead() {
        state = STATE_DEAD;
        // 加载游戏结束的资源
        if (overImg == null) {
            overImg = GameUtil.loadBufferedImage(Constant.OVER_IMG_PATH);
            scoreImg = GameUtil.loadBufferedImage(Constant.SCORE_IMG_PATH);
            againImg = GameUtil.loadBufferedImage(Constant.AGAIN_IMG_PATH);
        }
        countScore.isSaveScore(); // 判断是否保存纪录
    }

    // 判断小鸟是否死亡
    public boolean isDead() {
        return state == STATE_FALL || state == STATE_DEAD;
    }

    // 绘制实时分数
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(countScore.getScore());
        int x = Constant.FRAME_WIDTH - GameUtil.getStringWidth(Constant.CURRENT_SCORE_FONT, str) >> 1;
        g.drawString(str, x, Constant.FRAME_HEIGHT / 10);
    }

    private static final int SCORE_LOCATE = 5; // 位置补偿参数

    private int flash = 0; // 图片闪烁参数

    // 绘制游戏结束的显示
    private void drawGameOver(Graphics g) {
        // 绘制结束标志
        int x = Constant.FRAME_WIDTH - overImg.getWidth() >> 1;
        int y = Constant.FRAME_HEIGHT / 4;
        g.drawImage(overImg, x, y, null);

        // 绘制计分牌
        x = Constant.FRAME_WIDTH - scoreImg.getWidth() >> 1;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg, x, y, null);

        // 绘制本局的分数
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth() / 2 >> 1) + SCORE_LOCATE;// 位置补偿
        y += scoreImg.getHeight() >> 1;
        String str = Long.toString(countScore.getScore());
        x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
        y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
        g.drawString(str, x, y);

        // 绘制最高分数
        if (countScore.getBestScore() > 0) {
            str = Long.toString(countScore.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth() / 2 >> 1) - SCORE_LOCATE;// 位置补偿
            x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
            g.drawString(str, x, y);
        }

        // 绘制继续游戏，使图像闪烁
        final int COUNT = 30; // 闪烁周期
        if (flash++ > COUNT)
            GameUtil.drawImage(againImg,Constant.FRAME_WIDTH - againImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
        if (flash == COUNT * 2) // 重置闪烁参数
            flash = 0;
    }

    // 重置小鸟
    public void reset() {
        state = STATE_NORMAL; // 小鸟状态
        y = Constant.FRAME_HEIGHT >> 1; // 小鸟坐标
        speed = 0; // 小鸟速度

        int ImgHeight = birdImages[state][0].getHeight();
        birdRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2; // 小鸟碰撞矩形坐标

        countScore.reset(); // 重置计分器
        flash = 0;
    }

    // 获取小鸟的碰撞矩形
    public Rectangle getBirdRect() {
        return birdRect;
    }
}
