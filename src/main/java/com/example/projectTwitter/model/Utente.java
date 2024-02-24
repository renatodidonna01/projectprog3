package com.example.projectTwitter.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the utente database table.
 * 
 */
@Entity

@NamedQuery(name="Utente.findAll", query="SELECT u FROM Utente u")
public   class Utente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private String citta;

	private String cognome;

	private String email;

	private String nome;

	private String password;

	 @Enumerated(EnumType.STRING)
	   @Column(name = "ruolo")
	    private Role ruolo;
	 
	 
	 public enum Role {
		    USER,
		    ADMIN
		}

	//bi-directional many-to-one association to Commento
	@OneToMany(mappedBy="utente")
	private List<Commento> commentos;
	
	
	@OneToMany(mappedBy = "utente")
    private List<Tweet> tweets;
	

	
	
	

	//bi-directional many-to-many association to Utente
	@ManyToMany
	@JoinTable(
		name="seguiti"
		, joinColumns={
			@JoinColumn(name="FollowedID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="FollowerID")
			}
		)
	private List<Utente> utentes1;

	//bi-directional many-to-many association to Utente
	@ManyToMany(mappedBy="utentes1")
	private List<Utente> utentes2;

	public Utente() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRuolo() {
        return ruolo;
    }

    public void setRuolo(Role ruolo) {
        this.ruolo = ruolo;
    }

	public List<Commento> getCommentos() {
		return this.commentos;
	}

	public void setCommentos(List<Commento> commentos) {
		this.commentos = commentos;
	}

	public Commento addCommento(Commento commento) {
		getCommentos().add(commento);
		commento.setUtente(this);

		return commento;
	}

	public Commento removeCommento(Commento commento) {
		getCommentos().remove(commento);
		commento.setUtente(null);

		return commento;
	}

	public List<Tweet> getTweets() {
		return this.tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}



	public List<Utente> getUtentes1() {
		return this.utentes1;
	}

	public void setUtentes1(List<Utente> utentes1) {
		this.utentes1 = utentes1;
	}

	public List<Utente> getUtentes2() {
		return this.utentes2;
	}

	public void setUtentes2(List<Utente> utentes2) {
		this.utentes2 = utentes2;
	}
	
	
	
}