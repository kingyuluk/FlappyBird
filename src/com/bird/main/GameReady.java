package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 游戏启动界面类
 * 
 * @author Kingyu
 *
 */
public class GameReady {

	private BufferedImage titleImg;
	private BufferedImage noticeImg;

	private int flash; // 图像闪烁参数

	// 构造器中进行初始化，装载图像资源
	public GameReady() {
		titleImg = GameUtil.loadBUfferedImage(Constant.TITLE_IMG_PATH);
		noticeImg = GameUtil.loadBUfferedImage(Constant.NOTICE_IMG_PATH);
	}

	public void draw(Graphics g) {
		// 计算title图像的x、y坐标
		int x = Constant.FRAME_WIDTH - titleImg.getWidth() >> 1; //x坐标为窗口中央
		int y = Constant.FRAME_HEIGHT / 3;  //y坐标为游戏窗口的1/3处
		g.drawImage(titleImg, x, y, null); // 绘制

		// 使notice的图像闪烁
		final int COUNT = 30; // 闪烁周期
		if (flash++ > COUNT) {
			// 计算notice图像的x、y坐标
			x = Constant.FRAME_WIDTH - noticeImg.getWidth() >> 1;
			y = Constant.FRAME_HEIGHT / 5 * 3;
			g.drawImage(noticeImg, x, y, null); // 绘制
			if (flash == COUNT * 2) // 重置闪烁参数
				flash = 0;
		}
	}

}
