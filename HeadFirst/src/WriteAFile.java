//import java.io.FileWriter;
import java.io.*;
//将字符串写入文本文件
//写入文本数据（字符串）与写入对象是很类似的


public class WriteAFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//输入、输出相关的操作都必须包在try\catch块中
		try{
			FileWriter writer=new FileWriter("foo.txt");
			writer.write("Hello World");
			writer.close();//记得关闭连接！！！
		} catch(Exception ex){
			ex.printStackTrace();
		}

		
		//读取文本文件
		try{
			File myFile=new File("foo.txt");
			FileReader fileReader=new FileReader(myFile);
			
			BufferedReader reader=new BufferedReader(fileReader);
			
			String line=null;
			
			
			//建议的读取文本文件的方式！！！！readLine()
			//读取一个文本行。通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行。 
			//返回：
			//包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null 
			while((line=reader.readLine()) != null){
				System.out.println(line);
			}
			
			reader.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
