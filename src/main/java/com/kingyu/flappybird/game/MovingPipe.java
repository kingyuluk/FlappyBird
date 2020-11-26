package com.kingyu.flappybird.game;

import java.awt.Graphics;

import com.kingyu.flappybird.util.Constant;

/**
 * 移动水管类，继承Pipe类
 * 
 * @author Kingyu
 *
 */

public class MovingPipe extends Pipe {

	private int dealtY; // 移动水管的坐标
	public static final int MAX_DEALY = 50; // 最大移动距离
	private int dir;
	public static final int DIR_UP = 0;
	public static final int DIR_DOWN = 1;

	// 构造器
	public MovingPipe() {
		super();
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

		dealtY = 0;
		dir = DIR_DOWN;
		if (type == TYPE_TOP_HARD) {
			dir = DIR_UP;
		}
	}

	// 绘制方法
	public void draw(Graphics g, Bird bird) {
		switch (type) {
		case TYPE_HOVER_HARD:
			drawHoverHard(g);
			break;
		case TYPE_TOP_HARD:
			drawTopHard(g);
			break;
		case TYPE_BOTTOM_HARD:
			drawBottomHard(g);
			break;

		}
		// 鸟死后水管停止移动
		if (bird.isDead()) {
			return;
		}
		pipeLogic();

		// 绘制碰撞矩形
//		g.setColor(Color.black);
//		g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());
	}

	// 绘制移动的悬浮水管
	private void drawHoverHard(Graphics g) {
		// 拼接的个数
		int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制水管的上顶部
		g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
		// 绘制水管的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
		}
		// 绘制水管的下底部
		int y = this.y + height - PIPE_HEAD_HEIGHT;
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
	}

	// 绘制从上往下的移动水管
	private void drawTopHard(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
		// 绘制水管的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT, null);
		}
		// 绘制水管的顶部
		g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
				height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + dealtY, null);
	}

	// 绘制从下往上的移动水管
	private void drawBottomHard(Graphics g) {
		// 拼接的个数
		int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
		// 绘制水管的主体
		for (int i = 0; i < count; i++) {
			g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + dealtY, null);
		}
		// 绘制水管的顶部
		g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height + dealtY, null);
	}

	/**
	 * 可动水管的运动逻辑
	 */
	private void pipeLogic() {
		//x坐标的运动逻辑与普通水管相同
		x -= speed;
		pipeRect.x -= speed;
		if (x < -1 * PIPE_HEAD_WIDTH) {// 水管完全离开了窗口
			visible = false;
		}

		//水管上下移动的逻辑
		if (dir == DIR_DOWN) {
			dealtY++;
			if (dealtY > MAX_DEALY) {
				dir = DIR_UP;
			}
		} else {
			dealtY--;
			if (dealtY <= 0) {
				dir = DIR_DOWN;
			}
		}
		pipeRect.y = this.y + dealtY;
	}
	
}
