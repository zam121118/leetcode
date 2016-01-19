import javax.swing.*;
public class SimpleGui1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建JFrame和buttton
		JFrame frame=new JFrame();
		JButton button=new JButton("Click me");
		//关闭window时结束程序
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//将button加到frame的pane上
		frame.getContentPane().add(button);
		//设定frame的大小
		frame.setSize(300,300);
		//最后把frame显示出来
		frame.setVisible(true);
	}

}
