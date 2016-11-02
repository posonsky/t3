/**
 * Тесты класса CellCombination
 *
 * @author Stanislav Posonsky
 */

package ru.cpositive.t3.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
// import org.junit.AfterClass;
import org.junit.Before;
// import org.junit.BeforeClass;
import org.junit.Test;

public class CellCombinationTest {

	private static CellCombination cellComb;

	/*
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	*/

	@Before
	public void setUp() throws Exception {
		cellComb = new CellCombination(Dots.PLAYER_DOT,
				CellCombination.VERTICAL);
	}

	@After
	public void tearDown() throws Exception {
		cellComb = null;
	}

	@Test
	public final void testConstructor() {
		assertTrue(cellComb instanceof CellCombination);
	}

	@Test
	public final void testGetType() {
		assertTrue(cellComb.getType() == CellCombination.VERTICAL);
	}

	@Test
	public final void testGetDot() {
		assertTrue(cellComb.getDot() == Dots.PLAYER_DOT);
	}

	public static CellCombination makeCellCombination(int type, int[][] arr) {
		CellCombination testComb =
				new CellCombination(Dots.PLAYER_DOT, type);

		for (int i = 0; i < arr.length; i++) {
			Cell cell = new Cell(arr[i][0], arr[i][1]);
			cell.setDot(Dots.PLAYER_DOT);
			testComb.append(cell);
		}
		return testComb;
	}

	@Test
	public final void testCalculatePotential() {
		CellCombination aComb;

		int[][] arr1 = {{0, 0}, {3, 0}, {4, 0}};
		aComb = makeCellCombination(CellCombination.VERTICAL, arr1);

		double potential = aComb.calculatePotentialPower(new Cell(1, 0));
		// System.out.println(potential);
		assertEquals(1, potential, 0);

		int[][] arr2 = {{0, 0}, {1, 1}};
		aComb = makeCellCombination(CellCombination.DIAGONAL_DOWN, arr2);
		potential = aComb.calculatePotentialPower(new Cell(4, 4));
		// System.out.println(potential);
		assertEquals(-1, potential, 0);

		potential = aComb.calculatePotentialPower(new Cell(3, 3));
		// System.out.println(potential);
		assertEquals(0.5, potential, 0);
	}

	@Test(expected = AssertionError.class)
	public final void testCalculatePotentialWrongCellDot() {

		int[][] arr1 = {{0, 0}, {3, 0}, {4, 0}};
		CellCombination aComb = makeCellCombination(CellCombination.VERTICAL,
				arr1);
		Cell aCell = new Cell(1, 2);
		aCell.setDot(Dots.PLAYER_DOT);
		aComb.calculatePotentialPower(aCell);
	}

	@Test(expected = AssertionError.class)
	public final void testCalculatePotentialWrongCellLine() {

		int[][] arr1 = {{4, 1}, {3, 2}};
		CellCombination  aComb = makeCellCombination(
				CellCombination.DIAGONAL_UP, arr1);
		aComb.calculatePotentialPower(new Cell(1, 2));
	}

	@Test
	public final void testFindPotentialForVertical() {

		CellCombination aComb;

		int[][] arr1 = {{0, 0}, {1, 0}};
		aComb = makeCellCombination(CellCombination.VERTICAL, arr1);

		ArrayList<Cell> empties = new ArrayList<Cell>();
		empties.add(new Cell(2, 0));
		empties.add(new Cell(4, 0));

		aComb.findPotential(empties);
		assertEquals(1, aComb.potentialCellsCount());

		int[][] arr2 = {{1, 0}, {2, 0}};
		aComb = makeCellCombination(CellCombination.VERTICAL, arr2);

		empties = new ArrayList<Cell>();
		empties.add(new Cell(0, 0));
		empties.add(new Cell(3, 0));
		empties.add(new Cell(4, 0));

		aComb.findPotential(empties);
		assertEquals(3, aComb.potentialCellsCount());
	}
}
