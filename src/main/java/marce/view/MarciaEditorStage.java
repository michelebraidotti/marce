package marce.view;

import org.apache.commons.lang3.StringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import marce.commons.MarceDialogs;
import marce.domain.*;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by michele on 2/12/15.
 */
public class MarciaEditorStage extends Stage {
    private final ComboBox nomeMarciaComboBox;
    private final TextField edizione;
    private final DatePicker dataInizio;
    private final DatePicker dataFine;
    private final ComboBox postoComboBox;
    private final TextField km;
    private final TextField metri;
    private final TextField ore;
    private final TextField minuti;
    private final TextField secondi;
    private final PostoEditorStage postoEditorStage;
    private final Label progressivoLabelValue;

    private Marcia marcia;

    public MarciaEditorStage(MarcePrimaryStage primaryStage, Marcia existingMarcia, Set<String> denominazioni, Set<Posto> posti) {
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(primaryStage);
        postoEditorStage = new PostoEditorStage(this);
        if (existingMarcia == null) {
            this.marcia = new Marcia();
        }
        else {
            this.marcia = existingMarcia;
        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        int rowNumber = 0;

        Text sceneTitle = new Text((marcia == null || marcia.getId() == 0) ? "Nuova Marcia" : "Modifica marcia");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, rowNumber, 4, 1);

        rowNumber++;
        Label progressivoLabel = new Label("Progressivo:");
        grid.add(progressivoLabel, 0, rowNumber);
        progressivoLabelValue = new Label("--");
        grid.add(progressivoLabelValue, 1, rowNumber);


        rowNumber++;
        Label nomeMarciaLabel = new Label("Evento:");
        grid.add(nomeMarciaLabel, 0, rowNumber);
        ObservableList<String> options = FXCollections.observableList(new ArrayList(denominazioni));
        nomeMarciaComboBox = new ComboBox(options);
        nomeMarciaComboBox.setEditable(true);
        nomeMarciaComboBox.setMaxWidth(350);
        grid.add(nomeMarciaComboBox, 1, rowNumber);

        Label edizioneLabel = new Label("Edizione:");
        grid.add(edizioneLabel, 2, rowNumber);
        edizione = new TextField();
        edizione.setMaxWidth(50);
        grid.add(edizione, 3, rowNumber);


        rowNumber++;
        Label dataInizioLabel = new Label("Data inizio:");
        grid.add(dataInizioLabel, 0, rowNumber);
        dataInizio = new DatePicker();
        grid.add(dataInizio, 1, rowNumber);

        Label dataFineLabel = new Label("Data fine:");
        grid.add(dataFineLabel, 2, rowNumber);
        dataFine = new DatePicker();
        grid.add(dataFine, 3, rowNumber);

        rowNumber++;
        Label postoLabel = new Label("Posto:");
        grid.add(postoLabel, 0, rowNumber);
        ObservableList<Posto> optionsPosto = FXCollections.observableList(new ArrayList(posti));
        postoComboBox = new ComboBox(optionsPosto);
        postoComboBox.setMaxWidth(350);
        grid.add(postoComboBox, 1, rowNumber);
        Button postoButton = new Button("Aggiungi posto");
        postoButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                postoEditorStage.show();
            }
        });
        grid.add(postoButton, 2, rowNumber);

        rowNumber++;
        Label distanzaLabel = new Label("Distanza:");
        grid.add(distanzaLabel, 0, rowNumber);

        GridPane distanzaGrid = new GridPane();
        distanzaGrid.setAlignment(Pos.CENTER_LEFT);
        km = new TextField();
        km.setMaxWidth(50);
        distanzaGrid.add(km, 0, 0);
        Label kmLabel = new Label(" KM + ");
        distanzaGrid.add(kmLabel, 1, 0);
        metri = new TextField();
        metri.setMaxWidth(50);
        distanzaGrid.add(metri, 2, 0);
        Label metriLabel = new Label(" metri");
        distanzaGrid.add(metriLabel, 3, 0);
        grid.add(distanzaGrid, 1, rowNumber, 3, 1);

        rowNumber++;
        Label tempoLabel = new Label("Tempo:");
        grid.add(tempoLabel, 0, rowNumber);

        GridPane tempoGrid = new GridPane();
        ore = new TextField();
        ore.setMaxWidth(50);
        tempoGrid.add(ore, 0, 0);
        Label oreLabel = new Label(" Ore ");
        tempoGrid.add(oreLabel, 1, 0);
        minuti = new TextField();
        minuti.setMaxWidth(50);
        tempoGrid.add(minuti, 2, 0);
        Label minutiLabel = new Label(" minuti ");
        tempoGrid.add(minutiLabel, 3, 0);
        secondi = new TextField();
        secondi.setMaxWidth(50);
        tempoGrid.add(secondi, 4, 0);
        Label secondiLabel = new Label(" secondi");
        tempoGrid.add(secondiLabel, 6, 0);
        grid.add(tempoGrid, 1, rowNumber, 3, 1);

        rowNumber++;
        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setAlignment(Pos.CENTER);
        buttonsGrid.setHgap(10);
        buttonsGrid.setVgap(10);
        buttonsGrid.setPadding(new Insets(25, 25, 25, 25));

        Button newBtn = new Button((marcia.getId() == 0) ? "Salva" : "Aggiorna");
        HBox hbNewBtn = new HBox(10);
        hbNewBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbNewBtn.getChildren().add(newBtn);
        newBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                System.out.println("Hello world!");
                try {
                    marcia = getMarcia();
                    if (marcia.getId() == 0) {
                        primaryStage.onMarciaCreated(marcia);
                    } else {
                        primaryStage.onMarciaUpdated(marcia);
                    }
                    close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    MarceDialogs.showError(MarciaEditorStage.this, ex);
                }
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
        grid.add(buttonsGrid, 0, rowNumber, 4, 1);

        setFields();

        Scene scene = new Scene(grid, 850, 350);
        this.setTitle("Marcia");
        this.setScene(scene);
    }

    private void setFields() {
        if (marcia != null) {
            progressivoLabelValue.setText(marcia.getId().toString());
            nomeMarciaComboBox.getSelectionModel().select(marcia.getNomeEvento());
            edizione.setText(marcia.getEdizione().toString());
            dataInizio.setValue(marcia.getDataInizio().getLocalDate());
            dataFine.setValue(marcia.getDataInizio().getLocalDate());
            postoComboBox.getSelectionModel().select(marcia.getPosto());
            int kmParsed = Integer.parseInt(marcia.getKm().toPlainString().split("\\.")[0]);
            km.setText(kmParsed + "");
            if ( marcia.getKm().toPlainString().contains(".")) {
                int mtParsed = Integer.parseInt(marcia.getKm().toPlainString().split("\\.")[1]);
                metri.setText(mtParsed + "");
            }
            else {
                metri.setText("0");
            }
            ore.setText(marcia.getTempo().getOre().toString());
            minuti.setText(marcia.getTempo().getMinuti().toString());
            secondi.setText(marcia.getTempo().getSecondi().toString());
        }
    }

    public Marcia getMarcia() throws Exception {
        if ( nomeMarciaComboBox.getValue() == null ) throw new Exception("Il nome dell'evento e' obbligatorio");
        marcia.setNomeEvento(nomeMarciaComboBox.getValue().toString());
        if (StringUtils.isEmpty(edizione.getText())) edizione.setText("0");
        marcia.setEdizione(Integer.parseInt(edizione.getText()));
        if (postoComboBox.getValue() == null) postoComboBox.setValue(PostoFactory.getPostoIngoto());
        marcia.setPosto((Posto) postoComboBox.getValue());
        if (dataInizio.getValue() == null) throw new Exception("La data inizio e' obbligatoria");
        marcia.setDataInizio(new DataDelCalendario(dataInizio.getValue()));
        if ( dataFine.getValue() == null ) {
            dataFine.setValue(dataInizio.getValue());
        }
        marcia.setDataFine(new DataDelCalendario(dataFine.getValue()));
        if ( StringUtils.isEmpty(km.getText() )) km.setText("0");
        if ( StringUtils.isEmpty(metri.getText() )) metri.setText("0");
        marcia.setKm(new BigDecimal(km.getText() + "." + metri.getText()));
        if (StringUtils.isEmpty(ore.getText())) ore.setText("0");
        if (StringUtils.isEmpty(minuti.getText())) minuti.setText("0");
        if (StringUtils.isEmpty(secondi.getText())) secondi.setText("0");
        marcia.setTempo(new Tempo(ore.getText() + ":" + minuti.getText() + ":" + secondi.getText()));
        return marcia;
    }

    public void onNewPostoEvent(Posto posto) {
        postoComboBox.getItems().add(posto);
        postoComboBox.setValue(posto);
    }
}
