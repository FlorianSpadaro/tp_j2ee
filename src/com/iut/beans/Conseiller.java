package com.iut.beans;

import java.util.ArrayList;

public class Conseiller {
	private int id;
	private String nom;
	private String prenom;
	private Agence agence;
	private ArrayList<Client> clients;
	
	
	public Conseiller() {
		super();
	}
	public Conseiller(int id, String nom, String prenom, Agence agence) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.agence = agence;
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
	public Agence getAgence() {
		return agence;
	}
	public void setAgence(Agence agence) {
		this.agence = agence;
	}
	public ArrayList<Client> getClients() {
		return clients;
	}
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
	
	
	
}
