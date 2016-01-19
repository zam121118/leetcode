//流程控制学习
//异常时一种Exception类型的对象
public class TestExceptions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test="yes";
		//String test="no";
		try {
			System.out.println("start try");
			doRisky(test);//静态方法不能调用非静态方法
			System.out.println("end try");
		} catch (ScaryException se){
			System.out.println("scary exception");
		} finally {
			System.out.println("finally");
		}
		System.out.println("end of main");
	}
	//throws定义方法可以抛出的异常类型
	//异常方法中使用throw抛出定义好的异常对象
	static void doRisky(String test) throws ScaryException{
		System.out.println("start risky");
		if ("yes".equals(test)){
			throw new ScaryException();
		}
		System.out.println("end risky");
		return;
	}

}
