package marce.logic;

import marce.NotFoudException;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.domain.Posto;
import marce.domain.Tempo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author michele
 */
public class MarceManager {
    private List<Marcia> marce;

    public MarceManager() {
        marce = new ArrayList<Marcia>();
    }
    
    public MarceManager(List<Marcia> marce) {
        this.marce = marce;
    }
    
    public List<Marcia> getMarce() {
        return marce;
    }

    public void setMarce(List<Marcia> marce) {
        this.marce = marce;
    }
    
    public Marcia getMarcia(int marciaId) throws NotFoudException {
        for ( Marcia marcia:marce ) {
            if ( marcia.getId() == marciaId ) return marcia;
        }
        throw new NotFoudException("Marcia " + marciaId + " non trovata");
    }
    
    public boolean removeMarcia(int marciaId) throws NotFoudException {
        return remove(getMarcia(marciaId));
    }

    private int getNextId() {
        if ( marce.isEmpty() ) {
            return 1;
        }
        else {
            int max = 0;
            for (Marcia marcia:marce) {
                if ( marcia.getId() > max ) {
                    max = marcia.getId();
                }
            }
            return max + 1;
        }
    }
    
    public int size() {
        return marce.size();
    }

    public Marcia remove(int i) {
        return marce.remove(i);
    }

    public boolean remove(Marcia m) {
        return marce.remove(m);
    }

    public Marcia get(int i) {
        return marce.get(i);
    }

    public boolean add(Marcia m) throws InvalidIdException {
        if ( m.getId() == 0 ) {
            m.setId(getNextId());
        }
        return marce.add(m);
    }
    
    public Set<String> getDenominazioniList() {
        List<String> denominazioni = new ArrayList<>();
        for (Marcia marcia:marce) {
                if ( marcia.getNomeEvento() != null )
                    denominazioni.add(marcia.getNomeEvento());
        }
        // Arrays.sort(denominazioni);
        Set<String> denominazioniSet = new TreeSet<>(denominazioni);
        return denominazioniSet;
    }
    
    
    public Set<Posto> getPostiList() {
        List<Posto> posti = new ArrayList<>();
        for (Marcia marcia:marce) {
                if ( marcia.getPosto() != null)
                    posti.add(marcia.getPosto());
        }
        Set<Posto> postiSet = new TreeSet<>(posti);
        return postiSet;
    }

    public static BigDecimal totaleKm(List<Marcia> marce) {
        BigDecimal totale = new BigDecimal("0.00");
        for (Marcia marcia:marce) {
            totale = totale.add(marcia.getKm());
        }
        return totale;
    }

    public static Tempo totaleTempo(List<Marcia> marce) throws ParsingException {
        Tempo totale = new Tempo(0);
        for (Marcia marcia:marce) {
            totale = totale.add(marcia.getTempo());
        }
        return totale;
    }

    public List<Marcia> getPerAnno(int year) {
        List<Marcia> out = new ArrayList<>();
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
    	for (Marcia marcia:marce) {
            LocalDate dataInizio = marcia.getDataInizio().getLocalDate();
            if ( !(dataInizio.isBefore(start) || dataInizio.isAfter(end)) ) {
                out.add(marcia);
            }
        }
        return out;
    }

}
