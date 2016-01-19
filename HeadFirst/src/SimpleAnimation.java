//完整的动画程序

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

public class SimpleAnimation {
	
	int x=180;//在住GUI中创建两个实例变量用来代表图形的坐标
	int y=100;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleAnimation gui=new SimpleAnimation();
		gui.go();
	}

	public void go(){
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//创建出frame上的widget
		MyDrawPanel mydrawpanel=new MyDrawPanel();
		
		frame.getContentPane().add(BorderLayout.CENTER,mydrawpanel);
		frame.setSize(400,400);
		frame.setVisible(true);
		//重点！重复130次
		for(int i=0;i<50;i++){
			x-=3;
			//y+=3; //递增坐标值
			mydrawpanel.repaint();//要求重新绘制面板
			try{
				Thread.sleep(15); //加上延迟可以放缓
			}catch (Exception ex){}
		}
	}
	
	public class MyDrawPanel extends JPanel{
		public void paintComponent(Graphics g){
			//解决的办法是在每次画上新的圆圈之前把整个面板用原来的背景底色填满
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());//this.getWidth(), this.getHeight()从JPanel继承下来的方法
			
			g.setColor(Color.orange);
			g.fillOval(x, y, 100, 100); 
			
//			如此绘制图【片，忘记擦掉上一次的图片而出现痕迹
//			g.setColor(Color.orange);
//			g.fillOval(x, y, 100, 100);//使用外部类坐标值更新
		}
	}
}
