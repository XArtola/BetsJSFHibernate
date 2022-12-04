package nagusia;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import dataAccess.HibernateUtil;
import domain.Erabiltzailea;
import domain.Pertsona;

public class PertsonakSortu {

	public PertsonakSortu() {
	}
	
	private long id;
	private String izena;
	private String pasahitza;
	private int adina;


	private void createAndStoreBetsPertsona(String izena, String pasahitza, int adina) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Pertsona p = new Erabiltzailea();
		p.setIzena(izena);
		p.setPasahitza(pasahitza);
		p.setAdina(adina);
		session.persist(p);
		session.getTransaction().commit();
	}

	private List pertsonakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from BetsPertsona").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		PertsonakSortu ps = new PertsonakSortu();
		System.out.println("Pertsonen sorkuntza:");
		ps.createAndStoreBetsPertsona("user", "pass", 18);
		ps.createAndStoreBetsPertsona("user2", "pass",24);
		ps.createAndStoreBetsPertsona("user3", "pass", 30);
		System.out.println("Pertsonen zerrenda:");
		List pertsonak = ps.pertsonakZerrendatu();
		for (int i = 0; i < pertsonak.size(); i++) {
			Pertsona p = (Pertsona) pertsonak.get(i);
			System.out.println(" Izena: " + p.getIzena() + " Pasahitza: " + p.getPasahitza() + " Adina: " + p.getAdina());
		}
	}

}
