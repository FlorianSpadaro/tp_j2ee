package com.iut.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.iut.enums.DestinataireMessage;

public class Message {
	private int id;
	private Client client;
	private Conseiller conseiller;
	private String sujet;
	private String contenu;
	private LocalDateTime date;
	private String dateAffiche;
	private boolean lu;
	private LocalDateTime dateDerniereReponse;
	private String dateDerniereReponseAffiche;
	private DestinataireMessage destinataire;
	private ArrayList<ReponseMessage> reponses;
	
	public Message() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Conseiller getConseiller() {
		return conseiller;
	}

	public void setConseiller(Conseiller conseiller) {
		this.conseiller = conseiller;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
		this.dateAffiche = afficherDate();
	}

	public boolean isLu() {
		return lu;
	}

	public void setLu(boolean lu) {
		this.lu = lu;
	}

	public DestinataireMessage getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(DestinataireMessage destinataire) {
		this.destinataire = destinataire;
	}

	public ArrayList<ReponseMessage> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<ReponseMessage> reponses) {
		this.reponses = reponses;
	}

	public String getDateAffiche() {
		return dateAffiche;
	}

	public void setDateAffiche(String dateAffiche) {
		this.dateAffiche = dateAffiche;
	}
	public LocalDateTime getDateDerniereReponse() {
		return dateDerniereReponse;
	}

	public void setDateDerniereReponse(LocalDateTime dateDerniereReponse) {
		this.dateDerniereReponse = dateDerniereReponse;
		this.dateDerniereReponseAffiche = afficherDateDerniereReponse();
	}

	public String getDateDerniereReponseAffiche() {
		return dateDerniereReponseAffiche;
	}

	public void setDateDerniereReponseAffiche(String dateDerniereReponseAffiche) {
		this.dateDerniereReponseAffiche = dateDerniereReponseAffiche;
	}

	public String afficherDate()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return this.date.format(formatter);
	}
	
	public String afficherDateDerniereReponse()
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return this.dateDerniereReponse.format(formatter);
	}
}
