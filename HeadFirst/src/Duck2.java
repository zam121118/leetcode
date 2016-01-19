//关于静态变量的测试：静态变量被同类的所有实力共享
public class Duck2 {
	//int duckCount=0; 实例变量不会在每次创建duck2对象时，构造函数结构都是1
	//private static int duckCount=0;
	static int duckCount;
	public Duck2(){
		duckCount++;
		//System.out.println(duckCount);
	}
	
}
