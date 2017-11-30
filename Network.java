// floating branch
package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {
	private double[][] synapses;
	private int size;
	private ArrayList<int[]> inputs;
	private ArrayList<int[]> outputs;
	public Network(int size) {
		// create 2D array with zeros
		this.size = size;
		this.synapses = new double[size][size];
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}
	
	public void train(int[] inputs, int[] outputs) {
		this.inputs.add(inputs);
		this.outputs.add(outputs);
		int l = this.inputs.size();
		for(int i = 0 ; i < size ; i++ ){
			for(int j = 0 ; j < size ; j++){
				int sum = 0;
				for(int p = 0 ; p < l ; p++)
					sum += this.inputs.get(p)[i] * this.outputs.get(p)[j];
				this.synapses[i][j] = sum / (double)this.size;
			}
		}
	}
	
	public int[] test(int[] inputs, boolean debug) {
		if(debug)
			System.out.println("Testing input: " + Arrays.toString(inputs));
		int[] results = new int[size];
		int[] originalPattern = this.inputs.get(getClosestInputIndex(inputs));
		double u = this.getU(inputs, originalPattern);
		for(int i = 0 ; i < size ; i++) {
			double synapSum = 0;
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
	
	private double getU(int[] inputs, int[] original) {
		double u = 0; 
		double u2 = 0;
		for(int i = 0 ; i < size ; i++) {
			u += inputs[i];
			u2 += original[i];
		}
		return u <= u2 ? u /this.size : u2 / this.size;
	}
	
	public double getLoadParameter() {
		return this.inputs.size() / (double)this.size;
	}
	
	public double synapsesLoad() {
		int activeSynapseCount = 0;
		for(int i = 0 ; i < this.size; i++) {
			for(int j = 0 ; j < this.size ; j++) 
				activeSynapseCount += this.synapses[i][j];
		}
		return activeSynapseCount / Math.pow(this.size, 2);
	}
	
	public void pop() {
		this.inputs.remove(inputs.size() -1);
		this.outputs.remove(outputs.size() -1);
	}
	
	@Override
	public String toString() {
		String output = "";
		for(int i = 0 ; i < size ; i ++)
			output += Arrays.toString(this.synapses[i]) + "\n";
		return output;
	}
}
