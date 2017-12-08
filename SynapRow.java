package src;

public class SynapRow {
	public int rowNumber;
	public int colNumber;
	public int val;
	
	public SynapRow(int row, int col, int val) {
		this.rowNumber = row;
		this.colNumber = col;
		this.val = val;
	}
	
	@Override
	public String toString() {
		return "row: " + this.rowNumber + " col: " + this.colNumber +  " val: " + this.val;
	}
}
