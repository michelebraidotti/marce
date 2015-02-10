package marce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.logic.MarceFile;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class MarceApp extends Application {

    private Stage stage;
    private TableView table = new TableView();

    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        GridPane root = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        root.getColumnConstraints().addAll(column);

        Scene scene = new Scene(root, 1200, 800);

        // menu bar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");

        MenuItem newItem = new MenuItem("Nuovo");
        newItem.setOnAction(new NewAction());

        MenuItem openItem = new MenuItem("Apri");
        openItem.setOnAction(new OpenAction());

        MenuItem saveItem = new MenuItem("Salva");
        saveItem.setOnAction(new SaveAction());

        menuFile.getItems().addAll(newItem, openItem, saveItem);

        Menu menuHelp = new Menu("Aiuto");
        menuBar.getMenus().addAll(menuFile, menuHelp);

        root.add(menuBar, 0, 0);

        //button bar
        ToolBar toolBar = new ToolBar();
        Button buttonNew = new Button("Nuovo", new ImageView(new Image("icons/document_32.png")));
        buttonNew.setContentDisplay(ContentDisplay.TOP);
        buttonNew.setOnAction(new NewAction());

        Button buttonOpen = new Button("Apri", new ImageView(new Image("icons/folder_32.png")));
        buttonOpen.setContentDisplay(ContentDisplay.TOP);
        buttonOpen.setOnAction(new OpenAction());

        Button buttonSave = new Button("Salva", new ImageView(new Image("icons/save_32.png")));
        buttonSave.setContentDisplay(ContentDisplay.TOP);
        Button buttonSaveNew = new Button("Salva Con Nome", new ImageView(new Image("icons/save_32.png")));
        buttonSaveNew.setContentDisplay(ContentDisplay.TOP);
        Button buttonPrint = new Button("Stampa", new ImageView(new Image("icons/print_32.png")));
        buttonPrint.setContentDisplay(ContentDisplay.TOP);
        Button buttonSearch = new Button("Cerca", new ImageView(new Image("icons/search_32.png")));
        buttonSearch.setContentDisplay(ContentDisplay.TOP);

        toolBar.getItems().addAll(buttonNew, buttonOpen, buttonSave, buttonSaveNew, buttonPrint, buttonSearch);
        root.add(toolBar, 0, 1);

        // main table

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(table);

        root.add(vbox, 0, 2);

        // main window
        primaryStage.setTitle("Gestione MarceApp");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private class NewAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

        }
    }

    private class OpenAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    List<Marcia> marce = MarceFile.loadFromFile(file.getAbsolutePath ());
                    System.out.println("Loaded " + marce.size() + " marce");
                } catch (IOException e) {
                    showError(e);
                } catch (ParsingException e) {
                    showError(e);
                }
            }
        }
    }

    private void showError(Exception e) {
        Dialogs.create()
            .owner(stage)
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
}
