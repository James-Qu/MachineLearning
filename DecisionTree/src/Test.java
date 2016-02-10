import java.util.*;


public class Test {
	private static ArrayList<ArrayList<String>> data=DTree.importData();
	private static ArrayList<String> attribute=DTree.importAttribute();
	public static void main(String[] args) {
		//testImportAttribute();
		//testImportData();
		//testGetRange();
		//testCountData();
		//testSplitDataSet();
		//testCountLastColumn();
		//testIniEntropy();
		testConditionalEntropy();
	}

	//test importAttribute()
	public static void testImportAttribute(){
		ArrayList<String> attribute=DTree.importAttribute();
		Iterator<String> it=attribute.iterator();
		System.out.println("import attribute test:");
		while(it.hasNext()){
			System.out.print(it.next()+" ");
		}
		System.out.println();
	}

	//test importData()
	public static void testImportData(){
		//test import data
		ArrayList<ArrayList<String>> Data=DTree.importData();
		/*Iterator<ArrayList<String>> it1=Data.iterator();
				while(it1.hasNext()){
					for(int i=0;i<it1.next().size();i++){
						System.out.print(it1.next().get(i)+" ");
					}
				}*/

		/*for(int i=0;i<Data.size();i++){
					ArrayList<String> record=Data.get(i);
					for(int n=0;n<record.size();n++){
						System.out.print(record.get(n)+" ");
					}
				}*/
		System.out.println("import data test");
		for (ArrayList<String> l1 : Data) {
			for (String n : l1) {
				System.out.print(n + " "); 
			}

			System.out.println();
		} 
	}

	//test getRange()
	public static void testGetRange(){
		ArrayList<ArrayList<String>> data=DTree.importData();
		ArrayList<String> result=IG.getRange(data, 2);
		System.out.println("getRange test: column2");
		for(String i:result){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	//test count data
	public static void testCountData(){
		ArrayList<ArrayList<String>> data=DTree.importData();
		Map<String,Integer> resultMap=IG.countData(data, 1);
		System.out.println("countData test: column2");
		for(Map.Entry<String, Integer> entry: resultMap.entrySet()){
			System.out.println("Key: "+entry.getKey()+" value: "+entry.getValue());
		}
	}
	
	//test splitDataSet
	public static void testSplitDataSet(){
		ArrayList<ArrayList<String>> data=DTree.importData();
		ArrayList<String> attribute=DTree.importAttribute();
		IG ig=new IG(data,attribute);
		System.out.println("test splitDataSet:");
		ArrayList<ArrayList<String>> result=ig.splitDataSet(1,Integer.toString(1));
		for (ArrayList<String> l1 : result) {
			for (String n : l1) {
				System.out.print(n + " "); 
			}

			System.out.println();
		}
	}
	
	//test countLastColumn
	public static void testCountLastColumn(){
		Map<String,Integer> resultMap=DTree.countLastColumn(data);
		System.out.println("test countLastColumn:");
		for(Map.Entry<String, Integer> entry: resultMap.entrySet()){
			System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
		}
	}
	
	//test iniEntropy
	public static void testIniEntropy(){
		Map<String,Integer> resultMap=DTree.countLastColumn(data);
		IG ig=new IG(data,attribute);
		double result=ig.iniEntropy(data.size(), resultMap);
		System.out.println("test iniEntropy:");
		System.out.println("iniEntropy is: "+result);
	}
	
	//test conditionalEntropy
	public static void testConditionalEntropy(){
		IG ig=new IG(data,attribute);
		double result=ig.conditionalEntropy(1);
		System.out.println("choosing the first attribute to split: "+result);
	}
	
}
