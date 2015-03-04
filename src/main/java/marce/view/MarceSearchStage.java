package marce.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
    private TextField perAnno;
    private TextField perPosto;
    private TextField perMarcia;
    private TextField perKmMin;
    private TextField perKmMax;

    public MarceSearchStage(List<Marcia> marce) {
        marceTableView = new MarceTableView();
        marceManager = new MarceManager(marce);

        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.setHgap(10);
        root.setVgap(10);
        root.getColumnConstraints().addAll(column);

        Scene scene = new Scene(root, 1200, 800);

        int rowNumber = 0;
        Text sceneTitle = new Text("Cerca marce");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        VBox titleBox = new VBox();
        titleBox.setPadding(new Insets(10, 10, 10, 10));
        titleBox.getChildren().addAll(sceneTitle);
        root.add(titleBox, 0, rowNumber);

        rowNumber++;
        TabPane tabPane = new TabPane();
        tabPane.setMinHeight(140);
        tabPane.getTabs().addAll(buildCercaPerAnnoTab(), buildCercaPerPostoTab(), buildCercaPerMarciaTab(), buildCercaPerKmTab());
        root.add(tabPane, 0, rowNumber);

        rowNumber++;
        marceList = FXCollections.observableArrayList();
        marceList.addAll(marceManager.getMarce());
        marceTableView.setItems(marceList);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        marceTableView.setPrefHeight(800);
        vbox.getChildren().addAll(marceTableView);
        root.add(vbox, 0, rowNumber);

        setTitle("Cerca Marce");
        setScene(scene);
    }

    private Tab buildCercaPerAnnoTab() {
        Tab tab = new Tab();
        tab.setText("Per anno");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label annoLabel = new Label("Anno:");
        grid.add(annoLabel, 0, 0);
        perAnno = new TextField();
        grid.add(perAnno, 1, 0);

        tab.setContent(grid);
        return tab;
    }

    private Tab buildCercaPerPostoTab() {
        Tab tab = new Tab();
        tab.setText("Per posto");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label postoLabel = new Label("Posto:");
        grid.add(postoLabel, 0, 0);
        perPosto = new TextField();
        grid.add(perPosto, 1, 0);

        tab.setContent(grid);
        return tab;
    }

    private Tab buildCercaPerMarciaTab() {
        Tab tab = new Tab();
        tab.setText("Per marcia");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label marciaLabel = new Label("Marcia:");
        grid.add(marciaLabel, 0, 0);
        perMarcia = new TextField();
        perMarcia.setMinWidth(300);
        grid.add(perMarcia, 1, 0);

        tab.setContent(grid);
        return tab;
    }

    private Tab buildCercaPerKmTab() {
        Tab tab = new Tab();
        tab.setText("Per marcia");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label kmMinLabel = new Label("Km minimo:");
        grid.add(kmMinLabel, 0, 0);
        perKmMin = new TextField();
        grid.add(perKmMin, 1, 0);

        Label kmMaxLabel = new Label("Km massimo:");
        grid.add(kmMaxLabel, 0, 1);
        perKmMax = new TextField();
        grid.add(perKmMax, 1, 1);

        tab.setContent(grid);
        return tab;
    }
}
