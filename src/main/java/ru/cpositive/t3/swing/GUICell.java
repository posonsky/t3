package ru.cpositive.t3.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ru.cpositive.t3.logic.Cell;
import ru.cpositive.t3.logic.Dots;


public class GUICell extends JPanel {

	private static final long serialVersionUID = 1L;
	private SwingGameField gameField;
	private Cell cell;

	public GUICell(SwingGameField gameField, Cell cell) {
		super();
		this.gameField = gameField;
		this.cell = cell;
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	@Override
	public void paint(Graphics grph) {
		super.paint(grph);

		Dimension dim = getSize();
		double wi = dim.getWidth();
		double hi = dim.getHeight();
		int gap = (int) (wi - wi * 0.8) / 2;

		Graphics2D grph2 = (Graphics2D) grph;
		// grph2.setColor(Color.GREEN);
		grph2.setStroke(new BasicStroke(gap / 2));

		switch (cell.getDot()) {
		case PLAYER_DOT:
			// Рисуем крестик
			grph2.setColor(Color.GREEN);
			grph2.draw(
					new Line2D.Float(gap, gap, (int) wi - gap, (int) hi - gap));
			grph2.draw(
					new Line2D.Float(gap, (int) hi - gap, (int) wi - gap, gap));

			break;
		case AI_DOT:
			// Рисуем нолик
			grph2.setColor(Color.RED);
			grph2.draw(new Ellipse2D.Float((int) (wi * 0.25), gap,
					(int) (wi * 0.5), (int) (hi * 0.8)));
		default:
		}
	}

	public void allocatePlayer() {
		if (cell.setDot(Dots.PLAYER_DOT)) {
			// repaint();
			gameField.getGame().proceedCycle();
		} else {
			String msg = "Эта клетка уже занята";
			gameField.displayMessage(msg);
		}
	}

	/*
	 * public void allocateAI() { dot = Dots.AI_DOT; repaint(); }
	 *
	 * public void clear() { dot = Dots.EMPTY_DOT; repaint(); }
	 */
}
