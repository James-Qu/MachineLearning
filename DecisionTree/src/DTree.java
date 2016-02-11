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
			for(int i=0;i<temp.length;i++){
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
	public static ArrayList<ArrayList<String>> importData(){
		ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
		File file=new File("src/training_set.csv");
		FileReader fr;
		String line=null;
		try {
			fr = new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			br.readLine(); //skip attribute line
			while((line=br.readLine())!=null){
				String[] temp=line.split(",");
				ArrayList<String> record = new ArrayList<String>();
				for(int i=0;i<temp.length;i++){
					record.add(temp[i]);
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
	public static Map<String,Integer> countLastColumn(ArrayList<ArrayList<String>> data){

		Map<String,Integer> result=new HashMap<String,Integer>(); //Store the result of the count
		ArrayList<String> line=new ArrayList<String>(); 
		int j=0;
		int count=0;
		while(data.size()>j){
			line=data.get(j);
			String temp=line.get(line.size()-1);
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

	public Node createDTree(ArrayList<ArrayList<String>> data,ArrayList<String> attributeList){
		Node node=new Node();
		node.setData(data);
		node.setAttribute(attributeList);
		Map<String,Integer> countMap=DTree.countLastColumn(data);
		double iniEntropy=-1;
		int chosenAttribute=-1;
		//Node is pure
		//we need to scan the map to see if there is a 0 value count instead of using countMap.size<2
		//if(countMap.size()<2){
		if(scanCountMap(countMap)){
			//node.setName(Integer.toString(nodeNumberSetter++));
			//Map.Entry<String, Integer> entry=countMap.entrySet();
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
			chosenAttribute=ig.bestAttribute(iniEntropy);
		}
		ArrayList<String> splitValue=IG.getRange(data, chosenAttribute);
		node.setSplitValue(splitValue); 
		//set chosen attribute for the node
		node.setSplitOption(attributeList.get(chosenAttribute));
		//delete chosenattribute from the list
		attributeList.remove(chosenAttribute);

		int j=0;
		while(splitValue.size()>j){
			String value=splitValue.get(j);
			ArrayList<ArrayList<String>> subData=ig.splitDataSet(chosenAttribute, value);
			if(attributeList.size()==0){
				Node leaf=new Node();
				node.setName(Integer.toString(getMajority(countMap)));
				leaf.setLeaf(true);
				leaf.setlChild(null);
				leaf.setrChild(null);
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

}
