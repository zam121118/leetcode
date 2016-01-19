//BorderLayout布局组价的测试
//JFrame的布局管理器的默认布局是FlowLayout布局

import java.awt.BorderLayout;//布局管理器属于java.awt。*包
import javax.swing.*;


public class LayoutTry {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LayoutTry gui=new LayoutTry();
		gui.go();
	}
	
	
	public void go(){
		JFrame frame=new JFrame();
		JButton button1=new JButton("EAST");
		JButton button2=new JButton("WEST");
		JButton button3=new JButton("NORTH");
		JButton button4=new JButton("SOUTH");
		JButton button5=new JButton("CENTER");

		
		frame.getContentPane().add(BorderLayout.EAST,button1);
		frame.getContentPane().add(BorderLayout.WEST,button2);
		frame.getContentPane().add(BorderLayout.NORTH,button3);
		frame.getContentPane().add(BorderLayout.SOUTH,button4);
		frame.getContentPane().add(BorderLayout.CENTER,button5);

		
		frame.setSize(300,300);
		frame.setVisible(true);

	}

}
