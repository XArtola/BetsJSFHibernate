package bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Pertsona;

public class LoginBean {
	BLFacade facadeBL;
	
	Erabiltzailea erabiltzailea = new Erabiltzailea();
	
	private String izena;
	private String pasahitza;
	private int adina;
	
	Date jaiotzeData;
	

	public LoginBean() {

		facadeBL = FacadeBean.getBusinessLogic();
	}
	
	public Erabiltzailea getErabiltzailea() {
		return erabiltzailea;
	}
	public void setErabiltzailea(Erabiltzailea erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	public int getAdina() {
		return adina;
	}
	public void setAdina(int adina) {
		this.adina = adina;
	}
	
	public Date getJaiotzeData() {
		return jaiotzeData;
	}

	public void setJaiotzeData(Date jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}

	
	public String login()
	{
		return "login";
	}
	
	public String register()
	{
		return "register";
	}
	
	
	public String loginEgin() {
		
		Pertsona p = facadeBL.existitzenDa(izena, pasahitza);
	
		if(p != null) {
			
			return "aurrera";
		}
		return null;
		
		
	}
	
	public String erregistratu() {
		
		
		Pertsona p = facadeBL.erregistratu(izena, pasahitza, jaiotzeData);
		
		if(p!= null)
		
			return "atzera";
		
		return null;
		
	}
	
	public void onDateSelect(SelectEvent event) {

	//	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data aukeratua: " + event.getObject()));
		this.jaiotzeData = (Date) event.getObject();

	}
	
	

}
