import java.util.*;
import java.io.*;


public class DTree {
	public static ArrayList<String> importAttribute(){
		//ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();

		File file=new File("training_set.csv");
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
	public static ArrayList<ArrayList<String>> importData(){
		ArrayList<ArrayList<String>> data=new ArrayList<ArrayList<String>>();
		File file=new File("training_set.csv");
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
	public static double iniEntropy(ArrayList<ArrayList<String>> data){
		if(data.size()==0){
			return 0;
		}else{
			//Need a count function to get the probability

		}
		return 0;
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
	
	//calculate the best attribute to split
	
}