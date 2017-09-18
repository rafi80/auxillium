package com.rhl.auxilium.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.rhl.auxilium.conf.DBManager;
import com.rhl.auxilium.entities.BlokCzasu;

public class BlokCzasuDAO {

	private BlokCzasu blokCzasu = new BlokCzasu();	
	
	public BlokCzasuDAO() {
	}
	
	public BlokCzasu getBlokCzasu(){
		return blokCzasu;
	}
	
	public void setBlokCzasu(BlokCzasu blokCzasu) {
		this.blokCzasu = blokCzasu;
	}
	
	static Logger log = Logger.getLogger(BlokCzasuDAO.class);
	
	public List<BlokCzasu> getListaAktywnychBlokow(){
		EntityManager	em = DBManager.getManager().createEntityManager();
		@SuppressWarnings("unchecked")
		List<BlokCzasu> list = em.createNamedQuery("BlokCzasu.findAllActive").getResultList();
		em.close();
		return list;
	}
	
	public BlokCzasu zaladujDoEdycji(int id){
		EntityManager	em = DBManager.getManager().createEntityManager();
		this.blokCzasu = em.find(BlokCzasu.class,id);
		em.close();
		return this.blokCzasu;
	}
	
}
