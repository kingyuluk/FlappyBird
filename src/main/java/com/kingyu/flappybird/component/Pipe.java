package com.kingyu.flappybird.component;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

/**
 * 水管类，实现水管的绘制与运动逻辑
 *
 * @author Kingyu
 */
public class Pipe {
    static BufferedImage[] imgs; // 水管的图片，static保证图片只加载一次

    static {// 静态代码块，类加载的时候，初始化图片
        final int PIPE_IMAGE_COUNT = 3;
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // 所有水管的宽高
    public static final int PIPE_WIDTH = imgs[0].getWidth();
    public static final int PIPE_HEIGHT = imgs[0].getHeight();
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

    int x, y; // 水管的坐标，相对于元素层
    int width, height; // 水管的宽，高

    boolean visible; // 水管可见状态，true为可见，false表示可归还至对象池
    // 水管的类型
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_HARD = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_HARD = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_HARD = 5;

    // 水管的速度
    int speed;

    Rectangle pipeRect; // 水管的碰撞矩形

    // 构造器
    public Pipe() {
        this.speed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;

        pipeRect = new Rectangle();
        pipeRect.width = PIPE_WIDTH;
    }

    /**
     * 设置水管参数
     *
     * @param x:x坐标
     * @param y：y坐标
     * @param height：水管高度
     * @param type：水管类型
     * @param visible：水管可见性
     */
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }

    /**
     * 设置碰撞矩形参数
     */
    public void setRectangle(int x, int y, int height) {
        pipeRect.x = x;
        pipeRect.y = y;
        pipeRect.height = height;
    }

    // 判断水管是否位于窗口
    public boolean isVisible() {
        return visible;
    }

    // 绘制方法
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawBottomNormal(g);
                break;
            case TYPE_HOVER_NORMAL:
                drawHoverNormal(g);
                break;
        }
//      //绘制碰撞矩形
//      g.setColor(Color.black);
//      g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());

        //鸟死后水管停止移动
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // 绘制从上往下的普通水管
    private void drawTopNormal(Graphics g) {
        // 拼接的个数
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
        // 绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
        }
        // 绘制水管的顶部
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT, null); // 水管头部与水管主体的宽度不同，x坐标需要处理
    }

    // 绘制从下往上的普通水管
    private void drawBottomNormal(Graphics g) {
        // 拼接的个数
        int count = (height - PIPE_HEAD_HEIGHT - Constant.GROUND_HEIGHT) / PIPE_HEIGHT + 1;
        // 绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,
                    null);
        }
        // 绘制水管的顶部
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height, null);
    }

    // 绘制悬浮的普通水管
    private void drawHoverNormal(Graphics g) {
        // 拼接的个数
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // 绘制水管的上顶部
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
        // 绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // 绘制水管的下底部
        int y = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
    }

    /**
     * 普通水管的运动逻辑
     */
    private void movement() {
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {// 水管完全离开了窗口
            visible = false;
        }
    }

    /**
     * 判断当前水管是否完全出现在窗口中
     *
     * @return 若完全出现则返回true，否则返回false
     */
    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }

    // 获取水管的x坐标
    public int getX() {
        return x;
    }

    // 获取水管的碰撞矩形
    public Rectangle getPipeRect() {
        return pipeRect;
    }

}
