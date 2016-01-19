//关于静态方法的测试
public class Duck {
	//private int size;
	int size;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//在静态方法内使用实例变量，编译不知道是哪个实例变量
		//静态方法也不能调用非静态的方法和非静态变量
		Duck a=new Duck();
		a.setSize(3);
		System.out.println("Size of the duck is"+a.getSize());
	}
	public void setSize(int a){
		size=a;
	}
	public int getSize(){
		return size;
	}

}
