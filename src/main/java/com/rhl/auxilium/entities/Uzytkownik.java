package com.rhl.auxilium.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.servlet.ServletException;

import com.rhl.auxilium.utils.Hasher;

import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u"),
@NamedQuery(name="Uzytkownik.deleteById", query="Delete FROM Uzytkownik u where u.id=?1"),
@NamedQuery(name="Uzytkownik.findByPesel", query="SELECT u FROM Uzytkownik u where u.pesel like ?1"),
@NamedQuery(name="Uzytkownik.findByLogin", query="SELECT u FROM Uzytkownik u where u.login like ?1"),
@NamedQuery(name="Uzytkownik.findPasswordById", query="SELECT u.haslo FROM Uzytkownik u where u.id=?1"),
@NamedQuery(name="Uzytkownik.findAllPatients", query="SELECT u FROM Uzytkownik u where u.czyPracownik=0"),
@NamedQuery(name="Uzytkownik.findAllActiveDocs", query="SELECT u FROM Uzytkownik u where"
												+ " u.typPracownika=3 and u.czyAktywny=1"), 
@NamedQuery(name="Uzytkownik.findAllActivePatients", query="SELECT u FROM Uzytkownik u where"
														+ " u.czyPracownik=0 and u.czyAktywny=1"),
@NamedQuery(name="Uzytkownik.findByLastName", query="SELECT u FROM Uzytkownik u where u.nazwisko like ?1"),
@NamedQuery(name="Uzytkownik.findDocsBySepcialtyId", query="SELECT u FROM Uzytkownik u where u.specjalizacja=?1"),
})

