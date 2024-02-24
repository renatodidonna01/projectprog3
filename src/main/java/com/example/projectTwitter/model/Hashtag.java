package com.example.projectTwitter.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the hashtag database table.
 * 
 */
@Entity
@NamedQuery(name="Hashtag.findAll", query="SELECT h FROM Hashtag h")
public class Hashtag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int IDHashtag;

	private String testo;
	
	

	
	@OneToMany(mappedBy = "hashtag")
    private List<Tweet> tweets;
	
	

	public Hashtag() {
	}

	public int getIDHashtag() {
		return this.IDHashtag;
	}

	public void setIDHashtag(int IDHashtag) {
		this.IDHashtag = IDHashtag;
	}

	public String getTesto() {
		return this.testo;
	}
	
	
	public void setTesto(String testo) {
		this.testo = testo;
	}

	
	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	

	


	

}