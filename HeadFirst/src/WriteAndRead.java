
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;

public class WriteAndRead {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		//读文本文件,会因编码问题出现乱码
//		try{
//			
//			//穿件fileReader
//			FileReader fileReader=new FileReader(new File("myfile.txt"));
//			//连接缓冲区
//			BufferedReader bufferReader=new BufferedReader(fileReader);
//			
//			//显示字符串
//			String str=null;
//			
//			//逐行读取
//			while((str=bufferReader.readLine())!=null){
//				System.out.println(str);
//			}
//			
//		}catch (Exception ex){
//			ex.printStackTrace();
//		}
//		
		
		//读文本文件,关于更改该文件编码解决乱码
		try{
			File myfile=new File("myfile.txt");//创建文件对象
			FileInputStream fileStream=new FileInputStream(myfile);//创建文件输入流
			//输入流读，可控制编码
			InputStreamReader reader=new InputStreamReader(fileStream,"UTF-8");//此处编码指的文本文件的原始编码
			//将输入流读的放到缓冲区
			BufferedReader bufferReader=new BufferedReader(reader);
			String str=null;
			
//			//逐行读取
//			while((str=bufferReader.readLine())!=null){
////				System.out.println(str);
////			}
//		}catch (Exception ex){
//			ex.printStackTrace();
//		}
//		
//		
		
		//写文本文件
//		try{
			File myOutFile=new File("F:\\workspace\\try\\myoutfile.txt");
			FileOutputStream fileoutStream=new FileOutputStream(myOutFile);
			//EUC-CN是GB2312最常用的表示方法
			OutputStreamWriter writer=new OutputStreamWriter(fileoutStream,"UTF-8");
			BufferedWriter buffeWriter=new BufferedWriter(writer);
			//显示写入文件使用的编码
			System.out.println(writer.getEncoding());
			//循环的将myfile。txt文件写入myoutfile.txt
			while((str=bufferReader.readLine())!=null){
				writer.write(str);
				writer.write("\n");//每行结束换下一行
				writer.flush();
			}
			 
		//	String str1=null;
		}catch (Exception ex){
			ex.printStackTrace();
		}

		
		
	}

}
