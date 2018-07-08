package com.iut.beans;

import java.util.ArrayList;

public class Compte {
	private int id;
	private String libelle;
	private float montant;
	private float decouvertMax;
	private Client proprietaire1;
	private Client proprietaire2;
	private ArrayList<Transaction> debits;
	private ArrayList<Transaction> credits;
	
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
	public ArrayList<Transaction> getDebits() {
		return debits;
	}
	public void setDebits(ArrayList<Transaction> debits) {
		this.debits = debits;
	}
	public ArrayList<Transaction> getCredits() {
		return credits;
	}
	public void setCredits(ArrayList<Transaction> credits) {
		this.credits = credits;
	}
	public Client getProprietaire1() {
		return proprietaire1;
	}
	public void setProprietaire1(Client proprietaire1) {
		this.proprietaire1 = proprietaire1;
	}
	public Client getProprietaire2() {
		return proprietaire2;
	}
	public void setProprietaire2(Client proprietaire2) {
		this.proprietaire2 = proprietaire2;
	}
	
	
	
}
