import java.util.ArrayList;
import java.util.Map;

class Node{
	private String name; //split attribute name
	private String label;
	//private ArrayList<Node> child=new ArrayList<Node>(2);
	private Node lChild=null;
	private Node rChild=null;
	private boolean isLeaf=false;
	private ArrayList<Map<String, Integer>> data;
	private ArrayList<String> attributeList;
	private String splitOption; 
	private ArrayList<String> splitValue;
	private String upperBranchValue;
	private int key;
	
	public Node getlChild() {
		return lChild;
	}
	public void setlChild(Node lChild) {
		this.lChild = lChild;
	}
	public Node getrChild() {
		return rChild;
	}
	public void setrChild(Node rChild) {
		this.rChild = rChild;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public ArrayList<String> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(ArrayList<String> attributeList) {
		this.attributeList = attributeList;
	}
	public ArrayList<Map<String, Integer>> getData() {
		return data;
	}
	public void setData(ArrayList<Map<String, Integer>> data) {
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
		return splitValue;
	}
	public void setSplitValue(ArrayList<String> splitValue) {
		this.splitValue = splitValue;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	/*public ArrayList<Node> getChild() {
		return child;
	}
	public void setChild(ArrayList<Node> child) {
		this.child = child;
	}*/
	public String getUpperBranchValue() {
		return upperBranchValue;
	}
	public void setUpperBranchValue(String upperBranchValue) {
		this.upperBranchValue = upperBranchValue;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}

	
	
}