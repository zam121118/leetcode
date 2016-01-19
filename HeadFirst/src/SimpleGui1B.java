import java.awt.event.*;//监听接口和事件对象
import javax.swing.*; //GUI，很多widget类型

/*对处理操作事件感兴趣的类可以实现此接口，而使用该类创建的对象可使用组件的 addActionListener
  方法向该组件注册。在发生操作事件时，调用该对象的 actionPerformed 方法。 
*/
public class SimpleGui1B implements ActionListener {//表示SimpleGui1B是个ActionListener
	JButton button;//监听调用方法actionPerformed()使用button对象的方法，因此button全局变量
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleGui1B gui=new SimpleGui1B();
		gui.go();
	}
	
	public void go(){
		JFrame frame=new JFrame();
		button=new JButton("Click me");
		//向按钮注册
		button.addActionListener(this);
		
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.setVisible(true);
	}
	
	//实现interface上的方法。。。这是真正处理事件的方法
	public void actionPerformed(ActionEvent event){//按钮会以ActionEvent对象作为参数来调用此方法
		button.setText("I've been clicked!");//更改按钮文字，是javax.swing.AbstractButton的方法

	}

}
