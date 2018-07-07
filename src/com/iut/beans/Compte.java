package com.iut.beans;

public class Compte {
	private int id;
	private String libelle;
	private float montant;
	private float decouvertMax;
	
	public Compte() {
		super();
	}
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
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public float getDecouvertMax() {
		return decouvertMax;
	}
	public void setDecouvertMax(float decouvertMax) {
		this.decouvertMax = decouvertMax;
	}
	
	
}
