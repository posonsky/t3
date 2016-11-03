package ru.cpositive.t3.logic;

public interface GameField {

	public boolean isFinalState(Dots dot);
	public Cell mustAIBlockSomething();
	public void registerCombinations(CellCombination cellComb);
	public boolean setCell(int vert, int horiz, Dots dot, boolean queit);
	public boolean setCell(int vert, int horiz, Dots dot);

	public void display();

	public void displayMessage(String msg);
}
