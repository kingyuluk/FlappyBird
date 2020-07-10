package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 小鸟类，小鸟的绘制与飞行逻辑都在此类
 * 
 * @author Kingyu
 *
 */
public class Bird {
	public static final int IMG_COUNT = 8; // 图片数量
	private BufferedImage[][] imgs; // 小鸟的图片数组对象
	private int x, y; // 小鸟的坐标

	private BufferedImage image;
	int wingState;

	// 鸟的状态
	private int state;
	public static final int STATE_NORMAL = 0;
	public static final int STATE_UP = 1;
	public static final int STATE_DOWN = 2;
	public static final int STATE_DEAD = 3;

	// 在构造器中对资源初始化
	public Bird() {
		imgs = new BufferedImage[3][IMG_COUNT];
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < IMG_COUNT; i++) {
				imgs[j][i] = GameUtil.loadBUfferedImage(Constant.BIRDS_IMG_PATH[j][i]);
			}
		}
		// 初始化小鸟的坐标
		x = Constant.FRAME_WIDTH >> 1;
		y = Constant.FRAME_HEIGHT >> 1;
	}

	// 绘制小鸟
	public void draw(Graphics g) {
		Fly();
		// 小鸟图片位置
		int halfImgWidth = imgs[state][0].getWidth(null) >> 1;
		int halfImgHeight = imgs[state][0].getHeight(null) >> 1;
		if (speed > 0)
			image = imgs[STATE_UP][0];
		g.drawImage(image, x / 2 - halfImgWidth, y - halfImgHeight, null);
	}

	public static final int SPEED_UP = 25; // 小鸟向上的速度
	public static final double g = 9.8; // 重力加速度
	public static final double T = 0.2; // 小鸟的下落函数执行的时间
	
	private double h; // 小鸟y轴的位移量
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
	private void Fly() {
		// 翅膀状态，实现小鸟振翅飞行
		wingState++;
		image = imgs[state][wingState / 10 % IMG_COUNT];

		switch (state) {
		case STATE_NORMAL:
			break;

		case STATE_UP:
			break;

		case STATE_DOWN:
			// 物理公式
			speed -= g * T;
			h = speed * T - g * T * T / 2;
			y = (int) (y - h);
			break;
		}
		// 撞到上边缘或下边缘
		if (y < ((imgs[state][0].getHeight(null) >> 1) + Constant.TOP_BAR_HEIGHT)
				|| (y > Constant.FRAME_HEIGHT - (imgs[state][0].getHeight(null) >> 1))) {
			state = STATE_DEAD;
			reset();
		}
	}

	// 鸟的状态
	public void BirdUp() {
		if (keyIsReleased()) { // 如果按键是第一次按下
			state = STATE_UP;
			speed = SPEED_UP;
			wingState = 0; // 重置翅膀状态
			keyPressed();
		}
	}

	public void BirdDown() {
		state = STATE_DOWN;
	}

	public void reset() {
		state = STATE_NORMAL;
		speed = 0;
		x = Constant.FRAME_WIDTH >> 1;
		y = Constant.FRAME_HEIGHT >> 1;

	}
}
