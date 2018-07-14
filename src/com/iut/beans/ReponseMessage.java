package com.iut.beans;

import java.time.LocalDateTime;

import com.iut.enums.DestinataireMessage;

public class ReponseMessage {
	private int id;
	private String contenu;
	private LocalDateTime date;
	private boolean lu;
	private DestinataireMessage destinataire;
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
	
	
}
