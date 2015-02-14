/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marce.domain;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 *
 * @author michele
 */
public class DataDelCalendario implements Comparable {
    private LocalDate localDate;

    public DataDelCalendario() {
	    localDate = LocalDate.now();
    }

    public DataDelCalendario(LocalDate localDate) {
	    this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public DataDelCalendario(String data) throws ParsingException {
        String[] parts = data.split("-");
        if ( parts.length != 3 ) {
            throw new ParsingException("Errore nel convertire la data inizio: " + data);
        }
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        localDate = LocalDate.of(year, month, day);
    }

    public String exportString() {
	    return localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
    }

    @Override
    public String toString() {
        return localDate.getYear() + " " + localDate.getMonth() + " " + localDate.getDayOfMonth();
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
        if (this.localDate != other.localDate && (this.localDate == null || !this.localDate.equals(other.localDate))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.localDate != null ? this.localDate.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if ( o == null ) return -1;
        return (this.getLocalDate()).compareTo(((DataDelCalendario) o).getLocalDate());
    }

}
