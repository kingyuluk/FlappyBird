package com.bird.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	public static final int STATE_COUNT = 4; // 状态数
	private BufferedImage[][] imgs; // 小鸟的图片数组对象
	private int x, y; // 小鸟的坐标

	private BufferedImage image;
	int wingState;

	// 鸟的状态
	private int state;
	public static final int STATE_NORMAL = 0;
	public static final int STATE_UP = 1;
	public static final int STATE_DOWN = 2;
	public static final int STATE_FALL = 3;
	public static final int STATE_DEAD = 4;

	private Rectangle birdRect; // 碰撞矩形
	public static final int RECT_DESCALE = 2; // 补偿碰撞矩形宽高的参数
	
	private GameTime timing; //飞行时间

	// 在构造器中对资源初始化
	public Bird() {
		timing = new GameTime();
		imgs = new BufferedImage[STATE_COUNT][IMG_COUNT];
		for (int j = 0; j < STATE_COUNT; j++) {
			for (int i = 0; i < IMG_COUNT; i++) {
				imgs[j][i] = GameUtil.loadBUfferedImage(Constant.BIRDS_IMG_PATH[j][i]);
			}
		}
		// 初始化小鸟的坐标
		x = Constant.FRAME_WIDTH >> 1;
		y = Constant.FRAME_HEIGHT >> 1;

		int ImgWidth = imgs[state][0].getWidth();
		int ImgHeight = imgs[state][0].getHeight();

		// 初始化碰撞矩形的宽高
		int rectX = x / 2 - ImgWidth / 2;
		int rectY = y - ImgHeight / 2;
		int rectWidth = ImgWidth;
		int rectHeight = ImgHeight;

		birdRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, rectWidth - RECT_DESCALE * 3,
				rectHeight - RECT_DESCALE * 4); // 碰撞矩形的坐标与小鸟相同
	}

	public Rectangle getBirdRect() {
		return birdRect;
	}
	
	// 绘制小鸟
	public void draw(Graphics g) {
		Fly();
		
		int state_index = state > STATE_FALL ? STATE_FALL : state; //图片资源索引
		// 小鸟中心点计算
		int halfImgWidth = imgs[state_index][0].getWidth() >> 1;
		int halfImgHeight = imgs[state_index][0].getHeight() >> 1;
		if (speed > 0)
			image = imgs[STATE_UP][0];
		g.drawImage(image, x / 2 - halfImgWidth, y - halfImgHeight, null); // x坐标于窗口1/4处，y坐标位窗口中心

		// 绘制矩形
		g.setColor(Color.black);
		g.drawRect((int) birdRect.getX(), (int) birdRect.getY(), (int) birdRect.getWidth(), (int) birdRect.getHeight());
		
		//飞行时间
		g.drawString(Long.toString(timing.getTime()), 30, 50);
	}

	public static final int SPEED_UP = 32; // 小鸟向上的速度
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
		image = imgs[state > STATE_FALL ? STATE_FALL : state][wingState / 10 % IMG_COUNT];

		switch (state) {
		case STATE_NORMAL:
			break;

		case STATE_UP:
			//控制上边界
			if (y < ((imgs[state][0].getHeight(null) >> 1) + Constant.TOP_BAR_HEIGHT))
					y = (imgs[state][0].getHeight(null) >> 1) + Constant.TOP_BAR_HEIGHT;
			break;

		case STATE_DOWN:
			// 物理公式
			speed -= g * T;
			h = speed * T - g * T * T / 2;
			y = (int) (y - h);
			birdRect.y = (int) (birdRect.y - h);
			//控制边界，死亡条件
			if(y > Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1)) {
				y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1);
				birdRect.y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1);
				BirdFall();
			}
			break;
			
		case STATE_FALL:
			//鸟死亡，自由落体
			speed -= g * T;
			h = speed * T - g * T * T / 2;
			y = (int) (y - h);
			birdRect.y = (int) (birdRect.y - h);

			//控制坠落的边界
			if(y > Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1)) {
				y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1);
				birdRect.y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (imgs[state][0].getHeight()>>1);
				BirdDead();
		
			}
			break;
			
		case STATE_DEAD:
			break;
		}
		
		
		// 撞到上边缘或下边缘死亡
		if (y < ((image.getHeight() >> 1) + Constant.TOP_BAR_HEIGHT)
				|| (y > Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (image.getHeight()>>1))) {
			BirdFall();
		}
	}

	// 鸟的状态
	public void BirdUp() {	
		if (keyIsReleased()) { // 如果按键已释放
			if(state == STATE_DEAD || state == STATE_FALL ||state == STATE_UP)
				return;
			//开始计时
			if(timing.isReadyTiming()) {
				timing.startTiming();
			}
			state = STATE_UP;
			speed = SPEED_UP;
			wingState = 0; // 重置翅膀状态
			keyPressed();
		}
	}

	public void BirdDown() {
		if(state == STATE_DEAD || state == STATE_FALL)
			return;
		state = STATE_DOWN;
	}
	
	public void BirdFall() {
		state = STATE_FALL;
	}
	
	public void BirdDead() {
		state = STATE_DEAD;
		//结束计时
		timing.endTiming();
	}
	
	public boolean isDead() {
		return state == STATE_FALL || state == STATE_DEAD;
	}

	public void reset() {
		state = STATE_NORMAL;
		speed = 0;
		x = Constant.FRAME_WIDTH >> 1;
		y = Constant.FRAME_HEIGHT >> 1;

		int ImgHeight = imgs[state][0].getHeight();
		birdRect.y = this.y - ImgHeight / 2 + RECT_DESCALE * 2;
	}
}
