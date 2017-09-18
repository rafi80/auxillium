package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.Gabinet;

public class GabinetDAO {

	private Gabinet gabinet = new Gabinet();	
	
	public GabinetDAO() {
	}
	
	public Gabinet getGabinet(){
		return gabinet;
	}
	
	public void setGabinet(Gabinet gabinet) {
		this.gabinet = gabinet;
	}
	
	public List<Gabinet> getListaGabinetow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Gabinet> list = em.createNamedQuery("Gabinet.findAllActive").getResultList();
		em.close();
		return list;
	}
	public Gabinet zaladujDoEdycji(int id){
		EntityManager	em = DBManager.getManager().createEntityManager();
		this.gabinet = em.find(Gabinet.class,id);
		em.close();
		return this.gabinet;
	}
	
	public void edytuj(Gabinet gabinet) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		em.merge(gabinet);
		em.getTransaction().commit();
		em.close();
		this.gabinet = new Gabinet();

	}
	
	public void usun(int idDoUsuniecia) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Gabinet.deleteById");
		query1.setParameter(1, idDoUsuniecia);
//		query1.executeUpdate();
		int deletedData=query1.executeUpdate();
	    System.out.println(deletedData +" record is deleted.");
//		em.remove(gabinet);
		em.getTransaction().commit();
		em.close();
		this.gabinet = new Gabinet();

	}
	
	public void zdezaktywuj(int idDoDeaktywacji) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Gabinet.disactivateById");
		query1.setParameter(1, idDoDeaktywacji);
//		query1.executeUpdate();
		int disactivatedData=query1.executeUpdate();
	    System.out.println(disactivatedData +" record is disactivated.");
//		em.remove(gabinet);
		em.getTransaction().commit();
		em.close();
		this.gabinet = new Gabinet();

	}
	
	public void dodaj(Gabinet gabinet) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		em.persist(gabinet);
		em.getTransaction().commit();
		em.close();
		this.gabinet = new Gabinet();
	}
	
	public List<Gabinet> getListaGabinetowPoNumerze(String numer){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Gabinet.findByNumer");
		query1.setParameter(1, numer+'%');
		@SuppressWarnings("unchecked")
		List<Gabinet> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public String zwrocNumerPoId(int id){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Gabinet.findNumerById");
		query1.setParameter(1, id);
		String opis =(String) query1.getSingleResult();
		em.close();
		return opis;
	}
	

}
