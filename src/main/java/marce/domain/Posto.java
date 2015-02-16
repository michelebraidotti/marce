package marce.domain;

/**
 *
 * @author michele
 */
public class Posto implements Comparable {
    private String localita;
    private String zona;
    public static String SEPARATOR = ", ";

    public Posto() {
    }

    public Posto(String definizione) throws ParsingException {
        String[] localitaAndZona = definizione.split(SEPARATOR);
        if (localitaAndZona.length != 2)  {
            throw new ParsingException("Impossibile dedurre "
            + "localita e zona dalla sguente definizione: " + definizione);

        }
        this.localita = localitaAndZona[0];
        this.zona = localitaAndZona[1];
    }

    public Posto(String localita, String zona) {
        this.localita = localita;
        this.zona = zona;
    }

    public String getLocalita() {
	return localita;
    }

    public void setLocalita(String localita) {
	this.localita = localita;
    }

    public String getZona() {
	return zona;
    }

    public void setZona(String zona) {
	this.zona = zona;
    }

    @Override
    public String toString() {
	return localita + SEPARATOR + zona;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Posto)) {
            return false;
        }
        Posto castOther = (Posto) other;
        return this.localita.equals(castOther.localita)
            && this.zona.equals(castOther.zona);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.localita != null ? this.localita.hashCode() : 0);
        hash = 43 * hash + (this.zona != null ? this.zona.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if ( o == null ) return -1;
        return this.toString().compareTo(o.toString());
    }



}
