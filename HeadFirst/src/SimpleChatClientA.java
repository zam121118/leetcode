//简单聊天客户端程序第一版：只能发信给服务器端
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;//响应事件的类
//import java.awt.Event;关于Serialiable的一个包


public class SimpleChatClientA {
	JTextField outgoing;
	PrintWriter writer;
	//Socket sock;//???
	//创建GUI
	public void go(){
		JFrame frame=new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel=new JPanel();
		outgoing=new JTextField(20);
		JButton sendButton=new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(400,500);
		frame.setVisible(true);
		setUpNetworking();//调用网络连接方法
	}
	
	
	public void setUpNetworking(){
		try{
			Socket sock=new Socket("127.0.0.1",5000);
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("networking established!");
		}catch(Exception ex){
			System.out.println("连接服务器异常");
			ex.printStackTrace();
		}
	}
	
	
	public class SendButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			try{
				//可以开始写数据，println()会把信息送到服务器
				writer.println(outgoing.getText());//getText()返回此 TextComponent 中包含的文本。如果底层文档为 null，则将给出 NullPointerException。
				writer.flush();
			}catch(Exception e){
				System.out.println("按钮监听异常");
			}
			outgoing.setText("");//将此 TextComponent 文本设置为指定文本。如果该文本为 null 或空，则具有只删除旧文本的效果。插入文本时，得到的插入符的位置由该插入符类的实现确定。
			outgoing.requestFocus();//请求此 Component 获取输入焦点。
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SimpleChatClientA().go();
	}

}
