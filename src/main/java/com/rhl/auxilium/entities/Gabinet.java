package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the gabinet database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Gabinet.findAll", query="SELECT g FROM Gabinet g"),
@NamedQuery(name="Gabinet.findAllActive", query="SELECT g FROM Gabinet g where czyAktywny=1"),
@NamedQuery(name="Gabinet.deleteById", query="Delete FROM Gabinet g where g.id=?1"),
@NamedQuery(name="Gabinet.disactivateById", query="Update Gabinet g set g.czyAktywny=0 where g.id=?1"),
@NamedQuery(name="Gabinet.findByNumer", query="SELECT g FROM Gabinet g where czyAktywny=1 and g.numer like ?1"),
@NamedQuery(name="Gabinet.findNumerById", query="SELECT g.numer FROM Gabinet g where czyAktywny=1 and g.id=?1")
})
public class Gabinet implements Serializable {
	@Override
	public String toString() {
		return "Gabinet [id=" + id + ", numer=" + numer + ", opis=" + opis
				+ "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GABINET_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="GABINET_ID_GENERATOR")
	private int id;

//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="CREATED_AT")
//	private Date createdAt;

	private String numer;

	private String opis;
	
	@Column(name="CZY_AKTYWNY")
	private byte czyAktywny;

//	@Column(name="UPDATED_AT")
//	private Timestamp updatedAt;

	//bi-directional many-to-one association to Wizyta
	@OneToMany(mappedBy="gabinet")
	private List<Wizyta> wizyty;

	public Gabinet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public Date getCreatedAt() {
//		return this.createdAt;
//	}
//
//	public void setCreatedAt(Date createdAt) {
//		this.createdAt = createdAt;
//	}

	public String getNumer() {
		return this.numer;
	}

	public void setNumer(String numer) {
		this.numer = numer;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public byte getCzyAktywny() {
		return this.czyAktywny;
	}

	public void setCzyAktywny(byte czyAktywny) {
		this.czyAktywny = czyAktywny;
	}
	
//	public Timestamp getUpdatedAt() {
//		return this.updatedAt;
//	}

//	public void setUpdatedAt(Timestamp updatedAt) {
//		this.updatedAt = updatedAt;
//	}

	public List<Wizyta> getWizyty() {
		return this.wizyty;
	}

	public void setWizyty(List<Wizyta> wizyty) {
		this.wizyty = wizyty;
	}

	public Wizyta addWizyty(Wizyta wizyty) {
		getWizyty().add(wizyty);
		wizyty.setGabinet(this);

		return wizyty;
	}

	public Wizyta removeWizyty(Wizyta wizyty) {
		getWizyty().remove(wizyty);
		wizyty.setGabinet(null);

		return wizyty;
	}

}