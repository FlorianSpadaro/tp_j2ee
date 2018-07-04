package com.iut.beans;

public class Compte {
	private int id;
	private float montant;
	public Compte(int id, float montant) {
		super();
		this.id = id;
		this.montant = montant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
	
}
