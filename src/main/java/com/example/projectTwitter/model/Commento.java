package com.example.projectTwitter.model;

import java.io.Serializable;
import jakarta.persistence.*;


/**
 * The persistent class for the commento database table.
 * 
 */
@Entity
@NamedQuery(name="Commento.findAll", query="SELECT c FROM Commento c")
public class Commento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDCommento;

	@Lob
	private String testo;

	//bi-directional many-to-one association to Tweet
	@ManyToOne
	@JoinColumn(name="IDTweet")
	private Tweet tweet;

	//bi-directional many-to-one association to Utente
	@ManyToOne
	@JoinColumn(name="IdUtente")
	private Utente utente;

		
	public Commento() {
	}

	public int getIDCommento() {
		return this.IDCommento;
	}

	public void setIDCommento(int IDCommento) {
		this.IDCommento = IDCommento;
	}

	public String getTesto() {
		return this.testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Tweet getTweet() {
		return this.tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	

}