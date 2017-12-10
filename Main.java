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
		
		int[] i1 = {1,0,0,1};
		int[] i2 = {1,0,1,0};
		int[] i3 = {1,1,0,0};
		int[][] inputs = {i1,i2,i3};
		int[] outputs = {0,0,1,1};
		Network n = new Network(outputs.length, outputs.length * inputs.length);
		trainNet(n, inputs, outputs);
		// System.out.println(n);
		int[] result = n.test(i1, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
		System.out.println("\nSynapses matrix:\n\n"+n);

	}

	
	public static void trainNet(Network n, int[][] inputs, int[] outputs) {
		System.out.print("Training " + n.printSize() + " with inputs: ");
		for(int[] i : inputs)
			System.out.print(Arrays.toString(i));
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
