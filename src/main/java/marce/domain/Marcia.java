package marce.domain;

import java.math.BigDecimal;

public class Marcia {
//   public static Object[] HEADER = {"Progressivo", "Data", "Giorni", "Edizione", "Denominazione",
//	"Localita'", "Zona", "Km", "Tempo"};
    private Integer id = 0;
    private DataDelCalendario dataInizio = new DataDelCalendario();
    private DataDelCalendario dataFine = new DataDelCalendario();
    private String nomeEvento = "";
    private Integer edizione = 0;
    private Posto posto = new Posto();
    private BigDecimal km = new BigDecimal("0.000");
    private Tempo tempo = new Tempo(0);

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public DataDelCalendario getDataFine() {
	return dataFine;
    }

    public void setDataFine(DataDelCalendario dataFine) {
	this.dataFine = dataFine;
    }

    public DataDelCalendario getDataInizio() {
	return dataInizio;
    }

    public void setDataInizio(DataDelCalendario dataInizio) {
	this.dataInizio = dataInizio;
    }

    public Integer getEdizione() {
	return edizione;
    }

    public void setEdizione(Integer edizione) {
	    this.edizione = edizione;
    }

    public Posto getPosto() {
	return posto;
    }

    public void setPosto(Posto posto) {
	this.posto = posto;
    }

    public BigDecimal getKm() {
	return km;
    }

    public void setKm(BigDecimal km) {
	this.km = km;
    }

    public String getNomeEvento() {
	return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
	this.nomeEvento = nomeEvento;
    }

    public Tempo getTempo() {
	return tempo;
    }

    public void setTempo(Tempo tempo) {
	this.tempo = tempo;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Marcia other = (Marcia) obj;
	if (! this.id.equals(other.id) ) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 97 * hash + this.id;
	return hash;
    }
    
}
