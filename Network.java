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
	
	public void train(int[] inputs, int[] outputs) {
		savePair(inputs, outputs);
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++)
			for(int j = 0 ; j < l ; j++)
				synapses[i*l+j] = (inputs[i] & outputs[j]) | synapses[i*l+j];
	}
	
	public int[] test(int[] inputs, boolean debug) {
		if(debug)
		System.out.println("Testing input: " + Arrays.toString(inputs));
		int[] results = new int[(int)Math.sqrt(size)];
		int[] originalPattern = this.inputs.get(getClosestInputIndex(inputs));
		int u = this.getU(inputs, originalPattern);
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++) {
			int synapSum = 0;
			for(int j = 0 ; j < l ; j++)
				synapSum += inputs[j] * synapses[j*l+i];
			results[i] = (synapSum >= u ? 1 : 0);
		}
		return results;
	}
	
	private int hammingDistance(int[] i, int[] i2) {
		int dist = 0;
		for(int j = 0 ; j < i.length ; j++)
			dist += i[j] ^ i2[j]; // X-OR
		return dist;
	}
	
	// get the closest original input pattern given another pattern using Hamming distance 
	public  int getClosestInputIndex(int[] input) {
		int minDist = input.length;
		int minIndex = 0;
		for(int i = 0 ; i < this.inputs.size() ; i++) {
			int d = this.hammingDistance(input, this.inputs.get(i));
			if(d < minDist) {
				minDist = d;
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	public String printSize() {
		return (int)Math.sqrt(this.size) + " input network (" + this.size + "x" + this.size + ")";
	}
	
	private int getU(int[] inputs, int[] original) {
		int u = 0; 
		int u2 = 0;
		for(int i = 0 ; i < inputs.length ; i++) {
			u += inputs[i];
			u2 += original[i];
		}
		return u <= u2 ? u : u2;
	}
	
	public double getLoadParameter() {
		return this.inputs.size() / Math.sqrt(this.size);
	}

	private void savePair(int[] inputs, int[] outputs){
		this.inputs.add(inputs);
		this.outputs.add(outputs);
	}
	
	public double synapsesLoad() {
		int activeSynapseCount = 0;
		for(int i = 0 ; i < this.size ; i++) 
			activeSynapseCount += this.synapses[i];
		return activeSynapseCount / (double)this.size;
	}
	
	// public void pop() {
	// 	this.inputs.remove(inputs.size() -1);
	// 	this.outputs.remove(outputs.size() -1);
	// }
	
	@Override
	public String toString() {
		String output = "[";
		for(int i = 0 ; i < synapses.length ; i++){
			output += synapses[i] + ", " + ((i+1) % Math.sqrt(this.size) == 0 ? "\n" : "");
		}
		return output.substring(0, output.length() - 3)+"]";
	}
}
