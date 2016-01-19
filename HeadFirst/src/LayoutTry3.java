
//JPanel的布局管理器的默认布局是FlowLayout布局
//但是可以更改为BoxLayout,垂直放置组件
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;




public class LayoutTry3{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LayoutTry3 gui=new LayoutTry3();
		gui.go();

	}
	
	
	public void go(){
		JFrame frame=new JFrame();
		JPanel panel=new JPanel();
		panel.setBackground(Color.darkGray);
		//把布局管理器换掉
		//BoxLayout的构造函数需要知道管理哪个组件以及使用哪个轴
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		JButton button1=new JButton("shock me!");
		JButton button2=new JButton("bliss");
		JButton button3=new JButton("huh?");
		JButton button4=new JButton("wo qu");
		
		frame.getContentPane().add(BorderLayout.EAST,panel);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);


		frame.setSize(250,200);
		frame.setVisible(true);

	}

}
