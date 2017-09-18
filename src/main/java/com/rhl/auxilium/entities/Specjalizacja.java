package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;



/**
 * The persistent class for the specjalizacja database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Specjalizacja.findAll", query="SELECT s FROM Specjalizacja s"),
@NamedQuery(name="Specjalizacja.findNazwaById", query="SELECT s.nazwaSpecjalizacji FROM Specjalizacja s where s.id =?1"),
@NamedQuery(name="Specjalizacja.findById", query="SELECT s FROM Specjalizacja s where s.id=?1")
})
public class Specjalizacja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPECJALIZACJA_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="SPECJALIZACJA_ID_GENERATOR")
	private int id;

/*	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;*/

	@Column(name="NAZWA_SPECJALIZACJI")
	private String nazwaSpecjalizacji;

/*	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;*/

/*	//bi-directional many-to-one association to Uzytkownik
	@OneToMany(cascade=CascadeType.ALL, mappedBy="specjalizacja")
	private List<Uzytkownik> uzytkownicy;
*/
	public Specjalizacja() {
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

	public String getNazwaSpecjalizacji() {
		return this.nazwaSpecjalizacji;
	}

	public void setNazwaSpecjalizacji(String nazwaSpecjalizacji) {
		this.nazwaSpecjalizacji = nazwaSpecjalizacji;
	}

	@Override
	public String toString() {
		return "Specjalizacja [id=" + id + ", nazwaSpecjalizacji="
				+ nazwaSpecjalizacji + "]";
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
		uzytkownicy.setSpecjalizacja(this);

		return uzytkownicy;
	}

	public Uzytkownik removeUzytkownicy(Uzytkownik uzytkownicy) {
		getUzytkownicy().remove(uzytkownicy);
		uzytkownicy.setSpecjalizacja(null);

		return uzytkownicy;
	}*/

}