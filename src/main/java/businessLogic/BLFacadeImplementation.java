package businessLogic;
//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


//import configuration.ConfigXML;
import dataAccess.DataAccessInterface;
import domain.Erabiltzailea;
import domain.Event;
import domain.Pertsona;
import domain.Question;
import exceptions.AdinTxikikoa;
import exceptions.ErabiltzaileaExistizenDa;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation  implements BLFacade {
	DataAccessInterface dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
	}

	public BLFacadeImplementation(DataAccessInterface da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		da.emptyDatabase();
		da.open();
		da.initializeDB();
		da.close();

		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		dbManager.open();
		Question qry=null;
		
	/*    System.out.println(event.getEventDate());
	    System.out.println( new Date().compareTo(event.getEventDate())>0);*/
	   
		
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
	    //System.out.println( "IF PASA SU");

		 qry=dbManager.createQuestion(event,question,betMinimum);		

		  //  System.out.println( "CREATE QUESTION PASA SU");

		 
		 
		dbManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date)  {
		dbManager.open();
		List<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		dbManager.open();
		List<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		//DataAccess dB4oManager=new DataAccess(false);

		//dB4oManager.close();
		dbManager.close();


	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
	 
	 @Override
		public Pertsona existitzenDa(String izena, String pasahitza) {
			dbManager.open();
			Pertsona e = dbManager.getErabiltzailea(izena);
			dbManager.close();
			if (e != null && e.getPasahitza().equals(pasahitza)) {
				return e;
			}
			return null;
		}

		@Override
		public Pertsona erregistratu(String izena, String pasahitza, Date jaiotzeData, String rola) throws AdinTxikikoa, ErabiltzaileaExistizenDa {
			dbManager.open();
			Pertsona e = dbManager.erregistratu(izena, pasahitza, jaiotzeData, rola);
			dbManager.close();
			return e;
		}
		
		@Override
		public Pertsona getPertsona(String izena) {
			dbManager.open();
			Pertsona p = dbManager.getErabiltzailea(izena);
			dbManager.close();
			return p;
		}
		
		@Override
		public Erabiltzailea getErabiltzailea(String izena) {
			dbManager.open();
			Erabiltzailea e = dbManager.getErabiltzaileaIzenarekin(izena);
			dbManager.close();
			return e;
		}

		@Override
		public List<Erabiltzailea> getErabiltzaileaGuztiak() {
			dbManager.open();
			List<Erabiltzailea> er = dbManager.getErabiltzaileaGuztiak();
			dbManager.close();
			return er;
		}

		@Override
		public List<Pertsona> getPertsonaGuztiak() {
			dbManager.open();
			List<Pertsona> er = dbManager.getPertsonaGuztiak();
			dbManager.close();
			return er;
		}


}

