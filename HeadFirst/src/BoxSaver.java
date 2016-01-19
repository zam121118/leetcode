//让类能够序列化，实现Serializable
import java.io.*;

public class BoxSaver implements Serializable {

	
	private int width;
	private int height;
	
	
	public void setWidth(int w){
		width=w;
	}
	
	public void setHeight(int h){
		height=h;
	}
	
	public int getHeight(){
		return height*50;
	}

	public int getWidth(){
		return width*50;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建带保存的对象
		BoxSaver myBox=new BoxSaver();
		myBox.setHeight(50);
		myBox.setWidth(60);
		
		//将对象myBox进行序列化存储，有可能抛出异常
		try{
			FileOutputStream fs1=new FileOutputStream("foo.ser");//创建一个向指定 File 对象表示的文件中写入数据的文件输出流
			//只能将支持 java.io.Serializable 接口的对象写入流中
			ObjectOutputStream os1=new ObjectOutputStream(fs1);
			// 将指定的对象写入 ObjectOutputStream
			os1.writeObject(myBox);
			//关闭所关联的输出流
			os1.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		//将保存的对象反序列化，并调用方法进行测试
		try{
			FileInputStream fs2=new FileInputStream("foo.ser");
			ObjectInputStream os2=new ObjectInputStream(fs2);
			//顺序存储，读出对象的顺序必须保持不变
			//读出的对象注意强制转为Box类型
			BoxSaver b=(BoxSaver) os2.readObject();
			os2.close();
			
			//看看是否成功
			System.out.println("Box's 50 height："+b.getHeight());
			System.out.println("Box's 50 width："+b.getWidth());

		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
