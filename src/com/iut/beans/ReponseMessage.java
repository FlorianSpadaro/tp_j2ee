package com.iut.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.iut.enums.DestinataireMessage;

public class ReponseMessage {
	private int id;
	private String contenu;
	private LocalDateTime date;
	private String dateAffiche;
	private boolean lu;
	private DestinataireMessage destinataire;
	private Message messageLie;
	public ReponseMessage() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Message getMessageLie() {
		return messageLie;
	}
	public void setMessageLie(Message messageLie) {
		this.messageLie = messageLie;
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
