package com.rhl.auxilium.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class Hasher
 */

public class Hasher {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hasher() {
        // TODO Auto-generated constructor stub
    }
    
    
    
    public String hashuj(String dane) throws ServletException {
    	try {
    		MessageDigest m = MessageDigest.getInstance("SHA-1");
    		byte[] bufor = dane.getBytes();
    		m.update(bufor,0,bufor.length);
    		BigInteger hash = new BigInteger(1, m.digest());
//    		return String.format("%1$032X", hash);    
    		return String.format("%1$032X", hash);  
    	} catch (NoSuchAlgorithmException nsae) {
    		nsae.printStackTrace();
    	return null;
    	}
    }    
    
}
