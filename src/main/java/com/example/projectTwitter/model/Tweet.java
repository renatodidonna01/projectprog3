package com.example.projectTwitter.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tweet database table.
 * 
 */
@Entity
@NamedQuery(name="Tweet.findAll", query="SELECT t FROM Tweet t")

public class Tweet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDTweet;

	private Timestamp dataOra;

	
	private String testo;

	
	

	 @ManyToOne
	 @JoinColumn(name = "ID_Hashtag")
	 private Hashtag hashtag;
	 
	
	@ManyToOne
	@JoinColumn(name="IDUtente")
	private Utente utente;
	
	
	

	public Tweet() {
	}

	public int getIDTweet() {
		return this.IDTweet;
	}

	public void setIDTweet(int IDTweet) {
		this.IDTweet = IDTweet;
	}

	public Timestamp getDataOra() {
		return this.dataOra;
	}

	public void setDataOra(Timestamp dataOra) {
		this.dataOra = dataOra;
	}

	public String getTesto() {
		return this.testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}







	public Hashtag getHashtag() {
		return hashtag;
	}

	public void setHashtag(Hashtag hashtag) {
		this.hashtag = hashtag;
	}




	

	

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}