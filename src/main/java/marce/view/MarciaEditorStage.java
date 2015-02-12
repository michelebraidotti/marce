package marce.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import marce.domain.Marcia;

/**
 * Created by michele on 2/12/15.
 */
public class MarciaEditorStage extends Stage {
    public MarciaEditorStage(Stage primaryStage, Marcia marcia) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(primaryStage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text((marcia == null || marcia.getId() == 0) ? "Nuova Marcia" : "Modifica marcia");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label nomeMarciaLabel = new Label("Demominazione:");
        grid.add(nomeMarciaLabel, 0, 1);

        TextField nomeMarcia = new TextField();
        grid.add(nomeMarcia, 1, 1);

        Label dataInizioLabel = new Label("Data inizio:");
        grid.add(dataInizioLabel, 2, 1);
        DatePicker dataInizio = new DatePicker();
        grid.add(dataInizio, 3, 1);

        Label edizioneMarciaLabel = new Label("Edizione:");
        grid.add(edizioneMarciaLabel, 0, 2);

        TextField edizioneMarcia = new TextField();
        grid.add(edizioneMarcia, 1, 2);

        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setAlignment(Pos.CENTER);
        buttonsGrid.setHgap(10);
        buttonsGrid.setVgap(10);
        buttonsGrid.setPadding(new Insets(25, 25, 25, 25));

        Button newBtn = new Button((marcia == null || marcia.getId() == 0) ? "Salva" : "Aggiorna");
        HBox hbNewBtn = new HBox(10);
        hbNewBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbNewBtn.getChildren().add(newBtn);
        newBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Hello world!");
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
        grid.add(buttonsGrid, 0, 3, 4, 1);

        Scene scene = new Scene(grid, 800, 600);
        this.setTitle("Marcia");
        this.setScene(scene);

    }
}
