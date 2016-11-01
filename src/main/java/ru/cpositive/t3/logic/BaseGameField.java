package ru.cpositive.t3.logic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Класс игрового поля
 */
public abstract class BaseGameField implements GameField {

	private int maxGaps;

	protected Cell[][] field;

	private ArrayList<FieldLine> lines;
	private ArrayList<CellCombination> playerCellCombinations,
			aiCellCombinations;

	public BaseGameField() {
		field = new Cell[Params.FIELD_SIZE][Params.FIELD_SIZE];
		for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
			for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
				field[vert][horiz] = new Cell(vert, horiz);
			}
		}

		// Определяем линии
		lines = new ArrayList<FieldLine>();

		/**
		 * Чтобы понять логику, см. табличку TicTacToe.ods
		 */

		// Вертикали (колонки)
		for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
			FieldLine lineVert = new FieldLine(FieldLine.VERTICAL, horiz);
			for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
				lineVert.append(field[vert][horiz]);
			}
			lines.add(lineVert);
		}

		// Горизонтали (строки, ряды)
		for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
			FieldLine lineHoriz = new FieldLine(FieldLine.HORIZONTAL, vert);
			for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
				lineHoriz.append(field[vert][horiz]);
			}
			lines.add(lineHoriz);
		}

		// Диагонали "сверху вниз"
		for (int diagonalNum = -Params.FIELD_SIZE
				+ 1; diagonalNum < Params.FIELD_SIZE; diagonalNum++) {
			FieldLine lineDiagDown = new FieldLine(FieldLine.DIAGONAL_DOWN,
					diagonalNum);
			int firstVert = diagonalNum < 0 ? Math.abs(diagonalNum) : 0;
			int firstHoriz = diagonalNum < 0 ? 0 : diagonalNum;
			int diagonalLength = Params.FIELD_SIZE - Math.abs(diagonalNum);
			for (int vert = firstVert, horiz = firstHoriz, i = 0; i < diagonalLength; vert++, horiz++, i++) {
				lineDiagDown.append(field[vert][horiz]);
			}
			lines.add(lineDiagDown);
		}

		// Диагонали "снизу вверх"
		for (int diagonalNum = -Params.FIELD_SIZE
				+ 1; diagonalNum < Params.FIELD_SIZE; diagonalNum++) {
			FieldLine lineDiagUp = new FieldLine(FieldLine.DIAGONAL_UP,
					diagonalNum);
			int firstVert = diagonalNum < 0
					? Params.FIELD_SIZE - 1 - Math.abs(diagonalNum)
					: Params.FIELD_SIZE - 1;
			int firstHoriz = diagonalNum < 0 ? 0 : diagonalNum;
			int diagonalLength = Params.FIELD_SIZE - Math.abs(diagonalNum);
			for (int vert = firstVert, horiz = firstHoriz, i = 0; i < diagonalLength; vert--, horiz++, i++) {
				lineDiagUp.append(field[vert][horiz]);
			}
			lines.add(lineDiagUp);
		}

		playerCellCombinations = new ArrayList<CellCombination>();
		aiCellCombinations = new ArrayList<CellCombination>();
	}

	public boolean setCell(int vert, int horiz, char dot) {
		return setCell(vert, horiz, dot, false);
	}

	public boolean setCell(int vert, int horiz, char dot, boolean queit) {
		// Попадают ли координаты в пределы игрового поля?
		int hiBound = Params.FIELD_SIZE - 1;
		if (vert < 0 || vert > hiBound || horiz < 0 || horiz > hiBound) {
			if (!queit) {
				System.out.println("Координаты выходят за пределы поля: " + vert
						+ ", " + horiz + "!");
			}
			return false;
		}
		// Не занята ли уже ячейка?
		Cell cell = field[vert][horiz];
		if (!cell.isEmpty()) {
			if (!queit) {
				System.out.println(
						"Ячейка уже занята: " + ++vert + ", " + ++horiz + "!");
				return false;
			}
		}
		cell.setDot(dot);
		return true;
	}

	/*
	 * public void print() { System.out.println(); for (Cell row[] : field) {
	 * for (Cell cell : row) { System.out.print(cell.getDot()); }
	 * System.out.println(); } }
	 */

	abstract public void display();

	/**
	 * Сканирует линии игрового поля на наличие комбинаций, сохраняет найденные
	 * комбинации в соответствующих списках
	 */
	protected void scanCombinations() {

		playerCellCombinations.clear();
		aiCellCombinations.clear();

		char[] dots = { Params.PLAYER_DOT, Params.AI_DOT };

		// Для каждого знака
		for (char expectedDot : dots) {
			// Сканируем все линии
			for (FieldLine line : lines) {
				boolean inCombination = false;
				int gaps = 0;
				ArrayList<Cell> empties = new ArrayList<Cell>();
				CellCombination tmpComb = null;

				for (Cell cell : line.cells) {
					char currDot = cell.getDot();

					if (currDot == expectedDot) {
						if (!inCombination) {
							inCombination = true;
							tmpComb = new CellCombination(expectedDot,
									line.getType());
						}
						tmpComb.append(cell);

					} else if (currDot == Params.EMPTY_DOT) {
						empties.add(cell);
						gaps++;
						if (gaps >= maxGaps && inCombination) {
							// Если была комбинация, учитываем её
							inCombination = false;
							tmpComb.findPotential(empties);
							registerCombinations(tmpComb);
						}

					} else {
						if (inCombination) {
							// Если была комбинация, учитываем её
							inCombination = false;
							tmpComb.findPotential(empties);
							registerCombinations(tmpComb);
						}
					}

				}
				// Если была комбинация, учитываем её
				if (tmpComb != null) {
					tmpComb.findPotential(empties);
					registerCombinations(tmpComb);
				}
			}
		}
	}

	/**
	 * Сохраняет в соответствующем списке комбинацию, если она подходит под
	 * определённые критерии
	 */
	public void registerCombinations(CellCombination cellComb) {

		// В комбинации должно быть не менее двух клеток
		if (cellComb.cellsCount() < 2) {
			return;
		}

		switch (cellComb.getDot()) {
		case Params.PLAYER_DOT:
			playerCellCombinations.add(cellComb);
			break;

		case Params.AI_DOT:
			aiCellCombinations.add(cellComb);
		}
	}

	/**
	 * Проверяет, дошла ли игра до терминирующей стадии
	 */
	public boolean isFinalState(char dot) {

		scanCombinations();

		CellCombination winCombination = getWinCombination(dot);
		if (winCombination != null) {
			if (dot == Params.PLAYER_DOT) {
				System.out.println("Поздравляем! Вы выиграли!");
			} else {
				System.out.println("Победил искусственный интелект!");
			}
			return true;
		} else {
			// Есть ещё пустые клеточки?
			for (Cell row[] : field) {
				for (Cell cell : row) {
					if (cell.isEmpty()) {
						return false;
					}
				}
			}
			System.out.println("Победила дружба! Ничья...");
			return true;
		}
	}

	/**
	 * Просматривает все существующие комбинации и возвращает при наличии
	 * выигрышную
	 */
	private CellCombination getWinCombination(char dot) {

		ArrayList<CellCombination> combinations = dot == Params.PLAYER_DOT
				? playerCellCombinations : aiCellCombinations;

		for (CellCombination cellComb : combinations) {
			if (cellComb.isVictorious()) {
				return cellComb;
			}
		}
		return null;
	}

	/**
	 * Должен ли искусственный интеллект блокировать какую-то комбинацию
	 * противника?
	 *
	 * @return Ссылка на клетку или null
	 */
	public Cell mustAIBlockSomething() {

		if (playerCellCombinations.size() == 0) {
			return null;
		}

		/*
		 * System.out.println("Total combinations:" +
		 * playerCellCombinations.size()); for (CellCombination playerComb:
		 * playerCellCombinations) { System.out.println("Danger: " +
		 * playerComb.getDanger()); }
		 */

		playerCellCombinations.sort(
				Comparator.comparing(CellCombination::getDanger)
						.reversed());

		/*
		 * for (CellCombination playerComb: playerCellCombinations) {
		 * System.out.println("Danger: " + playerComb.getDanger()); }
		 */

		CellCombination mostDangerComb = playerCellCombinations.get(0);
		return mostDangerComb.getFirstPotencialCell();
	}
}
