package leetcode;
//greatest common divisor of two nonnegative p and q,use 欧几里德算法,辗转相除法
//两个整数的最大公约数等于其中较小的那个数和两数的相除余数的最大公约数
public class gcd {
	public static int gcd(int p,int q){
		if(q==0) return p;
		int r=p%q;
		return gcd(q,r);
	}
}
