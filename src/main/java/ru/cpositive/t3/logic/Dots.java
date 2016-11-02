package ru.cpositive.t3.logic;


public enum Dots {
	EMPTY_DOT('.'), PLAYER_DOT('X'), AI_DOT('0');

	private char symbol;

	Dots(char symbol) {
		this.symbol = symbol;
	}

	public char getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}
