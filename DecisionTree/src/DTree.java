import java.util.*;
import java.io.*;


public class DTree {
	//tested
	public static ArrayList<String> importAttribute(){
		//ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();

		File file=new File("src/training_set.csv");
		FileReader fr=null;
		//String line=null;
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
	public Map<String,Integer> countLastColumn(ArrayList<ArrayList<String>> data){
		
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
		return result;
	}
	
	
}

class Node{
	private String name;
	private Node[] parent;
	private Node[] child;
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> attribute;
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
}

class IG{
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> attribute;
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

	//calculate initial entropy
	public static double iniEntropy(int dataSize, Map<String,Integer> countMap){
		double result=-1;
		int zeroCount=countMap.get(0);
		int oneCount=countMap.get(1);
		double zeroProb=zeroCount/dataSize;
		double oneProb=oneCount/dataSize;
		result=-zeroProb*(Math.log(zeroProb)/Math.log(2))-oneProb*(Math.log(oneProb)/Math.log(2));
		
		return result;
	}

	//get column data of assigned attribute
	public static ArrayList<String> getColumn(ArrayList<ArrayList<String>> data,int index){
		ArrayList<String> column=new ArrayList<String>();
		Iterator<ArrayList<String>> it=data.iterator();
		while(it.hasNext()){
			String temp=it.next().get(index);
			column.add(temp);
		}
		return column;
	}
	
	//count attribute data
	public static Map<String,Integer> countData(ArrayList<ArrayList<String>> data,int index){
		ArrayList<String> column=getColumn(data,index);
		Iterator<String> it=column.iterator();
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		int zeroCount=0,oneCount=0;
		while(it.hasNext()){
			if(it.next().equals("1")){
				oneCount++;
			}else{
				zeroCount++;
			}
		}
		dataMap.put("0", zeroCount);
		dataMap.put("1", oneCount);
		return dataMap;
	}
	
	//Split data set based on attribute
	public ArrayList<ArrayList<String>> splitDataSet(int attribute,String value){
		ArrayList<ArrayList<String>> subDataSet=new ArrayList<ArrayList<String>>();
		int j=0;
		while(data.size()>j){
			if(data.get(j).get(attribute).equals(value)){
				subDataSet.add(data.get(j));
			}
			j++;
		}
		return subDataSet;
	}
	
}