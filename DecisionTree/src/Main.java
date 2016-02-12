import java.util.ArrayList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		ArrayList<Map<String, Integer>> data=DTree.importData();
		ArrayList<String> attributeList=DTree.importAttribute();
		DTree decisionTree=new DTree();
		Node root=decisionTree.createTree(data, attributeList);
		decisionTree.printTree(root,0);
	}
}
