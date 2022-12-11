package dataAccess;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import dataAccess.HibernateUtil;
import domain.Erabiltzailea;
import domain.Event;
import domain.Pertsona;
import domain.Question;
import exceptions.AdinTxikikoa;
import exceptions.ErabiltzaileaExistizenDa;
import exceptions.QuestionAlreadyExist;
import nagusia.GalderakSortu;
import nagusia.GertaerakSortu;

public class HibernateDataAccess implements DataAccessInterface {

	protected Session session;

	public HibernateDataAccess() {
		open();
	}

	@Override
	public void open() {
		System.out.println("Opening DataBase");
		if (HibernateUtil.getSessionFactory().getCurrentSession().isOpen())
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		else
			session = HibernateUtil.getSessionFactory().openSession();
	}

	@Override
	public void close() {

		HibernateUtil.getSessionFactory().close();
		session = null;

	}

	@Override
	public void emptyDatabase() {

		session.beginTransaction();
		session.createQuery("delete from Event");
		session.createQuery("delete from Question");
		session.getTransaction().commit();

	}

	@Override
	public void initializeDB() {

		session.beginTransaction();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month, 28));
			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 28));
			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
			q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
			q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
			q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
			q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
			q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			/*
			 * session.persist(q1); session.persist(q2); session.persist(q3);
			 * session.persist(q4); session.persist(q5); session.persist(q6);
			 */

			session.persist(ev1);
			session.persist(ev2);
			session.persist(ev3);
			session.persist(ev4);
			session.persist(ev5);
			session.persist(ev6);
			session.persist(ev7);
			session.persist(ev8);
			session.persist(ev9);
			session.persist(ev10);
			session.persist(ev11);
			session.persist(ev12);
			session.persist(ev13);
			session.persist(ev14);
			session.persist(ev15);
			session.persist(ev16);
			session.persist(ev17);
			session.persist(ev18);
			session.persist(ev19);
			session.persist(ev20);

			Erabiltzailea er1 = new Erabiltzailea("user", "pass", new Date());
			session.persist(er1);

			session.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {

		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		session.beginTransaction();

		Event ev = (Event) session.get(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) {
			session.getTransaction().rollback();
			// throw new
			// QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			throw new QuestionAlreadyExist("Errorea: Galdera hori existitzen da");
		}

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

		session.beginTransaction();
		System.out.println(">> DataAccess: getEvents");
		// List<Event> res = new List<Event>();
		Query q = session.createQuery("from Event where eventDate= :date");
		q.setParameter("date", date);
		List<Event> events = q.list();

		for (Event ev : events) {
			System.out.println(ev.toString());
			// res.add(ev);
		}
		session.getTransaction().commit();
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

	@Override
	public List<Date> getEventsMonth(Date date) {

		session.beginTransaction();
		System.out.println(">> DataAccess: getEventsMonth");
		// Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		/*
		 * TypedQuery<Date> query = db.
		 * createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2"
		 * ,Date.class); query.setParameter(1, firstDayMonthDate); query.setParameter(2,
		 * lastDayMonthDate);
		 */

		Query q = session
				.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN :1 and :2");
		q.setParameter("1", firstDayMonthDate);
		q.setParameter("2", lastDayMonthDate);

		List<Date> dates = q.list();

		// List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			// res.add(d);
		}

		session.getTransaction().commit();

		return dates;

	}

	@Override
	public boolean existQuestion(Event event, String question) {
		session.beginTransaction().commit();
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		// Event ev = db.find(Event.class, event.getEventNumber());
		Event ev = (Event) session.get(Event.class, event.getEventNumber());
		session.getTransaction().commit();
		return ev.DoesQuestionExists(question);

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// CUARENTENA
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean existitzenDa(String izena, String pasahitza) {
		session.beginTransaction();
		Pertsona e = (Pertsona) session.get(Pertsona.class, izena);
		session.getTransaction().commit();
		if (e == null)
			return false;
		else {
			return e.pasahitzaZuzena(pasahitza);
		}
	}

	public Pertsona getErabiltzailea(String izena) {
		session.beginTransaction();
		Pertsona pertsona = (Pertsona) session.get(Erabiltzailea.class, izena);
		session.getTransaction().commit();
		return pertsona;
	}

	public Erabiltzailea getErabiltzaileaIzenarekin(String izena) {
		session.beginTransaction();
		Erabiltzailea erabiltzailea = (Erabiltzailea) session.get(Erabiltzailea.class, izena);
		session.getTransaction().commit();
		return erabiltzailea;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	// CUARENTENA
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean adinaDu(Date jaiotzeData) {

		//System.out.println(adina);

		return (UtilDate.calculateAdina(jaiotzeData) >= 18);
	}

	public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData)
			throws AdinTxikikoa, ErabiltzaileaExistizenDa {
		// Aztertu ea aurretik existitzen den erabiltzailea izen horrekin
		Pertsona e = this.getErabiltzailea(izena);
		if (e == null) {
			// Erabiltzailerik ez da existitzen
			// Aztertu ea adina >= 18 den
			boolean adinaNahikoa = this.adinaDu(jaiotzeData);
			if (adinaNahikoa) {

				Pertsona er = this.sortuErabiltzailea(izena, pasahitza, jaiotzeData);
				return er;

			} else {

				throw new AdinTxikikoa("Erabiltzaileak ezin du 18 urte baino gutxiago izan");

			}
		} else {
			throw new ErabiltzaileaExistizenDa("Erabiltzaile izena ez dago erabilgarri");
		}
	}

	public Pertsona sortuErabiltzailea(String izena, String pasahitza, Date jaiotzeData) {
		this.open(); // wtf
		session.beginTransaction();
		// TODO: Soilik Erabiltzaileak sortu daitezke.
		Pertsona er = new Erabiltzailea(izena, pasahitza, jaiotzeData);
		session.persist(er);
		session.getTransaction().commit();
		return er;
	}

	public List<Erabiltzailea> getErabiltzaileaGuztiak() {

		session.beginTransaction();
		Query q = session.createQuery("from Erbitlzailea");
		List<Erabiltzailea> erabiltzaileak = q.list();
		session.getTransaction().commit();

		return erabiltzaileak;
	}

	public List<Pertsona> getPertsonaGuztiak() {
		session.beginTransaction();
		Query q = session.createQuery("from Pertsona");
		List<Pertsona> pertsonak = q.list();
		session.getTransaction().commit();

		return pertsonak;
	}

}
