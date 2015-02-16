package marce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.logic.MarceFile;
import marce.logic.MarceManager;
import marce.view.MarcePrimaryStage;
import marce.view.MarciaEditorStage;
import org.controlsfx.dialog.Dialogs;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class MarceApp extends Application {


    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage = new MarcePrimaryStage();
        primaryStage.show();
    }
}
