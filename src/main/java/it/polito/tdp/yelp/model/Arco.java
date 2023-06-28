package it.polito.tdp.yelp.model;

public class Arco {
	
	private Business business1;
	private Business business2;
	private double peso;
	
	
	public Arco(Business business1, Business business2, double peso) {
		super();
		this.business1 = business1;
		this.business2 = business2;
		this.peso = peso;
	}
	
	
	public Business getBusiness1() {
		return business1;
	}
	
	
	public Business getBusiness2() {
		return business2;
	}
	
	
	public double getPeso() {
		return peso;
	}
	
	
	
}
