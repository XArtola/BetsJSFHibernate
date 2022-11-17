package bean;

import java.util.Date;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Event;

public class BetsBean {

	Date data;
	
	BLFacade facadeBL = FacadeBean.getBusinessLogic();

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BetsBean() {
		

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

		Vector<Event> gertaerak = facadeBL.getEvents(data);
		
		for( int i= 0; i < gertaerak.size(); i++) {
			
			
			System.out.println(gertaerak.get(i).toString());
			
		}

	}

}
