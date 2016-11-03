/**
 * Тесты класса GameField
 *
 * @author Stanislav Posonsky
 */

package ru.cpositive.t3.logic;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;


class TestGameField extends BaseGameField {

	@Override
	public void display() {
		// Мы просто переопределяем метод
	}

	@Override
	public void displayMessage(String msg) {
		// Мы просто переопределяем метод
	}
}


public class BaseGameFieldTest {

	@Test
	public final void testConstructor() {
		GameField field = new TestGameField();
		assertTrue(field instanceof GameField);
	}

	// @Ignore
	@Test
	public final void testMustAIBlockSomething() {

		int[][] arr1 = {{0, 0}, {3, 0}};
		CellCombination aComb1 = CellCombinationTest.makeCellCombination(
				CellCombination.VERTICAL, arr1);

		ArrayList<Cell> empties = new ArrayList<Cell>();
		empties.add(new Cell(1, 0));
		empties.add(new Cell(2, 0));
		aComb1.findPotential(empties);

		int[][] arr2 = {{0, 2}, {1, 2}, {2, 2}};
		CellCombination aComb2 = CellCombinationTest.makeCellCombination(
				CellCombination.VERTICAL, arr2);
		empties = new ArrayList<Cell>();
		empties.add(new Cell(3, 2));
		aComb2.findPotential(empties);

		int[][] arr3 = {{2, 4}, {3, 4}};
		CellCombination aComb3 = CellCombinationTest.makeCellCombination(
				CellCombination.VERTICAL, arr3);
		empties = new ArrayList<Cell>();
		empties.add(new Cell(0, 4));
		empties.add(new Cell(1, 4));
		empties.add(new Cell(4, 4));
		aComb3.findPotential(empties);

		BaseGameField gameField = new TestGameField();
		gameField.registerCombinations(aComb1);
		gameField.registerCombinations(aComb2);
		gameField.registerCombinations(aComb3);

		Cell cell = gameField.mustAIBlockSomething();

		// System.out.println(cell);

		assertEquals(3, cell.getVert());
		assertEquals(2, cell.getHoriz());
	}
}
