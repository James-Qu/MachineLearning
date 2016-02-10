import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class IG{
	private ArrayList<ArrayList<String>> data;
	private ArrayList<String> attributeList;
	public IG(ArrayList<ArrayList<String>> data2, ArrayList<String> attributeList) {
		this.data=data2;
		this.attributeList=attributeList;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	public ArrayList<String> getAttribute() {
		return this.attributeList;
	}
	public void setAttribute(ArrayList<String> attribute) {
		this.attributeList = attribute;
	}

	//calculate initial entropy
	public double iniEntropy(int dataSize, Map<String,Integer> countMap){
		/*double result=-1;
		int zeroCount=countMap.get(0);
		int oneCount=countMap.get(1);
		double zeroProb=zeroCount/dataSize;
		double oneProb=oneCount/dataSize;
		result=-zeroProb*(Math.log(zeroProb)/Math.log(2))-oneProb*(Math.log(oneProb)/Math.log(2));*/
		
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

	//get column data of assigned attribute
	public static ArrayList<String> getRange(ArrayList<ArrayList<String>> data,int index){
		/*ArrayList<String> column=new ArrayList<String>();
		Iterator<ArrayList<String>> it=data.iterator();
		while(it.hasNext()){
			String temp=it.next().get(index);
			if(!column.contains(temp))
			column.add(temp);
		}*/
		
		/*ArrayList<String> range=new ArrayList<String>();
		String specificData="";
		int j=0;
		while(data.size()>j){
			specificData=data.get(index).get(j);
			if(!range.contains(specificData)){
				range.add(specificData);
			}
			j++;
		}*/
		
		ArrayList<String> range=new ArrayList<String>();
		ArrayList<String> line=new ArrayList<String>();
		String spcificData="";
		for(int i=0;i<data.size();i++){
			line=data.get(i);
			spcificData=line.get(index);
			if(!range.contains(spcificData)){
				range.add(spcificData);
			}
		}
		return range;
	}

	//count attribute data
	public static Map<String,Integer> countData(ArrayList<ArrayList<String>> data,int index){
		/*ArrayList<String> column=getRange(data,index);
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
		dataMap.put("1", oneCount);*/
		
		ArrayList<String> instance=getRange(data,index);
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		String specificData;
		int j=0;
		while(data.size()>j){
			instance=data.get(j);
			specificData=instance.get(index);
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

	//calculate H(Y|X) given Y as parameter
	public double conditionalEntropy(int attri){
		double result=0;
		ArrayList<String> dataRange=getRange(data, attri); //see what the value range is(0 or 1 or both)

		//Maybe not necessary
		//DTree tree=new DTree();
		/*Map<String,Integer> countMap;
		int j=0;
		int zeroCount=0,oneCount=0;
		while(j<dataRange.size()){
			int zeroCount=0,oneCount=0;
			ArrayList<ArrayList<String>> subDataSet=splitDataSet(attri, dataRange.get(j));
			countMap=DTree.countLastColumn(subDataSet);
			//Problem! Maybe null.
			zeroCount=countMap.get(0);
			oneCount=countMap.get(1);
			j++;
		}
		if(zeroCount==oneCount){
			result=1;
		}else if(zeroCount==0||oneCount==0){
			result=0;
		}else{
			int total=zeroCount+oneCount;
			double zeroProb=zeroCount/total;
			double oneProb=oneCount/total;
			result=-zeroProb*(Math.log(zeroProb)/Math.log(2))-oneProb*(Math.log(oneProb)/Math.log(2));
		}*/
		
		DTree tree=new DTree();
		Map<String,Integer> countMap;
		double n1=0;
		for(int i=0;i<dataRange.size();i++){
			double zeroCount=0,oneCount=0;
			ArrayList<ArrayList<String>> subDataSet=splitDataSet(attri, dataRange.get(i));
			countMap=DTree.countLastColumn(subDataSet);
			zeroCount=countMap.get(Integer.toString(0));
			oneCount=countMap.get(Integer.toString(1));
			
			/*if(zeroCount==oneCount){
				result=1;
			}else if(zeroCount==0||oneCount==0){
				result=0;
			}else{
				double total=zeroCount+oneCount;
				double zeroProb=zeroCount/total;
				double oneProb=oneCount/total;
				result=-zeroProb*(Math.log(zeroProb)/Math.log(2))-oneProb*(Math.log(oneProb)/Math.log(2));
			}*/
			n1=(double)subDataSet.size()/data.size();
			result+=n1*entropyCalculation(zeroCount, oneCount);
			
		}

		return result;
	}

	//get best attribute to split
	public int bestAttribute(double iniEntropy){
		int chosenAttribute=-1;
		double max=0,ig=0;
		int j=0;
		String attributeName;
		double secondBestIG;
		
		while(attributeList.size()-1>j){
			max=iniEntropy-conditionalEntropy(j);
			if(max>ig){
				secondBestIG=ig;
				ig=max;
				chosenAttribute=j;
				attributeName=attributeList.get(chosenAttribute);
			}
			j++;
		}
		//At the same time, delete this best attribute from the attribute list
		return chosenAttribute;
	}
	
	//Entropy calculation
	public static double entropyCalculation(double zeroCount,double oneCount){
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

}