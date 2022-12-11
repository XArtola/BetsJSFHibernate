package bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class BetsBean {

	Date data = null;

	BLFacade facadeBL;

	List<Event> gertaerak = new ArrayList<Event>();

	Event gertaera = null;

	List<Question> galderak = new ArrayList<Question>();

	Question galdera = null;;

	int minBet = 1;

	String question = "";

	public BetsBean() {

		facadeBL = FacadeBean.getBusinessLogic();

	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<Event> getGertaerak() {
		return gertaerak;
	}

	public void setGertaerak(Vector<Event> gertaerak) {
		this.gertaerak = gertaerak;
	}

	public Event getGertaera() {
		return gertaera;
	}

	public void setGertaera(Event gertaera) {
		this.gertaera = gertaera;
	}

	public List<Question> getGalderak() {
		return galderak;
	}

	public void setGalderak(Vector<Question> galderak) {
		this.galderak = galderak;
	}

	public Question getGaldera() {
		return galdera;
	}

	public void setGaldera(Question galdera) {
		this.galdera = galdera;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getMinBet() {
		return minBet;
	}

	public void setMinBet(int minBet) {
		this.minBet = minBet;
	}

	public String create() {

		garbitu();

		return "create";
	}

	public String find() {

		garbitu();

		return "find";
	}

	/*
	 * public String atzera() { return "atzera"; }
	 */

	public void onDateSelect(SelectEvent event) {

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
		gertaerak = facadeBL.getEvents((Date) event.getObject());
	}

	public void onEventSelect(SelectEvent event) {

		Event eventua = (Event) event.getObject();
		galderak = eventua.getQuestions();

	}

	/*
	 * public void onEventSelectLista(SelectEvent event) {
	 * 
	 * System.out.println("fahbdsklfbskljsbdfklbbdfshklbdf");
	 * 
	 * 
	 * 
	 * gertaera = (Event) event.getObject();
	 * 
	 * System.out.println(gertaera.toString());
	 * 
	 * }
	 */

	public void gordeGaldera() {

		if (gertaera == null) {
			// FacesContext.getCurrentInstance().addMessage(null,
			// new FacesMessage("Errorea: Gertaera bat aukeratu behar da"));

			// FacesContext context = FacesContext.getCurrentInstance();
			// context.addMessage("somekey1", new FacesMessage(FacesMessage.SEVERITY_ERROR,
			// "Connection failed.", "Erorrea"));
			FacesContext.getCurrentInstance().addMessage("createMezuak",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea", "Gertaera bat aukeratu behar da"));
		}

		else {
			// System.out.println(gertaera.toString()+" "+question+ " "+ minBet );

			try {
				facadeBL.createQuestion(gertaera, this.question, this.minBet);
			} catch (EventFinished e) {
				FacesContext.getCurrentInstance().addMessage("createMezuak",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea", "Data hori pasata dago"));
			} catch (QuestionAlreadyExist e) {
				FacesContext.getCurrentInstance().addMessage("createMezuak",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errorea", "Galdera hori existizen da"));
			}

			FacesContext.getCurrentInstance().addMessage("createMezuak",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Informazioa", "Galdera modu zuzenean sortu da"));

		}

	}

	private void garbitu() {

		this.data = null;

		this.gertaerak = new ArrayList<Event>();

		this.gertaera = null;

		this.galderak = new ArrayList<Question>();

		this.galdera = null;
		;

		this.minBet = 1;

		this.question = "";

	}

}
