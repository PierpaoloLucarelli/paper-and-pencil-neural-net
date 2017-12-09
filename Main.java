package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Using sparese matrix                                 //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Using sparse matrix");
		System.out.println("-----------");
		
		int[] inputs = {1,0,1,0,0,1};
		int[] outputs = {1,1,0,1,1,0};
		int[] newI = {0,1,0,0,1,0};
		int[] newO = {1,0,1,0,0,1};
		Network n = new Network(inputs.length);
		trainNet(n, inputs, outputs);
		trainNet(n, newI, newO);
		System.out.println(n);
		int[] result = n.test(inputs, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		int[] result2 = n.test(newI, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result2));
		
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Performance test                                  //
		////////////////////////////////////////////////////////////////////////////////
		System.out.println("\nTesting performance");
		System.out.println("-----------");
		int size = 10, iterations = 500, total = 0, maxCount = 0;
		double loadParameter = 0, maxLoad = 0, synapsesLoad = 0, maxSyn = 0;
		for(int j = 0 ; j < iterations ; j++) {
			Network net = new Network(size);
			int[][] inPatterns = generateRandomPatterns(size);
			int[][] outPatterns = generateRandomPatterns(size);
			int count = 0;
			for(int i = 0 ; i < inPatterns.length ; i++) {
				net.train(inPatterns[i], outPatterns[i]);
				int[] res = net.test(inPatterns[i], false);
				if(Arrays.equals(res, outPatterns[i])) {
					count++;
				} else {
					// network has made an error in recal
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
