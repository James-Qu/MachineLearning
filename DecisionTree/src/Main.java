import java.util.ArrayList;
import java.util.Map;

public class Main {
	private static int nodeNumberSetter=0;
	public Node createDTree(ArrayList<ArrayList<String>> data,ArrayList<String> attributeList){
		Node node=new Node();
		node.setData(data);
		node.setAttribute(attributeList);
		Map<String,Integer> countMap=DTree.countLastColumn(data);
		double iniEntropy;
		int chosenAttribute;
		//Node is pure
		if(countMap.size()<2){
			node.setName(Integer.toString(nodeNumberSetter++));
			node.setLeaf(true);
			node.setChild(null);
			
			return node;
		}/*else if(IG.getAttribute().size()==0){ //Attribute all used
			
		}*/
		IG ig=new IG(data,attributeList);
		iniEntropy=ig.iniEntropy(data.size(), countMap);
		chosenAttribute=ig.bestAttribute(iniEntropy);
		
		ArrayList<String> splitValue=ig.getRange(data, chosenAttribute);
		node.setSplitValue(splitValue);
		
		//set chosen attribute for the node
		node.setSplitOption(ig.getAttribute().get(chosenAttribute));
		
		int j=0;
		while(splitValue.size()>j){
			String value=splitValue.get(j);
			ArrayList<ArrayList<String>> subData=ig.splitDataSet(chosenAttribute, value);
			if(ig.getAttribute().size()==0){
				node.setName(Integer.toString(nodeNumberSetter++));
				node.setLeaf(true);
				node.setChild(null);
				//return node;
			}else{
				Node newNode=createDTree(subData,attributeList);
				node.getChild().add(newNode);
			}
			j++;
		}
		
		return node;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}