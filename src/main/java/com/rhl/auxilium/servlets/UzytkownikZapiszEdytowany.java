package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rhl.auxilium.dao.UzytkownikDAO;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.utils.Hasher;


@WebServlet("/ZapiszEdytowanegoUzytkownika.do")
public class UzytkownikZapiszEdytowany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UzytkownikZapiszEdytowany() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static Logger log = Logger.getLogger(UzytkownikZapiszEdytowany.class);
   	List<String> errors = new ArrayList<String>();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try{	
				//int idDoEdycji = Integer.parseInt(request.getParameter("edytowany"));
				request.setAttribute("zrodlo",request.getParameter("zrodlo")); 
				UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
				Uzytkownik uzytkownik = new Uzytkownik();
				
				System.out.println("Edytowane id: " +request.getParameter("edytowaneID"));
				
				uzytkownik.setId(Integer.parseInt(request.getParameter("edytowaneID")));
				uzytkownik.setImie(request.getParameter("imie"));
				uzytkownik.setNazwisko((String)request.getParameter("nazwisko"));
				uzytkownik.setDrugieImie(request.getParameter("drugieImie"));
				uzytkownik.setPesel(request.getParameter("pesel"));
				uzytkownik.setNumerTelefonu(request.getParameter("numerTelefonu"));
				uzytkownik.setAdres(request.getParameter("adres"));
				uzytkownik.setEmail(request.getParameter("email"));
				
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
				
				if ((String)request.getParameter("login")!=null || (String)request.getParameter("login")=="") {
					uzytkownik.setLogin((String)request.getParameter("login"));
				};
				
				
				if (request.getParameter("haslo").equals(uzytkownikDAO.getHasloPoId(uzytkownik.getId()))) {
					//jezeli haslo sie nie zmienilo, to go nie hashujemy
					uzytkownik.setHaslo((String)request.getParameter("haslo"));					
				} else if ((String)request.getParameter("haslo")!=null || (String)request.getParameter("haslo")=="") {
					Hasher hasher = new Hasher();
					uzytkownik.setHaslo(hasher.hashuj(((String)request.getParameter("haslo"))));
				}
			
				System.out.println("Dane do update'u  uzytkownika: " + uzytkownik.toString());
				
				uzytkownikDAO.edytuj(uzytkownik);
				request.getRequestDispatcher("wyswietlUzytkownikow.jsp").forward(request, response);
		} catch (RollbackException e) {
			System.out.println("A to feler: " + e.getMessage());
			errors.add("Wystpi&lstrok; b&lstrok;&aogon;d przy zmianie danych:");
			errors.add("W systemie istnieje ju&zdot; podany login. Prosz&eogon; u&zdot;y&cacute; innego.");
			//errors.add(e.toString());
			StringWriter bledy = new StringWriter();
			//e.printStackTrace(new PrintWriter(bledy));
			request.setAttribute("stacktrace", bledy.toString());
			request.getRequestDispatcher("ShowError.jsp").forward(request, response);
		} finally {
			out.close();
		}
	}
   
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
