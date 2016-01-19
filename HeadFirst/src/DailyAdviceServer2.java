//聊天服务器端，增加关闭套接字方法
//try块和finally块是两个代码块，使用同一个变量，必须在两个快之外声明！！！！
import java.io.*;
import java.net.*;

public class DailyAdviceServer2 {
	
	String[] adviceList={"Take smaller bites","Go for the tight jeans.No they do Not make you look fat.","One word:inappropriate","Just for today,be honest.Tell your boss what you *really* think","You might want to rethink that haircut"};
	
	public void go(){
		
			//ServerSocket会监听客户端对这台机器在4242端口上的要求
		try{
			ServerSocket serverSock=new ServerSocket(4242);

			//服务器进入无穷循环等待服务客户端的请求
			while(true){
				Socket sock=null;//try块和finally块是两个代码块，使用同一个变量，必须在两个快之外声明！！！！
				try{	
					sock=serverSock.accept();//侦听并接收到此套接字的连接。此方法在连接传入之前一直阻塞。
					PrintWriter writer=new PrintWriter(sock.getOutputStream());//getOutputStream()返回此套接字的输出流
					String advice=getAdvice();
					writer.println(advice);
					writer.close();//使用Socket连接来送出建议，送出后关闭连接该流
					System.out.println(advice);
				}catch (Exception ex){
					ex.printStackTrace();
				}finally{
					//与一个客户通话结束，要关闭Socket
					try{
						if(sock!=null){
							sock.close();
						}
					}catch(Exception e){
						//e.printStackTrace();}
						System.out.println("关闭套接字异常");//为了发现抛出的异常是因为关闭套接字有错
					}
				}
			}
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("服务器监听端口是4242，你已经在监听了");//为了发现抛出的异常是因为程序已经在监听本机4242端口
		}	
	}
		
	public String getAdvice(){
		int random=(int) (Math.random()*adviceList.length);
		return adviceList[random];
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DailyAdviceServer2 server=new DailyAdviceServer2();
		server.go();
	}

}
