package com.iut.beans;

public class Transaction {
	private int id;
	private Compte debiteur;
	private Compte crediteur;
	private float montant;
	public Transaction(int id, Compte debiteur, Compte crediteur, float montant) {
		super();
		this.id = id;
		this.debiteur = debiteur;
		this.crediteur = crediteur;
		this.montant = montant;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Compte getDebiteur() {
		return debiteur;
	}
	public void setDebiteur(Compte debiteur) {
		this.debiteur = debiteur;
	}
	public Compte getCrediteur() {
		return crediteur;
	}
	public void setCrediteur(Compte crediteur) {
		this.crediteur = crediteur;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	
}
