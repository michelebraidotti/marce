package marce.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Created by michele on 8/7/15.
 */
public class CurrentlyOpenedFileView extends GridPane {
    private final Label marceFilePathLabel;

    public CurrentlyOpenedFileView() {
        GridPane bottomPane = new GridPane();
        bottomPane.setMaxHeight(90);
        bottomPane.setPadding(new Insets(0, 10, 10, 10));
        Label marceFileLabel = new Label("File in uso: ");
        bottomPane.add(marceFileLabel, 0, 0);
        marceFilePathLabel = new Label("Nessun file");
        bottomPane.add(marceFilePathLabel, 1, 0);
    }

    public void updateFilePathLabel(String filePathLabel) {
        marceFilePathLabel.setText(filePathLabel == null? "No file" : filePathLabel);
    }
}
