# Flappy Bird
Flappy Bird for desktop platforms.

基于Java和JDK基本库编写

开发平台为macOS 10.15.5，开发工具为Eclipse IDE (4.16.0)，Java SE 8[1.8.0_251]


## Overview

本项目为Flappy bird的桌面平台版，具备原版的所有功能，且相较于原版优化了游戏难度的梯度并加入移动型水管，使游戏的可玩性更高。
### 游戏玩法
每局游戏随机刷新所有元素，游戏只需空格键即可操作，敲击按键小鸟就会振翅向上飞，且受到重力作用会不断下坠，需要玩家控制小鸟不断飞行，并注意躲避随机生成的水管，每飞过一对水管就会得分，飞行过程中如果撞到水管或掉落在地则游戏结束。


## 游戏界面

### 游戏启动
![image](https://github.com/kingyuluk/flappy-bird/blob/master/examples/start.png)

### 运行示例
![image](https://github.com/kingyuluk/flappy-bird/blob/master/examples/play.gif)

### 游戏结束
![image](https://github.com/kingyuluk/flappy-bird/blob/master/examples/over.png)


## Package Contents
* com.bird.app    游戏的入口

* com.bird.main   游戏的内容

* com.bird.util   自定义的工具

## Change Log
  
v1.2.0 - July 11, 2020
* 现在水管可以移动了，随着游戏分数的上升会提升游戏难度

v1.1.0 - July 11, 2020
* 添加了悬浮型的水管

v1.0.0 - July 10, 2020
* 具备完整的游戏功能

## Notes

* 文本编码格式为UTF-8，若注释出现乱码请修改编译器的文本编码格式

* 由于使用了sun.*包，不同版本的JDK中sun包中的类可能发生变化，因此无法确保工作在所有JAVA平台上

## Contact
* email: <kingyuluk@mail.dlut.edu.cn>
