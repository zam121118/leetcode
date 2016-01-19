import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextArea1 implements ActionListener {
	
	JTextArea text;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TextArea1 gui=new TextArea1();
		gui.go();
	}
	
	public void go(){
		//创建GUI的4个步骤
		JFrame frame=new JFrame();
		JPanel panel=new JPanel();
		JButton button=new JButton("Just Click It");
		//设置鼠标的监听
		button.addActionListener(this);
		frame.getContentPane().add(BorderLayout.CENTER,panel);
		frame.getContentPane().add(BorderLayout.SOUTH,button);//注意间隔.号是英文的
		frame.setSize(350,300);
		frame.setVisible(true);		
		
		//创建JText，10行，每行20字
		text=new JTextArea(10,20);
		text.setLineWrap(true);//开启自动换行
		
		JScrollPane scroller=new JScrollPane(text);//将文本区域加到滚动区域
		//设计只有纵向滚动条，取消横向滚动
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		 //将滚动区域加到面板上
		panel.add(scroller);
		
	}
	
	//鼠标监听处理方法
	public void actionPerformed(ActionEvent ev){
		//加入文字
		text.append("Button Clicked \n");//在按下按钮时插入一个换行字符，不然会粘在一起
	}

}
