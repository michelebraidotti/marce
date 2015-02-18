package marce.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import marce.domain.Posto;

/**
 * Created by michele on 2/15/15.
 */
public class PostoEditorStage extends Stage {

    private final TextField localita;
    private final TextField zona;

    public  PostoEditorStage(MarciaEditorStage primaryStage) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(primaryStage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        int rowNumber = 0;

        Text sceneTitle = new Text("Nuovo Posto");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, rowNumber, 2, 1);

        rowNumber++;
        Text  localitaLabel = new Text("Posto:");
        grid.add(localitaLabel, 0, rowNumber);
        localita = new TextField();
        grid.add(localita, 1, rowNumber);

        rowNumber++;
        Text zonaLabel = new Text("Zona:");
        grid.add(zonaLabel, 0, rowNumber);
        zona = new TextField();
        grid.add(zona, 1, rowNumber);

        rowNumber++;
        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setAlignment(Pos.CENTER);
        buttonsGrid.setHgap(10);
        buttonsGrid.setVgap(10);
        buttonsGrid.setPadding(new Insets(25, 25, 25, 25));

        Button newBtn = new Button("Aggiungi");
        HBox hbNewBtn = new HBox(10);
        hbNewBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbNewBtn.getChildren().add(newBtn);
        newBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                primaryStage.onNewPostoEvent(getPosto());
                localita.setText("");
                zona.setPromptText("");
                close();
            }
        });
        buttonsGrid.add(hbNewBtn, 0, 1);

        Button cancelBtn = new Button("Cancella");
        HBox hbCancelBtn = new HBox(10);
        hbCancelBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbCancelBtn.getChildren().add(cancelBtn);
        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                close();
            }
        });
        buttonsGrid.add(cancelBtn, 1, 1);
        grid.add(buttonsGrid, 0, rowNumber, 2, 1);

        Scene scene = new Scene(grid, 300, 200);
        this.setTitle("Posto");
        this.setScene(scene);
    }

    public Posto getPosto() {
        Posto posto = new Posto();
        posto.setLocalita(localita.getText());
        posto.setZona(zona.getText());
        return  posto;
    }

    public void setPosto(Posto posto) {
        localita.setText(posto.getLocalita());
        zona.setText(posto.getZona());
    }
}
