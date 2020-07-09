package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 游戏未开始的内容
 * 
 * @author Kingyu
 *
 */
public class GameReady {

	private BufferedImage titleImg;
	private BufferedImage noticeImg;

	private int flash;

	public GameReady() {
		titleImg = GameUtil.loadBUfferedImage(Constant.TITLE_IMG_PATH);
		noticeImg = GameUtil.loadBUfferedImage(Constant.NOTICE_IMG_PATH);
	}

	public void draw(Graphics g) {
		int x = Constant.FRAME_WIDTH - titleImg.getWidth() >> 1;
		int y = Constant.FRAME_HEIGHT / 5 << 1;
		g.drawImage(titleImg, x, y, null);

		final int COUNT = 30;
		if (flash++ > COUNT) {
			x = Constant.FRAME_WIDTH - noticeImg.getWidth() >> 1;
			y = Constant.FRAME_HEIGHT / 5 * 3;
			g.drawImage(noticeImg, x, y, null);
			if (flash == COUNT * 2)
				flash = 0;
		}
	}
	
}
