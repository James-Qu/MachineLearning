import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class IG{
	/*private ArrayList<Map<String,Integer>> data;
	private ArrayList<String> attributeList;
	public IG(ArrayList<Map<String,Integer>> data2, ArrayList<String> attributeList) {
		this.data=data2;
		this.attributeList=attributeList;
	}
	public ArrayList<Map<String,Integer>> getData() {
		return data;
	}
	public void setData(ArrayList<Map<String,Integer>> data) {
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
		for(int i=0;i<data.size();i++){
			line=data.get(i);
			spcificData=line.get(index);
			if(!range.contains(spcificData)){
				range.add(spcificData);
			}
		}
		for(int i=0;i<data.size();i++){
			Map<String,Integer> record=data.get(i);
			spcificData=record.get(attributeKey).toString();
			if(!range.contains(spcificData)){
				range.add(spcificData);
			}
		}

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
	public ArrayList<Map<String, Integer>> splitDataSet(String attribute,String value){
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
	public double conditionalEntropy(String attribute){
		double result=0;
		ArrayList<String> dataRange=getRange(data, attribute); //see what the value range is(0 or 1 or both)
		DTree tree=new DTree();
		Map<String,Integer> countMap;
		double n1=0;
		for(int i=0;i<dataRange.size();i++){
			double zeroCount=0,oneCount=0;
			ArrayList<Map<String, Integer>> subDataSet=splitDataSet(attribute, dataRange.get(i));
			countMap=countLastColumn(subDataSet);
			zeroCount=countMap.get(Integer.toString(0));
			oneCount=countMap.get(Integer.toString(1));

			n1=(double)subDataSet.size()/data.size();
			result+=n1*entropyCalculation(zeroCount, oneCount);

		}

		return result;
	}

	//get best attribute to split
	public String bestAttribute(double iniEntropy,ArrayList<String> attributeList){
		int chosenAttribute=-1;
		double max=0,ig=0;
		int j=0;
		String attributeName="";
		double secondBestIG=0;

		while(attributeList.size()-1>j){
			String attributeKey=attributeList.get(j);
			max=iniEntropy-conditionalEntropy(attributeKey);
			System.out.println("iniEntropy is: "+iniEntropy+"| current max: "+max  + "|" + " This round ig is: "+ig);
			
			if(max>ig){
				secondBestIG=ig;
				ig=max;
				chosenAttribute=j;
				attributeName=attributeList.get(chosenAttribute);
			}
			j++;
			System.out.println("chosenAttribute is:"+chosenAttribute+" attri name is: "+attributeName+" attribute list is: "+attributeList);
		}
		if(chosenAttribute==-1){
			chosenAttribute=0;
			
			return "end";
			
			//System.out.println("attribute list is: "+attributeList);
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
	
	//count last column copy
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
	}*/

}