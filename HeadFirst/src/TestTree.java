import java.awt.print.Book;
import java.util.*;

public class TestTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestTree().go();
	}
	public void go(){
		Book b1=new Book("How Cats Work");
		Book b2=new Book("Rmix your Body");
		Book b3=new Book("Finding Emo");

		TreeSet<Book> tree=new TreeSet<Book>();
		tree.add(b1);
		tree.add(b2);
		tree.add(b3);
		System.out.println(tree);
	}
	public class Book {
		String title;
		public Book(String t){
			title=t;
		}
	}
}
