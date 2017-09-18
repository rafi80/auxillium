package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;

/*import java.sql.Timestamp;
import java.util.List;*/

import java.util.Date;

/**
 * The persistent class for the wizyta database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Wizyta.findAll", query="SELECT w FROM Wizyta w"),
@NamedQuery(name="Wizyta.getByDateAndGabinet", query="SELECT w FROM Wizyta w where w.gabinet=?1 and w.dataWizyty=?2"),
@NamedQuery(name="Wizyta.getByDateGabinetAndBlokCzasu", query="SELECT w FROM Wizyta w where w.gabinet=?1 and w.dataWizyty=?2 and w.blokCzasu=?3"),
@NamedQuery(name="Wizyta.deleteById", query="Delete FROM Wizyta w where w.id=?1"),
@NamedQuery(name="Wizyta.getById", query="SELECT w FROM Wizyta w where w.id=?1"),
@NamedQuery(name="Wizyta.getByDateAndLekarz", query="SELECT w FROM Wizyta w where w.dataWizyty=?1 and w.lekarz=?2"),
@NamedQuery(name="Wizyta.getByPacjent", query="SELECT w FROM Wizyta w where w.lekarz is not null and w.pacjent=?1"),
@NamedQuery(name="Wizyta.getFreeOrReservedVisitByDateAndPatient", query="SELECT w FROM Wizyta w where w.lekarz is not null and w.dataWizyty=?1 and (w.pacjent=null or w.pacjent=?2)"),
@NamedQuery(name="Wizyta.getFreeOrReservedVisitByDatePatientAndDocs", query="SELECT w FROM Wizyta w where w.lekarz is not null and w.dataWizyty=?1 and (w.pacjent=null or w.pacjent=?2) and w.lekarz in ?3")
})
public class Wizyta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="WIZYTA_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="WIZYTA_ID_GENERATOR")
	private int id;

/*	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;*/

	@Column(name="CZY_PACJENT_PRZYBYL")
	private byte czyPacjentPrzybyl;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_WIZYTY")
	private Date dataWizyty;

/*	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;*/

	//bi-directional many-to-one association to OpisWizyty
	@OneToOne(optional=true, mappedBy="wizyta")
	//private List<OpisWizyty> opisyWizyt;
	private OpisWizyty opisWizyty;

	//bi-directional many-to-one association to BlokCzasu
	@ManyToOne
	@JoinColumn(name="ID_BLOK_CZASU")
	private BlokCzasu blokCzasu;

	//bi-directional many-to-one association to Gabinet
	@ManyToOne
	@JoinColumn(name="PRZYPISANY_GABINET")
	private Gabinet gabinet;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="PRZYPISANY_LEKARZ")
	private Uzytkownik lekarz;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="PRZYPISANY_PACJENT")
	private Uzytkownik pacjent;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="ZMODYFIKOWANY_PRZEZ")
	private Uzytkownik lekarzModyfikujacy;

	public Wizyta() {
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

	public byte getCzyPacjentPrzybyl() {
		return this.czyPacjentPrzybyl;
	}

	public void setCzyPacjentPrzybyl(byte czyPacjentPrzybyl) {
		this.czyPacjentPrzybyl = czyPacjentPrzybyl;
	}

	public Date getDataWizyty() {
		return this.dataWizyty;
	}

	public void setDataWizyty(Date dataWizyty) {
		this.dataWizyty = dataWizyty;
	}

/*	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}*/

	public OpisWizyty getOpisWizyty() {
		return this.opisWizyty;
	}

	public void setOpisWizyty(OpisWizyty opisWizyty) {
		this.opisWizyty = opisWizyty;
	}

/*	public OpisWizyty addOpisWizyty(OpisWizyty opisWizyty) {
		getOpisWizyty().add(opisWizyty);
		opisWizyty.setWizyta(this);
		return opisWizyty;
	}

	public OpisWizyty removeOpisyWizyt(OpisWizyty opisWizyty) {
		getOpisWizyty().remove(opisWizyty);
		opisWizyty.setWizyta(null);

		return opisyWizyt;
	}*/

	public BlokCzasu getBlokCzasu() {
		return this.blokCzasu;
	}

	public void setBlokCzasu(BlokCzasu blokCzasu) {
		this.blokCzasu = blokCzasu;
	}

	public Gabinet getGabinet() {
		return this.gabinet;
	}

	public void setGabinet(Gabinet gabinet) {
		this.gabinet = gabinet;
	}

	public Uzytkownik getLekarz() {
		return this.lekarz;
	}

	public void setLekarz(Uzytkownik lekarz) {
		this.lekarz = lekarz;
	}

	public Uzytkownik getPacjent() {
		return this.pacjent;
	}

	public void setPacjent(Uzytkownik pacjent) {
		this.pacjent = pacjent;
	}

	public Uzytkownik getLekarzModyfikujacy() {
		return this.lekarzModyfikujacy;
	}

	public void setLekarzModyfikujacy(Uzytkownik lekarzModyfikujacy) {
		this.lekarzModyfikujacy = lekarzModyfikujacy;
	}

	@Override
	public String toString() {
		return "Wizyta [id=" + id + ", czyPacjentPrzybyl=" + czyPacjentPrzybyl
				+ ", dataWizyty=" + dataWizyty + ", opisyWizyt=" + opisWizyty
				+ ", blokCzasu=" + blokCzasu + ", gabinet=" + gabinet
				+ ", lekarz=" + lekarz + ", pacjent=" + pacjent
				+ ", lekarzModyfikujacy=" + lekarzModyfikujacy + "]";
	}

}