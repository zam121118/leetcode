package leetcode;
/*follow up for "Remove Duplicates":what if duplicates are allowed at most twice?
*/	

public class RemoveDuplicatesfromSortedArrayⅡ {
	public int removeDuplicates(int A[]){
		int len=A.length;
		if(len<3) return len;
		int index=2;
		for(int i=0;i<len;i++){
			if(A[i]!=A[index-2]){
				A[index++]=A[i];
				
			}
		}
		return index;
	}
	
	public static void main(String args[]){
		int b[]={1,1,1,2,2,3};
		int index=2;
		for(int i=0;i<6;i++){
			if(b[i]!=b[index-2]){
				b[index++]=b[i];
			}
		}
		System.out.print(index);
		for(int i=0;i<index;i++){
			System.out.print(b[i]);

		}
	}
}
