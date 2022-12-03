package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import eredua.HibernateUtil;
import eredua.domeinua.Event;
import eredua.domeinua.Question;
import exceptions.QuestionAlreadyExist;
import nagusia.GalderakSortu;
import nagusia.GertaerakSortu;

public class HibernateDataAccess implements DataAccessInterface {

	private Session session;

	@Override
	public void open() {

		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}

	@Override
	public void close() {

		HibernateUtil.getSessionFactory().close();
		session.close();

	}

	@Override
	public void emptyDatabase() {

		session.beginTransaction();
		// delete cascade dagoenez ez da beharrezkoa galderak borratzeko query bat
		// egitea
		session.createQuery("delete from Event");

	}

	@Override
	public void initializeDB() {

		GalderakSortu g = new GalderakSortu();
		System.out.println("Galderen sorkuntza:");

		g.createAndStoreQuestion(1, "Zeinek irabaziko du partidua?", 1);
		g.createAndStoreQuestion(1, "Zeinek sartuko du lehenengo gola?", 2);
		g.createAndStoreQuestion(11, "Zeinek irabaziko du partidua?", 1);
		g.createAndStoreQuestion(11, "Zenbat gol sartuko dira?", 2);
		g.createAndStoreQuestion(17, "Zeinek irabaziko du partidua?", 1);
		g.createAndStoreQuestion(17, "Golak sartuko dira lehenengo zatian?", 2);

		GertaerakSortu e = new GertaerakSortu();
		System.out.println("Gertaeren sorkuntza:");

		Calendar today = Calendar.getInstance();

		int month = today.get(Calendar.MONTH);
		month += 1;
		int year = today.get(Calendar.YEAR);
		if (month == 12) {
			month = 0;
			year += 1;
		}
		e.createAndStoreEvent("Atlético-Athletic", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Getafe-Celta", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Español-Villareal", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Malaga-Valencia", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Girona-Leganés", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
		e.createAndStoreEvent("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

		e.createAndStoreEvent("Atletico-Athletic", UtilDate.newDate(year, month, 1));
		e.createAndStoreEvent("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
		e.createAndStoreEvent("Getafe-Celta", UtilDate.newDate(year, month, 1));
		e.createAndStoreEvent("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
		e.createAndStoreEvent("Español-Villareal", UtilDate.newDate(year, month, 1));
		e.createAndStoreEvent("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

		e.createAndStoreEvent("Málaga-Valencia", UtilDate.newDate(year, month, 28));
		e.createAndStoreEvent("Girona-Leganés", UtilDate.newDate(year, month, 28));
		e.createAndStoreEvent("Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
		e.createAndStoreEvent("Betis-Real Madrid", UtilDate.newDate(year, month, 28));

	}

	@Override
	public List<Date> getEventsMonth(Date date) {
		return null;
	}

	@Override
	public boolean existQuestion(Event event, String question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {

		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = (Event) session.get(Event.class, event.getEventDate());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		session.beginTransaction();

		Question q = ev.addQuestion(question, betMinimum);
		session.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
								// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		// db.getTransaction().commit();
		session.getTransaction().commit();
		return q;

	}

	@Override
	public List<Event> getEvents(Date date) {

		System.out.println(">> DataAccess: getEvents");
		//List<Event> res = new List<Event>();
		Query q = session.createQuery("from Event where data= :date");
		q.setParameter("date", date);

		List<Event> events = q.list();
		for (Event ev : events) {
			System.out.println(ev.toString());
			//res.add(ev);
		}
		return events;

		/*
		 * Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		 * session.beginTransaction();
		 * 
		 * Query q = session.createQuery("from Event where data= :date");
		 * q.setParameter("date", date);
		 * 
		 * List<Event> events = q.list();
		 * 
		 * session.getTransaction().commit(); return events;
		 * 
		 */

	}

}
