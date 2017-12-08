package src;

import java.util.ArrayList;

public class SparseSynap {
	private ArrayList<SynapRow> synapses;
	
	public SparseSynap() {
		this.synapses = new ArrayList<>();
	}
	
	public void addVal(int rowIndex, int colIndex, int val) {
		SynapRow r = new SynapRow(rowIndex, colIndex, val);
		this.synapses.add(r);
	}
	
	public boolean hasVal(int row, int col) {
		for(SynapRow s : synapses)
			if(s.rowNumber == row && s.colNumber == col)
				return true;
		return false;
	}
	
//	public int sumColumn(int[] inputs, int col) {
//		int sum = 0;
//		for(int i = 0 ; i < inputs.length ; i++) {
//			sum += (inputs[i] * )
//		}
//	}

	
	@Override
	public String toString(){
		String output = "";
		for(SynapRow s : this.synapses)
			output += s.toString() + "\n";
		return output;
	}
}