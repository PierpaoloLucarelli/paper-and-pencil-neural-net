package src;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 1                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Exercise 1");
		System.out.println("-----------");
		
		int[] inputs = {1,0,1,1};
		int[] outputs = {0,0,1,1};
		Network n = new Network(inputs.length);
		trainNet(n, inputs, outputs);
		int[] result = n.test(inputs);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 2                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 2");
		System.out.println("-----------");
		int[] modifiedI = {1,0,0,1};
		System.out.println("Testing with incomplete input: " + Arrays.toString(modifiedI));
		result = n.test(modifiedI);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 3                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 3");
		System.out.println("-----------");
		int[] noizyI = {1,1,1,1};
		System.out.println("Testing with noizy input: " + Arrays.toString(noizyI));
		result = n.test(noizyI);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		System.out.println(n);
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 4                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 4");
		System.out.println("-----------");
		inputs = new int[] {1,0,1,0,0,1};
		outputs = new int[] {1,1,0,0,1,1};
		int[] newIns = {0,1,1,0,0,0};
		int[] newOuts = {1,1,0,1,0,1};
		n = new Network(inputs.length);
		trainNet(n, inputs, outputs);
		trainNet(n, newIns, newOuts);
		result = n.test(inputs);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		int[] newResult = n.test(newIns);
		System.out.println("Output neurons after testing: " + Arrays.toString(newResult));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 5                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 5");
		System.out.println("-----------");
		modifiedI = new int[] {1,0,0,0,0,1};
		System.out.println("Testing with incomplete input:");
		result = n.test(modifiedI);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		modifiedI = new int[] {0,1,0,0,0,0};
		System.out.println("Testing with noisy input:");
		result = n.test(modifiedI);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
	}
	
	public static void trainNet(Network n, int[] inputs, int[] outputs) {
		System.out.print("Training " + n.printSize());
		System.out.print(" with input: " + Arrays.toString(inputs));
		System.out.println(" and output: " + Arrays.toString(outputs));
		n.train(inputs, outputs);
	}

}
