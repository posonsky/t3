package ru.cpositive.t3.logic;

public abstract class Player {

	protected Dots dot;
	protected GameField gameField;

	public Player(GameField gameField) {
		this.gameField = gameField;
	}

	abstract protected void realTurn();

	public boolean turn() {
		realTurn();
		return gameField.isFinalState(dot);
	}
}
