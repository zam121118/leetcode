//与SimpleChatClient分别进行编译运行

import java.io.*;
import java.net.*;
import java.util.*;//ArrayList
public class VerySimpleChatServer {

	ArrayList clientOutputStreams;//输出给客户端的流串
	
	public class ClientHandler implements Runnable{//定义服务器线程的任务
		BufferedReader reader;
		Socket sock;
		public ClientHandler(Socket clientSocket){//构造函数，获取来自客户端的输入流
			try{
				sock=clientSocket;//新创建的与客户端通信的服务器端套接字
				InputStreamReader isReader=new InputStreamReader(sock.getInputStream());
				reader=new BufferedReader(isReader);//获取从客户端接收的内容
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
 
		public void run(){//实现Runnable的唯一无参方法
			String message;
			try{
				while((message=reader.readLine())!=null){
					System.out.println("read"+message);//显示服务器读到的内容
					tellEveryone(message);//并将新接收到的内容发给所有连接到的客户端
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	
	public void go(){//与客户端建立连接，并启动新线程负责与客户端通信
		clientOutputStreams=new ArrayList();//输出给客户端的串流动态数组
		try{
			ServerSocket serverSock=new ServerSocket(5000);
			 
			while(true){//无限循环监听，
				Socket clientSocket=serverSock.accept();//为新连接到服务器的客户端创建新的通信套接字
				PrintWriter writer=new PrintWriter(clientSocket.getOutputStream());//从服务器输出的串流
				clientOutputStreams.add(writer);//将从服务器输出的串流保存在对象动态数组
				
				Thread t=new Thread(new ClientHandler(clientSocket));//启动新线程，接收客户端的串流并转发给所有客户端
				t.start();
				System.out.println("got a connection");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void tellEveryone(String message){//将动态数组clientOutputStreams里的所有消息转发给所有客户端
		Iterator it=clientOutputStreams.iterator();//为动态数组创建迭代器
		while(it.hasNext()){//遍历过程中，判定是否还有下一个元素
			try{
				PrintWriter writer=(PrintWriter) it.next();//遍历该元素。(即取出下一个元素),并强制转换为PrintWriter类型
				writer.println(message);
				writer.flush();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VerySimpleChatServer().go();
	}

}
