package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;

/**
 * 云朵类
 * 
 * @author Kingyu
 *
 */
public class Cloud {

	private int dir; // 方向
	public static final int DIR_NONE = 0;
	public static final int DIR_LEFT = 1;
	public static final int DIR_RIGHT = 2;

	private int speed; // 速度
	private int x, y; // 坐标

	private BufferedImage img;

	// 云朵图片缩放的比例 1.0~2.0
	private double scale;
	private int scaleImageWidth;
	private int scaleImageHeight;

	// 构造器
	public Cloud(BufferedImage img, int dir, int x, int y) {
		super();
		this.img = img;
		this.dir = dir;
		this.x = x;
		this.y = y;
		
		this.speed = 2; //云朵的速度
		scale = 1 + Math.random(); // Math.random()返回0.0~1.0的随机值
		// 缩放云朵图片
		scaleImageWidth = (int) (scale * img.getWidth());
		scaleImageHeight = (int) (scale * img.getWidth());
	}

	// 绘制方法
	public void draw(Graphics g, Bird bird) {
		int speed = this.speed;
		
		if(dir == DIR_NONE)   //云彩不动
			speed = 0;
		
		x = (dir == DIR_LEFT) ? x - speed : x + speed; // 方向逻辑
		g.drawImage(img, x, y, scaleImageWidth, scaleImageHeight, null);
	}

	/**
	 * 判断云朵是否飞出屏幕
	 * 
	 * @return 飞出则返回true，否则返回false
	 */
	public boolean isOutFrame() {
		boolean result = false;
		if (dir == DIR_LEFT) {
			if (x < -1 * scaleImageWidth) {
				return true;
			}
		} else if (dir == DIR_RIGHT) {
			if (x > Constant.FRAME_WIDTH) {
				return true;
			}
		}
		return result;
	}

	// 改变方向
	public void setDir(int dir) {
		this.dir = dir;
	}
}
