package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		/////////////////////////////////////////////////////////////////////////////////
		//                                Exercise 1                                  //
		////////////////////////////////////////////////////////////////////////////////
		
		System.out.println("Using floaing point inputs and weights");
		System.out.println("-----------");
		
		double[] inputs = {0.5,0.7,0.8};
		double[] outputs = {0.9,0.3,0.7};
		
		Network n = new Network(inputs.length);
		trainNet(n, inputs, outputs);
		double[] result = n.test(inputs, true);
		System.out.println("Output neurons after testing: " + Arrays.toString(result));
	}

	
	public static void trainNet(Network n, double[] inputs, double[] outputs) {
		System.out.print("Training " + n.printSize());
		System.out.print(" with input: " + Arrays.toString(inputs));
		System.out.println(" and output: " + Arrays.toString(outputs));
		n.train(inputs, outputs);
	}
}
