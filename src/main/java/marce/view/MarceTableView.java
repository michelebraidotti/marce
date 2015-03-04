package marce.view;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import marce.domain.Marcia;

/**
 * Created by michele on 2/22/15.
 */
public class MarceTableView extends TableView {

    public MarceTableView() {

        TableColumn progressivoCol = new TableColumn("Progressivo");
        progressivoCol.setMinWidth(100);
        progressivoCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn eventoCol = new TableColumn("Evento");
        eventoCol.setMinWidth(350);
        eventoCol.setCellValueFactory(new PropertyValueFactory<>("nomeEvento"));

        TableColumn edizioneCol = new TableColumn("Edizione");
        edizioneCol.setMinWidth(100);
        edizioneCol.setCellValueFactory(new PropertyValueFactory<>("edizione"));

        TableColumn dataInizioCol = new TableColumn("Data Inizio");
        dataInizioCol.setMinWidth(100);
        dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));

        TableColumn dataFineCol = new TableColumn("Data Fine");
        dataFineCol.setMinWidth(100);
        dataFineCol.setCellValueFactory(new PropertyValueFactory<>("dataFine"));

        TableColumn postoCol = new TableColumn("Posto");
        postoCol.setMinWidth(200);
        postoCol.setCellValueFactory(new PropertyValueFactory<>("posto"));

        TableColumn distanzaCol = new TableColumn("Distanza");
        distanzaCol.setMinWidth(100);
        distanzaCol.setCellValueFactory(new PropertyValueFactory<>("km"));

        TableColumn tempoCol = new TableColumn("Tempo");
        tempoCol.setMinWidth(100);
        tempoCol.setCellValueFactory(new PropertyValueFactory<>("tempo"));

        getColumns().addAll(progressivoCol, dataInizioCol, dataFineCol, eventoCol, edizioneCol, postoCol, distanzaCol, tempoCol);
    }


}
