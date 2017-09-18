package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.TypPracownika;

public class TypPracownikaDAO {

	private TypPracownika typPracownika = new TypPracownika();	
	
	public TypPracownikaDAO() {
	}
	
	public TypPracownika getTypPracownika(){
		return typPracownika;
	}
	
	public void setTypPracownika(TypPracownika typPracownika) {
		this.typPracownika = typPracownika;
	}
	
	public List<TypPracownika> getListaTypowPracownikow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<TypPracownika> list = em.createNamedQuery("TypPracownika.findAll").getResultList();
		em.close();
		return list;
	}
	
}
