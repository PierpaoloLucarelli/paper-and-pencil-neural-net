package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {

	private Layer l1;
	private Layer l2;
	private int size;

	public Network(int size){
		this.size = size;
		this.l1 = new Layer(size);
		this.l2 = new Layer(size);
	}

	public void train(int[] inputs, int[] outputs){
		this.l1.train(inputs, outputs);
		int[] r1 = l1.test(inputs, false);
		this.l2.train(r1, outputs);
	}

	public int[] test(int[] inputs, boolean debug){
		int[] r1 = l1.test(inputs, debug);
		int[] r2 = l2.test(r1, debug);
		return r2;
	}

	public String printSize() {
		return (int)Math.pow(this.size,2) + " synapse network (" + this.size + "x" + this.size + ")";
	}

	@Override
	public String toString() {
		String output = "Layer1:\n";
		output += l1.toString();
		output += "\nLayer2:\n";
		output += l2.toString();
		return output;
	}

	public void pop(){
		this.l1.pop();
		this.l2.pop();
	}

	public double getLoadParameter() {
		return (this.l1.getLoadParameter() + this.l2.getLoadParameter()) / 2;
	}

	public double synapsesLoad() {
		return (this.l1.synapsesLoad() + this.l2.synapsesLoad()) / 2;
	}
}
