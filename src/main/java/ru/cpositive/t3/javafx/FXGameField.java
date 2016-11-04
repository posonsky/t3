package ru.cpositive.t3.javafx;


import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import ru.cpositive.t3.JavaFXGame;
import ru.cpositive.t3.logic.BaseGameField;
import ru.cpositive.t3.logic.Params;


public class FXGameField extends BaseGameField {

	private JavaFXGame game;
	private GridPane gridPane;

	public FXGameField(JavaFXGame game, StackPane stackPane) {
		super();

		this.game = game;

		// Очистим панель
		stackPane.getChildren().clear();

		gridPane = new GridPane();
		gridPane.setHgap(5);
		gridPane.setVgap(5);
		gridPane.setPadding(new Insets(10, 10, 10, 10));

		NumberBinding rectsAreaSize = Bindings.min(stackPane.heightProperty(),
				stackPane.widthProperty());

		for (int horiz = 0; horiz < Params.FIELD_SIZE; horiz++) {
			for (int vert = 0; vert < Params.FIELD_SIZE; vert++) {
				CanvasCell cell = new CanvasCell(this, field[horiz][vert]);

				// Биндим размеры
				cell.heightProperty()
						.bind(rectsAreaSize.divide(Params.FIELD_SIZE + 0.5));
				cell.widthProperty().bind(cell.heightProperty());

				gridPane.add(cell, horiz, vert);
			}
		}

		stackPane.getChildren().add(gridPane);
	}

	@Override
	public void display() {
		// Ничего
		if (gridPane != null) {
			for (Node node : gridPane.getChildren()) {
				CanvasCell canvasCell = (CanvasCell) node;
				canvasCell.draw();
			}
		}
	}

	@Override
	public void displayMessage(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Крестики & нолики");
		alert.setHeaderText("Внимание, внимание!");
		alert.setContentText(msg);

		alert.showAndWait();
	}

	public JavaFXGame getGame() {
		return game;
	}
}

