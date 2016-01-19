//聊天客户端
import java.io.*;
import java.net.*;

public class DailyAdviceClient {
	
	public void go(){
		
		try{
			//创建套接字
			Socket socket=new Socket("127.0.0.1",4242);
			//串流读取
			InputStreamReader streamReader=new InputStreamReader(socket.getInputStream());//getInputStream()返回此套接字的输入流
			//存入缓冲区
			BufferedReader reader=new BufferedReader(streamReader);
			String advice=null;
			//获取串流内容并输出
			advice=reader.readLine();
			System.out.println("Today you Should:"+advice);
			//记得关闭串流
			reader.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DailyAdviceClient client=new DailyAdviceClient();
		client.go();
	}

}
