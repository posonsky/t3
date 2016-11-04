package ru.cpositive.t3.javafx;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.cpositive.t3.logic.Cell;
import ru.cpositive.t3.logic.Dots;

public class CanvasCell extends Canvas {

	private FXGameField gameField;
	private Cell cell;

	public CanvasCell(FXGameField gameField, Cell cell) {
		super();
		this.gameField = gameField;
		this.cell = cell;

		widthProperty().addListener(evt -> draw());
		heightProperty().addListener(evt -> draw());

		setOnMouseClicked(evt -> allocatePlayer());
	}

	private void allocatePlayer() {
		if (cell.setDot(Dots.PLAYER_DOT)) {
			draw();
			gameField.getGame().proceedCycle();
		} else {
			String msg = "Эта клетка уже занята!";
			gameField.displayMessage(msg);
		}
	}

	public synchronized void draw() {
		double width = getWidth();
		double height = getHeight();

		GraphicsContext gc = getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, width, height);

		gc.setLineWidth(5);

		switch (cell.getDot()) {
		case PLAYER_DOT:
			// Рисуем крестик
			gc.setStroke(Color.GREEN);
			gc.strokeLine(0, 0, width, height);
			gc.strokeLine(0, height, width, 0);
			break;
		case AI_DOT:
			// Рисуем нолик
			gc.setStroke(Color.RED);
			gc.strokeOval(width * 0.2, height * 0.05, width * 0.6,
					height * 0.9);
			break;
		default:
		}
	}
}

