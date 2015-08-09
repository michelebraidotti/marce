package marce.domain;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author michele
 */
public class Tempo {
    private Integer value;
    public final static String SEPARATOR = ":";

    public Tempo(Integer t) {
	value = t;
    }

    public Integer getValue() {
	return value;
    }

    public void setValue(Integer value) {
	this.value = value;
    }

    public Tempo(String tempo) throws ParsingException {
        if ( tempo == null || tempo.equals("") ) {
            this.value = 0;
        }
        else {
            String[] parts = tempo.split(SEPARATOR);
            if ( parts.length != 3 ) {
                throw new ParsingException("Errore nel convertire il tempo " + tempo);
            }
            int hours = Integer.parseInt(parts[0]);
            int mins = Integer.parseInt(parts[1]);
            int secs = Integer.parseInt(parts[2]);
            this.value = hours*3600 + mins*60 + secs;
        }
    }

    public Integer getOre() {
        return this.value/3600;
    }

    public Integer getMinuti() {
        return (this.value%3600)/60;
    }

    public Integer getSecondi() {
        return (this.value%3600)%60;
    }

    private String[] splitTempo() {
        return StringUtils.split(this.toString(), ":");
    }

    @Override
    public String toString() {
        int hours = getOre();
        int min = getMinuti();
        int sec = getSecondi();
        String hourStr = hours + "";
        if ( hours < 10 ) hourStr = "0" + hourStr;
        String minStr = min + "";
        if ( min < 10 ) minStr = "0" + minStr;
        String secStr = sec + "";
        if ( sec < 10 ) secStr = "0" + secStr;
        return hourStr + SEPARATOR + minStr + SEPARATOR + secStr;
    }

    public String toStringWithDays() {
        String toStr = this.toString();
        String[] splt = toStr.split(SEPARATOR);
        int hours = Integer.parseInt(splt[0]);
        if ( hours > 24 ) {
            int days = hours/24;
            int remainHours = hours%24;
            return days + "gg " + remainHours + SEPARATOR + splt[1] + SEPARATOR + splt[2];
        }
        else {
            return this.toString();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tempo other = (Tempo) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
        return hash;
    }

    public Tempo add(Tempo t) {
        this.value += t.value;
        return new Tempo(this.value);
    }

}
