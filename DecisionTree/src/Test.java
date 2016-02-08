import java.util.*;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test import attribute
		ArrayList<String> attribute=DTree.importAttribute();
		Iterator<String> it=attribute.iterator();
		while(it.hasNext()){
			System.out.print(it.next()+" ");
		}
		
		//test import data
		ArrayList<ArrayList<String>> Data=DTree.importData();
		/*Iterator<ArrayList<String>> it1=Data.iterator();
		while(it1.hasNext()){
			for(int i=0;i<it1.next().size();i++){
				System.out.print(it1.next().get(i)+" ");
			}
		}*/
		
		
		/*for(int i=0;i<Data.size();i++){
			ArrayList<String> record=Data.get(i);
			for(int n=0;n<record.size();n++){
				System.out.print(record.get(n)+" ");
			}
		}*/
		
		for (ArrayList<String> l1 : Data) {
			   for (String n : l1) {
			       System.out.print(n + " "); 
			   }

			   System.out.println();
			} 
	}

}
