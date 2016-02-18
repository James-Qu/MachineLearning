import java.util.ArrayList;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		/*int nodesToPrune=Integer.parseInt(args[0]);
		String trainingPath=args[1];
		String validationPath=args[2];
		String testPath=args[3];
		String isPrint=args[4];*/

	/*	ArrayList<Map<String, Integer>> trainingData=DTree.importData(trainingPath);
		ArrayList<String> trainingDataAttributeList=DTree.importAttribute(trainingPath);


		ArrayList<Map<String, Integer>> validationData=DTree.importData(validationPath);
		ArrayList<String> validationDataAttributeList=DTree.importAttribute(validationPath);

		ArrayList<String> testDataAttributesList=DTree.importAttribute(testPath);
		ArrayList<Map<String, Integer>> testData=DTree.importData(testPath);*/
		
		
		
		ArrayList<Map<String, Integer>> trainingData=DTree.importData("training_set.csv");
		ArrayList<String> trainingDataAttributeList=DTree.importAttribute("training_set.csv");


		ArrayList<Map<String, Integer>> validationData=DTree.importData("validation_set.csv");
		ArrayList<String> validationDataAttributeList=DTree.importAttribute("validation_set.csv");

		ArrayList<String> testDataAttributesList=DTree.importAttribute("test_set.csv");
		ArrayList<Map<String, Integer>> testData=DTree.importData("test_set.csv");
		
		

		DTree decisionTree=new DTree();
		Node root=decisionTree.createTree(trainingData, trainingDataAttributeList);
		//if(isPrint.equals("1")){
			decisionTree.printTree(root,0);
		//}
		System.out.println("Total nodes: "+decisionTree.getKeyGenerator());
		double accuracy=decisionTree.getAccuracy(root, trainingData);
		System.out.println("The accuracy of training data is: "+accuracy);
		System.out.println("The total leaf depth sum: "+decisionTree.getDepthCount());
		System.out.println("The leaf node count: "+decisionTree.getLeafCount());
		System.out.println("The average depth is: "+decisionTree.getDepthCount()/decisionTree.getLeafCount());

		//Node newRoot=decisionTree.createTree(trainingData, trainingDataAttributeList);
		double valiAccuracy=decisionTree.getAccuracy(root, validationData);
		System.out.println("The accuracy of validation data is: "+valiAccuracy);
		decisionTree.prune(root, 4);
		double afterPruneAccuracy=decisionTree.getAccuracy(root, validationData);
		System.out.println("The accuracy of validation after prune is: "+afterPruneAccuracy);

		if(valiAccuracy<afterPruneAccuracy){
			System.out.println("Pruned tree is used, the accuracy for test data is: "+decisionTree.getAccuracy(root, testData));
		}else{
			DTree newDecisionTree=new DTree();
			Node originRoot=newDecisionTree.createTree(trainingData, trainingDataAttributeList);
			System.out.println("Original tree is used, the accuracy for the test data is: "+decisionTree.getAccuracy(originRoot, testData));
		}
		
		DTree randomTree=new DTree();
		Node randomTreeRoot=randomTree.createRandomTree(trainingData, trainingDataAttributeList);
		randomTree.printTree(randomTreeRoot, 0);
		System.out.println("Total nodes: "+randomTree.getKeyGenerator());
		System.out.println("Accuracy: "+randomTree.getAccuracy(randomTreeRoot, testData));
		System.out.println("Total depth sum: "+randomTree.getRandomDepthSum());
		System.out.println("Leaf nodes: "+ randomTree.getRandomLeafCount());
		System.out.println("Average depth: "+randomTree.getRandomDepthSum()/randomTree.getRandomLeafCount());
	}
}
