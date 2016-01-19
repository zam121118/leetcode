//全新的SimpleChatClient,发信给服务器端的同时接收来自服务器的消息并显示在滚动区域。
//简单聊天客户端程序第一版：只能发信给服务器端
import java.io.*;
import javax.swing.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;//响应事件的类
//import java.awt.Event;关于Serialiable的一个包


public class SimpleChatClient {
	JTextField outgoing;
	JTextArea incoming;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SimpleChatClient().go();
	}
	
	//创建GUI，并启用多线程！
	public void go(){
		JFrame frame=new JFrame("Ludicrously Simple Chat Client");
		JPanel mainPanel=new JPanel();
		incoming=new JTextArea(15,20);//15行，50列
		incoming.setLineWrap(true);//设置自动换行
		incoming.setWrapStyleWord(true);//指示是否应该使用单词边界来换行
		incoming.setEditable(false);//指示此 TextComponent是否应该为可编辑的
		JScrollPane qScroller=new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		outgoing=new JTextField(20);
		JButton sendButton=new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		setUpNetworking();//调用网络连接方法
//启动新线程，以内部类作为任务，此任务是读取服务器的socket串流显示在文本区域
		Thread readerThread=new Thread(new IncomingReader());
		readerThread.start();
		
		frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
		frame.setSize(400,500);
		frame.setVisible(true);
	}
	
	
	private void setUpNetworking(){//使用socket取得输入/输出的串流
		try{
			sock=new Socket("127.0.0.1",5000);
			InputStreamReader streamReader=new InputStreamReader(sock.getInputStream());
			reader=new BufferedReader(streamReader);
			writer=new PrintWriter(sock.getOutputStream());
			System.out.println("networking established!");
		}catch(Exception ex){
			System.out.println("连接服务器异常");
			ex.printStackTrace();
		}
	}
	
	//用户按下按钮时送出文本字段的内容到服务器上
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
	
	
	//Thread的任务！run()会持续的读取服务器信息并把它加到可滚动的文本区域上
	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while((message=reader.readLine())!=null){
					System.out.println("read"+message);
					incoming.append(message+"\n");//在JTextArea后面追加显示收到的信息
				}
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("获取信息时异常");
			}
		}
	}
}
