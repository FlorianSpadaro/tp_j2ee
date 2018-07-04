package com.iut.beans;

import java.util.ArrayList;

public class Client {
	private int id;
	private String nom;
	private String prenom;
	private Conseiller conseiller;
	private ArrayList<Compte> comptes;
	
	
	
	public Client() {
		super();
	}
	public Client(int id, String nom, String prenom, Conseiller conseiller, ArrayList<Compte> comptes) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.conseiller = conseiller;
		this.comptes = comptes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Conseiller getConseiller() {
		return conseiller;
	}
	public void setConseiller(Conseiller conseiller) {
		this.conseiller = conseiller;
	}
	public ArrayList<Compte> getComptes() {
		return comptes;
	}
	public void setComptes(ArrayList<Compte> comptes) {
		this.comptes = comptes;
	}
	
	
}
