import java.util.ArrayList;

class Node{
	private String name;
	private Node[] parent;
	private Node[] child;
	private boolean isLeaf;
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> attribute;
	private ArrayList<String> splitOption;
	private ArrayList<String> splitValue;
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
	public Node[] getChild() {
		return child;
	}
	public void setChild(Node[] child) {
		this.child = child;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	public ArrayList<String> getAttribute() {
		return attribute;
	}
	public void setAttribute(ArrayList<String> attribute) {
		this.attribute = attribute;
	}
	public ArrayList<String> getSplitOption() {
		return splitOption;
	}
	public void setSplitOption(ArrayList<String> splitOption) {
		this.splitOption = splitOption;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public ArrayList<String> getSplitValue() {
		return splitValue;
	}
	public void setSplitValue(ArrayList<String> splitValue) {
		this.splitValue = splitValue;
	}
}