public class Uzytkownik implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Uzytkownik [id=" + id + ", imie=" + imie + ", nazwisko="
				+ nazwisko + ", pesel="
				+ pesel + "]";
	}

	@Id
	@SequenceGenerator(name="UZYTKOWNIK_ID_GENERATOR", sequenceName="ORDER_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="UZYTKOWNIK_ID_GENERATOR")
	private int id;

	private String adres;

	private String email;
	
/*	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;*/

	@Column(name="CZY_AKTYWNY")
	private byte czyAktywny;

	@Column(name="CZY_PRACOWNIK")
	private byte czyPracownik;

	@Column(name="DRUGIE_IMIE")
	private String drugieImie;

	private String haslo;

	private String imie;

	private String login;

	private String nazwisko;

	@Column(name="NUMER_TELEFONU")
	private String numerTelefonu;

	private String pesel;

/*	@Column(name="UPDATED_AT")
	private Timestamp updatedAt;*/

	//bi-directional many-to-one association to OpisWizyty
	@OneToMany(mappedBy="stworzonyPrzezUzytkownika")
	private List<OpisWizyty> opisyStworzonychWizyt;

	//bi-directional many-to-one association to OpisWizyty
	@OneToMany(mappedBy="zmodyfikowanyPrzezUzytkownika")
	private List<OpisWizyty> opisyZmodyfikowanychWizyt;

/*	//bi-directional many-to-one association to Specjalizacja
	@ManyToOne
	@JoinColumn(name="ID_SPECJALIZACJA")*/
	
	@Column(name="ID_SPECJALIZACJA")
	//private Specjalizacja specjalizacja;
	private Integer specjalizacja;

/*	//bi-directional many-to-one association to TypPracownika
	@ManyToOne
	@JoinColumn(name="ID_TYP_PRACOWNIKA")*/
	
	@Column(name="ID_TYP_PRACOWNIKA")
	//private TypPracownika typPracownika;
	private Integer typPracownika;

	//bi-directional many-to-one association to Wizyta
	@OneToMany(cascade=CascadeType.ALL, mappedBy="lekarz")
	private List<Wizyta> wizytyLekarza;

	//bi-directional many-to-one association to Wizyta
	@OneToMany(cascade=CascadeType.ALL, mappedBy="pacjent")
	private List<Wizyta> wizytyPacjenta;

	//bi-directional many-to-one association to Wizyta
	@OneToMany(cascade=CascadeType.ALL, mappedBy="lekarzModyfikujacy")
	private List<Wizyta> wizytyZmodyfikowane;

	public Uzytkownik() {
	}

	
	public boolean czyZgodneHaslo(String podaneHaslo) {
		return this.haslo.equals(getHash(podaneHaslo));
	}
	
	//zwraca hash hasla dla podanego hasla
	private String getHash (String haslo) {
		Hasher hasher = new Hasher();
		try {
			return hasher.hashuj(haslo);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdres() {
		return this.adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

/*	public Date getCreatedAt() {
		return this.createdAt;
	}*/

/*	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}*/

	public byte getCzyAktywny() {
		return this.czyAktywny;
	}

	public void setCzyAktywny(byte czyAktywny) {
		this.czyAktywny = czyAktywny;
	}

	public byte getCzyPracownik() {
		return this.czyPracownik;
	}

	public void setCzyPracownik(byte czyPracownik) {
		this.czyPracownik = czyPracownik;
	}

	public String getDrugieImie() {
		return this.drugieImie;
	}

	public void setDrugieImie(String drugieImie) {
		this.drugieImie = drugieImie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNumerTelefonu() {
		return this.numerTelefonu;
	}

	public void setNumerTelefonu(String numerTelefonu) {
		this.numerTelefonu = numerTelefonu;
	}

	public String getPesel() {
		return this.pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

/*	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}*/

	public List<OpisWizyty> getOpisyStworzonychWizyt() {
		return this.opisyStworzonychWizyt;
	}

	public void setOpisyStworzonychWizyt(List<OpisWizyty> opisyStworzonychWizyt) {
		this.opisyStworzonychWizyt = opisyStworzonychWizyt;
	}

	public OpisWizyty addOpisyStworzonychWizyt(OpisWizyty opisyStworzonychWizyt) {
		getOpisyStworzonychWizyt().add(opisyStworzonychWizyt);
		opisyStworzonychWizyt.setStworzonyPrzezUzytkownika(this);

		return opisyStworzonychWizyt;
	}

	public OpisWizyty removeOpisyStworzonychWizyt(OpisWizyty opisyStworzonychWizyt) {
		getOpisyStworzonychWizyt().remove(opisyStworzonychWizyt);
		opisyStworzonychWizyt.setStworzonyPrzezUzytkownika(null);

		return opisyStworzonychWizyt;
	}

	public List<OpisWizyty> getOpisyZmodyfikowanychWizyt() {
		return this.opisyZmodyfikowanychWizyt;
	}

	public void setOpisyZmodyfikowanychWizyt(List<OpisWizyty> opisyZmodyfikowanychWizyt) {
		this.opisyZmodyfikowanychWizyt = opisyZmodyfikowanychWizyt;
	}

	public OpisWizyty addOpisyZmodyfikowanychWizyt(OpisWizyty opisyZmodyfikowanychWizyt) {
		getOpisyZmodyfikowanychWizyt().add(opisyZmodyfikowanychWizyt);
		opisyZmodyfikowanychWizyt.setZmodyfikowanyPrzezUzytkownika(this);

		return opisyZmodyfikowanychWizyt;
	}

	public OpisWizyty removeOpisyZmodyfikowanychWizyt(OpisWizyty opisyZmodyfikowanychWizyt) {
		getOpisyZmodyfikowanychWizyt().remove(opisyZmodyfikowanychWizyt);
		opisyZmodyfikowanychWizyt.setZmodyfikowanyPrzezUzytkownika(null);

		return opisyZmodyfikowanychWizyt;
	}

	public Integer getSpecjalizacja() {
		return this.specjalizacja;
	}

	public void setSpecjalizacja(Integer specjalizacja) {
		this.specjalizacja = specjalizacja;
	}

	public Integer getTypPracownika() {
		return this.typPracownika;
	}

	public void setTypPracownika(Integer typPracownika) {
		this.typPracownika = typPracownika;
	}

	public List<Wizyta> getWizytyLekarza() {
		return this.wizytyLekarza;
	}

	public void setWizytyLekarza(List<Wizyta> wizytyLekarza) {
		this.wizytyLekarza = wizytyLekarza;
	}

	public Wizyta addWizytyLekarza(Wizyta wizytyLekarza) {
		getWizytyLekarza().add(wizytyLekarza);
		wizytyLekarza.setLekarz(this);

		return wizytyLekarza;
	}

	public Wizyta removeWizytyLekarza(Wizyta wizytyLekarza) {
		getWizytyLekarza().remove(wizytyLekarza);
		wizytyLekarza.setLekarz(null);

		return wizytyLekarza;
	}

	public List<Wizyta> getWizytyPacjenta() {
		return this.wizytyPacjenta;
	}

	public void setWizytyPacjenta(List<Wizyta> wizytyPacjenta) {
		this.wizytyPacjenta = wizytyPacjenta;
	}

	public Wizyta addWizytyPacjenta(Wizyta wizytyPacjenta) {
		getWizytyPacjenta().add(wizytyPacjenta);
		wizytyPacjenta.setPacjent(this);

		return wizytyPacjenta;
	}

	public Wizyta removeWizytyPacjenta(Wizyta wizytyPacjenta) {
		getWizytyPacjenta().remove(wizytyPacjenta);
		wizytyPacjenta.setPacjent(null);

		return wizytyPacjenta;
	}

	public List<Wizyta> getWizytyZmodyfikowane() {
		return this.wizytyZmodyfikowane;
	}

	public void setWizytyZmodyfikowane(List<Wizyta> wizytyZmodyfikowane) {
		this.wizytyZmodyfikowane = wizytyZmodyfikowane;
	}

	public Wizyta addWizytyZmodyfikowane(Wizyta wizytyZmodyfikowane) {
		getWizytyZmodyfikowane().add(wizytyZmodyfikowane);
		wizytyZmodyfikowane.setLekarzModyfikujacy(this);

		return wizytyZmodyfikowane;
	}

	public Wizyta removeWizytyZmodyfikowane(Wizyta wizytyZmodyfikowane) {
		getWizytyZmodyfikowane().remove(wizytyZmodyfikowane);
		wizytyZmodyfikowane.setLekarzModyfikujacy(null);

		return wizytyZmodyfikowane;
	}

}