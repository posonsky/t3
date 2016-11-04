package ru.cpositive.t3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.cpositive.t3.javafx.FXGameField;
import ru.cpositive.t3.logic.AIPlayer;
import ru.cpositive.t3.logic.Dots;
import ru.cpositive.t3.logic.Player;

public class JavaFXGame extends Application {

	private BorderPane root;
	private FXGameField gameField;
	private Player aiPlayer;

	protected void newGame() {
		// System.out.println("New game");
		StackPane stackPane = new StackPane();

		gameField = new FXGameField(this, stackPane);

		root.setCenter(stackPane);
		aiPlayer = new AIPlayer(gameField);
	}

	public void proceedCycle() {
		// gameField.display();
		if (gameField.isFinalState(Dots.PLAYER_DOT)) {
			// exit();
			newGame();
		} else {
			boolean aiWin = aiPlayer.turn();
			gameField.display();
			if (aiWin) {
				// exit();
				newGame();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Button btnExit = new Button();
		btnExit.setText("Выход");
		btnExit.setOnAction((ae) -> Platform.exit());
		Button btnNewGame = new Button();
		btnNewGame.setText("Новая игра");
		btnNewGame.setOnAction((ae) -> newGame());

		FlowPane controls = new FlowPane();
		controls.setVgap(8);
		controls.setHgap(4);
		controls.setAlignment(Pos.CENTER);
		controls.getChildren().addAll(btnNewGame, btnExit);

		root = new BorderPane();
		root.setBottom(controls);

		newGame();

		Scene scene = new Scene(root, 400, 450);
		primaryStage.setTitle("Крестики-нолики");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
