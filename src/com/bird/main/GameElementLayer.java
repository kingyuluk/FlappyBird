package com.bird.main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.bird.util.Constant;

/**
 * 游戏中各种元素层的类
 * @author Kingyu
 *
 */

public class GameElementLayer {
	
	private List<Pipe> pipes;
	
	public GameElementLayer(){
		pipes = new ArrayList<>();
		
		initPipe();
	}
	
	/**
	 * 初始化元素层
	 */
	private void initPipe() {
		pipes.add(new Pipe(Constant.FRAME_WIDTH>>1, 0, Constant.FRAME_HEIGHT>>1, Pipe.TYPE_TOP_NORMAL));
		pipes.add(new Pipe(Constant.FRAME_WIDTH-100, 0, Constant.FRAME_HEIGHT>>2, Pipe.TYPE_TOP_NORMAL));
		
		pipes.add(new Pipe(Constant.FRAME_WIDTH/3, Constant.FRAME_HEIGHT/3, Constant.FRAME_HEIGHT/3, Pipe.TYPE_HOVER_NORMAL));
		
	 	pipes.add(new Pipe(Constant.FRAME_WIDTH>>1, 0, Constant.FRAME_HEIGHT>>1, Pipe.TYPE_BOTTOM_NORMAL));
		pipes.add(new Pipe(Constant.FRAME_WIDTH-100, 0, Constant.FRAME_HEIGHT>>2, Pipe.TYPE_BOTTOM_NORMAL));
	}

	public void draw(Graphics g) {
		for(int i=0; i<pipes.size(); i++) {
			pipes.get(i).draw(g);
		}
	}
}
