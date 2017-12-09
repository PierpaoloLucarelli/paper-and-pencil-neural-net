package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {
	private int[] synapses;
	private int size;
	private ArrayList<int[]> inputs;
	private ArrayList<int[]> outputs;
	
	public Network(int size) {
		// create 2D array with zeros
		this.size = size*size;
		this.synapses = new int[this.size];
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}
	
	/*
		Training algorithm:
		1) for each combination between input and output bits
			- Calculate the logical AND between in and out
			- Use this value to perform logical OR with the current weight
		This mantains the hebbian proprety of the network
	*/
	public void train(int[] inputs, int[] outputs) {
		saveInputs(inputs, outputs);
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++)
			for(int j = 0 ; j < l ; j++)
				synapses[i*l+j] = (inputs[i] & outputs[j]) | synapses[i*l+j];
	}
	
	/*
		test algorithm:
		1) given an input, find the cloasest matching pattern by using the Hamming dist
		2) calculate the least number of 1s bewteen the original and closest input
		3) for each column in the synap matrix, run the integrator and comparator
		4) return the predicted output pattern
	*/
	public int[] test(int[] inputs, boolean debug) {
		if(debug)
		System.out.println("Testing input: " + Arrays.toString(inputs));
		int[] results = new int[(int)Math.sqrt(size)];
		int[] originalPattern = getClosestInput(inputs);
		int u = this.getU(inputs, originalPattern);
		for(int i = 0 ; i < inputs.length ; i++) {
			int synapSum = integrator(inputs, i);
			results[i] = comparator(u,synapSum);
		}
		return results;
	}

	// calculates the sum of the product of an input pattern with a synap column at index i 
	private int integrator(int[] inputs, int i){
		int synapSum = 0;
		int l = inputs.length;
		for(int j = 0 ; j < l ; j++)
			synapSum += inputs[j] * synapses[j*l+i];
		return synapSum;
	}

	// returns 1 if the column sum is grater than the threshold, otherwise returns 0
	private int comparator(int u, int synapSum){
		return synapSum >= u ? 1 : 0;
	}
	
	// returns the number of different digits between two patterns
	private int hammingDistance(int[] i, int[] i2) {
		int dist = 0;
		for(int j = 0 ; j < i.length ; j++)
			dist += i[j] ^ i2[j]; // X-OR
		return dist;
	}
	
	// get the closest original input pattern given another pattern using Hamming distance 
	public int[] getClosestInput(int[] input) {
		int minDist = input.length;
		int minIndex = 0;
		for(int i = 0 ; i < this.inputs.size() ; i++) {
			int d = this.hammingDistance(input, this.inputs.get(i));
			if(d < minDist) {
				minDist = d;
				minIndex = i;
			}
		}
		return this.inputs.get(minIndex);
	}
	
	public String printSize() {
		return (int)Math.sqrt(this.size) + " input network (" + this.size + "x" + this.size + ")";
	}

	// saves the inputs that have been trained
	private void saveInputs(int[] inputs, int[] outputs){
		this.inputs.add(inputs);
		this.outputs.add(outputs);
	}
	
	// returns the least number of 1s in 2 patterns (threshold)
	private int getU(int[] inputs, int[] original) {
		int u = 0; 
		int u2 = 0;
		for(int i = 0 ; i < inputs.length ; i++) {
			u += inputs[i];
			u2 += original[i];
		}
		return u <= u2 ? u : u2;
	}
	
	// returns the load parameter of the network
	public double getLoadParameter() {
		return this.inputs.size() / Math.sqrt(this.size);
	}
	
	// returns the fraction of strengthened synapses
	public double synapsesLoad() {
		int activeSynapseCount = 0;
		for(int i = 0 ; i < this.size ; i++) 
			activeSynapseCount += this.synapses[i];
		return activeSynapseCount / (double)this.size;
	}
	
	public void pop() {
		this.inputs.remove(inputs.size() -1);
		this.outputs.remove(outputs.size() -1);
	}
	
	@Override
	public String toString() {
		String output = "[";
		for(int i = 0 ; i < synapses.length ; i++){
			output += synapses[i] + ", " + ((i+1) % Math.sqrt(this.size) == 0 ? "\n" : "");
		}
		return output.substring(0, output.length() - 3)+"]";
	}
}
