package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.component.chart.bar.BarChart;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Erabiltzailea;
import domain.Pertsona;
import domain.Rola;
import exceptions.AdinTxikikoa;
import exceptions.ErabiltzaileaExistizenDa;

public class LoginBean {
	
	BLFacade facadeBL;

	//Erabiltzailea erabiltzailea = new Erabiltzailea();

	private String izena;
	private String pasahitza;
	private int adina;
	
	Date jaiotzeData;

	Pertsona pertsona;

	boolean loged = false;
	
	List<Rola> rolak = new ArrayList<Rola>();;
	
	Rola rola;

	public LoginBean() {

		facadeBL = FacadeBean.getBusinessLogic();
		rolak.add(new Rola(1, "erabiltzailea"));
		rolak.add(new Rola(2, "administratzailea"));
	}

	public Pertsona getPertsona() {
		return pertsona;
	}

	public void setPertsona(Pertsona pertsona) {
		this.pertsona = pertsona;
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
	
	public List<Rola> getRolak() {
		return rolak;
	}

	public void setRolak(List<Rola> rolak) {
		this.rolak = rolak;
	}

	public Rola getRola() {
		return rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

	public String login() {
		return "login";
	}

	public String register() {
		return "register";
	}

	public String loginEgin() {

		FacesContext context = FacesContext.getCurrentInstance();

		this.pertsona =  facadeBL.existitzenDa(izena, pasahitza);

		if (this.pertsona != null) {

			this.loged = true;
			
			System.out.println(this.pertsona.getClass());
			
			if (this.pertsona.getClass().descriptorString() == "class domain.Erabiltzailea")
				
				this.rola = this.rolak.get(1);
			
			//else if (this.pertsona instanceof Admin)
			else if (this.pertsona.getClass().descriptorString() == "class domain.Admin")

			
				this.rola = this.rolak.get(2);

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

			this.pertsona = facadeBL.erregistratu(izena, pasahitza, jaiotzeData, rola.getMota());

		} catch (AdinTxikikoa e) {
			context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));

		} catch (ErabiltzaileaExistizenDa e) {
			context.addMessage("mezuak", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));

		}

		if (this.pertsona != null) {

			this.loged = true;

			return "aurrera";
		}
		
		else 
			return null;

	}

	public void onDateSelect(SelectEvent event) {

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
		
		this.pertsona = null;

		return "logOut";

	}

/*	private void garbitu() {

		this.izena = "";
		this.pasahitza = "";
		this.jaiotzeData = null;
		this.erabiltzailea = null;

	}*/

	/////////////////////////////////PROBA CHART//////////////////////////////////////////////////////
	///    http://www.primefaces.org:8080/showcase/ui/chart/bar.xhtml?jfwid=37731
/*	private BarChart initBarModel() {
		BarChart model = new BarChart();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);

        model.;
        model.addSeries(girls);

        return model;
    }*/
	
	

}
