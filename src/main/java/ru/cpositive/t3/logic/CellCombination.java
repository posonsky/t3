package ru.cpositive.t3.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Комбинация заполненных и пустых, потенциально могущих войти в
 * данную комбинацию клеток поля
 */
class CellCombination extends FieldLine {

	// private ArrayList<Cell> cells;
	Map<Double, List<Cell>> potentialCells;

	private char dot;

	CellCombination(char dot, int type) {
		super(type, 0);
		this.dot = dot;

		potentialCells = new HashMap<Double, List<Cell>>();
	}

	/**
	 * Добавляет заполненную клетку в комбинацию
	 */
	public void append(Cell cell) {
		// Все ячейки должны содержать один знак
		if (cell.getDot() != dot) {
			throw new IllegalArgumentException("Wrong cell dot!");
		}

		int cellLineNum = getLineNumber(type, cell);
		// Первая добавляемая ячейка определяет номер линии
		if (cells.size() == 0) {
			lineNum = cellLineNum;
		}

		if (isCellInLine(lineNum, type, cell)) {
			cells.add(cell);
		} else {
			switch (type) {
			case VERTICAL:
				throw new IllegalArgumentException("Wrong cell column!");

			case HORIZONTAL:
				throw new IllegalArgumentException("Wrong cell row!");

			default:
				throw new IllegalArgumentException("Wrong cell diagonal!");
			}
		}
		// System.out.println(cells.size());
	}

	public int cellsCount() {
		return cells.size();
	}

	public boolean isVictorious() {
		return cells.size() == Params.WIN_COMBINATON_LENGTH;
	}

	public int potentialCellsCount() {
		int count = 0;
		for (List<Cell> cells : potentialCells.values()) {
			for (int i = 0; i < cells.size(); i++, count++)
				;
		}
		return count;
	}

	public double totalPotencialCellsPower() {
		double power = 0;
		for (Double cellsPower : potentialCells.keySet()) {
			power += cellsPower * potentialCells.get(cellsPower).size();
		}
		return power;
	}

	public char getDot() {
		return dot;
	}

	/**
	 * Потенциал одной пустой клетки, находящейся на линии
	 */
	double calculatePotentialPower(Cell emptyCell) {
		// Потенциальной может быть только пустая клетка
		assert (emptyCell.isEmpty());
		// Клетка должна принадлежать линии комбинации
		assert (isCellInLine(lineNum, type, emptyCell));

		int minDistance = Params.FIELD_SIZE;
		for (Cell cell : cells) {
			int distance = cell.getDistance(emptyCell);
			minDistance = distance < minDistance ? distance : minDistance;
		}

		if (minDistance <= Params.MAX_GAPS) {
			return 1 / (double) minDistance;
		}
		return -1;
	}

	/**
	 * Из переданного списка пустых клеток выбирает те, что могут стать членами
	 * комбинации и добавляет их в список потенциальных
	 */
	public void findPotential(ArrayList<Cell> empties) {

		List<Double> powers = new ArrayList<Double>();
		List<Cell> tmpEmpties = new ArrayList<Cell>();

		for (Cell emptyCell : empties) {
			double potential = calculatePotentialPower(emptyCell);
			if (potential > 0) {
				powers.add(potential);
				tmpEmpties.add(emptyCell);
			}
		}

		for (int i = 0; i < powers.size(); i++) {
			double currPower = powers.get(i);
			List<Cell> currEmpties = potentialCells.get(currPower);
			if (currEmpties != null) {
				currEmpties.add(tmpEmpties.get(i));
			} else {
				currEmpties = new ArrayList<Cell>();
				currEmpties.add(tmpEmpties.get(i));
				potentialCells.put(currPower, currEmpties);
			}
		}
	}

	/**
	 * Вычисляет, насколько комбинация является "перспективной" 2 — коэффициент
	 * для повышения значимости количества занятых клеток
	 */
	public double getDanger() {
		return cellsCount() * 2 + totalPotencialCellsPower();
	}

	/**
	 * Находит первую с максимальной "силой" потенциальную клетку для комбинации
	 * игрока
	 *
	 * @return эту клетку
	 */
	public Cell getFirstPotencialCell() {

		ArrayList<Double> powers = new ArrayList<Double>();
		powers.addAll(potentialCells.keySet());
		Collections.sort(powers, Collections.reverseOrder());
		List<Cell> cells = potentialCells.get(powers.get(0));
		return cells.get(0);
	}
}
