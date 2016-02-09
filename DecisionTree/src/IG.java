import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	public static ArrayList<String> getRange(ArrayList<ArrayList<String>> data,int index){
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
		ArrayList<String> column=getRange(data,index);
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

	//calculate H(Y|X) given Y as parameter
	public double conditionalEntropy(int attri){
		double result=0;
		ArrayList<String> dataRange=getRange(data, attri); //see what the value range is(0 or 1 or both)

		//Maybe not necessary
		//DTree tree=new DTree();
		Map<String,Integer> countMap;
		int j=0;
		int zeroCount=0,oneCount=0;
		while(j<dataRange.size()){
			/*int zeroCount=0,oneCount=0;*/
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
		}

		return result;
	}

	//get best attribute to split
	public int bestAttribute(double iniEntropy){
		int chosenAttribute=-1;
		double max=0,ig=0;
		int j=0;
		while(attribute.size()>j){
			max=iniEntropy-conditionalEntropy(j);
			if(max>ig){
				ig=max;
				chosenAttribute=j;
			}
			j++;
		}
		return chosenAttribute;
	}


}