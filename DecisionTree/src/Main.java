import java.util.ArrayList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		ArrayList<ArrayList<String>> data=DTree.importData();
		ArrayList<String> attributeList=DTree.importAttribute();
		DTree decisionTree=new DTree();
		decisionTree.createDTree(data, attributeList);
	}
	
}
