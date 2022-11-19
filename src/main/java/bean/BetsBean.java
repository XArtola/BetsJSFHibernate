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

public class BetsBean {

	Date data;

	BLFacade facadeBL;

	Vector<Event> gertaerak;

	Event gertaera;

	Vector<Question> galderak;

	Question galdera;

	int minBet;

	public BetsBean() {

		facadeBL = FacadeBean.getBusinessLogic();

	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Vector<Event> getGertaerak() {
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

	public Vector<Question> getGalderak() {
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

	public void gordeGaldera() {

	}

}
