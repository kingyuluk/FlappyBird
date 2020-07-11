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
 *
 */
public class Bird {
	public static final int IMG_COUNT = 8; // 图片数量
	public static final int STATE_COUNT = 4; // 状态数
	private BufferedImage[][] birdImgs; // 小鸟的图片数组对象
	private int x, y; // 小鸟的坐标

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

	private Rectangle birdRect; // 碰撞矩形
	public static final int RECT_DESCALE = 2; // 补偿碰撞矩形宽高的参数

	private GameTime timing; // 飞行时间

	// 在构造器中对资源初始化
	public Bird() {
		timing = GameTime.getInstance(); // 计时器

		// 读取小鸟图片资源
		birdImgs = new BufferedImage[STATE_COUNT][IMG_COUNT];
		for (int j = 0; j < STATE_COUNT; j++) {
			for (int i = 0; i < IMG_COUNT; i++) {
				birdImgs[j][i] = GameUtil.loadBUfferedImage(Constant.BIRDS_IMG_PATH[j][i]);
			}
		}

		// 初始化小鸟的坐标
		x = Constant.FRAME_WIDTH >> 2;
		y = Constant.FRAME_HEIGHT >> 1;

		int ImgWidth = birdImgs[state][0].getWidth();
		int ImgHeight = birdImgs[state][0].getHeight();

		// 初始化碰撞矩形
		int rectX = x - ImgWidth / 2;
		int rectY = y - ImgHeight / 2;
		int rectWidth = ImgWidth;
		int rectHeight = ImgHeight;
		birdRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, rectWidth - RECT_DESCALE * 3,
				rectHeight - RECT_DESCALE * 4); // 碰撞矩形的坐标与小鸟相同
	}

	// 绘制方法
	public void draw(Graphics g) {
		fly();
		int state_index = state > STATE_FALL ? STATE_FALL : state; // 图片资源索引
		// 小鸟中心点计算
		int halfImgWidth = birdImgs[state_index][0].getWidth() >> 1;
		int halfImgHeight = birdImgs[state_index][0].getHeight() >> 1;
		if (speed > 0)
			image = birdImgs[STATE_UP][0];
		g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null); // x坐标于窗口1/4处，y坐标位窗口中心

		if (state == STATE_DEAD) {
			drawGameover(g);
		} else if (state == STATE_FALL) {
		} else {
			drawScore(g);
		}
		// 绘制矩形
//		g.setColor(Color.black);
//		g.drawRect((int) birdRect.getX(), (int) birdRect.getY(), (int) birdRect.getWidth(), (int) birdRect.getHeight());
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
	private void fly() {
		// 翅膀状态，实现小鸟振翅飞行
		wingState++;
		image = birdImgs[state > STATE_FALL ? STATE_FALL : state][wingState / 10 % IMG_COUNT];

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
			birdRect.y = (int) (birdRect.y - h);
			// 控制坠落的边界，若y坐标 > 窗口的高度 - 地面的高度 - 小鸟图片的高度则死亡
			if (birdRect.y >= Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1)) {
				y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1);
				birdRect.y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1);
				birdFall();
			}

			break;

		case STATE_FALL:
			// 鸟死亡，自由落体
			speed -= g * T;
			h = speed * T - g * T * T / 2;
			y = (int) (y - h);
			birdRect.y = (int) (birdRect.y - h);

			// 控制坠落的边界，若y坐标 > 窗口的高度 - 地面的高度 - 小鸟图片的高度则死亡
			if (birdRect.y >= Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1)) {

				y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1);
				birdRect.y = Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (birdImgs[state][0].getHeight() >> 1);

				GameFrame.setGameState(GameFrame.STATE_OVER); // 改变游戏状态
				birdDead();
			}
			break;

		case STATE_DEAD:
			break;
		}

		// 控制上方边界
		if (birdRect.y < -1 * Constant.TOP_PIPE_LENGTHENING / 2) {
			birdRect.y = -1 * Constant.TOP_PIPE_LENGTHENING / 2;
			y = -1 * Constant.TOP_PIPE_LENGTHENING / 2;
		}

		// 控制下方边界
		if (birdRect.y > Constant.FRAME_HEIGHT - Constant.GROUND_HEIGHT - (image.getHeight() >> 1)) {
			birdFall();
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
		// 结束计时
		timing.endTiming();
	}

	// 小鸟死亡
	public void birdDead() {
		state = STATE_DEAD;
		// 加载游戏结束的资源
		if (overImg == null) {
			overImg = GameUtil.loadBUfferedImage(Constant.OVER_IMG_PATH);
			scoreImg = GameUtil.loadBUfferedImage(Constant.SCORE_IMG_PATH);
			againImg = GameUtil.loadBUfferedImage(Constant.AGAIN_IMG_PATH);
		}
	}

	public boolean isDead() {
		return state == STATE_FALL || state == STATE_DEAD;
	}

	// 开始计时的方法
	public void startTiming() {
		if (timing.isReadyTiming())
			timing.startTiming();
	}

	// 绘制实时分数
	private void drawScore(Graphics g) {
		g.setColor(Color.white);
		g.setFont(Constant.TIME_FONT);
		String str = Long.toString(timing.TimeToScore());
		int x = Constant.FRAME_WIDTH - GameUtil.getStringWidth(Constant.TIME_FONT, str) >> 1;
		g.drawString(str, x, Constant.FRAME_HEIGHT / 10);
	}

	private static final int SCORE_LOCATE = 5; // 位置补偿参数

	private int flash = 0; // 图片闪烁参数

	// 绘制游戏结束的显示
	private void drawGameover(Graphics g) {
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
		String str = Long.toString(timing.TimeToScore());
		x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
		y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
		g.drawString(str, x, y);

		// 绘制最高分数
		if (timing.getBestScore() > 0) {
			str = Long.toString(timing.getBestScore());
			x = (Constant.FRAME_WIDTH + scoreImg.getWidth() / 2 >> 1) - SCORE_LOCATE;// 位置补偿
			x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
			g.drawString(str, x, y);
		}
		// 绘制继续游戏
		final int COUNT = 30; // 控制闪烁间隔的参数
		if (flash++ > COUNT) {
			x = Constant.FRAME_WIDTH - againImg.getWidth() >> 1;
			y = Constant.FRAME_HEIGHT / 5 * 3;
			g.drawImage(againImg, x, y, null);
			if (flash == COUNT * 2)
				flash = 0;
		}
	}

	// 重置小鸟
	public void reset() {
		state = STATE_NORMAL; // 小鸟状态
		y = Constant.FRAME_HEIGHT >> 1; // 小鸟坐标
		speed = 0; // 小鸟速度

		int ImgHeight = birdImgs[state][0].getHeight();
		birdRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2; // 小鸟碰撞矩形坐标

		timing.reset(); // 计时器
		flash = 0;
	}

	// 获取小鸟的碰撞矩形
	public Rectangle getBirdRect() {
		return birdRect;
	}
}