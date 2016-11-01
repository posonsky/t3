package ru.cpositive.t3.logic;

public interface GameField {

	public boolean isFinalState(char dot);
	public Cell mustAIBlockSomething();
	public void registerCombinations(CellCombination cellComb);
	public boolean setCell(int vert, int horiz, char dot, boolean queit);
	public boolean setCell(int vert, int horiz, char dot);
	public void display();
}
