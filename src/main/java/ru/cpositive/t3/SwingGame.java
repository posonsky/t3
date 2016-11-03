package ru.cpositive.t3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.cpositive.t3.logic.AIPlayer;
import ru.cpositive.t3.logic.Dots;
import ru.cpositive.t3.logic.GameField;
import ru.cpositive.t3.logic.Params;
import ru.cpositive.t3.logic.Player;
import ru.cpositive.t3.swing.SwingGameField;


public class SwingGame extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton btnNewGame = new JButton("Новая игра");
	JButton btnExit = new JButton("Выход");
	GridLayout layoutTTT = new GridLayout(Params.FIELD_SIZE, Params.FIELD_SIZE);

	JPanel panelTTT;
	GameField gameField;
	Player aiPlayer;

	public SwingGame(String name) {
		super(name);
	}

	public void addComponentsToPane(final Container pane) {

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(1, 3));

		btnExit.addActionListener((ae) -> exit());
		btnNewGame.addActionListener((ae) -> newGame(pane));
		controls.add(btnNewGame);
		controls.add(btnExit);

		pane.add(controls, BorderLayout.SOUTH);

		newGame(pane);
	}

	private void exit() {
		setVisible(false);
		dispose();
		System.exit(NORMAL);
	}

	private void newGame(final Container pane) {

		if (panelTTT == null) {
			panelTTT = new JPanel();

			panelTTT.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panelTTT.setLayout(layoutTTT);
			layoutTTT.setHgap(1);
			layoutTTT.setVgap(1);

			panelTTT.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent ce) {
					// System.out.println("componentResized");
					Dimension panelSize = panelTTT.getSize();
					int minSize = (int) Math.min(panelSize.getHeight(),
							panelSize.getWidth());
					panelTTT.setSize(new Dimension(minSize, minSize));
					layoutTTT.layoutContainer(panelTTT);
				}
			});

			pane.add(panelTTT, BorderLayout.CENTER);
		}

		gameField = new SwingGameField(this, panelTTT);
		panelTTT.updateUI();

		aiPlayer = new AIPlayer(gameField);
	}

	private static void createAndShowGUI() {

		SwingGame frame = new SwingGame("Крестики-нолики");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 800, 550);
		frame.setMinimumSize(new Dimension(600, 350));
		frame.addComponentsToPane(frame.getContentPane());
		frame.setVisible(true);
	}

	public void proceedCycle() {
		gameField.display();
		if (gameField.isFinalState(Dots.PLAYER_DOT)) {
			// exit();
			newGame(this.getContentPane());
		} else {
			boolean aiWin = aiPlayer.turn();
			gameField.display();
			if (aiWin) {
				// exit();
				newGame(this.getContentPane());
			}
		}
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

