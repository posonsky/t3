package ru.cpositive.t3;

import ru.cpositive.t3.console.ConsoleGameField;
import ru.cpositive.t3.console.HumanPlayer;
import ru.cpositive.t3.logic.AIPlayer;
import ru.cpositive.t3.logic.GameField;
import ru.cpositive.t3.logic.Player;

/**
 * Консольный вариант игры
 *
 * @author Stanislav Posonsky
 */
public class ConsoleGame {

	GameField gameField;

	public void play() {
		gameField = new ConsoleGameField();

		Player[] players = { new HumanPlayer(gameField),
				new AIPlayer(gameField) };

		while (true) {
			for (Player player : players) {
				if (player.turn()) {
					gameField.display();
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		new ConsoleGame().play();
	}
}
