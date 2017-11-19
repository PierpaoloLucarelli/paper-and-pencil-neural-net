package src;

import java.util.Arrays;

public class Network {
	private int[][] synapses;
	private int size;
	public Network(int size) {
		// create 2D array with zeros
		this.size = size;
		this.synapses = new int[size][size];
	}
	
	public void train(int[] inputs, int[] outputs) {
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++)
			for(int j = 0 ; j < l ; j++)
				this.synapses[i][j] = (inputs[i] & outputs[j]) | this.synapses[i][j];
	}
	
	public int[] test(int[] inputs) {
		System.out.println("Testing input: " + Arrays.toString(inputs));
		int[] results = new int[size];
		int u = this.getU(inputs);
		for(int i = 0 ; i < size ; i++) {
			int synapSum = 0;
			for(int j = 0 ; j < size ; j++)
				synapSum += inputs[j] * synapses[j][i];
			results[i] = (synapSum >= u ? 1 : 0);
		}
		return results;
	}
	
	private int getU(int[] inputs) {
		int u = 0;
		for(int i = 0 ; i < size ; i++)
			u += inputs[i];
		return u;
	}
	
	public String printSize() {
		return (int)Math.pow(this.size,2) + " synapse network (" + this.size + "x" + this.size + ")";
	}
	
	@Override
	public String toString() {
		String output = "";
		for(int i = 0 ; i < size ; i ++) {
			output += Arrays.toString(this.synapses[i]) + "\n";
		}
		return output;
	}
}
