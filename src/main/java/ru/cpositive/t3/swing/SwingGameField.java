package ru.cpositive.t3.swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ru.cpositive.t3.SwingGame;
import ru.cpositive.t3.logic.BaseGameField;
import ru.cpositive.t3.logic.Params;

public class SwingGameField extends BaseGameField {

	private SwingGame game;
	private JPanel panel;

	public SwingGameField(SwingGame game, JPanel panel) {
		super();
		this.game = game;
		this.panel = panel;

		// Очистим панель
		this.panel.removeAll();

		for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
			for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
				GUICell cell = new GUICell(this, field[horiz][vert]);

				cell.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent evt) {
						// if (evt.getButton() == MouseEvent.BUTTON1) {
						cell.allocatePlayer();
						// } else if (evt.getButton() == MouseEvent.BUTTON3) {
						// cell.allocateAI();
						// }
					}
				});
				panel.add(cell);
			}
		}
	}

	@Override
	public void display() {
		for (Component cmp : panel.getComponents()) {
			cmp.repaint();
		}
	}

	@Override
	public void displayMessage(String msg) {
		JOptionPane.showMessageDialog(panel.getParent(), msg);
	}

	public SwingGame getGame() {
		return game;
	}
}
