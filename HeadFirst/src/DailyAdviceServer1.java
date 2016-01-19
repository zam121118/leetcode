//聊天服务器端，原书版
import java.io.*;
import java.net.*;

public class DailyAdviceServer1 {
	
	String[] adviceList={"Take smaller bites","Go for the tight jeans.No they do Not make you look fat.","One word:inappropriate","Just for today,be honest.Tell your boss what you *really* think","You might want to rethink that haircut"};
	
	public void go(){
		
			
		try{
			//ServerSocket会监听客户端对这台机器在4242端口上的要求
			ServerSocket serverSock=new ServerSocket(4242);
			//服务器进入无穷循环等待服务客户端的请求
			while(true){
				Socket sock=serverSock.accept();//侦听并接收到此套接字的连接。此方法在连接传入之前一直阻塞。
				
				PrintWriter writer=new PrintWriter(sock.getOutputStream());//getOutputStream()返回此套接字的输出流
				String advice=getAdvice();
				writer.println(advice);
				writer.close();//使用Socket连接来送出建议，送出后关闭连接该流
				System.out.println(advice);
			}
		}catch (Exception ex){
			//ex.printStackTrace();
			System.out.println("服务器监听端口是4242，你已经在监听了");//为了发现抛出的异常是因为程序已经在监听本机4242端口
		}
	}
		
	public String getAdvice(){
		int random=(int) (Math.random()*adviceList.length);
		return adviceList[random];
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DailyAdviceServer1 server=new DailyAdviceServer1();
		server.go();
	}

}
