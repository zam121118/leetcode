package leetcode;

import java.util.HashSet;

/*Given a sorted array, remove the duplicates in place such that 
 * each element appear only once and return the new length.
Do not allocate extra space for another array, 
you must do this in place with constant memory.*/

public class RemoveDuplicatesfromSortedArray2 {
	public int removeDuplicates(int A[]){
		HashSet<Integer> set=new HashSet<Integer>();
		int count=0;
		for(int i=0;i<A.length;i++){
			if(!set.contains(A[i])){//不做判断可以，set会自动去重
				set.add(A[i]);
				A[count++]=A[i];
			}
		}
		
		return count;//新数组长度诶count
	}

}
