package ru.cpositive.t3.console;

import ru.cpositive.t3.logic.BaseGameField;
import ru.cpositive.t3.logic.Cell;


public class ConsoleGameField extends BaseGameField {

	public void display() {
		System.out.println();
		for (Cell row[] : field) {
			for (Cell cell : row) {
				System.out.print(cell.getDot());
			}
			System.out.println();
		}
	}

}
