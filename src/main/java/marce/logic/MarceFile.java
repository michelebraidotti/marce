package marce.logic;

import marce.domain.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author michele
 */
public class MarceFile {
    public static String DELIMITER="\"";
    public static String SEPARATOR=";";
    public static int COL_COUNT = 9;
    
    public static List<Marcia> loadFromFile(String absPath) throws FileNotFoundException, IOException, ParsingException {
        List<String> lines = new ArrayList<String>();
        BufferedReader readbuffer = new BufferedReader(new FileReader(absPath));
        String strRead;
        while ( (strRead=readbuffer.readLine()) != null ){
            lines.add(strRead);
        }
        List<Marcia> marce = new ArrayList<>();
        for (String line:lines) {
            String[] entries = line.split(SEPARATOR);
            int i = 0;
            String[] row = new String[COL_COUNT];
            for (String entry:entries) {
                String inPattern = DELIMITER + "(.*)" + DELIMITER;
                Pattern p  = Pattern.compile(inPattern);
                Matcher m = p.matcher("");
                m.reset(entry);
                String content = null;
                try {
                    m.find();
                    content = m.group(1);
                }
                catch ( Exception ex) {
                    System.out.println( entry + ":" + ex.getMessage() );
                }
                row[i++] = content;
            }
            int j = 0;
            Marcia marcia  = new Marcia();
            marcia.setId(Integer.parseInt(row[j++]));
            marcia.setDataInizio(new DataDelCalendario(row[j++]));
            marcia.setDataFine(new DataDelCalendario(row[j++]));
            marcia.setNomeEvento(row[j++]);
            if ( row[j] == null || row[j].equals("")) {
                marcia.setEdizione(0);
            }
            else {
                marcia.setEdizione(Integer.parseInt(row[j]));
            }
            j++;
            marcia.setPosto(new Posto(row[j++],row[j++]));
            marcia.setKm( new BigDecimal(row[j++]));
            marcia.setTempo(new Tempo(row[j++]));
            marce.add(marcia);
        }
        return marce;
    }

    public static void writeToFile(String absPath, List<Marcia> marce) throws IOException {
	FileWriter fstream = new FileWriter(absPath);
        try (BufferedWriter out = new BufferedWriter(fstream)) {
            for(Marcia marcia:marce) {
                String line = "";
                // {"Progressivo", "Data Inizio", "Data Fine", "Denominazione", "Edizione", "Localita'", "Zona", "Km", "Tempo"}
                int j = 0;
                line += DELIMITER + marcia.getId() + DELIMITER + SEPARATOR; // ID
                line += DELIMITER + marcia.getDataInizio().exportString()
                        + DELIMITER + SEPARATOR; // Data inizio
                line += DELIMITER + marcia.getDataFine().exportString()
                        + DELIMITER + SEPARATOR; // Data fine
                line += DELIMITER + marcia.getNomeEvento() + DELIMITER + SEPARATOR; // Denominazione
                line += DELIMITER + marcia.getEdizione() + DELIMITER + SEPARATOR; // Edizione
                line += DELIMITER + marcia.getPosto().getLocalita() + DELIMITER + SEPARATOR; // Localita
                line += DELIMITER + marcia.getPosto().getZona() + DELIMITER + SEPARATOR; // Zona
                line += DELIMITER + marcia.getKm() + DELIMITER + SEPARATOR; // Km
                line += DELIMITER + marcia.getTempo() + DELIMITER + "\n"; // Tempo
                out.write(line);
            }
        }
    } 
}
