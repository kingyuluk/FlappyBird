package com.kingyu.flappybird.component;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

/**
 * 缩小药水类，鸟触碰了缩小药水就会使鸟的体型减小到三分之一
 */
public class ShrinkPotion {
    int x; // 缩小药水的横坐标
    int y; // 缩小药水的纵坐标

    int width;
    int speed;
    private static BufferedImage img; // 缩小药水的图片
    public static final int POTION_WIDTH = img.getWidth();
    public static final int POTION_HEIGHT = img.getHeight();
    private Rectangle potionRect; // 用于碰撞检测的矩形

    // 构造方法，初始化缩小药水的位置和图片
    public ShrinkPotion() {
        this.speed = Constant.GAME_SPEED;
        this.width = POTION_WIDTH;
        potionRect = new Rectangle();
        potionRect.width = POTION_WIDTH;
    }

    public void setAttribute(int x, int y) {
        this.x = x;
        this.y = y;
        setRectangle(this.x, this.y, POTION_HEIGHT);
    }
    public void setRectangle(int x, int y, int height) {
        potionRect.x = x;
        potionRect.y = y;
        potionRect.height = height;
    }

    // 绘制缩小药水
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    // 获取缩小药水的矩形，用于碰撞检测
    public Rectangle getRect() {
        return potionRect;
    }

    // 缩小药水的移动，可以根据需要添加特定的移动逻辑
    public void move() {
        // 这里简单地向左移动，你可以根据游戏需求修改移动逻辑
        int speed = Constant.GAME_SPEED;
        x -= speed;
        potionRect.setLocation(x, y);
    }


    // 判断缩小药水是否完全出现在窗口中
    public boolean isInFrame() {
        return x + img.getWidth() > 0;
    }
}
