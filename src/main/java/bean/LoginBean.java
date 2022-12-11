package bean;

import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Erabiltzailea;
import domain.Pertsona;
import exceptions.AdinTxikikoa;
import exceptions.ErabiltzaileaExistizenDa;

public class LoginBean {
	BLFacade facadeBL;

	Erabiltzailea erabiltzailea = new Erabiltzailea();

	private String izena;
	private String pasahitza;
	private int adina;
	
	private int saldoa;

	Date jaiotzeData;

	//Pertsona pertsona;

	boolean loged = false;

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

	public String login() {
		return "login";
	}

	public String register() {
		return "register";
	}

	public String loginEgin() {

		FacesContext context = FacesContext.getCurrentInstance();

		this.erabiltzailea = (Erabiltzailea) facadeBL.existitzenDa(izena, pasahitza);

		if (this.erabiltzailea != null) {

			this.loged = true;

			return "aurrera";
		}

		else {

			// context.addMessage("loginMezua", new FacesMessage(FacesMessage.SEVERITY_WARN,
			// "Errorea saioa hastean: Erabiltzailea edo pasahitza ez da existizen",
			// "Erabiltzailea edo pasahitza ez da existizen"));
			context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_WARN, "Errorea",
					"Erabiltzailea edo pasahitza ez da zuzena"));

			return null;
		}
	}

	public String erregistratu() {

		FacesContext context = FacesContext.getCurrentInstance();
		/* RequestContext rContext = RequestContext.getCurrentInstance(); */

		try {

			this.erabiltzailea = (Erabiltzailea) facadeBL.erregistratu(izena, pasahitza, jaiotzeData);

		} catch (AdinTxikikoa e) {
			context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));

		} catch (ErabiltzaileaExistizenDa e) {
			context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));

		}

		if (this.erabiltzailea != null) {

			this.loged = true;

			return "aurrera";
		}
		
		else 
			return null;

	}

	public void onDateSelect(SelectEvent event) {

		// FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Data
		// aukeratua: " + event.getObject()));
		this.jaiotzeData = (Date) event.getObject();

	}

	public void filtroLanding() {

		if (this.loged == true) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("Main.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void kontrolaMain() {

		if (this.loged == false) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("LandingPage.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public String logOut() {

		this.loged = false;
		
		this.erabiltzailea =null;

		return "logOut";

	}

	private void garbitu() {

		this.izena = "";
		this.pasahitza = "";
		this.jaiotzeData = null;
		this.erabiltzailea = null;

	}
	/*
	 * public void validateIzena(FacesContext context, UIComponent comp, Object
	 * value) {
	 * 
	 * System.out.println("inside validate method");
	 * 
	 * String izena = (String) value;
	 * 
	 * if (izena.length() < 1) { // ((UIInput) comp).setValid(false);
	 * 
	 * 
	 * context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "Error", "Erabiltzaile izenak ezin du hutsa izan"));
	 * 
	 * }
	 * 
	 * }
	 * 
	 * public void validatePasahitza(FacesContext context, UIComponent comp, Object
	 * value) {
	 * 
	 * System.out.println("inside validate method");
	 * 
	 * String pass = (String) value;
	 * 
	 * if (pass.length() < 1) { // ((UIInput) comp).setValid(false);
	 * 
	 * context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "Error", "Psahitzak ezin du hutsa izan"));
	 * 
	 * }
	 * 
	 * }
	 */
	
	/////////////////////////////////PROBA CHART//////////////////////////////////////////////////////
	///    http://www.primefaces.org:8080/showcase/ui/chart/bar.xhtml?jfwid=37731
	/*private CartesianChartModel model;
	public ChartBean() {
	model = new CartesianChartModel();
	ChartSeries boys = new ChartSeries();
	boys.setLabel("Boys");
	boys.set("2004", 120);
	boys.set("2005", 100);
	ChartSeries girls = new ChartSeries();
	girls.setLabel("Girls");
	girls.set("2004", 52);
	girls.set("2005", 60);
	model.addSeries(boys);
	model.addSeries(girs);
	}
	public CartesianChartModel getModel() { return model; }*/
	
	

}
