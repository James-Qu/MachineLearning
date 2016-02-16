import java.util.*;


public class Test {
	private static ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
	private static ArrayList<String> attribute=DTree.importAttribute("src/training_set.csv");
	public static void main(String[] args) {
		//testImportAttribute();
		//testImportData();
		testGetRange();
		//testCountData();
		//testSplitDataSet();
		//testCountLastColumn();
		//testIniEntropy();
		//testConditionalEntropy();
		//testBestAttribute();
		//testGetBestAttribute();
	}

	//test importAttribute()
	public static void testImportAttribute(){
		ArrayList<String> attribute=DTree.importAttribute("src/training_set.csv");
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
		ArrayList<Map<String,Integer>> Data=DTree.importData("src/training_set.csv");
		System.out.println("import data test");
		for (Map<String,Integer> l1 : Data) {
			for (Map.Entry<String, Integer> entry:l1.entrySet()) {
				System.out.print(/*"Key: " +*/entry.getKey()+ " "+entry.getValue()+" "); 
			}

			System.out.println();
		} 
		System.out.println(data.size());
	}

	//test getRange()
	public static void testGetRange(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		ArrayList<String> result=dt.getRange(data, "XB");
		System.out.println("getRange test: column XM");
		for(String i:result){
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	//test count data
	public static void testCountData(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		Map<String,Integer> resultMap=dt.countData(data, "XH");
		System.out.println("countData test: XG");
		for(Map.Entry<String, Integer> entry: resultMap.entrySet()){
			System.out.println("Key: "+entry.getKey()+" value: "+entry.getValue());
		}
	}
	
	//test splitDataSet
	public static void testSplitDataSet(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		//IG ig=new IG(data,attribute);
		System.out.println("test splitDataSet on Class:");
		ArrayList<Map<String,Integer>> result=dt.splitDataSet(data,"XH",Integer.toString(1));
		for (Map<String, Integer> l1 : result) {
			for (Map.Entry<String, Integer> entry: l1.entrySet()) {
				System.out.print("Key: " +entry.getKey()+ "value: "+entry.getValue()); 
			}

			System.out.println();
		}
		System.out.println(result.size());
	}
	
	//test countLastColumn
	public static void testCountLastColumn(){
		DTree dt=new DTree();
		Map<String,Integer> resultMap=dt.countLastColumn(data);
		System.out.println("test countLastColumn:");
		for(Map.Entry<String, Integer> entry: resultMap.entrySet()){
			System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
		}
	}
	
	//test iniEntropy
	public static double testIniEntropy(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		Map<String,Integer> resultMap=dt.countLastColumn(data);
		double result=dt.iniEntropy(data.size(), resultMap);
		System.out.println("test iniEntropy:");
		System.out.println("iniEntropy is: "+result);
		return result;
	}
	
	//test conditionalEntropy
	public static void testConditionalEntropy(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		double result=dt.conditionalEntropy(data,"XO");
		System.out.println("conditional entropy is: "+result+" The gain is: "+(testIniEntropy()-result));
	}
	
	//test bestAttribute
	public static void testBestAttribute(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		DTree dt=new DTree();
		Map<String,Integer> resultMap=dt.countLastColumn(data);
		double result=dt.iniEntropy(data.size(), resultMap);
		String bestAttributeKey=dt.bestAttribute(data,result,attribute);
		System.out.println("test bestAttribute");
		System.out.println("The best attribute index is: "+bestAttributeKey);
		
	}
	
	public static void testGetBestAttribute(){
		ArrayList<Map<String,Integer>> data=DTree.importData("src/training_set.csv");
		ArrayList<String> attributes=DTree.importAttribute("src/training_set.csv");
		DTree dt=new DTree();
		String result=dt.getBestAttribute(data, attributes);
		System.out.println(result);
	}
}
