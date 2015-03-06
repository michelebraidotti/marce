package marce.commons;

import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * Created by michele on 3/6/15.
 */
public class MarceDialogs {

    public static void showError(Stage owner, Exception e) {
        Dialogs.create()
                .owner(owner)
                .title("Error Dialog")
                .masthead("Problema.")
                .message(e.getMessage())
                .showError();
    }

    protected static void showError(Stage owner, String message) {
        Dialogs.create()
                .owner(owner)
                .title("Error Dialog")
                .masthead("Problema.")
                .message(message)
                .showError();
    }
}
