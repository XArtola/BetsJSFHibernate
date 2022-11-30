package nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.Event;

import org.hibernate.Session;

import configuration.UtilDate;

import java.util.*;

public class GertaerakSortu {
	public GertaerakSortu() {
	}

	private void createAndStoreEvent(String deskribapena, Date data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = new Event();
		e.setDeskribapena(deskribapena);
		e.setData(data);
		session.persist(e);
		session.getTransaction().commit();
	}

	private List gertaerakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		GertaerakSortu e = new GertaerakSortu();
		System.out.println("Gertaeren sorkuntza:");
	/*	e.createAndStoreEvent("Anek ondo egin du logina", new Date());
		e.createAndStoreEvent("Nerea saiatu da login egiten", new Date());
		e.createAndStoreEvent("Kepak ondo egin du logina", new Date());	 */
		
		
		  Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
		   e.createAndStoreEvent("Atlético-Athletic", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Eibar-Barcelona", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Getafe-Celta", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Alavés-Deportivo", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Español-Villareal", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Malaga-Valencia", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Girona-Leganés", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Real Sociedad-Levante", UtilDate.newDate(year,month,17));
		   e.createAndStoreEvent("Betis-Real Madrid", UtilDate.newDate(year,month,17));
		   
		   e.createAndStoreEvent("Atletico-Athletic", UtilDate.newDate(year,month,1));
		   e.createAndStoreEvent("Eibar-Barcelona", UtilDate.newDate(year,month,1));
		   e.createAndStoreEvent("Getafe-Celta", UtilDate.newDate(year,month,1));
		   e.createAndStoreEvent("Alavés-Deportivo", UtilDate.newDate(year,month,1));
		   e.createAndStoreEvent("Español-Villareal", UtilDate.newDate(year,month,1));
		   e.createAndStoreEvent("Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
		   
		   e.createAndStoreEvent("Málaga-Valencia", UtilDate.newDate(year,month,28));
		   e.createAndStoreEvent("Girona-Leganés", UtilDate.newDate(year,month,28));
		   e.createAndStoreEvent("Real Sociedad-Levante", UtilDate.newDate(year,month,28));
		   e.createAndStoreEvent("Betis-Real Madrid", UtilDate.newDate(year,month,28));

	
		
		System.out.println("Gertaeren zerrenda:");
		List gertaerak = e.gertaerakZerrendatu();
		for (int i = 0; i < gertaerak.size(); i++) {
			Event ev = (Event) gertaerak.get(i);
			System.out.println("Id: " + ev.getId() + " Deskribapena: " + ev.getDeskribapena() + " Data: " + ev.getData());
		}
	}
}