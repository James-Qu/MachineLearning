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


}
