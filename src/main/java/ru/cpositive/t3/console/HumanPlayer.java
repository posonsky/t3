package ru.cpositive.t3.console;

import java.util.Scanner;

import ru.cpositive.t3.logic.GameField;
import ru.cpositive.t3.logic.Params;
import ru.cpositive.t3.logic.Player;


public class HumanPlayer extends Player {

	private Scanner scanner = new Scanner(System.in);

	public HumanPlayer(GameField gameField) {
		super(gameField);
		this.dot = Params.PLAYER_DOT;
	}

	@Override
	protected void realTurn() {
		int vert, horiz;
		do {
			gameField.display();

			System.out.println("Введите координаты V H (от 1 до "
					+ Params.FIELD_SIZE + "):");

			vert = scanner.nextInt() - 1;
			horiz = scanner.nextInt() - 1;
			// System.out.println("Real V=" + vert + ", H=" + horiz);

		} while (!gameField.setCell(vert, horiz, Params.PLAYER_DOT));
	}
}
