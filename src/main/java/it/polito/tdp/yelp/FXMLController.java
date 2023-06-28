/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnLocaleMigliore"
    private Button btnLocaleMigliore; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="cmbLocale"
    private ComboBox<Business> cmbLocale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    	}
    	
    	Business business = this.cmbLocale.getValue();
    	
    	if(business == null) {
    		this.txtResult.setText("Scegli un locale!");
    		return;
    	}
    	
    	String input = this.txtX.getText();
    	
    	Double x = 0.0;
    	
    	try {
    		
    		x = Double.parseDouble(input);
    		
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valore numerico a x!");
    		return;
    	}
    	
    	
    	if(x < 0 || x > 1) {
    		this.txtResult.setText("Inserisci un numero compreso tra 0 e 1!");
    		return;
    	}
    	
    	List<Business> percorso = this.model.trovaPercorso(business, x);
    	
    	this.txtResult.appendText("PERCORSO: \n");
    	
    	for(Business b : percorso) {
    		
    		this.txtResult.appendText(b + "\n");
    		
    	}
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String city = this.cmbCitta.getValue();
    	
    	if(city == null) {
    		this.txtResult.setText("Scegli una città!");
    		return;
    	}
    	
    	Integer anno = this.cmbAnno.getValue();
    	
    	if(anno == null) {
    		this.txtResult.setText("Scegli un anno!");
    		return;
    	}
    	
    	this.model.buildGraph(city, anno);
    	
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNVertici() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
    	
    	this.cmbLocale.getItems().addAll(this.model.getVertici());
    	

    }

    @FXML
    void doLocaleMigliore(ActionEvent event) {

    	this.txtResult.clear();
    	
    	if(!this.model.isGrafoLoaded()) {
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    	}
    	
    	Business best = this.model.getBestBusiness();
    	
    	if(best == null) {
    		this.txtResult.setText("Non ci sono locali migliori!");
    		return;
    	}
    	
    	this.txtResult.appendText("LOCALE MIGLIORE: " + best.getBusinessName());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnLocaleMigliore != null : "fx:id=\"btnLocaleMigliore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbLocale != null : "fx:id=\"cmbLocale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbCitta.getItems().addAll(this.model.getCities());
    	
    	for(int i = 2005; i <= 2013; i++) {
    		this.cmbAnno.getItems().add(i);
    	}
    	
    	
    }
}
