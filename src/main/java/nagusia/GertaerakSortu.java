package nagusia;

import eredua.HibernateUtil;
import eredua.domeinua.BetsGertaera;
import org.hibernate.Session;
import java.util.*;

public class GertaerakSortu {
	public GertaerakSortu() {
	}

	private void createAndStoreBetsGertaera(String deskribapena, Date data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		BetsGertaera e = new BetsGertaera();
		e.setDeskribapena(deskribapena);
		e.setData(data);
		session.persist(e);
		session.getTransaction().commit();
	}

	private List gertaerakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from BetsGertaera").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		GertaerakSortu e = new GertaerakSortu();
		System.out.println("Gertaeren sorkuntza:");
		e.createAndStoreBetsGertaera("Anek ondo egin du logina", new Date());
		e.createAndStoreBetsGertaera("Nerea saiatu da login egiten", new Date());
		e.createAndStoreBetsGertaera("Kepak ondo egin du logina", new Date());	
		System.out.println("Gertaeren zerrenda:");
		List gertaerak = e.gertaerakZerrendatu();
		for (int i = 0; i < gertaerak.size(); i++) {
			BetsGertaera ev = (BetsGertaera) gertaerak.get(i);
			System.out
					.println("Id: " + ev.getId() + " Deskribapena: " + ev.getDeskribapena() + " Data: " + ev.getData());
		}
	}
}