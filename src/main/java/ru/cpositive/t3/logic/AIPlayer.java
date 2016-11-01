package ru.cpositive.t3.logic;

import java.util.Random;

public class AIPlayer extends Player {

	private Random rand = new Random();

	public AIPlayer(GameField gameField) {
		super(gameField);
		this.dot = Params.AI_DOT;
	}

	/**
	 * Если необходимо заблокировать комбинацию игрока, преимущественно
	 * выполняется блокировка
	 */
	@Override
	protected void realTurn() {

		Cell blockingCell = gameField.mustAIBlockSomething();
		if (blockingCell != null) {
			gameField.setCell(blockingCell.getVert(), blockingCell.getHoriz(),
					Params.AI_DOT, true);
			return;
		}
		// Это заглушка для хода ЭВМ, когда нет нужды блокировать
		// комбинацию противника
		int vert, horiz;
		do {
			vert = rand.nextInt(Params.FIELD_SIZE);
			horiz = rand.nextInt(Params.FIELD_SIZE);
		} while (!gameField.setCell(vert, horiz, Params.AI_DOT, true));
	}
}
