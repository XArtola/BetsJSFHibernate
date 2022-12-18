package nagusia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


	private void createAndStoreBetsPertsona(String izena, String pasahitza, Date jaiotzeData) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Pertsona p = new Erabiltzailea();
		p.setIzena(izena);
		p.setPasahitza(pasahitza);
		p.setJaiotzeData(jaiotzeData);
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
		
		String date_string = "26-09-1989";
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
	    Date date = null;;
		try {
			date = formatter.parse(date_string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		PertsonakSortu ps = new PertsonakSortu();
		System.out.println("Pertsonen sorkuntza:");
		ps.createAndStoreBetsPertsona("user", "pass", date);
		ps.createAndStoreBetsPertsona("user2", "pass",date);
		ps.createAndStoreBetsPertsona("user3", "pass", date);
		System.out.println("Pertsonen zerrenda:");
		List pertsonak = ps.pertsonakZerrendatu();
		for (int i = 0; i < pertsonak.size(); i++) {
			Pertsona p = (Pertsona) pertsonak.get(i);
			System.out.println(" Izena: " + p.getIzena() + " Pasahitza: " + p.getPasahitza() + " Adina: " + p.getAdina());
		}
	}

}
