import java.util.ArrayList;

public class Main {
	public Node createDTree(ArrayList<ArrayList<String>> data,ArrayList<String> attributeList){
		Node node=new Node();
		node.setData(data);
		node.setAttribute(attributeList);
		return node;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
