//FlowLayout顺序布局会让按钮取得刚好所需的大小,
//但会一直水平排列
//JPanel的布局管理器的默认布局是FlowLayout布局
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;


public class LayoutTry2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LayoutTry2 gui=new LayoutTry2();
		gui.go();

	}
	
	
	public void go(){
		JFrame frame=new JFrame();
		JPanel panel=new JPanel();
		panel.setBackground(Color.darkGray);
		
		JButton button1=new JButton("shock me!");
		JButton button2=new JButton("bliss");
		JButton button3=new JButton("huh?");
		JButton button4=new JButton("wo qu");
		JButton button5=new JButton("CENTER");

		
		frame.getContentPane().add(BorderLayout.EAST,panel);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);


		frame.setSize(200,200);
		frame.setVisible(true);

	}

}
