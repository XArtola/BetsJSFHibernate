package nagusia;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import eredua.HibernateUtil;
import eredua.domeinua.BetsGaldera;
import eredua.domeinua.BetsGertaera;

public class GalderakSortu {
	
	
	public GalderakSortu() {
	}

	
	
	
	private void createAndStoreBetsGaldera (String question, float betMinimun, String result) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		BetsGaldera g = new BetsGaldera();
		g.setQuestion(question);
		g.setBetMinimum(betMinimun);
		g.setResult(result);
		session.persist(g);
		session.getTransaction().commit();
	}

	private List gertaerakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from BetsGaldera").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		GalderakSortu g = new GalderakSortu();
		//GertaerakSortu e = new GertaerakSortu();
		System.out.println("Galderen sorkuntza:");
		g.createAndStoreBetsGaldera("Zeinek irabaziko du?", 5, null);
		g.createAndStoreBetsGaldera("Zein izango da emaitza?", 6, null);
		g.createAndStoreBetsGaldera("Zenbat gol sartuko dituzte", 7, null);
		System.out.println("Galderen zerrenda:");
		List galderak = g.gertaerakZerrendatu();
		for (int i = 0; i < galderak.size(); i++) {
			BetsGaldera  ga = (BetsGaldera) galderak.get(i);
			System.out.println("Id: " + ga.getId() + " Galdera: " + ga.getQuestion() + " Gutxieneko apustua:" + ga.getBetMinimum() + " Emaitza " + ga.getResult());
		}
	}
	
	
	
	

}
