// floating 2 branch
package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {
	private double[] synapses;
	private int size;
	private ArrayList<double[]> inputs;
	private ArrayList<double[]> outputs;
	
	public Network(int size) {
		// create 2D array with zeros
		this.size = size*size;
		this.synapses = new double[this.size];
		this.inputs = new ArrayList<>();
		this.outputs = new ArrayList<>();
	}
	
	/*
		Train algorithm:
		1) using equation 2 form the Paper, we multiply input and output and sum
		2) we normalize tha weights to get values between 0 and 1
	*/
	public void train(double[] inputs, double[] outputs) {
		saveInputs(inputs, outputs);
		int l = inputs.length;
		for(int i = 0 ; i < l ; i++)
			for(int j = 0 ; j < l ; j++)
				synapses[i*l+j] += (inputs[i] * outputs[j]);
		normalize();
	}
	
	/*
		Test algorithm:
		1) Run integrator to sum all comllumns
		2) Use the sigmoid function to get a fuzzy output value
	*/
	public double[] test(double[] inputs, boolean debug) {
		if(debug)
		System.out.println("Testing input: " + Arrays.toString(inputs));
		double[] results = new double[(int)Math.sqrt(size)];
		for(int i = 0 ; i < inputs.length ; i++) {
			double synapSum = integrator(inputs, i);
			results[i] = sigmoid(synapSum);
		}
		return results;
	}


	// normalise the synaptic weights
	public void normalize(){
		double max = 0;
		for(int i = 0 ; i < this.synapses.length ; i++)
			if(synapses[i] > max) max = synapses[i];
		for(int i = 0 ; i < this.synapses.length ; i++)
			this.synapses[i] /= max;
	}

	public double sigmoid(double sum){
		return 1 / (1 + Math.pow(Math.E, -sum));
	}

	// calculates the sum of the product of an input pattern with a synap column at index i 
	private double integrator(double[] inputs, int i){
		double synapSum = 0;
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
	private double hammingDistance(double[] i, double[] i2) {
		double dist = 0;
		for(int j = 0 ; j < i.length ; j++)
			dist += Math.abs(i[j] - i2[j]); 
		return dist;
	}
	
	// get the closest original input pattern given another pattern using Hamming distance 
	public double[] getClosestInput(double[] input) {
		double minDist = input.length;
		int minIndex = 0;
		for(int i = 0 ; i < this.inputs.size() ; i++) {
			double d = this.hammingDistance(input, this.inputs.get(i));
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
	private void saveInputs(double[] inputs, double[] outputs){
		this.inputs.add(inputs);
		this.outputs.add(outputs);
	}
	
	// returns the least number of 1s in 2 patterns (threshold)
	private int getU(double[] inputs, double[] original) {
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
