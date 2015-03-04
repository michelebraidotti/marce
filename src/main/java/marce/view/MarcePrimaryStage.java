package marce.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.logic.InvalidIdException;
import marce.logic.MarceFile;
import marce.logic.MarceManager;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/16/15.
 */
public class MarcePrimaryStage extends Stage {
    private MarceTableView marceTableView = null;
    private MarceManager marceManager = new MarceManager();
    private ObservableList<Marcia> marceList;

    public MarcePrimaryStage() {
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);

        Scene scene = new Scene(root, 1200, 800);

        // menu bar
        root.add(buildMenuBar(), 0, 0);

        //button bar
        root.add(buildToolBar(), 0, 1);

        // main marceTableView
        marceTableView = new MarceTableView();
        marceTableView.setEditable(true);

        marceList = FXCollections.observableArrayList();
        marceTableView.setItems(marceList);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        marceTableView.setPrefHeight(800);
        vbox.getChildren().addAll(marceTableView);
        root.add(vbox, 0, 2);

        setTitle("Gestione Marce");
        setScene(scene);
    }

    public void onMarciaCreated(Marcia marcia) {
        try {
            marceManager.add(marcia);
        } catch (InvalidIdException e) {
            showError(e);
        }
        marceList.add(marcia);
    }

    public void onMarciaUpdated(Marcia marcia) {
        // find old marcia into model and replace it
        // refresh view
    }

    private ToolBar buildToolBar() {
        ToolBar toolBar = new ToolBar();
        Button buttonNew = new Button("Nuovo", new ImageView(new Image("icons/document_32.png")));
        buttonNew.setContentDisplay(ContentDisplay.TOP);
        buttonNew.setOnAction(new NewAction());

        Button buttonOpen = new Button("Apri", new ImageView(new Image("icons/folder_32.png")));
        buttonOpen.setContentDisplay(ContentDisplay.TOP);
        buttonOpen.setOnAction(new OpenAction());

        Button buttonSave = new Button("Salva", new ImageView(new Image("icons/save_32.png")));
        buttonSave.setContentDisplay(ContentDisplay.TOP);
        buttonSave.setOnAction(new SaveAction());

        Button buttonSaveNew = new Button("Salva Con Nome", new ImageView(new Image("icons/save_32.png")));
        buttonSaveNew.setContentDisplay(ContentDisplay.TOP);
        buttonSaveNew.setOnAction(new SaveWithNameAction());

        Button buttonPrint = new Button("Stampa", new ImageView(new Image("icons/print_32.png")));
        buttonPrint.setContentDisplay(ContentDisplay.TOP);
        buttonPrint.setOnAction(new PrintAction());

        Button buttonSearch = new Button("Cerca", new ImageView(new Image("icons/search_32.png")));
        buttonSearch.setContentDisplay(ContentDisplay.TOP);
        buttonSearch.setOnAction(new SearchAction());

        toolBar.getItems().addAll(buttonNew, buttonOpen, buttonSave, buttonSaveNew, buttonPrint, buttonSearch);
        return toolBar;
    }

    private MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");

        MenuItem newItem = new MenuItem("Nuovo");
        newItem.setOnAction(new NewAction());

        MenuItem openItem = new MenuItem("Apri");
        openItem.setOnAction(new OpenAction());

        MenuItem saveItem = new MenuItem("Salva");
        saveItem.setOnAction(new SaveWithNameAction());

        MenuItem saveNewItem = new MenuItem("Salva Con Nome");
        saveNewItem.setOnAction(new SaveAction());

        MenuItem printItem = new MenuItem("Stampa");
        printItem.setOnAction(new PrintAction());

        MenuItem searchItem = new MenuItem("Cerca");
        searchItem.setOnAction(new SearchAction());

        menuFile.getItems().addAll(newItem, openItem, saveItem, saveNewItem, printItem, searchItem);

        Menu menuHelp = new Menu("Aiuto");
        menuBar.getMenus().addAll(menuFile, menuHelp);
        return menuBar;
    }

    private class NewAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Marcia marcia = marceManager.getNew();
            MarciaEditorStage newMarciaStage = new MarciaEditorStage(MarcePrimaryStage.this, null, marceManager.getDenominazioniList(), marceManager.getPostiList());
            newMarciaStage.show();
        }
    }

    private class OpenAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Apri marce");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            File file = fileChooser.showOpenDialog(MarcePrimaryStage.this);
            if (file != null) {
                List<Marcia> marce = new ArrayList<Marcia>();
                try {
                    marce = MarceFile.loadFromFile(file.getAbsolutePath());
                } catch (IOException e) {
                    showError(e);
                } catch (ParsingException e) {
                    showError(e);
                }
                marceManager.setMarce(marce);
                marceList.addAll(marce);
            }
        }
    }

    protected void showError(Exception e) {
        Dialogs.create()
                .owner(MarcePrimaryStage.this)
                .title("Error Dialog")
                .masthead("Problema.")
                .message(e.getMessage())
                .showError();
    }

    private class SaveAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class SaveWithNameAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class PrintAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class SearchAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            MarceSearchStage marceSearchStage = new MarceSearchStage(marceManager.getMarce());
            marceSearchStage.show();
        }
    }
}