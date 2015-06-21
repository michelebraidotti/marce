package marce.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.logic.MarceManager;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by michele on 2/22/15.
 */
public class MarceSearchStage extends Stage {
    private final ObservableList<Marcia> marceList;
    private final Label kmTotali;
    private final Label tempoTotale;
    private FilteredList<Marcia> filteredMarceList;
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
        tabPane.setMinHeight(90);
        tabPane.getTabs().addAll(buildCercaPerAnnoTab(), buildCercaPerPostoTab(), buildCercaPerMarciaTab(), buildCercaPerKmTab());
        root.add(tabPane, 0, rowNumber);

        rowNumber++;
        marceList = FXCollections.observableArrayList();
        marceList.addAll(marceManager.getMarce());
        filteredMarceList = new FilteredList<Marcia>(marceList, p -> true);
        marceTableView.setItems(filteredMarceList);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        marceTableView.setPrefHeight(800);
        vbox.getChildren().addAll(marceTableView);
        root.add(vbox, 0, rowNumber);

        rowNumber++;
        GridPane bottomPane = new GridPane();
        bottomPane.setMaxHeight(90);
        bottomPane.setPadding(new Insets(0, 10, 10, 10));
        Label kmTotaliLabel = new Label("Km totali: ");
        bottomPane.add(kmTotaliLabel, 0, 0);
        kmTotali = new Label("0");
        bottomPane.add(kmTotali, 1, 0);
        Label tempoTotaleLabel = new Label(", Tempo totale: ");
        bottomPane.add(tempoTotaleLabel, 2, 0);
        tempoTotale = new Label("0:0:0");
        bottomPane.add(tempoTotale, 3, 0);
        root.add(bottomPane, 0, rowNumber);

        setTitle("Cerca Marce");
        setScene(scene);

        filteredMarceList.addListener(new ListChangeListener<Marcia>(){

            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Marcia> pChange) {
                List<Marcia> filteredMarce = new ArrayList<Marcia>();
                Iterator<Marcia> filteredMarceIterator = filteredMarceList.listIterator();
                while (filteredMarceIterator.hasNext()) {
                    filteredMarce.add(filteredMarceIterator.next());
                }
                kmTotali.setText(MarceManager.TotaleKm(filteredMarce) + "");
                try {
                    tempoTotale.setText(MarceManager.TotaleTempo(filteredMarce).toString());
                } catch (ParsingException e) {
                    tempoTotale.setText("??");
                    e.printStackTrace();
                }

            }
        });

    }

    private class SearchTab extends Tab {
        private final GridPane grid;

        public SearchTab(String title) {
            setText(title);
            setClosable(false);

            grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            setContent(grid);
        }

        public void add(Node child, int columnIndex, int rowIndex) {
            grid.add(child, columnIndex, rowIndex);
        }

    }

    private Tab buildCercaPerAnnoTab() {
        SearchTab tab = new SearchTab("Per anno");

        Label annoLabel = new Label("Anno:");
        tab.add(annoLabel, 0, 0);
        perAnno = new TextField();
        tab.add(perAnno, 1, 0);

        perAnno.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMarceList.setPredicate(marcia -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                Integer annoFilter = null;
                try {
                    annoFilter = Integer.parseInt(newValue.toLowerCase());
                }
                catch (NumberFormatException nfe) {
                    annoFilter = null;
                }

                if (annoFilter == null || marcia.getDataInizio().getLocalDate().getYear() == annoFilter) {
                    return true;
                }
                return false;
            });
        });

        return tab;
    }

    private Tab buildCercaPerPostoTab() {
        SearchTab tab = new SearchTab("Per posto");

        Label postoLabel = new Label("Posto:");
        tab.add(postoLabel, 0, 0);
        perPosto = new TextField();
        tab.add(perPosto, 1, 0);

        perPosto.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMarceList.setPredicate(marcia -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (marcia.getPosto().getLocalita().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (marcia.getPosto().getZona().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        return tab;
    }

    private Tab buildCercaPerMarciaTab() {
        SearchTab tab = new SearchTab("Per marcia");

        Label marciaLabel = new Label("Marcia:");
        tab.add(marciaLabel, 0, 0);
        perMarcia = new TextField();
        perMarcia.setMinWidth(300);
        tab.add(perMarcia, 1, 0);

        perMarcia.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMarceList.setPredicate(marcia -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (marcia.getNomeEvento().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false;
            });
        });

        return tab;
    }

    private Tab buildCercaPerKmTab() {
        SearchTab tab = new SearchTab("Per km");

        Label kmMinLabel = new Label("Km minimo:");
        tab.add(kmMinLabel, 0, 0);
        perKmMin = new TextField();
        tab.add(perKmMin, 1, 0);

        Label kmMaxLabel = new Label("Km massimo:");
        tab.add(kmMaxLabel, 2, 0);
        perKmMax = new TextField();
        tab.add(perKmMax, 3, 0);

        perKmMin.textProperty().addListener(new KmChangeListener());
        perKmMax.textProperty().addListener(new KmChangeListener());

        return tab;
    }

    private class KmChangeListener implements ChangeListener<Object> {

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            filteredMarceList.setPredicate(marcia -> {
                if (!StringUtils.isEmpty(perKmMin.getText()) && !StringUtils.isEmpty(perKmMax.getText())) {
                    BigDecimal min = new BigDecimal(perKmMin.getText());
                    BigDecimal max = new BigDecimal(perKmMax.getText());
                    BigDecimal marciaKm = marcia.getKm();
                    if ( marciaKm.compareTo(min) == 1 && marciaKm.compareTo(max) == -1 ) return true;
                    return false;
                }
                return  true;
            });

        }

    }
}
