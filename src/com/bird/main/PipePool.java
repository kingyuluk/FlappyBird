package com.bird.main;

import java.util.ArrayList;
import java.util.List;

import com.bird.util.Constant;

/**
 * 为了避免反复地创建和销毁对象，使用对象池来提前创建好一些对象，使用时从对象池中获得，使用完毕归还
 * 
 * @author Kingyu
 *
 */
public class PipePool {
	private static List<Pipe> pool = new ArrayList<Pipe>(); // 池中对象的容器
	private static List<MovingPipe> movingPool = new ArrayList<MovingPipe>(); // 池中对象的容器

	public static final int INIT_PIPE_COUNT = (Constant.FRAME_WIDTH
			/ (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2; // 根据窗口宽度算得对象池中对象的初始个数
	public static final int MAX_PIPE_COUNT = 30; // 对象池中对象的最大个数，自行定义

	// 初始化水管容器
	static {
		for (int i = 0; i < INIT_PIPE_COUNT; i++) {
			pool.add(new Pipe());
		}
		for (int i = 0; i < INIT_PIPE_COUNT; i++) {
			movingPool.add(new MovingPipe());
		}
	}

	/**
	 * 从对象池中获取一个对象
	 * 
	 * @return 传入对象的类型，以判断从哪个对象池中获取
	 */
	public static Pipe get(String className) {
		if ("Pipe".equals(className)) {
			int size = pool.size();
			if (size > 0) {
				return pool.remove(size - 1); // 移除并返回最后一个
			} else {
				return new Pipe(); // 空对象池，返回一个新对象
			}
		} else {
			int size = movingPool.size();
			if (size > 0) {
				return movingPool.remove(size - 1); // 移除并返回最后一个
			} else {
				return new MovingPipe(); // 空对象池，返回一个新对象
			}
		}
	}

	/**
	 * 归还对象给容器
	 * 
	 * @param pipe
	 */
	public static void giveBack(Pipe pipe) {
		//判断类的类型
		if(pipe.getClass() == Pipe.class) {
			if (pool.size() < MAX_PIPE_COUNT) {
				pool.add(pipe);
			}
		}else {
			if (movingPool.size() < MAX_PIPE_COUNT) {
				movingPool.add((MovingPipe)pipe);
			}
		}
	}
}
