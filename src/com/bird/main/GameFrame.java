package com.bird.main;

import static com.bird.util.Constant.FRAME_HEIGHT;
import static com.bird.util.Constant.FRAME_WIDTH;
import static com.bird.util.Constant.FRAME_X;
import static com.bird.util.Constant.FRAME_Y;
import static com.bird.util.Constant.GAME_INTERVAL;
import static com.bird.util.Constant.GAME_TITLE;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * 主窗口类，游戏窗口和绘制的相关内容
 * 
 * @author Kingyu
 *
 */

public class GameFrame extends Frame implements Runnable {

	private static final long serialVersionUID = 1L; // 保持版本的兼容性

	// 在构造器中初始化
	public GameFrame() {
		initFrame(); // 初始化游戏窗口
		setVisible(true); // 窗口默认为不可见，设置为可见
		initGame(); // 初始化游戏对象
	}

	// 初始化游戏窗口
	private void initFrame() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT); // 设置窗口大小
		setTitle(GAME_TITLE); // 设置窗口标题
		setLocation(FRAME_X, FRAME_Y); // 窗口初始位置
		setResizable(false); // 设置窗口大小不可变
		// 添加关闭窗口事件（监听窗口发生的事件，派发给参数对象，参数对象调用对应的方法）
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0); // 结束程序
			}
		});
		// 添加按键监听
		addKeyListener(new BirdKeyListener());
	}

	// 用于接收按键事件的对象的内部类
	class BirdKeyListener implements KeyListener {
		// 按键按下
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyChar();
			if (keycode == KeyEvent.VK_SPACE) {
				bird.BirdUp();
				bird.BirdDown();
			}
		}

		// 按键松开
		public void keyReleased(KeyEvent e) {
			int keycode = e.getKeyChar();
			if (keycode == KeyEvent.VK_SPACE) {
				bird.keyReleased();
			}
		}

		public void keyTyped(KeyEvent e) {
		}
	}

	private GameBackground background; // 游戏背景对象
	private GameForeground foreground; // 游戏前景对象
	private Bird bird; // 小鸟对象
	private GameElementLayer gameElement;

	// 对游戏中的对象进行初始化
	private void initGame() {
		background = new GameBackground();
		gameElement = new GameElementLayer();
		foreground = new GameForeground();
		bird = new Bird();
		// 启动用于刷新窗口的线程
		new Thread(this).start();
	}

	/**
	 * 绘制需要屏幕内容 当repaint()方法被调用时，JVM会调用update() 不要主动调用update 参数g是系统提供的画笔，由系统进行实例化
	 * 
	 * 单独启动一个线程，不断地快速调用repaint()，让系统对整个窗口进行重绘
	 * 
	 */
	public void update(Graphics g) {

		Graphics bufG = bufImg.getGraphics(); // 获得图片画笔

		// 使用图片画笔将需要绘制的内容绘制到图片
		background.draw(bufG); // 背景层
		gameElement.draw(bufG); // 游戏元素层
		foreground.draw(bufG); // 前景层
		bird.draw(bufG); // 鸟

		g.drawImage(bufImg, 0, 0, null); // 一次性将图片绘制到屏幕上
	}

	@Override
	public void run() {
		while (true) {
			repaint(); // 通过调用repaint(),让JVM调用update()
			try {
				Thread.sleep(GAME_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// 系统线程：屏幕内容的绘制，窗口事件的监听与处理
	// 项目中存在两个线程：系统线程；自定义线程,调用repaint()。
	// 两个线程会抢夺系统资源，可能会出现一次刷新周期所绘制的内容，并没有在一次刷新周期内完成
	// （双缓冲）单独定义一张图片，将需要绘制的内容绘制到这张图片，再一次性地将图片绘制到窗口
	private BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
}
