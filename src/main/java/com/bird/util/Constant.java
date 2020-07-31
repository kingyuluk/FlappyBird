package com.bird.util;

import com.bird.main.GameElementLayer;
import com.bird.main.Pipe;

import java.awt.*;
import java.net.URL;

/**
 * 常量类
 *
 * @author Kingyu 后续优化可写入数据库或文件中，便于修改
 */

public class Constant {
	// 窗口尺寸
	public static final int FRAME_WIDTH = 420;
	public static final int FRAME_HEIGHT = 640;

	// 游戏标题
	public static final String GAME_TITLE = "Flappy Bird written by Kingyu";

	// 窗口位置
	public static final int FRAME_X = 600;
	public static final int FRAME_Y = 100;


	private static final String basePath;

	static {
		URL resource = Constant.class.getClassLoader().getResource(".");
		if (resource != null) {
			basePath = resource.getPath();
		} else {
			basePath = "";
		}
	}

	// 图像资源路径
	public static final String BG_IMG_PATH = basePath + "img/background.png"; // 背景图片

	// 小鸟图片
	public static final String[][] BIRDS_IMG_PATH = {
			{basePath + "img/0.png",
					basePath + "img/1.png",
					basePath + "img/2.png",
					basePath + "img/3.png",
					basePath + "img/4.png",
					basePath + "img/5.png",
					basePath + "img/6.png",
					basePath + "img/7.png"},
			{basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png",
					basePath + "img/up.png"},
			{basePath + "img/down_0.png",
					basePath + "img/down_1.png",
					basePath + "img/down_2.png",
					basePath + "img/down_3.png",
					basePath + "img/down_4.png",
					basePath + "img/down_5.png",
					basePath + "img/down_6.png",
					basePath + "img/down_7.png"},
			{basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",
					basePath + "img/dead.png",}};

	// 云朵图片
	public static final String[] CLOUDS_IMG_PATH = {basePath + "img/cloud_0.png",
			basePath + "img/cloud_1.png"};

	// 水管图片
	public static final String[] PIPE_IMG_PATH = {basePath + "img/pipe.png",
			basePath + "img/pipe_top.png",
			basePath + "img/pipe_bottom.png"};

	public static final String TITLE_IMG_PATH = basePath + "img/title.png";
	public static final String NOTICE_IMG_PATH = basePath + "img/start.png";
	public static final String SCORE_IMG_PATH = basePath + "img/score.png";
	public static final String OVER_IMG_PATH = basePath + "img/over.png";
	public static final String AGAIN_IMG_PATH = basePath + "img/again.png";

	public static final String SCORE_FILE_PATH = basePath + "score"; // 分数文件路径

	//游戏音乐
	public static final String MUSIC_FLY = basePath + "wav/fly.wav";
	public static final String MUSIC_CRASH = basePath + "wav/crash.wav";
	public static final String MUSIC_SCORE = basePath + "wav/score.wav";

	// 游戏背景色
	public static final Color BG_COLOR = new Color(0x4bc4cf);

	// 游戏刷新率
	public static final int GAME_INTERVAL = 1000 / 60;

	// 标题栏高度
	public static final int TOP_BAR_HEIGHT = 20;

	// 地面高度
	public static final int GROUND_HEIGHT = 35;

	// 上方管道加长
	public static final int TOP_PIPE_LENGTHENING = 100;

	public static final int CLOUD_BORN_PERCENT = 6; // 云朵生成的概率，单位为百分比
	public static final int CLOUD_IMAGE_COUNT = 2; // 云朵图片的个数
	public static final int MAX_CLOUD_COUNT = 7; // 云朵的最大数量

	public static final Font CURRENT_SCORE_FONT = new Font("华文琥珀", Font.BOLD, 32);// 字体
	public static final Font SCORE_FONT = new Font("华文琥珀", Font.BOLD, 24);// 字体

	// 窗口可容纳的水管数量+2， 由窗口宽度、水管宽度、水管间距算得
	public static final int FULL_PIPE = (Constant.FRAME_WIDTH
			/ (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;
}
