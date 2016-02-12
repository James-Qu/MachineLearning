import java.util.*;
import java.util.Map.Entry;
import java.io.*;


public class DTree {
	private static boolean[] attributeIsUsed;


	//tested
	public static ArrayList<String> importAttribute(){
		File file=new File("src/training_set.csv");
		FileReader fr=null;
		String[] temp=new String[20];
		ArrayList<String> attribute=new ArrayList<String>();
		try {
			fr = new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine(); //Read attribute line
			temp=line.split(",");
			for(int i=0;i<temp.length-1;i++){
				attribute.add(temp[i]);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attribute;
	}

	//tested
	public static ArrayList<Map<String, Integer>> importData(){
		ArrayList<Map<String, Integer>> data=new ArrayList<Map<String,Integer>>();
		File file=new File("src/training_set.csv");
		FileReader fr;
		String line=null;
		String[] temp=new String[21];
		ArrayList<String> attribute=new ArrayList<String>();
		try {
			fr = new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			line=br.readLine(); //skip attribute line
			temp=line.split(",");
			for(int i=0;i<temp.length;i++){
				attribute.add(temp[i]);
			}
			
			while((line=br.readLine())!=null){
				String[] temp1=line.split(",");
				//ArrayList<String> record = new ArrayList<String>();
				Map<String,Integer> record=new HashMap<String,Integer>();
				for(int i=0;i<temp1.length;i++){
					//record.add(temp1[i]);
					record.put(temp[i], Integer.parseInt(temp1[i]));
				}
				data.add(record);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	//count the last column, how many 0, how many 1, return a map
	public static Map<String,Integer> countLastColumn(ArrayList<Map<String, Integer>> data){

		Map<String,Integer> result=new HashMap<String,Integer>(); //Store the result of the count
		Map<String, Integer> record=new HashMap<String,Integer>(); 
		int j=0;
		int count=0;
		while(data.size()>j){
			record=data.get(j);
			String temp=record.get("Class").toString();
			if(result.containsKey(temp)){
				result.put(temp,result.get(temp)+1);
			}else{
				result.put(temp, 1);
			}
			j++;
		}
		//if there is no 0 or 1 at the end. Add a value as 0.
		if(!result.containsKey(Integer.toString(0))){
			result.put(Integer.toString(0), 0);
		}
		if(!result.containsKey(Integer.toString(1))){
			result.put(Integer.toString(1), 0);
		}

		return result;
	}

	public static boolean scanCountMap(Map<String,Integer> countMap){
		for(Map.Entry<String, Integer> entry:countMap.entrySet()){
			if(entry.getValue()==0){
				return true;
			}
		}
		return false;
	}

	/*public Node createDTree(ArrayList<ArrayList<String>> data,ArrayList<String> attributeList){
		Node node=new Node();
		node.setData(data);
		node.setAttribute(attributeList);
		Map<String,Integer> countMap=DTree.countLastColumn(data);
		double iniEntropy=-1;
		int chosenAttribute=-1;
		//Node is pure
		//we need to scan the map to see if there is a 0 value count instead of using countMap.size<2
		if(scanCountMap(countMap)){
			//divide(node, countMap);
			for(Map.Entry<String, Integer> entry:countMap.entrySet()){
				if(entry.getValue()>0){
					if(entry.getKey().equals("0")){
						node.setName("0");
						break;
					}else{
						node.setName("1");
						break;
					}
				}
			}
			node.setLeaf(true);
			node.setlChild(null);
			node.setrChild(null);

			return node;
		}
		IG ig=new IG(data,attributeList);
		iniEntropy=ig.iniEntropy(data.size(), countMap);
		if(!Double.isNaN(iniEntropy)){
			//if(chosenAttribute!=-1){
			chosenAttribute=ig.bestAttribute(iniEntropy);
			//}else{
			if(chosenAttribute==-1){
				node.setLeaf(true);
				node.setlChild(null);
				node.setrChild(null);
				return node;
			}
			return node;
			//}
		}
		ArrayList<String> splitValue=IG.getRange(data, chosenAttribute);
		node.setSplitValue(splitValue); 
		//set chosen attribute for the node
		node.setSplitOption(attributeList.get(chosenAttribute));
		node.setName(attributeList.get(chosenAttribute));
		//delete chosenattribute from the list
		attributeList.remove(chosenAttribute);

		int j=0;
		while(splitValue.size()>j){
			String value=splitValue.get(j);
			ArrayList<ArrayList<String>> subData=ig.splitDataSet(chosenAttribute, value);
			if(attributeList.size()==0){
				//Node leaf=new Node();
				node.setName(Integer.toString(getMajority(countMap)));
				node.setLeaf(true);
				node.setlChild(null);
				node.setrChild(null);
				if(Integer.parseInt(value)==1){
					node.setrChild(leaf);;
				}else{
					node.setlChild(leaf);
				}
			}else{
				if(node.isLeaf()==false){
					if(Integer.parseInt(value)==0){
						node.setlChild(createDTree(subData,attributeList));
					}else{
						node.setrChild(createDTree(subData,attributeList));
					}
				}
			}
			j++;
		}
		
		return node;
	}*/

	private void divide(Node node, Map<String, Integer> countMap) {
		for(Map.Entry<String, Integer> entry:countMap.entrySet()){
			if(entry.getValue()>0){
				if(entry.getKey().equals("0")){
					node.setName("0");
					break;
				}else{
					node.setName("1");
					break;
				}
			}
		}
	}

	//get majority when attribute all used.
	public static int getMajority(Map<String,Integer> lastColumnCountMap){
		String majority="";
		int max=-1;
		Iterator<Entry<String, Integer>> it=lastColumnCountMap.entrySet().iterator();
		for(int i=0;it.hasNext();i++){
			Map.Entry<String, Integer> entry=it.next();
			String key=entry.getKey();
			Integer val=(Integer)entry.getValue();
			if(val>max){
				max=val;
				majority=key;
			}
		}
		return max;
	}

	public void printTree(Node root,int level){
		for(int i=0;i<level;i++){
			System.out.print("-");
		}
		if(root==null){
			System.out.println("null root");
			return;
		}
		System.out.println(root.getName());
		if(root.isLeaf()){
			return;
		}
		for(int i=0;i<root.getChild().size();i++){
			printTree(root.getChild().get(i),level+1);
		}
	}

	public Node createTree(ArrayList<Map<String, Integer>> data,
			ArrayList<String> attributes){
		//create a root node for tree
		Node node=new Node();
		node.setData(data);
		node.setAttributeList(attributes);
		String chosenAttribute = "";
		Map<String,Integer> countMap=DTree.countLastColumn(data);

		//if all data + or -, return single node
		if(scanCountMap(countMap)){
			//divide(node, countMap);
			for(Map.Entry<String, Integer> entry:countMap.entrySet()){
				if(entry.getValue()>0){
					if(entry.getKey().equals("0")){
						node.setName("leaf");
						node.setLabel("0");
						break;
					}else{
						node.setName("leaf");
						node.setLabel("1");
						break;
					}
				}
			}
			node.setLeaf(true);
			node.setChild(null);

			return node;
		}

		//if attributes empty. return single node
		if(attributes.size()==0){
			node.setName(Integer.toString(getMajority(countMap)));
			node.setLabel(Integer.toString(getMajority(countMap)));
			node.setLeaf(true);
			node.setChild(null);
			return node;
		}

		//Otherwise part
		IG ig=new IG(data,attributes);
		double iniEntropy=ig.iniEntropy(data.size(), countMap);
		//if(!Double.isNaN(iniEntropy)){
			chosenAttribute=ig.bestAttribute(iniEntropy,attributes);
			
		//}

		ArrayList<String> splitValue=IG.getRange(data, chosenAttribute);
		node.setSplitValue(splitValue); 
		int j=0;
		while(splitValue.size()>j){
			String value=splitValue.get(j);
			ArrayList<Map<String,Integer>> subData=ig.splitDataSet(chosenAttribute, value);
			/*for(int i=0;i<subData.size();i++){
				subData.get(i).remove(chosenAttribute);
			}*/
			if(subData.size()==0){
				Node leaf=new Node();
				leaf.setName(Integer.toString(getMajority(countMap)));
				leaf.setLabel(Integer.toString(getMajority(countMap)));
				leaf.setLeaf(true);
				//leaf.setChild(null);
			}else{
					node.setName(chosenAttribute);
				if(attributes.size()!=0){
					attributes.remove(chosenAttribute);
				}
				if(node.isLeaf()==false){
					node.getChild().add(
							createTree(subData,attributes));
				}
			}
			j++;
		}
		return node;
	}
}