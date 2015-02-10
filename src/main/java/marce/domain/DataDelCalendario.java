/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marce.domain;

import java.text.DateFormat;
import java.util.Calendar;

/**
 *
 * @author michele
 */
public class DataDelCalendario implements Comparable {
    private Calendar cal;

    public DataDelCalendario() {
	cal = Calendar.getInstance();
    }

    public DataDelCalendario(Calendar calendar) {
	cal = calendar;
    }

    public Calendar getCal() {
	return cal;
    }

    public void setCal(Calendar cal) {
	this.cal = cal;
    }

    public DataDelCalendario(String data) throws ParsingException {
	String[] parts = data.split("-");
	if ( parts.length != 3 ) {
	    throw new ParsingException("Errore nel convertire la data inizio: " + data);
	}
	int year = Integer.parseInt(parts[0]);
	int month = Integer.parseInt(parts[1]);
	int day = Integer.parseInt(parts[2]);
	cal = Calendar.getInstance();
	cal.set(year, month, day, 0, 0);
    }

    public String exportString() {
	return cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public long getTimeInMillis() {
        return cal.getTimeInMillis();
    }

    @Override
    public String toString() {
	DateFormat dtf = DateFormat.getDateInstance();
	return dtf.format(this.cal.getTime());
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final DataDelCalendario other = (DataDelCalendario) obj;
	if (this.cal != other.cal && (this.cal == null || !this.cal.equals(other.cal))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 79 * hash + (this.cal != null ? this.cal.hashCode() : 0);
	return hash;

    }
    
    @Override
    public int compareTo(Object o) {
        if ( o == null ) return -1;
        Long thisOne = new Long(this.getTimeInMillis());
        Long otherOne = new Long( ((DataDelCalendario) o).getTimeInMillis() );
	return thisOne.compareTo(otherOne);
    }

}
