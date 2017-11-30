// floating branch

package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 1                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Exercise 1");
		System.out.println("-----------");

		int[] inputs = {1,0,1,1};
		int[] outputs = {1,0,0,1};
		int[] newinputs = {1,1,0,0};
		int[] newoutputs = {0,1,0,1};
		Network n = new Network(inputs.length);
		trainNet(n, inputs, outputs);
		trainNet(n, newinputs, newoutputs);
		int[] result = n.test(inputs, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		System.out.println(n);
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 2                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 2");
		System.out.println("-----------");
		int[] modifiedI = {1,0,0,1};
		System.out.println("Testing with incomplete input: " + Arrays.toString(modifiedI));
		result = n.test(modifiedI, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 3                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 3");
		System.out.println("-----------");
		int[] noizyI = {1,1,1,1};
		System.out.println("Testing with noizy input: " + Arrays.toString(noizyI));
		result = n.test(noizyI, true);
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
		result = n.test(inputs, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		int[] newResult = n.test(newIns, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(newResult));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 5                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("\nExercise 5");
		System.out.println("-----------");
		modifiedI = new int[] {1,0,0,0,0,1};
		System.out.println("Testing with incomplete input:");
		result = n.test(modifiedI, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		modifiedI = new int[] {0,1,0,0,0,0};
		System.out.println("Testing with noisy input:");
		result = n.test(modifiedI, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 6                                  //
		////////////////////////////////////////////////////////////////////////////////
		System.out.println("\nExercise 6");
		System.out.println("-----------");
		int size = 6;
		int iterations = 500;
		int total = 0;
		int maxCount = 0;
		double loadParameter = 0;
		double maxLoad = 0;
		double synapsesLoad = 0;
		double maxSyn = 0;
		for(int j = 0 ; j < iterations ; j++) {
			
			Network net = new Network(size);
//			System.out.println("n is; " + net);
			int[][] inPatterns = generateRandomPatterns(size);
			int[][] outPatterns = generateRandomPatterns(size);
			int count = 0;
			for(int i = 0 ; i < inPatterns.length ; i++) {
				net.train(inPatterns[i], outPatterns[i]);
				int[] res = net.test(inPatterns[i], false);
//				System.out.println("input syn: " + Arrays.toString(inPatterns[i]));
//				System.out.println("out syn: " + Arrays.toString(outPatterns[i]));
//				System.out.println("res: " + Arrays.toString(res)+"\n");
				if(Arrays.equals(res, outPatterns[i])) {
					count++;
				} else {
					// network has made an error
//					System.out.println(net);
					net.pop();
					double lp = net.getLoadParameter();
					double sl = net.synapsesLoad();
					loadParameter += lp;
					synapsesLoad += sl;
					if(lp > maxLoad)
						maxLoad = lp;
					if(sl > maxSyn)
						maxSyn = sl;
					break;
				}
			}
			total += count;
			if(count > maxCount)
				maxCount = count;
		}
		
		System.out.println("Network of size: " + size);
		System.out.println("Network on average trained: " + total/iterations + " patterns");
		System.out.println("Network average load param: " + loadParameter/iterations);
		System.out.println("Network average synapses load: " + synapsesLoad/iterations);
		System.out.println("Network max load parameter: " + maxLoad);
		System.out.println("Network max synapses load: " + maxSyn);
		System.out.println("Network max number of trained patterns: " + maxCount);
	}

	
	public static void trainNet(Network n, int[] inputs, int[] outputs) {
		System.out.print("Training " + n.printSize());
		System.out.print(" with input: " + Arrays.toString(inputs));
		System.out.println(" and output: " + Arrays.toString(outputs));
		n.train(inputs, outputs);
	}
	
	public static int[] intToBinaryArray(int n, int size) {
		String[] bin = String.format("%"+size+"s", Integer.toBinaryString(n)).replace(' ', '0').split("");
		int[] binArray = new int[bin.length];
		for (int i = 0; i < bin.length; i++)
		    binArray[i] = Integer.parseInt(bin[i]);
		return binArray;
	}
	
	public static int[][] generateRandomPatterns(int size) {
		ArrayList<Integer> list = new ArrayList<>();
		int[][] patterns = new int[size*2][size];
		for (int i = 0; i < Math.pow(2, size) ; i++)
		    list.add(i);
		int[] a = new int[size*2];
		for (int count = 0; count < size*2; count++) {
		    a[count] = list.remove((int)(Math.random() * list.size()));
		    patterns[count] = intToBinaryArray(a[count], size);
		}
		return patterns;
	}
}
