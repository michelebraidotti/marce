package marce.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import marce.commons.MarceDialogs;
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
    private File marceFile;
    private final Label marceFilePathLabel;

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
        marceTableView.setPrefHeight(700);
        vbox.getChildren().addAll(marceTableView);
        root.add(vbox, 0, 2);

        GridPane bottomPane = new GridPane();
        bottomPane.setMaxHeight(90);
        bottomPane.setPadding(new Insets(0, 10, 10, 10));
        Label marceFileLabel = new Label("File in uso: ");
        bottomPane.add(marceFileLabel, 0, 0);
        marceFilePathLabel = new Label("Nessun file");
        bottomPane.add(marceFilePathLabel, 1, 0);
        root.add(bottomPane, 0, 3);

        setTitle("Gestione Marce");
        setScene(scene);
    }

    private File getMarceFile() {
        return marceFile;
    }

    private void setMarceFile(File marceFile) {
        this.marceFile = marceFile;
        marceFilePathLabel.setText(this.marceFile == null? "No file" : this.marceFile.getAbsolutePath());
    }

    public void onMarciaCreated(Marcia marcia) {
        try {
            marceManager.add(marcia);
        } catch (InvalidIdException e) {
            MarceDialogs.showError(this, e);
            MarciaEditorStage newMarciaStage = new MarciaEditorStage(MarcePrimaryStage.this, marcia, marceManager.getDenominazioniList(), marceManager.getPostiList());
            newMarciaStage.show();
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
            setMarceFile(fileChooser.showOpenDialog(MarcePrimaryStage.this));
            if (getMarceFile() != null) {
                List<Marcia> marce = new ArrayList<Marcia>();
                try {
                    marce = MarceFile.loadFromFile(getMarceFile().getAbsolutePath());
                } catch (IOException e) {
                    MarceDialogs.showError(MarcePrimaryStage.this, e);
                } catch (ParsingException e) {
                    MarceDialogs.showError(MarcePrimaryStage.this, e);
                }
                marceManager.setMarce(marce);
                marceList.addAll(marce);
            }
        }
    }

    private class SaveAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (getMarceFile() != null) {
                try {
                    MarceFile.writeToFile(getMarceFile().getAbsolutePath(), marceManager.getMarce());
                } catch (IOException e) {
                    MarceDialogs.showError(MarcePrimaryStage.this, e);
                }
            }
        }
    }

    private class SaveWithNameAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Nuovo file marce");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            setMarceFile(fileChooser.showSaveDialog(MarcePrimaryStage.this));
            try {
                MarceFile.writeToFile(getMarceFile().getAbsolutePath(), marceManager.getMarce());
            } catch (IOException e) {
                MarceDialogs.showError(MarcePrimaryStage.this, e);
            }
        }
    }

    private class PrintAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            Printer printer = Printer.getDefaultPrinter();
            Stage dialogStage = new Stage(StageStyle.DECORATED);
            PrinterJob job = PrinterJob.createPrinterJob(printer);
            if (job != null) {
                boolean showDialog = job.showPageSetupDialog(dialogStage);
                if (showDialog) {
                    marceTableView.setScaleX(0.60);
                    marceTableView.setScaleY(0.60);
                    marceTableView.setTranslateX(-220);
                    marceTableView.setTranslateY(-70);
                    boolean success = job.printPage(marceTableView);
                    if (success) {
                        job.endJob();
                    }
                    marceTableView.setTranslateX(0);
                    marceTableView.setTranslateY(0);
                    marceTableView.setScaleX(1.0);
                    marceTableView.setScaleY(1.0);
                }
            }
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
