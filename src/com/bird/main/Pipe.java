package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 管道类
 * 
 * @author Kingyu
 *
 */
public class Pipe {
	private static BufferedImage[] imgs; // 管道的图片，static保证图片只加载一次
	static {// 静态块，类加载的时候，初始化图片
		final int PIPE_IMAGE_COUNT = 3;
		imgs = new BufferedImage[PIPE_IMAGE_COUNT];
		for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
			imgs[i] = GameUtil.loadBUfferedImage(Constant.PIPE_IMG_PATH[i]);
		}
	}

	// 所有碰撞元素的宽高
	public static final int PIPE_WIDTH = imgs[0].getWidth();
	public static final int PIPE_HEIGHT = imgs[0].getHeight();
	public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
	public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

	private int x, y; // 管道的坐标
	private int height; // 管道的宽，高

	private int type; // 管道的类型
	public static final int TYPE_TOP_NORMAL = 0;
	public static final int TYPE_TOP_HARD = 1;
	public static final int TYPE_BOTTOM_NORMAL = 2;
	public static final int TYPE_BOTTOM_HARD = 3;
	public static final int TYPE_HOVER_NORMAL = 4;
	public static final int TYPE_HOVER_HARD = 5;

	public Pipe(int x, int y, int height, int type) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.type = type;
	}

	public void draw(Graphics g) {
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
	}

	// 绘制从上往下的普通管道
	private void drawTopNormal(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
		}
		// 绘制管道的顶部
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), height - PIPE_HEAD_HEIGHT, null); // 管道头部与管道主体的宽度不同，x坐标需要处理
	}

	// 绘制从下往上的普通管道
	private void drawBottomNormal(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT, null);
		}
		// 绘制管道的顶部
		g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), Constant.FRAME_HEIGHT - height, null);
	}

	// 绘制悬浮的普通管道
	private void drawHoverNormal(Graphics g) {
		// 拼接的个数
		int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制管道的上顶部
				g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), y, null);
		// 绘制管道的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
		}
		// 绘制管道的下底部
		int y = this.y + height - PIPE_HEAD_HEIGHT;
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - PIPE_WIDTH) >> 1), y, null);
	}
}
