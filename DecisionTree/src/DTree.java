import java.util.*;
import java.util.Map.Entry;
import java.io.*;


public class DTree {
	private static int keyGenerator=0;

	public static int getKeyGenerator() {
		return keyGenerator;
	}

	public static void setKeyGenerator(int keyGenerator) {
		DTree.keyGenerator = keyGenerator;
	}

	//tested
	public static ArrayList<String> importAttribute(String path){
		File file=new File(path);
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
	public static ArrayList<Map<String, Integer>> importData(String path){
		ArrayList<Map<String, Integer>> data=new ArrayList<Map<String,Integer>>();
		File file=new File(path);
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
				Map<String,Integer> record=new HashMap<String,Integer>();
				for(int i=0;i<temp1.length;i++){
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
	public Map<String,Integer> countLastColumn(ArrayList<Map<String, Integer>> data){

		Map<String,Integer> result=new HashMap<String,Integer>(); //Store the result of the count
		Map<String, Integer> record=new HashMap<String,Integer>(); 
		int j=0;
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

	public boolean scanCountMap(Map<String,Integer> countMap){
		for(Map.Entry<String, Integer> entry:countMap.entrySet()){
			if(entry.getValue()==0){
				return true;
			}
		}
		return false;
	}


	//get majority when attribute all used.
	public String getMajority(Map<String,Integer> lastColumnCountMap){
		String majority="";
		int max=-1;
		Iterator<Entry<String, Integer>> it=lastColumnCountMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Integer> entry=it.next();
			String key=entry.getKey();
			Integer value=entry.getValue();
			if(value>max){
				max=value;
				majority=key;
			}
		}

		return majority;
	}

	public void printTree(Node root,int level){
		for(int i=0;i<level;i++){
			System.out.print("|");
		}
		if(root==null){
			System.out.println("null root");
			return;
		}
		System.out.print(root.getName()+"=0 :");
		if(root.getlChild().isLeaf()==true){
			System.out.println(root.getlChild().getLabel());
		}else{
			System.out.println();
			printTree(root.getlChild(),level+1);
		}

		for(int i=0;i<level;i++){
			System.out.print("|");
		}
		System.out.print(root.getName()+"=1 :");
		if(root.getrChild().isLeaf()==true){
			System.out.println(root.getrChild().getLabel());
		}else{
			System.out.println();
			printTree(root.getrChild(), level+1);
		}
	}

	public Node createTree(ArrayList<Map<String, Integer>> data,
			ArrayList<String> attributes){
		ArrayList<String> subAttributes=new ArrayList<String>();
		//create a root node for tree
		Node node=new Node();
		node.setKey(keyGenerator++);
		node.setData(data);
		node.setAttributeList(attributes);
		String chosenAttribute = "";
		Map<String,Integer> countMap=countLastColumn(data);

		//if all data + or -, return single node
		if(scanCountMap(countMap)){
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
			return node;
		}

		//if attributes empty. return single node
		if(attributes.size()==0){
			node.setName("leaf:");
			node.setLabel(getMajority(countMap));
			node.setLeaf(true);
			return node;
		}

		chosenAttribute=getBestAttribute(data, attributes);
		//several attribute left, no ig.
		if(chosenAttribute.equals("end")){
			node.setName("leaf:");
			node.setLabel(getMajority(countMap));
			node.setLeaf(true);
			return node;
		}

		ArrayList<String> splitValue=getRange(data, chosenAttribute);
		node.setSplitValue(splitValue); 
		int j=0;
		while(splitValue.size()>j){
			String value=splitValue.get(j);
			ArrayList<Map<String,Integer>> subData=splitDataSet(data,chosenAttribute, value);
			if(subData.size()==0){
				Node leaf=new Node();
				leaf.setKey(keyGenerator++);
				leaf.setName("leaf:");
				leaf.setLabel(getMajority(countMap));
				leaf.setLeaf(true);
				if(value.equals("0")){
					node.setlChild(leaf);
				}else{
					node.setrChild(leaf);
				}
			}else{
				node.setName(chosenAttribute);
				if(attributes.size()!=0){
					attributes.remove(chosenAttribute); 
					subAttributes.addAll(attributes);
				}
				if(node.isLeaf()==false){

					if(value.equals("0")){
						node.setlChild(createTree(subData,subAttributes));
					}else{
						node.setrChild(createTree(subData, subAttributes));
					}
				}
			}
			j++;
		}
		return node;
	}




	//calculate initial entropy
	public double iniEntropy(int dataSize, Map<String,Integer> countMap){
		double result=0;
		Iterator it=countMap.entrySet().iterator();
		for(int i=0;it.hasNext();i++){
			Map.Entry<String, Integer> entry=(Entry<String, Integer>) it.next();
			Integer num=entry.getValue();
			double temp=(double)num/(double)dataSize;
			result+=-temp*(Math.log((double)temp)/Math.log((double)2));
		}
		return result;
	}

	//get column range of assigned attribute
	public ArrayList<String> getRange(ArrayList<Map<String,Integer>> data,String attributeKey){
		ArrayList<String> range=new ArrayList<String>();
		ArrayList<String> line=new ArrayList<String>();
		String spcificData="";
		int temp=-1,max=-1;
		for(int i=0;i<data.size();i++){
			Map<String,Integer> record=data.get(i);
			spcificData=record.get(attributeKey).toString();
			if(!range.contains(spcificData)){
				range.add(spcificData);
			}
		}
		Collections.sort(range);
		return range;
	}

	//count attribute data
	public Map<String,Integer> countData(ArrayList<Map<String,Integer>> data,String index){
		Map<String,Integer> instance=new HashMap<String,Integer>();
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		String specificData;
		int j=0;
		while(data.size()>j){
			instance=data.get(j);
			specificData=instance.get(index).toString();
			if(dataMap.containsKey(specificData)){
				dataMap.put(specificData, dataMap.get(specificData)+1);
			}else{
				dataMap.put(specificData, 1);
			}
			j++;
		}
		return dataMap;
	}

	//Split data set based on attribute
	public ArrayList<Map<String, Integer>> splitDataSet(ArrayList<Map<String,Integer>> data,String attribute,String value){
		ArrayList<Map<String, Integer>> subDataSet=new ArrayList<Map<String, Integer>>();
		int j=0;
		while(data.size()>j){
			if(data.get(j).get(attribute).toString().equals(value)){
				subDataSet.add(data.get(j));
			}
			j++;
		}
		return subDataSet;
	}

	//calculate H(Y|X) given Y as parameter
	public double conditionalEntropy(ArrayList<Map<String,Integer>> data,String attribute){
		double result=0;
		ArrayList<String> dataRange=getRange(data, attribute); //see what the value range is(0 or 1 or both)
		DTree tree=new DTree();
		Map<String,Integer> countMap;
		double n1=0;
		for(int i=0;i<dataRange.size();i++){
			double zeroCount=0,oneCount=0;
			ArrayList<Map<String, Integer>> subDataSet=splitDataSet(data,attribute, dataRange.get(i));
			countMap=countLastColumn(subDataSet);
			zeroCount=countMap.get(Integer.toString(0));
			oneCount=countMap.get(Integer.toString(1));

			n1=(double)subDataSet.size()/data.size();
			result+=n1*entropyCalculation(zeroCount, oneCount);

		}

		return result;
	}

	//get best attribute to split
	public String bestAttribute(ArrayList<Map<String,Integer>> data,double iniEntropy,ArrayList<String> attributeList){
		int chosenAttribute=-1;
		double max=0,ig=0;
		int j=0;
		String attributeName="";

		while(attributeList.size()>j){
			String attributeKey=attributeList.get(j);
			max=iniEntropy-conditionalEntropy(data,attributeKey);

			if(max>ig){
				ig=max;
				chosenAttribute=j;
				attributeName=attributeList.get(chosenAttribute);
			}
			j++;
		}
		if(chosenAttribute==-1){
			chosenAttribute=0;
			return "end";
		}
		return attributeList.get(chosenAttribute);
	}

	//Entropy calculation
	public double entropyCalculation(double zeroCount,double oneCount){
		double result=0;
		if(zeroCount==oneCount){
			result=1;
		}else if(zeroCount==0||oneCount==0){
			result=0;
		}else{
			double total=zeroCount+oneCount;
			double zeroProb=zeroCount/total;
			double oneProb=oneCount/total;
			result=-zeroProb*(Math.log(zeroProb)/Math.log(2))-oneProb*(Math.log(oneProb)/Math.log(2));
		}
		return result;
	}


	public String getBestAttribute(ArrayList<Map<String,Integer>> data,ArrayList<String> attributes){
		String chosenAttribute = "";
		Map<String,Integer> countMap=countLastColumn(data);
		double iniEntropy=iniEntropy(data.size(), countMap);
		chosenAttribute=bestAttribute(data,iniEntropy,attributes);
		return chosenAttribute;
	}

	//calculate the accuracy of tree.
	public double getAccuracy(Node node,ArrayList<Map<String,Integer>> data){
		Node originNode=node;
		String attribute="";
		Integer dataSetActual=null;
		double correctCount=0,errorCount=0;
		double accuracy=0;
		for(int i=0;i<data.size();i++){
			Map<String,Integer> instance=data.get(i);
			while(node.isLeaf()==false){
				attribute=node.getName();
				dataSetActual=instance.get(attribute);
				if(dataSetActual==0){
					node=node.getlChild();
				}else if(dataSetActual==1){
					node=node.getrChild();
				}
			}
			int label=Integer.parseInt(node.getLabel());
			Integer actual=instance.get("Class");
			if(label==actual){
				correctCount++;
			}else{
				errorCount++;
			}
			node=originNode;
		}
		accuracy=correctCount/(double)data.size();
		System.out.println("The correct instance is: "+correctCount+". The wrong instance is: "+errorCount);
		return accuracy;
	}

	//prune method
	public void prune(Node node,int howManyNodes){
		Node originNode=node;
		ArrayList<Integer> randomNodes=new ArrayList<Integer>();
		for(int i=0;i<howManyNodes;i++){
			int random=(int)(Math.random()*275);
			randomNodes.add(random);
			searchNode(node, random);
			addLabelAfterPrune(node, random);
			node=originNode;
		}


	}

	//search node function
	public void searchNode(Node node,int key){
		int nodeKey=node.getKey();
		if(node.getKey()==key){
			node.setLeaf(true);
			return;
		}
		if(node.isLeaf()==true){
			return;
		}
		searchNode(node.getlChild(),key);
		searchNode(node.getrChild(), key);
	}

	//add label after prune
	public void addLabelAfterPrune(Node node,int key){
		if(node.getKey()==key){
			ArrayList<Map<String,Integer>> nodeData=node.getData();
			Map<String,Integer> countLastColumnMap=countLastColumn(nodeData);
			String majority=getMajority(countLastColumnMap);
			node.setLabel(majority);
		}
		if(node.isLeaf()==true){
			return;
		}
		addLabelAfterPrune(node.getlChild(), key);
		addLabelAfterPrune(node.getrChild(), key);
	}

}