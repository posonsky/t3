/**
 * Тесты класса Cell
 *
 * @author Stanislav Posonsky
 */
package ru.cpositive.t3.logic;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CellTest {

	@Test
	public final void testConstructorRightValues() {
		Cell cell = new Cell(1, 2);
		assertEquals(cell.getHoriz(), 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testConstructorCoordLess() {
		@SuppressWarnings("unused")
		Cell cell = new Cell(-1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testConstructorCoordMore() {
		@SuppressWarnings("unused")
		Cell cell = new Cell(1, 20);
	}

	@Test
	public final void testIsEmpty() {
		Cell cell = new Cell(1, 2);
		assertTrue(cell.isEmpty());
	}

	@Test
	public final void testSetNGetDot() {
		Cell cell = new Cell(1, 2);

		assertEquals(cell.getDot(), Dots.EMPTY_DOT);
		assertTrue(cell.setDot(Dots.PLAYER_DOT));
		assertEquals(cell.getDot(), Dots.PLAYER_DOT);
		assertFalse(cell.setDot(Dots.AI_DOT));
		assertFalse(cell.setDot(Dots.PLAYER_DOT));
		assertFalse(cell.setDot(Dots.EMPTY_DOT));
		assertEquals(cell.getDot(), Dots.PLAYER_DOT);
	}

	@Test
	public final void testGetVert() {
		Cell cell = new Cell(1, 2);
		assertEquals(1, cell.getVert());
	}

	@Test
	public final void testGetHoriz() {
		Cell cell = new Cell(1, 2);
		assertEquals(2, cell.getHoriz());
	}

	@Test
	public final void testGetDistance() {
		Cell cell = new Cell(1, 2);

		assertEquals(1, cell.getDistance(new Cell(1, 1)));
		assertEquals(1, cell.getDistance(new Cell(2, 1)));
		assertEquals(2, cell.getDistance(new Cell(3, 3)));
		assertEquals(1, cell.getDistance(new Cell(0, 1)));
		assertEquals(3, cell.getDistance(new Cell(4, 0)));
	}
}


