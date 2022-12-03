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
import eredua.domeinua.Event;
import eredua.domeinua.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class BetsBean {

	Date data;

	BLFacade facadeBL;

	List<Event> gertaerak;

	Event gertaera;

	List<Question> galderak;

	Question galdera;

	int minBet;

	String question;

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

		return "create";
	}

	public String find() {
		return "find";
	}

	public String atzera() {
		return "atzera";
	}

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

		 System.out.println(gertaera.toString()+" "+question+ " "+ minBet );

		if (gertaera == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Errorea: Gertaera bat aukeratu behar da"));
		}

		else {

			try {
				facadeBL.createQuestion(gertaera, this.question, this.minBet);
			} catch (EventFinished e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: Data hori pasata dago"));
			} catch (QuestionAlreadyExist e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Errorea: DGaldera hori existizen da"));
			}

		}

	}

}
