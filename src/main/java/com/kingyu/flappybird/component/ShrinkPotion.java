package com.kingyu.flappybird.component;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

/**
 * ��Сҩˮ�࣬��������Сҩˮ�ͻ�ʹ������ͼ�С������֮һ
 */
public class ShrinkPotion {
    int x; // ��Сҩˮ�ĺ�����
    int y; // ��Сҩˮ��������

    int width;
    int speed;
    private static BufferedImage img; // ��Сҩˮ��ͼƬ
    public static final int POTION_WIDTH = img.getWidth();
    public static final int POTION_HEIGHT = img.getHeight();
    private Rectangle potionRect; // ������ײ���ľ���

    // ���췽������ʼ����Сҩˮ��λ�ú�ͼƬ
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

    // ������Сҩˮ
    public void draw(Graphics g) {
        g.drawImage(img, x, y, null);
    }

    // ��ȡ��Сҩˮ�ľ��Σ�������ײ���
    public Rectangle getRect() {
        return potionRect;
    }

    // ��Сҩˮ���ƶ������Ը�����Ҫ����ض����ƶ��߼�
    public void move() {
        // ����򵥵������ƶ�������Ը�����Ϸ�����޸��ƶ��߼�
        int speed = Constant.GAME_SPEED;
        x -= speed;
        potionRect.setLocation(x, y);
    }


    // �ж���Сҩˮ�Ƿ���ȫ�����ڴ�����
    public boolean isInFrame() {
        return x + img.getWidth() > 0;
    }
}
