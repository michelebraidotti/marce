package marce;

import javafx.application.Application;
import javafx.stage.Stage;
import marce.view.MarcePrimaryStage;

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
