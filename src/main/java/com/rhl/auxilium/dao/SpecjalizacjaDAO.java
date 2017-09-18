package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.Specjalizacja;

public class SpecjalizacjaDAO {

	private Specjalizacja specjalizacja = new Specjalizacja();	
	
	public SpecjalizacjaDAO() {
	}
	
	public Specjalizacja getSpecjalizacja(){
		return specjalizacja;
	}
	
	public void setSpecjalizacja(Specjalizacja specjalizacja) {
		this.specjalizacja = specjalizacja;
	}
	
	public List<Specjalizacja> getListaSpecjalizacji(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Specjalizacja> list = em.createNamedQuery("Specjalizacja.findAll").getResultList();
		em.close();
		return list;
	}
	
	public String getNazwaSpecjalizacjiPoId(int id){
		String nazwa ="";
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Specjalizacja.findNazwaById");
		query1.setParameter(1, id);
		nazwa = (String) query1.getSingleResult();
		return nazwa;
	}
	
	public Specjalizacja getSpecjalizacjaPoId(int id){
		Specjalizacja specjalizacja = null;
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Specjalizacja.findById");
		query1.setParameter(1, id);
		try{
			specjalizacja = (Specjalizacja) query1.getSingleResult();
			return specjalizacja;
		} catch (NoResultException e) {}
		System.out.println("Zwracam nulla.");
		return null;
	}
}
