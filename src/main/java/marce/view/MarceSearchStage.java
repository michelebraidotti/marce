package marce.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import marce.domain.Marcia;
import marce.logic.MarceManager;

import java.util.List;

/**
 * Created by michele on 2/22/15.
 */
public class MarceSearchStage extends Stage {
    private final ObservableList<Object> marceList;
    private MarceTableView marceTableView;
    private MarceManager marceManager;

    public MarceSearchStage(List<Marcia> marce) {
        marceTableView = new MarceTableView();
        marceManager = new MarceManager(marce);

        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);

        Scene scene = new Scene(root, 1200, 800);
        marceList = FXCollections.observableArrayList();
        marceList.addAll(marceManager.getMarce());
        marceTableView.setItems(marceList);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        marceTableView.setPrefHeight(800);
        vbox.getChildren().addAll(marceTableView);
        root.add(vbox, 0, 2);

        setTitle("Cerca Marce");
        setScene(scene);
    }
}
