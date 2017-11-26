package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {
	private int[][] synapses;
	private int size;
	private ArrayList<int[]> inputs;
	private ArrayList<int[]> outputs;
	public Network(int size) {
		// create 2D array with zeros
		this.size = size;
		this.synapses = new int[size][size];
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}
	
	public void train(int[] inputs, int[] outputs) {
		this.inputs.add(inputs);
		this.outputs.add(outputs);
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++)
			for(int j = 0 ; j < l ; j++)
				this.synapses[i][j] = (inputs[i] & outputs[j]) | this.synapses[i][j];
	}
	
	public int[] test(int[] inputs) {
		System.out.println("Testing input: " + Arrays.toString(inputs));
		int[] results = new int[size];
		int[] originalPattern = this.inputs.get(getClosestInputIndex(inputs));
		int u = this.getU(inputs, originalPattern);
		for(int i = 0 ; i < size ; i++) {
			int synapSum = 0;
			for(int j = 0 ; j < size ; j++)
				synapSum += inputs[j] * synapses[j][i];
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
		return (int)Math.pow(this.size,2) + " synapse network (" + this.size + "x" + this.size + ")";
	}
	
	private int getU(int[] inputs, int[] original) {
		int u = 0; 
		int u2 = 0;
		for(int i = 0 ; i < size ; i++) {
			u += inputs[i];
			u2 += original[i];
		}
		return u <= u2 ? u : u2;
	}
	
	public double getLoadParameter() {
		return this.inputs.size() / this.size;
	}
	
	public double synapsesLoad() {
		int activeSynapseCount = 0;
		for(int i = 0 ; i < this.size ; i++) {
			for(int j = 0 ; i < this.size ; j++)
				activeSynapseCount += this.synapses[i][j];
		}
		return activeSynapseCount / Math.pow(this.size, 2);
	}
	
	@Override
	public String toString() {
		String output = "";
		for(int i = 0 ; i < size ; i ++)
			output += Arrays.toString(this.synapses[i]) + "\n";
		return output;
	}
}
