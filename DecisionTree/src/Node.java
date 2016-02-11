import java.util.ArrayList;

class Node{
	private String name; //split attribute name
	private String label;
	private ArrayList<Node> child=new ArrayList<Node>();
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

	
	public ArrayList<String> getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(ArrayList<String> attributeList) {
		this.attributeList = attributeList;
	}
	public ArrayList<String> getSplitRule() {
		return splitRule;
	}
	public void setSplitRule(ArrayList<String> splitRule) {
		this.splitRule = splitRule;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public ArrayList<Node> getChild() {
		return child;
	}
	public void setChild(ArrayList<Node> child) {
		this.child = child;
	}

	
	
}