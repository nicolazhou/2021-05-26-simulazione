package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	
	private List<String> cities;
	private Map<String, Business> idBusinessMap;
	
	private Graph<Business, DefaultWeightedEdge> graph;

	
	private List<Business> soluzione;
	
	
	public Model() {
		this.dao = new YelpDao();
		
		this.cities = new ArrayList<>();
		
		this.cities = this.dao.getAllCities();
		
		this.idBusinessMap = new HashMap<>();
		
		for(Business b : this.dao.getAllBusiness()) {
			this.idBusinessMap.put(b.getBusinessId(), b);
		}
		

	}
	
	
	public void buildGraph(String city, Integer year) {
		
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		// Nodi
		List<Business> nodi = new ArrayList<>(this.dao.getVertici(city, year, this.idBusinessMap));
		
		Graphs.addAllVertices(this.graph, nodi);
		
		
		// Archi
		for(Arco ar : this.dao.getArchi(city, year, idBusinessMap)) {
				
			Graphs.addEdgeWithVertices(this.graph, ar.getBusiness1(), ar.getBusiness2(), ar.getPeso());
				
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# Vertici: " + this.graph.vertexSet().size());
		System.out.println("# Archi: " + this.graph.edgeSet().size());
		
		
		
	}
	
	
	public Business getBestBusiness() {
		
		Business result = null;
		double best = 0.0;
		
		for(Business b : this.graph.vertexSet()) {
			
			System.out.println(b.getBusinessName());
			double val = 0.0;
			
			for(DefaultWeightedEdge e : this.graph.incomingEdgesOf(b)) {
				val += this.graph.getEdgeWeight(e);
				//System.out.println("+ " + val);
			}
			
			for(DefaultWeightedEdge e : this.graph.outgoingEdgesOf(b)) {	
				val -= this.graph.getEdgeWeight(e);
				//System.out.println("- " + val);
			}
			
			System.out.println(val);
			
			if(val > best) {
				result = b;
				best = val;
				
				System.out.println(b.getBusinessName());
			}
			
			
			
		}
		
		return result;
		
	}
	
	public Set<Business> getVertici() {
		return this.graph.vertexSet();
	}
	
	public boolean isGrafoLoaded() {
		
		if(this.graph == null) {
			return false;
		}
		
		return true;
	}
	
	
	public int getNVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.graph.edgeSet().size();
	}
	
	
	public List<String> getCities() {
		
		Collections.sort(this.cities);
		
		return this.cities;	
	}
	
	
	
	public List<Business> trovaPercorso(Business partenza, double x) {
		
		Business destinazione = this.getBestBusiness();
		
		
		// Inizializzazione
		this.soluzione = null;
		List<Business> parziale = new ArrayList<>();
		
		
		parziale.add(partenza);
		
		cerca(parziale, x, destinazione);
		
		return this.soluzione;
		
	}


	private void cerca(List<Business> parziale, double x, Business destinazione) {
		// TODO Auto-generated method stub
		
		Business ultimo = parziale.get(parziale.size()-1);
		
		// Condizione di terminazione
		if(ultimo.equals(destinazione)) {
			
			if(this.soluzione == null) {
				this.soluzione = new ArrayList<>(parziale);
				return;
			} else if(parziale.size() < this.soluzione.size()) {
				// NOTA: per calcolare i percorsi più lunghi, basta
				// mettere > nell'istuzione precedente
				this.soluzione = new ArrayList<>(parziale);
				return;
			} else
				return;
			
		}
		
		
		// Ricorsione
		for(DefaultWeightedEdge e : this.graph.outgoingEdgesOf(ultimo)) {
			
			if(this.graph.getEdgeWeight(e) > x) {
				
				Business prossimo = Graphs.getOppositeVertex(this.graph, e, ultimo);
				
				if(!parziale.contains(prossimo)) {
					
					parziale.add(prossimo);
					
					cerca(parziale, x, destinazione);
					
					parziale.remove(parziale.size()-1);
					
				}
				
				
			}
		}
		
		
		
	}


	



	
}
