package nagusia;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;


import eredua.HibernateUtil;
import eredua.domeinua.Event;
import eredua.domeinua.Question;

public class GalderakSortu {

	public GalderakSortu() {
	}

	private void createAndStoreQuestion(String question, float betMinimun, String result) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Question g = new Question();
		g.setQuestion(question);
		g.setBetMinimum(betMinimun);
		g.setResult(result);
		session.persist(g);
		session.getTransaction().commit();
	}

	private void createAndStoreQuestion(String question, float betMinimun) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Question g = new Question();
		g.setQuestion(question);
		g.setBetMinimum(betMinimun);
		session.persist(g);
		session.getTransaction().commit();
	}
	
	

	
	
	

	public Question createAndStoreQuestion(long eventId, String question, float betMinimun) {

		Question q = new Question();
		q.setQuestion(question);
		q.setBetMinimum(betMinimun);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		
		

		Query qr = session.createQuery("from Event where id= :eventId");
		qr.setParameter("eventId", eventId);
		List<Event> events = qr.list();
		Event ev = events.get(0);
		
		q.setEvent(ev);
		
		session.persist(q);
		session.getTransaction().commit();

		
		return q;

	}

	private List galderakZerrendatu() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Question").list();
		session.getTransaction().commit();
		return result;
	}

	public static void main(String[] args) {
		GalderakSortu g = new GalderakSortu();
		//GertaerakSortu e = new GertaerakSortu();
		System.out.println("Galderen sorkuntza:");
		/*g.createAndStoreQuestion("Zeinek irabaziko du?", 5, null);
		g.createAndStoreQuestion("Zein izango da emaitza?", 6, null);
		g.createAndStoreQuestion("Zenbat gol sartuko dituzte", 7, null);*/
		
		g.createAndStoreQuestion(1, "Zeinek irabaziko du partidua?",1);
		g.createAndStoreQuestion(1,"Zeinek sartuko du lehenengo gola?",2);
		g.createAndStoreQuestion(11,"Zeinek irabaziko du partidua?",1);
		g.createAndStoreQuestion(11,"Zenbat gol sartuko dira?",2);
		g.createAndStoreQuestion(17,"Zeinek irabaziko du partidua?",1);
		g.createAndStoreQuestion(17,"Golak sartuko dira lehenengo zatian?",2);
		
		System.out.println("Galderen zerrenda:");
		List galderak = g.galderakZerrendatu();
		for (int i = 0; i < galderak.size(); i++) {
			Question  ga = (Question) galderak.get(i);
			System.out.println("Id: " + ga.getId() + " Galdera: " + ga.getQuestion() + " Gutxieneko apustua:" + ga.getBetMinimum() + " Emaitza " + ga.getResult());
		}
	}

}
