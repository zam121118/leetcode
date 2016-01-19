//成功！实现多重监听
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;

public class TwoButtons { //主要的GUI类并不实现ActionListener

	JFrame frame; //内部类需要调用的外部类全局变量
	JLabel label;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TwoButtons gui=new TwoButtons();
		gui.go();
	}
	
	public void go(){
		//创建frame对象，设置窗口关闭停止程序
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//创建colorbutton组件，设置colorbutton监听接口
		JButton colorbutton=new JButton("Click ang change color");
		colorbutton.addActionListener(new ColorListener());//相对于将this传给监听的注册方法，现在传的是对应的实例
		
		JButton labelbutton=new JButton("change labe!");
		labelbutton.addActionListener(new LabelListener());
		
		label=new JLabel("I'm a lable!");
		MyDrawPanel drawPanel=new MyDrawPanel();
		
		frame.getContentPane().add(BorderLayout.SOUTH,colorbutton);//依照指定区域把widget放上去
		frame.getContentPane().add(BorderLayout.CENTER,drawPanel);
		frame.getContentPane().add(BorderLayout.EAST,labelbutton);
		frame.getContentPane().add(BorderLayout.WEST,label);


		frame.setSize(300,300);
		frame.setVisible(true);
	}
	
	//创建两个内部类
	class ColorListener implements ActionListener {//终于可以在单一的类中做出不同的ActionListener了
		public void actionPerformed(ActionEvent event){//java.awt.event包
			//javax.swing.JFrame类方法
			frame.repaint();//直接存取frame,不需要明确指定外部类的引用
		}
	}

	class LabelListener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			label.setText("I'v changed!!");//内部类可以直接存取label
		}
	}

}
