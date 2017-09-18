package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the blok_czasu database table.
 * 
 */
@Entity
@Table(name="blok_czasu")
@NamedQueries({
@NamedQuery(name="BlokCzasu.findAll", query="SELECT b FROM BlokCzasu b"),
@NamedQuery(name="BlokCzasu.findAllActive", query="SELECT b FROM BlokCzasu b where b.czyAktywny =1")
})

public class BlokCzasu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BLOK_CZASU_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="BLOK_CZASU_ID_GENERATOR")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;

	@Column(name="CZY_AKTYWNY")
	private byte czyAktywny;

	private String opis;

	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;

	//bi-directional many-to-one association to Wizyta
	@OneToMany(mappedBy="blokCzasu")
	private List<Wizyta> wizyty;

	public BlokCzasu() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public byte getCzyAktywny() {
		return this.czyAktywny;
	}

	public void setCzyAktywny(byte czyAktywny) {
		this.czyAktywny = czyAktywny;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Wizyta> getWizyty() {
		return this.wizyty;
	}

	public void setWizyty(List<Wizyta> wizyty) {
		this.wizyty = wizyty;
	}

	public Wizyta addWizyty(Wizyta wizyty) {
		getWizyty().add(wizyty);
		wizyty.setBlokCzasu(this);

		return wizyty;
	}

	public Wizyta removeWizyty(Wizyta wizyty) {
		getWizyty().remove(wizyty);
		wizyty.setBlokCzasu(null);

		return wizyty;
	}

	@Override
	public String toString() {
		return "BlokCzasu [id=" + id + ", czyAktywny=" + czyAktywny + ", opis="
				+ opis +  "]";
	}

}