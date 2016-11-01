package ru.cpositive.t3.logic;

import java.util.ArrayList;

/*
 * Линия игрового поля
 */
class FieldLine {

	final static int VERTICAL = 0;
	final static int HORIZONTAL = 1;
	final static int DIAGONAL_DOWN = 2;
	final static int DIAGONAL_UP = 3;

	protected ArrayList<Cell> cells;

	protected int type; // Вертикаль, горизонталь или диагональ
	protected int lineNum; // "Адрес" вертикали, горизонтали или диагонали

	FieldLine(int type, int lineNum) {
		this.type = type;
		this.lineNum = lineNum;
		cells = new ArrayList<Cell>();
	}

	/**
	 * Вычисляет для клетки номер вертикали, горизонтали или диагонали заданного
	 * типа
	 */
	public int getLineNumber(int lineType, Cell cell) {
		switch (lineType) {
		case VERTICAL:
			return cell.getHoriz();

		case HORIZONTAL:
			return cell.getVert();

		case DIAGONAL_DOWN:
			return cell.getHoriz() - cell.getVert();

		case DIAGONAL_UP:
			int coordSum = cell.getVert() + cell.getHoriz();
			return coordSum - Params.FIELD_SIZE + 1;
		}
		throw new IllegalArgumentException("Wrong lineType: " + lineType);
	}

	/**
	 * Проверяет, находится ли клетка на заданной вертикали, горизонтали или
	 * диагонали
	 */
	public boolean isCellInLine(int lineNum, int lineType, Cell cell) {
		return (getLineNumber(lineType, cell) == lineNum);
	}

	/**
	 * Добавляет клетку в линию
	 */
	public void append(Cell cell) {
		if (!isCellInLine(lineNum, type, cell)) {
			throw new IllegalArgumentException("Wrong cell for line: " + lineNum
					+ ", type: " + type + " - " + cell);
		}
		cells.add(cell);
	}

	public int getType() {
		return type;
	}
}
