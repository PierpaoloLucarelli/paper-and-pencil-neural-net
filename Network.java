package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Network {

	private Layer l1;
	private Layer l2;
	private int width;
	private int height;

	public Network(int width, int height){
		this.width = width;
		this.height = height;
		this.l1 = new Layer(width,height);
		this.l2 = new Layer(width, height);
	}

	public void train(int[][] inputs, int[] outputs){
		this.l1.train(inputs, outputs);
		int[][] r1 = new int[inputs.length][outputs.length];
		for(int i = 0 ; i < inputs.length ; i++) {
			r1[i] = l1.test(inputs[i], false, 1);
		}
		this.l2.train(r1, outputs);
	}

	public int[] test(int[] inputs, boolean debug){
		int[] r1 = l1.test(inputs, debug, 1);
		int[] r2 = l2.test(r1, debug, 2);
		return r2;
	}

	public String printSize() {
		return this.width + "x" + this.height + " Network";
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