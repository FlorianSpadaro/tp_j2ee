package com.iut.beans;

public class Agence {
	private int id;
	private String libelle;
	private String telephone;
	private String adresse;
	private String codePostal;
	private String ville;
	

	public Agence(int id, String libelle, String telephone, String adresse, String codePostal, String ville) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.telephone = telephone;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
}
