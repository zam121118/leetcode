package leetcode;
import java.util.*;
/*Given a sorted array, remove the duplicates in place such that 
 * each element appear only once and return the new length.
Do not allocate extra space for another array, 
you must do this in place with constant memory.*/

public class RemoveDuplicatesfromSortedArray {

    //跳过重复值，直接查找非重复值，j记录非重复值坐标，i查找下一个非重复值坐标
    //算法思想：i,j从0,当[i]=[j]，则使i++,否则将[i]赋值给[j++]后i与j均自增1，继续循环
	public int removeDuplicates(int[] A) {
		if (A.length==0) return 0;
		int j=0;
		for (int i=0; i<A.length; i++)
			if (A[i]!=A[j]) A[++j]=A[i];
		return ++j;//返回新数组长度
	}
	
	
	
	
/*    同思想，不同写法
 * 		public static int removeDuplicates(int[] A) {
        int count = 0;
        int len = A.length;
        if(len==0) return 0;
        for (int i = 0; i < len; i++) {
            if (count == 0 || A[i] != A[count - 1]) {
                A[count++] = A[i];
            }
        }
        return count;
    }*/
}
