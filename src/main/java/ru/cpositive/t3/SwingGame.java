package ru.cpositive.t3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ru.cpositive.t3.logic.Params;
import ru.cpositive.t3.swing.GUICell;


public class SwingGame extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton btnNewGame = new JButton("Новая игра");
	JButton btnExit = new JButton("Выход");
	GridLayout layoutTTT = new GridLayout(Params.FIELD_SIZE, Params.FIELD_SIZE);
	final JPanel panelTTT = new JPanel();

	public SwingGame(String name) {
		super(name);
	}

	public void addComponentsToPane(final Container pane) {

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(1, 3));

		btnExit.addActionListener((ae) -> exit());
		btnNewGame.addActionListener((ae) -> newGame());
		controls.add(btnNewGame);
		controls.add(btnExit);

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

		// Add cells
		for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
			for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
				GUICell cell = new GUICell();

				cell.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent evt) {
						if (evt.getButton() == MouseEvent.BUTTON1) {
							cell.allocatePlayer();
						} else if (evt.getButton() == MouseEvent.BUTTON3) {
							cell.allocateAI();
						}
					}
				});

				panelTTT.add(cell);
			}
		}

		pane.add(panelTTT, BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
	}

	private void exit() {
		setVisible(false);
		dispose();
		System.exit(NORMAL);
	}

	private void newGame() {
		for (Component cmp : panelTTT.getComponents()) {
			GUICell cell = (GUICell) cmp;
			cell.clear();
		}
	}

	private static void createAndShowGUI() {

		SwingGame frame = new SwingGame("Крестики-нолики");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(300, 300, 800, 550);
		frame.setMinimumSize(new Dimension(600, 350));
		frame.addComponentsToPane(frame.getContentPane());
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

