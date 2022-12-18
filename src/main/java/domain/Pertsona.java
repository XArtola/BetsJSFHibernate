package domain;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import configuration.UtilDate;


@Entity
public abstract class Pertsona {
	
	@Id
	private String izena;
	private String pasahitza;
	private Date jaiotzeData;
	
	public Pertsona() {
		this.pasahitza = null;
		this.izena = null;
		this.jaiotzeData = null;
	}

	public Pertsona(String izena, String pasahitza, Date jaiotzeData) {
					
				
		this.izena = izena;
		this.pasahitza = pasahitza;
		this.jaiotzeData = jaiotzeData;
		
	}

	public String toString() {
		return "Izena: " + this.izena + " Adina: " + UtilDate.calculateAdina(this.jaiotzeData);
	}

	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}


	public Date getJaiotzeData() {
		return jaiotzeData;
	}

	public void setJaiotzeData(Date jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}

	public boolean pasahitzaZuzena(String pasahitza) {
		return (pasahitza.equals(this.pasahitza));
	}
	
	public int getAdina() {
		
		return UtilDate.calculateAdina(this.jaiotzeData);
		
	}
		
	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		else if(!(other instanceof Pertsona)) return false;
		else {
			Pertsona oEr = (Pertsona) other;
			return (this.getIzena().equals(oEr.getIzena()));
		}
	}

}