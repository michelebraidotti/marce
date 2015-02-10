package marce.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import marce.NotFoudException;
import marce.domain.Marcia;
import marce.domain.ParsingException;
import marce.domain.Posto;
import marce.domain.Tempo;

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
    
    public Marcia getNew() {
        Marcia marcia = new Marcia();
        marcia.setId(getNextId());
        return marcia;
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
        if ( m.getId() == 0 )
            throw new InvalidIdException("Una nuova marcia non puo'avere id zero");
        else 
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
    
    public String[] getDenominazioniListAsArray() {
        Set<String> denominazioni = getDenominazioniList();
        String[] denominazioniArr = new String[denominazioni.size()];
        int i = 0;
        for (String d:denominazioni) {
            denominazioniArr[i++] = d;
        }
        return denominazioniArr;
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
    
    public Posto[] getPostiListAsArray() {
        Set<Posto> posti = getPostiList();
        Posto[] postiArr = new Posto[posti.size()];
        int i = 0;
        for (Posto p:posti) {
            postiArr[i++] = p;
        }
        return postiArr;
    }

    public BigDecimal totaleKm() {
	BigDecimal totale = new BigDecimal("0.00");
	for (Marcia marcia:marce) {
	    totale = totale.add(marcia.getKm());
	}
	return totale;
    }

    public Tempo totaleTempo() throws ParsingException {
	Tempo totale = new Tempo(0);
	for (Marcia marcia:marce) {
	    totale = totale.add(marcia.getTempo());
	}
	return totale;
    }

    public List<Marcia> getPerAnno(int year) {
        List<Marcia> out = new ArrayList<>();
	Calendar start = Calendar.getInstance();
	start.set(year, 1, 1);
	Calendar end = Calendar.getInstance();
	end.set(year, 12, 31);
    	for (Marcia marcia:marce) {
	    Calendar dataInizio = marcia.getDataInizio().getCal();
	    if ( !(dataInizio.before(start) || dataInizio.after(end)) ) {
		out.add(marcia);
	    }
	}
        return out;
    }

}
