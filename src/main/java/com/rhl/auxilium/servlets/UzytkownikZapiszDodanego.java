package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rhl.auxilium.dao.UzytkownikDAO;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.utils.Hasher;

@WebServlet("/ZapiszDodawanegoUzytkownika.do")
public class UzytkownikZapiszDodanego extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UzytkownikZapiszDodanego() {
        super();
        // TODO Auto-generated constructor stub
    }

	static Logger log = Logger.getLogger(UzytkownikZapiszDodanego.class);
	List<String> errors = new ArrayList<String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		request.setAttribute("errors", errors);
		
		try{	
				request.setAttribute("zrodlo",request.getParameter("zrodlo")); 
				
				Uzytkownik uzytkownik = new Uzytkownik();
				
				uzytkownik.setImie((String)request.getParameter("imie"));
				uzytkownik.setNazwisko((String)request.getParameter("nazwisko"));
				uzytkownik.setDrugieImie((String)request.getParameter("drugieImie"));
				uzytkownik.setPesel((String)request.getParameter("pesel"));
				uzytkownik.setNumerTelefonu((String)request.getParameter("numerTelefonu"));
				uzytkownik.setAdres((String)request.getParameter("adres"));
				uzytkownik.setEmail((String)request.getParameter("email"));
				
				if ((String)request.getParameter("czyPracownik")!=null || (String)request.getParameter("czyPracownik")=="") {
					uzytkownik.setCzyPracownik(Byte.parseByte( request.getParameter("czyPracownik")));
				} else {
					uzytkownik.setCzyPracownik(Byte.parseByte("0"));
				}
				
				
				if ((String)request.getParameter("typPracownika")!=null || (String)request.getParameter("typPracownika")=="") {
					uzytkownik.setTypPracownika(Integer.parseInt((String)request.getParameter("typPracownika")));
				} else {
					uzytkownik.setTypPracownika((Integer) 0);
				}
				
				if ((String)request.getParameter("specjalizacja")!=null || (String)request.getParameter("specjalizacja")=="") {
					uzytkownik.setSpecjalizacja(Integer.parseInt((String)request.getParameter("specjalizacja")));
				} else {
					uzytkownik.setSpecjalizacja((Integer) 0);
				}
				
				if ((String)request.getParameter("czyAktywny")!=null || (String)request.getParameter("czyAktywny")=="") {
					uzytkownik.setCzyAktywny(Byte.parseByte(request.getParameter("czyAktywny")));
				} else {
					uzytkownik.setCzyAktywny(Byte.parseByte("0"));
				}			
				
				uzytkownik.setLogin((String)request.getParameter("login"));
				
				String podanyLogin = ((String)request.getParameter("login"));
				
				Hasher hasher = new Hasher();
				uzytkownik.setHaslo(hasher.hashuj(((String)request.getParameter("haslo"))));
				System.out.println(uzytkownik.toString());

				request.getRequestDispatcher(wykonajZapis(uzytkownik, podanyLogin)).forward(request, response);
				//request.getRequestDispatcher("wyswietlUzytkownikow.jsp").forward(request, response);
		} finally {
			out.close();
		}
	}
    
    private String wykonajZapis(Uzytkownik uzytkownikDoZapisu, String podanyLogin) {
    			UzytkownikDAO uzytkownikDoEdycji = new UzytkownikDAO();
  				//1. sprawdź, czy login juz istnieje. Jezeli tak - wyslij komunikat, jezeli nie, kontynuuj
  				
    			try {
    				if ((Integer)uzytkownikDoEdycji.getUzytkownikPoLoginie(podanyLogin).getId()!=null) {
    					errors.add("Istnieje ju&zdot; konto U&zdot;ytkownika o podanym loginie. Wybierz inny login.");
    					return("dodawanieUzytkownikow.jsp");
    				} 
    			} catch (NullPointerException e) {
    				//nie rob nic
    			}
  				
  				//Jezeli nie istnieje uzytkownik o tym samym loginie >> udane dodanie
  				//zapisz uzytkownika do sesji i wyslij do do wlasciwego panelu
  				
  				uzytkownikDoEdycji.dodaj(uzytkownikDoZapisu);
  				//session.setAttribute("uzytkownik", uzytkownikDoZapisu);
  				//przekierujDoPaneluDomowego(request);
  				return("wyswietlUzytkownikow.jsp");
  	}
    
/*    private String zahashujHaslo (String haslo) {
    	String hasloDoZapisu = "";
    	
    	// pobierz i zhashuj pierwsze haslo
    	
    	// pobierz i zhashuj powtorzne haslo
    	
    	// jezeli sa zhashowane sa zgodne, zwroc haslo do zapisu    	
    	return hasloDoZapisu;
    	
    	// w przeciwnym wypadku rzuc wyjatek i przekaz komunikat do GUI
    }*/
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
