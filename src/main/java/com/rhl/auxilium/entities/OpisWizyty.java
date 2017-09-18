package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;



/**
 * The persistent class for the opis_wizyty database table.
 * 
 */
@Entity
@Table(name="opis_wizyty")
@NamedQueries({
@NamedQuery(name="OpisWizyty.findAll", query="SELECT o FROM OpisWizyty o"),
@NamedQuery(name="OpisWizyty.getByWizyta", query="SELECT o FROM OpisWizyty o where o.wizyta=?1")
})
public class OpisWizyty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="OPIS_WIZYTY_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="OPIS_WIZYTY_ID_GENERATOR")
	private int id;

/*	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;*/

	@Lob
	private String opis;

/*	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;*/

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="STWORZONY_PRZEZ")
	private Uzytkownik stworzonyPrzezUzytkownika;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="ZMODYFIKOWANY_PRZEZ")
	private Uzytkownik zmodyfikowanyPrzezUzytkownika;

	//bi-directional one-to-one association to Wizyta
	@OneToOne
	@JoinColumn(name="STWORZONY_PODCZAS")
	private Wizyta wizyta;

	public OpisWizyty() {
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
	}
*/
	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

/*	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}*/

	public Uzytkownik getStworzonyPrzezUzytkownika() {
		return this.stworzonyPrzezUzytkownika;
	}

	public void setStworzonyPrzezUzytkownika(Uzytkownik stworzonyPrzezUzytkownika) {
		this.stworzonyPrzezUzytkownika = stworzonyPrzezUzytkownika;
	}

	public Uzytkownik getZmodyfikowanyPrzezUzytkownika() {
		return this.zmodyfikowanyPrzezUzytkownika;
	}

	public void setZmodyfikowanyPrzezUzytkownika(Uzytkownik zmodyfikowanyPrzezUzytkownika) {
		this.zmodyfikowanyPrzezUzytkownika = zmodyfikowanyPrzezUzytkownika;
	}

	public Wizyta getWizyta() {
		return this.wizyta;
	}

	public void setWizyta(Wizyta wizyta) {
		this.wizyta = wizyta;
	}

	@Override
	public String toString() {
		return "OpisWizyty [id=" + id +  "]";
	}

}