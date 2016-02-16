import java.util.ArrayList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		ArrayList<Map<String, Integer>> trainingData=DTree.importData("src/training_set.csv");
		ArrayList<String> trainingDataAttributeList=DTree.importAttribute("src/training_set.csv");
		
		ArrayList<Map<String, Integer>> validationData=DTree.importData("src/validation_set.csv");
		ArrayList<String> validationDataAttributeList=DTree.importAttribute("src/validation_set.csv");
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
	}
}
