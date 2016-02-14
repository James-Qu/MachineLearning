import java.util.*;
import java.util.Map.Entry;
import java.io.*;


public class DTree {
	private static boolean[] attributeIsUsed;
	private static int keyGenerator=0;

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
	public Map<String,Integer> countLastColumn(ArrayList<Map<String, Integer>> data){

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

	public boolean scanCountMap(Map<String,Integer> countMap){
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

		if(keyGenerator==41){
			System.out.println("This is node 40");
			System.out.println("data on this node is:");
			System.out.println(data);
			System.out.println("attributelist is:");
			System.out.println(attributes);
		}
		System.out.println("node number: "+keyGenerator);
		node.setData(data);
		node.setAttributeList(attributes);
		String chosenAttribute = "";
		Map<String,Integer> countMap=countLastColumn(data);

		//if all data + or -, return single node
		if(scanCountMap(countMap)){
			System.out.println("should follow by LEAF");
			for(Map.Entry<String, Integer> entry:countMap.entrySet()){
				if(entry.getValue()>0){
					if(entry.getKey().equals("0")){
						System.out.println("left LEAF");
						node.setName("leaf");
						node.setLabel("0");
						break;
					}else{
						System.out.println("right LEAF");
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
			System.out.println("this node is a LEAF ");
			node.setName("leaf:");
			node.setLabel(getMajority(countMap));
			node.setLeaf(true);
			return node;
		}

		chosenAttribute=getBestAttribute(data, attributes);
		/*if(attributes.size()!=0){
			attributes.remove(chosenAttribute); 
		}*/
		//several attribute left, no ig.
		if(chosenAttribute.equals("end")){
			System.out.println("this node is also a LEAF ");
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
						//node.getChild().add(0, createTree(subData,attributes));
						node.setlChild(createTree(subData,subAttributes));
						//node.getlChild().setUpperBranchValue(value);
					}else{
						//node.getChild().add(1, createTree(subData, attributes));
						node.setrChild(createTree(subData, subAttributes));
						//node.getrChild().setUpperBranchValue(value);
					}
					//node.getChild().add(createTree(subData,attributes));
				}
			}
			j++;
		}
		return node;
	}



	//Moved from IG
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
		/*for(int i=0;i<data.size();i++){
				line=data.get(i);
				spcificData=line.get(index);
				if(!range.contains(spcificData)){
					range.add(spcificData);
				}
			}*/
		for(int i=0;i<data.size();i++){
			Map<String,Integer> record=data.get(i);
			spcificData=record.get(attributeKey).toString();
			if(!range.contains(spcificData)){
				range.add(spcificData);
			}
		}
		Collections.sort(range);
		/*if(range.size()<2){
				return range;
			}
			for(int i=0;i<range.size();i++){
				temp=Integer.parseInt(range.get(i));
				if(temp>max){
					max=temp;
				}
			}*/

		return range;
	}

	//count attribute data
	public Map<String,Integer> countData(ArrayList<Map<String,Integer>> data,String index){
		//ArrayList<String> instance=getRange(data,index);
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
		//double secondBestIG=0;

		while(attributeList.size()>j){
			String attributeKey=attributeList.get(j);
			max=iniEntropy-conditionalEntropy(data,attributeKey);
			//System.out.println("iniEntropy is: "+iniEntropy+"| current max: "+max  + "|" + " This round ig is: "+ig);

			if(max>ig){
				//secondBestIG=ig;
				ig=max;
				chosenAttribute=j;
				attributeName=attributeList.get(chosenAttribute);
			}
			j++;
			//System.out.println("chosenAttribute is:"+chosenAttribute+" attri name is: "+attributeName+" attribute list is: "+attributeList);
		}
		if(chosenAttribute==-1){
			chosenAttribute=0;
			System.out.println("attribute=-1, no more ig. should add leaf node");
			return "end";

			//System.out.println("attribute list is: "+attributeList);
		}
		System.out.println("The best attribute is: "+ attributeList.get(chosenAttribute));
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
}