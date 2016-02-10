import java.util.ArrayList;

class Node{
	private String name;
	private Node[] parent;
	private ArrayList<Node> child;
	private boolean isLeaf=false;
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> attributeList;
	private String splitOption;
	private ArrayList<String> splitRule;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Node[] getParent() {
		return parent;
	}
	public void setParent(Node[] parent) {
		this.parent = parent;
	}
	public ArrayList<Node> getChild() {
		return child;
	}
	public void setChild(ArrayList<Node> child) {
		this.child = child;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	public ArrayList<String> getAttribute() {
		return attributeList;
	}
	public void setAttribute(ArrayList<String> attribute) {
		this.attributeList = attribute;
	}
	public String getSplitOption() {
		return splitOption;
	}
	public void setSplitOption(String splitOption) {
		this.splitOption = splitOption;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public ArrayList<String> getSplitValue() {
		return splitRule;
	}
	public void setSplitValue(ArrayList<String> splitValue) {
		this.splitRule = splitValue;
	}

	
	
}