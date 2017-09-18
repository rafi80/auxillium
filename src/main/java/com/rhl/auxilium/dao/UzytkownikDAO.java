package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.Uzytkownik;

public class UzytkownikDAO {

	private Uzytkownik uzytkownik = new Uzytkownik();	
	
	public UzytkownikDAO() {
	}
	
	public Uzytkownik getUzytkownik(){
		return uzytkownik;
	}
	
	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}
	
	static Logger log = Logger.getLogger(UzytkownikDAO.class);
	
	public List<Uzytkownik> getListaUzytkownikow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Uzytkownik> list = em.createNamedQuery("Uzytkownik.findAll").getResultList();
		em.close();
		return list;
	}
	
	public Uzytkownik zaladujDoEdycji(int id){
		EntityManager	em = DBManager.getManager().createEntityManager();
		this.uzytkownik = em.find(Uzytkownik.class,id);
		em.close();
		return this.uzytkownik;
	}
	
	public void edytuj(Uzytkownik uzytkownik) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		em.merge(uzytkownik);
		em.getTransaction().commit();
		em.close();
		this.uzytkownik = new Uzytkownik();
	}
	
	public void usun(int idDoUsuniecia) {
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.deleteById");
		query1.setParameter(1, idDoUsuniecia);
		int deletedData=query1.executeUpdate();
	    System.out.println(deletedData +" record is deleted.");
		em.getTransaction().commit();
		em.close();
		this.uzytkownik = new Uzytkownik();
	}
	
	public void dodaj(Uzytkownik uzytkownik)  {
		EntityManager	em = DBManager.getManager().createEntityManager();
			em.getTransaction().begin();
			em.persist(uzytkownik);
			em.getTransaction().commit();
			this.uzytkownik = new Uzytkownik();
			em.close();
	}
		
	
	
	public List<Uzytkownik> getListaUzytkownikowPoPeselu(String pesel){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.findByPesel");
		query1.setParameter(1, pesel+'%');
		@SuppressWarnings("unchecked")
		List<Uzytkownik> list=query1.getResultList();
		em.close();
		return list;
	}
	
	public Uzytkownik getUzytkownikPoLoginie(String login){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.findByLogin");
		query1.setParameter(1, login);
		try {
			Uzytkownik znalezionyUzytkownik=(Uzytkownik)query1.getSingleResult();
			System.out.println("Zwracam usera.");
		em.close();
		return znalezionyUzytkownik; 
		} catch (NoResultException e) {}
		System.out.println("Zwracam nulla.");
		return null;
	}
	
	public String getHasloPoId(int id){
		String haslo ="";
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.findPasswordById");
		query1.setParameter(1, id);
		haslo = (String) query1.getSingleResult();
		return haslo;
	}
	
	public List<Uzytkownik> getListaPacjentow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
//		em.getTransaction().begin();
//		Query query1 = em.createNamedQuery("Uzytkownik.findAllPatients");
//		query1.setParameter(1, id);
		@SuppressWarnings("unchecked")
//		List<Uzytkownik> list = query1.getResultList();
		List<Uzytkownik> list = em.createNamedQuery("Uzytkownik.findAllPatients").getResultList();
		em.close();
		return list;
	}

	public List<Uzytkownik> getListaAktywnychLekarzy(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Uzytkownik> list = em.createNamedQuery("Uzytkownik.findAllActiveDocs").getResultList();
		em.close();
		return list;
	}
	
	public List<Uzytkownik> getListaAktywnychPacjentow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<Uzytkownik> list = em.createNamedQuery("Uzytkownik.findAllActivePatients").getResultList();
		em.close();
//		System.out.println(list);
		return list;
	}
	
	
	public List<Uzytkownik> getListaUzytkownikowPoNazwisku(String nazwisko){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.findByLastName");
		query1.setParameter(1, nazwisko+'%');
		@SuppressWarnings("unchecked")
		List<Uzytkownik> list=query1.getResultList();
		em.close();
		return list;
	}
	
	
	
	public List<Uzytkownik> getListaLekarzyPoIdSpecjalizacji(Integer idSpecjalizacji){
		EntityManager	em = DBManager.getManager().createEntityManager();
		em.getTransaction().begin();
		Query query1 = em.createNamedQuery("Uzytkownik.findDocsBySepcialtyId");
		query1.setParameter(1, idSpecjalizacji);
		try {
			@SuppressWarnings("unchecked")
			List<Uzytkownik> list=query1.getResultList();
			em.close();
			System.out.println("Zwracam liste:" + list);
			return list;			
		} catch (NoResultException e) {}
		System.out.println("Zwracam nulla.");
		return null;
	}
}
