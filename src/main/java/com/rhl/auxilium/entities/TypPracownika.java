package com.rhl.auxilium.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the typ_pracownika database table.
 * 
 */
@Entity
@Table(name="typ_pracownika")
@NamedQuery(name="TypPracownika.findAll", query="SELECT t FROM TypPracownika t")
public class TypPracownika implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TYP_PRACOWNIKA_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="TYP_PRACOWNIKA_ID_GENERATOR")
	private int id;

/*	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;*/

	@Column(name="TYP_PRACOWNIKA")
	private String typPracownika;

/*	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;*/

/*	//bi-directional many-to-one association to Uzytkownik
	@OneToMany(cascade=CascadeType.ALL, mappedBy="typPracownika" )
	private List<Uzytkownik> uzytkownicy;*/

	public TypPracownika() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

/*	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}*/

	public String getTypPracownika() {
		return this.typPracownika;
	}

	public void setTypPracownika(String typPracownika) {
		this.typPracownika = typPracownika;
	}

	@Override
	public String toString() {
		return "TypPracownika [id=" + id + ", typPracownika=" + typPracownika
				+ "]";
	}

/*	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}*/

/*	public List<Uzytkownik> getUzytkownicy() {
		return this.uzytkownicy;
	}

	public void setUzytkownicy(List<Uzytkownik> uzytkownicy) {
		this.uzytkownicy = uzytkownicy;
	}

	public Uzytkownik addUzytkownicy(Uzytkownik uzytkownicy) {
		getUzytkownicy().add(uzytkownicy);
		uzytkownicy.setTypPracownika(this);

		return uzytkownicy;
	}

	public Uzytkownik removeUzytkownicy(Uzytkownik uzytkownicy) {
		getUzytkownicy().remove(uzytkownicy);
		uzytkownicy.setTypPracownika(null);

		return uzytkownicy;
	}*/

}