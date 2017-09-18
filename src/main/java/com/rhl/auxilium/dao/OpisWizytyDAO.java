package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.OpisWizyty;
import com.rhl.auxilium.entities.Wizyta;


public class OpisWizytyDAO {

	private OpisWizyty opisWizyty = new OpisWizyty();	
	
	public OpisWizytyDAO() {
	}
	
	public OpisWizyty getOpisWizyty(){
		return opisWizyty;
	}
	
	public void setOpisWizyty(OpisWizyty opisWizyty) {
		this.opisWizyty = opisWizyty;
	}
	
	public List<OpisWizyty> getListaOpisowWizyt(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<OpisWizyty> list = em.createNamedQuery("OpisWizyty.findAll").getResultList();
		em.close();
		return list;
	}
	
	public OpisWizyty getOpisWizytyPoWizycie(Wizyta wizyta){
		OpisWizyty opisWizyty =null;
		try{
			EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			Query query1 = em.createNamedQuery("OpisWizyty.getByWizyta");
			query1.setParameter(1, wizyta);
			opisWizyty = (OpisWizyty) query1.getSingleResult();
			return opisWizyty;
		} catch (NoResultException e) {
			System.out.println("Nie znaleziono obiektu OpisWizyty dla podanych pramaterów. Zwaracam null");
			return null;
		}
	}
	
	public void edytuj(OpisWizyty opisWizyty) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		em.merge(opisWizyty);
		em.getTransaction().commit();
		em.close();
		this.opisWizyty = new OpisWizyty();
	}
	
	public void dodaj(OpisWizyty opisWizyty)  {
		EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			em.persist(opisWizyty);
			em.getTransaction().commit();
			this.opisWizyty = new OpisWizyty();
			em.close();
	}
	
}
