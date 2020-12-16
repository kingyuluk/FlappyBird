package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 云朵类，实现云朵的绘制和运动逻辑
 *
 * @author Kingyu
 */
public class Cloud {

    private final int speed; // 速度
    private int x; // 坐标
    private final int y;

    private final BufferedImage img;

    private final int scaleImageWidth;
    private final int scaleImageHeight;

    // 构造器
    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = Constant.GAME_SPEED * 2; //云朵的速度
        // 云朵图片缩放的比例 1.0~2.0
        double scale = 1 + Math.random(); // Math.random()返回0.0~1.0的随机值
        // 缩放云朵图片
        scaleImageWidth = (int) (scale * img.getWidth());
        scaleImageHeight = (int) (scale * img.getWidth());
    }

    // 绘制方法
    public void draw(Graphics g, Bird bird) {
        int speed = this.speed;
        if (bird.isDead())
            speed = 1;
        x -= speed;
        g.drawImage(img, x, y, scaleImageWidth, scaleImageHeight, null);
    }

    /**
     * 判断云朵是否飞出屏幕
     *
     * @return 飞出则返回true，否则返回false
     */
    public boolean isOutFrame() {
        return x < -1 * scaleImageWidth;
    }

}
