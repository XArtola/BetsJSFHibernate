package domain;

import java.util.ArrayList;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


@Entity
public class Erabiltzailea extends Pertsona {

	private double saldoa;

	public Erabiltzailea() {
		super();
		this.saldoa = 0;
	}

	public Erabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		super(izena, pasahitza, jaiotzeData);
			this.saldoa = 0;
		}


	public double getSaldoa() {
		return saldoa;
	}

	public void setSaldoa(double saldoa) {
		this.saldoa = saldoa;
	}

	public void saldoaAldatu(Double diruKop) {
		this.setSaldoa(this.getSaldoa() + diruKop);
	}

	public void saldoaAldatu(double irabaziDirua) {

		double dirua = irabaziDirua + this.getSaldoa();
		this.setSaldoa(dirua);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		else if (!(other instanceof Erabiltzailea))
			return false;
		else {
			Erabiltzailea oEr = (Erabiltzailea) other;
			return (this.getIzena().equals(oEr.getIzena()));
		}
	}

}