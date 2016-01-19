//失败！！！实现多重监听
//1.实现监听者的接口 2.向事件源注册 3.等待事件源调用你的事件处理程序（定义在监听者的接口中的方法）
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

public class SimpleGui3C implements ActionListener{
	JFrame frame;//由于监听调用方法actionperformed()使用对象frame,因此frame应当全局变量
	JButton labelbutton,colorbutton;
	JLabel label;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleGui3C gui=new SimpleGui3C();
		gui.go();
	}
	public void go(){
		//创建frame对象，设置窗口关闭停止程序
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//穿件button组件，设置button监听接口
		//JButton button=new JButton("Click ang change color");
		colorbutton=new JButton("Click ang change color");
		labelbutton=new JButton("change labe!");
		label=new JLabel("I'm a lable!");
		
		//实现多重监听失败的例子
		colorbutton.addActionListener(this);
		labelbutton.addActionListener(this);
		
		//创建自己的JPanel对象，添加绘图组件至frame
		MyDrawPanel drawPanel=new MyDrawPanel();
		frame.getContentPane().add(BorderLayout.SOUTH,colorbutton);//依照指定区域把widget放上去
		frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
		frame.getContentPane().add(BorderLayout.EAST,labelbutton);
		frame.getContentPane().add(BorderLayout.WEST,label);


		frame.setSize(300,300);
		frame.setVisible(true);
		
	}
//这样的监听是的colorbutton与labelbutton都可以改变圆的颜色，
//也就是说分不清来自于哪个监听事件
//   需要实现多重监听
	public void actionPerformed(ActionEvent event){
		frame.repaint();//当用户按下按钮就要求frame重新绘制
		label.setText("I'v changed!");
	}

}
