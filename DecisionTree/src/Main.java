import java.util.ArrayList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		/*int nodesToPrune=Integer.parseInt(args[0]);
		String trainingPath=args[1];
		String validationPath=args[2];
		String testPath=args[3];
		String isPrint=args[4];*/
		
		ArrayList<Map<String, Integer>> trainingData=DTree.importData("src/training_set.csv");
		ArrayList<String> trainingDataAttributeList=DTree.importAttribute("src/training_set.csv");
	
		
		ArrayList<Map<String, Integer>> validationData=DTree.importData("src/validation_set.csv");
		ArrayList<String> validationDataAttributeList=DTree.importAttribute("src/validation_set.csv");
		
		ArrayList<String> testDataAttributesList=DTree.importAttribute("src/test_set.csv");
		ArrayList<Map<String, Integer>> testData=DTree.importData("src/test_set.csv");
		
		DTree decisionTree=new DTree();
		Node root=decisionTree.createTree(trainingData, trainingDataAttributeList);
		decisionTree.printTree(root,0);
		System.out.println("Total nodes: "+DTree.getKeyGenerator());
		double accuracy=decisionTree.getAccuracy(root, trainingData);
		System.out.println("The accuracy of training data is:"+accuracy);
		
		//Node newRoot=decisionTree.createTree(trainingData, trainingDataAttributeList);
		double valiAccuracy=decisionTree.getAccuracy(root, validationData);
		System.out.println("The accuracy of validation data is:"+valiAccuracy);
		decisionTree.prune(root, 3);
		double afterPruneAccuracy=decisionTree.getAccuracy(root, validationData);
		System.out.println("The accuracy of validation after prune is:"+afterPruneAccuracy);
		
		if(valiAccuracy<afterPruneAccuracy){
			System.out.println("Pruned tree is used, the accuracy for test data is: "+decisionTree.getAccuracy(root, testData));
		}else{
			DTree newDecisionTree=new DTree();
			Node originRoot=newDecisionTree.createTree(trainingData, trainingDataAttributeList);
			System.out.println("original tree is used, the accuracy for the test data is: "+decisionTree.getAccuracy(originRoot, testData));
		}
	}
}
