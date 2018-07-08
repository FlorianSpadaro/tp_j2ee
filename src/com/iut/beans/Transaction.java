package com.iut.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
	private int id;
	private LocalDateTime date;
	private String dateAffiche;
	private Compte compteCorrespondant;
	private float montant;
	
	
	public Transaction() {
		super();
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
	public Compte getCompteCorrespondant() {
		return compteCorrespondant;
	}
	public void setCompteCorrespondant(Compte compteCorrespondant) {
		this.compteCorrespondant = compteCorrespondant;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getDateAffiche() {
		return dateAffiche;
	}
	public void setDateAffiche(String dateAffiche) {
		this.dateAffiche = dateAffiche;
	}
	public String afficherDate()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return this.date.format(formatter);
	}
	
}
