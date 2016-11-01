package ru.cpositive.t3.logic;

/*
 * Класс клетки игрового поля
 */
public class Cell {
	final static String WRONG_VERT_HORIZ = "Real vertical and horizontal "
			+ "coordinates must to be in from 0 to " + Params.FIELD_SIZE + "!";

	private int vert, horiz;
	private char dot;

	Cell(int vert, int horiz) {
		int hiBound = Params.FIELD_SIZE - 1;
		if (vert < 0 || vert > hiBound || horiz < 0 || horiz > hiBound) {
			throw new IllegalArgumentException(WRONG_VERT_HORIZ);
		}

		this.vert = vert;
		this.horiz = horiz;
		this.dot = Params.EMPTY_DOT;
	}

	public boolean isEmpty() {
		return dot == Params.EMPTY_DOT;
	}

	public boolean setDot(char dot) {
		if (isEmpty()) {
			this.dot = dot;
			return true;
		}
		return false;
	}

	public char getDot() {
		return dot;
	}

	public int getVert() {
		return vert;
	}

	public int getHoriz() {
		return horiz;
	}

	public int getDistance(Cell cell) {
		return Math.max(Math.abs(getVert() - cell.getVert()),
				Math.abs(getHoriz() - cell.getHoriz()));
	}

	@Override
	public String toString() {
		return "Cell(" + vert + ", " + horiz + ", '" + dot + "')";
	}

}
