import java.util.Calendar;
import java.util.Date;




public class PrimitiveType {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer i=new Integer(42);
		i++;
		//输出primitive对象使用print()而不是println()
		//primitive对象可以进行数值运算
		System.out.println(i);
		Integer j=new Integer(5);
		System.out.println(j);
		int k=i+j;
		System.out.println(k);
		//primitive包装对象有静态的实用性方法！
		//该方法为取用String并返回给你primitive主数据类型值：
		String s="2";
		int x=Integer.parseInt(s);
		double d=Double.parseDouble("420.24");
		boolean b=new Boolean("true").booleanValue();
		System.out.println(x);
		System.out.println(b);
		System.out.println(d);
		if(!b){
			System.out.print("end of main");
		}
		
		
		//反过来将primitive主数据类型值转换成String
		double dd=42.5;
		String doubleS=""+d;//注意：此处“+”这个操作是JAVA中唯一有重载过的运算符
		double ddd=43.5;
		String doubleString=Double.toString(ddd);//Double这个类的静态方法
		int pwd=24;
		String pwdString=Integer.toString(pwd);
		System.out.println(pwd);
		System.out.println(dd);
		System.out.println(ddd);
		
		
		//数字的格式化
		String xs=String.format("%,d",1000000000);
		System.out.println(xs);
		
		//日期的格式化
		Date today=new Date();
		String date=String.format("%tc",today);
		String date_1=String.format("%tr",today);
		String date_2=String.format("%tA,%tB,%td",today,today,today);
		String date_3=String.format("%tA,%<tB,%<td",today);
		System.out.println(date);
		System.out.println(date_1);
		System.out.println(date_2);
		System.out.println(date_3);

		//日起操作：使用java.util.Calender类
		Calendar c=Calendar.getInstance();
		c.set(2004, 1, 7, 15, 40);
		long day1=c.getTimeInMillis();
		day1+=1000*60*60;
		c.setTimeInMillis(day1);
		System.out.println("new hour "+c.get(c.HOUR_OF_DAY));
		c.add(c.DATE,35);
		System.out.println("add 35 days "+c.getTime());
		c.set(c.DATE, 1);
		System.out.println("set to 1 "+c.getTime());
	}

}
