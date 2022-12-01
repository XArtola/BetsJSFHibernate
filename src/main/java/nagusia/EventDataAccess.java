package nagusia;

import java.util.Date;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;


import eredua.HibernateUtil;
import eredua.domeinua.Event;
import eredua.domeinua.Question;

public class EventDataAccess {

	public Question createQuestion(Event event, String question, float betMinimum) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		

		Question q = new Question();
		q.setQuestion(question);
		q.setBetMinimum(betMinimum);
		// g.setResult(result);
		session.persist(q);
		session.getTransaction().commit();
		
		return q;

	}

	public Vector<Event> getEvents(Date date) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("from Event where data= :date");
		q.setParameter("date", date);
		Vector<Event> events = (Vector<Event>) q.list();

		session.getTransaction().commit();
		return events;

	}
	/*
	 * public Vector<Date> getEventsMonth(Date date) {
	 * 
	 * 
	 * 
	 * }
	 */
	
	public static void main(String[] args) {
		
		
		
		
	}

}
