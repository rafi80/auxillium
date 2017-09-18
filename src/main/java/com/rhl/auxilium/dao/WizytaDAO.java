package com.rhl.auxilium.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
/*import javax.persistence.NoResultException;
*/
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.BlokCzasu;
import com.rhl.auxilium.entities.Gabinet;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.entities.Wizyta;

public class WizytaDAO {

	private Wizyta wizyta = new Wizyta();	
	
	public WizytaDAO() {
	}
	
	public Wizyta getWizyta(){
		return wizyta;
	}
	
	public void setWizyta(Wizyta wizyta) {
		this.wizyta = wizyta;
	}
	
	static Logger log = Logger.getLogger(WizytaDAO.class);
	
	public List<Wizyta> getListaWizyt(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Wizyta> list = em.createNamedQuery("Wizyta.findAll").getResultList();
		em.close();
		return list;
	}
	
	public void edytuj(Wizyta wizyta) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		em.merge(wizyta);
		em.getTransaction().commit();
		em.close();
		this.wizyta = new Wizyta();
	}
	
	public void usun(int idDoUsuniecia) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.deleteById");
		query1.setParameter(1, idDoUsuniecia);
		int deletedData=query1.executeUpdate();
	    System.out.println(deletedData +" record is deleted.");
		em.getTransaction().commit();
		em.close();
		this.wizyta = new Wizyta();
	}
	
	public void dodaj(Wizyta wizyta)  {
		EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			em.persist(wizyta);
			em.getTransaction().commit();
			this.wizyta = new Wizyta();
			em.close();
	}
		
	public List<Wizyta> getListaWizytPoDacieIGabinecie(Gabinet gabinet, Date dataWizyty){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.getByDateAndGabinet");
		query1.setParameter(1, gabinet);
		query1.setParameter(2, dataWizyty);
		@SuppressWarnings("unchecked")
		List<Wizyta> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public Integer getIdWizytyPoDacieGabinecieIBlokuCzasu(Gabinet gabinet, Date dataWizyty, BlokCzasu blokCzasu) throws NoResultException{
		try {
			EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			Query query1 = em.createNamedQuery("Wizyta.getByDateGabinetAndBlokCzasu");
			query1.setParameter(1, gabinet);
			query1.setParameter(2, dataWizyty);
			query1.setParameter(3, blokCzasu);
			Wizyta wizyta= (Wizyta)query1.getSingleResult();
			em.close();
			Integer	idWizyty = wizyta.getId();
			return idWizyty; }
		catch (NoResultException e) {
			System.out.println("Nie znaleziono obiektu Wizyta dla podanych pramaterów. Zwaracam null");
			return null;
		}
	}
	
	public Wizyta getWizytaPoId(int id) throws NoResultException{
		try {
			EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			Query query1 = em.createNamedQuery("Wizyta.getById");
			query1.setParameter(1, id);
			Wizyta wizyta= (Wizyta)query1.getSingleResult();
			em.close();
			return wizyta; }
		catch (NoResultException e) {
			System.out.println("Nie znaleziono obiektu Wizyta dla podanego id. Zwaracam null");
			return null;
		}
	}
	
	public List<Wizyta> getListaWizytPoDacieILekarzu(Date dataWizyty, Uzytkownik lekarz){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.getByDateAndLekarz");
		query1.setParameter(1, dataWizyty);
		query1.setParameter(2, lekarz);
		@SuppressWarnings("unchecked")
		List<Wizyta> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public List<Wizyta> getListaWizytPoPacjencie(Uzytkownik pacjent){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.getByPacjent");
		query1.setParameter(1, pacjent);
		@SuppressWarnings("unchecked")
		List<Wizyta> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public List<Wizyta> getListaWolnychWizytPoDacieIPacjencie(Date dataWizyty, Uzytkownik pacjent){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.getFreeOrReservedVisitByDateAndPatient");
		query1.setParameter(1, dataWizyty);
		query1.setParameter(2, pacjent);
		@SuppressWarnings("unchecked")
		List<Wizyta> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public List<Wizyta> getListaWolnychWizytPoDaciePacjencieILekarzach(Date dataWizyty, Uzytkownik pacjent, List<Uzytkownik> lekarz){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Wizyta.getFreeOrReservedVisitByDatePatientAndDocs");
		query1.setParameter(1, dataWizyty);
		query1.setParameter(2, pacjent);
		query1.setParameter(3, lekarz);
		@SuppressWarnings("unchecked")
		List<Wizyta> list=query1.getResultList();
		em.close();
		return list;
	}
	
}
