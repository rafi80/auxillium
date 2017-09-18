package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rhl.auxilium.dao.SpecjalizacjaDAO;
import com.rhl.auxilium.dao.UzytkownikDAO;
import com.rhl.auxilium.dao.WizytaDAO;
import com.rhl.auxilium.entities.Specjalizacja;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.entities.Wizyta;


@WebServlet("/WyszukajWizytyPoSpecjalizacjiLekarza.do")
public class WizytaWyswietlPoSpecjalizacji extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WizytaWyswietlPoSpecjalizacji() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static Logger log = Logger.getLogger(WizytaPacjentEdycjaEtap2.class);
	List<String> errors = new ArrayList<String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		request.setAttribute("errors", errors);
		
		try{
				String pobranaData = request.getParameter("edytowanaData");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date aktualnaData = sdf.parse(pobranaData);
				
				Integer idSpecjalizacji =  Integer.parseInt((String)request.getParameter("specjalizacja"));
								
				HttpSession session = request.getSession();
				Uzytkownik pacjent = (Uzytkownik) session.getAttribute("uzytkownik");
				
				Specjalizacja specjalizacja = new Specjalizacja();
				SpecjalizacjaDAO specjalizacjaDAO = new SpecjalizacjaDAO();
				String nazwaSpecjalizacji = "";
				
				specjalizacja = specjalizacjaDAO.getSpecjalizacjaPoId(idSpecjalizacji);
				if (specjalizacja!=null) {
					nazwaSpecjalizacji = specjalizacja.getNazwaSpecjalizacji();
				} else {
					nazwaSpecjalizacji = "Specjalizacja nie jest filtrowana";
				}
				
				WizytaDAO wizytaDAO = new WizytaDAO();
				List<Wizyta> listaWizyt = null;
				List<Uzytkownik> listaLekarzyPoSpecjalizacji = null;
				UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
				
				
				if (idSpecjalizacji!=0){
					listaLekarzyPoSpecjalizacji = uzytkownikDAO.getListaLekarzyPoIdSpecjalizacji(idSpecjalizacji);
					if (listaLekarzyPoSpecjalizacji.isEmpty() == false) {
						listaWizyt = wizytaDAO.getListaWolnychWizytPoDaciePacjencieILekarzach(aktualnaData, pacjent, listaLekarzyPoSpecjalizacji) ; 
						System.out.println("Lista wizyt z DAO: "+ listaWizyt);
						sortWizytyPoBlokachCzasu(listaWizyt);
						// posortuj liste wizyt po bloku czasu
						
					} 
				} else {
					listaWizyt = wizytaDAO.getListaWolnychWizytPoDacieIPacjencie(aktualnaData, pacjent);
					sortWizytyPoBlokachCzasu(listaWizyt);
				}
				
					
				
			
				request.setAttribute("zrodlo","panelPacjenta"); 
				request.setAttribute("edytowanaData", request.getParameter("edytowanaData"));
				System.out.println("Przeslana data: " + request.getParameter("edytowanaData"));
				request.setAttribute("listaWizyt",listaWizyt);
				request.setAttribute("specjalizacja", nazwaSpecjalizacji);
				System.out.println("Przeslana lista Wizyt: " + listaWizyt);
				request.getRequestDispatcher("edycjaWizyty-Pacjent-etap2.jsp").forward(request, response);
		} finally {
			out.close();
		}
	}
    
    public void sortWizytyPoBlokachCzasu (List<Wizyta> listaDoSortowania) {
    	Comparator<Wizyta> cmp = new Comparator<Wizyta>() {
    	      public int compare(Wizyta w1, Wizyta w2) {
    	        return Integer.valueOf(w1.getBlokCzasu().getId()).compareTo(w2.getBlokCzasu().getId());
    	      }
    	      };
    	Collections.sort(listaDoSortowania, cmp);
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
		try {
			processRequest(request, response);
		} catch (ParseException e) {
			System.out.println("A to feler: " + e.getMessage());
			errors.add("Wystpi&lstrok; b&lstrok;&aogon;d przy parsowaniu daty:");
			errors.add(e.toString());
			StringWriter bledy = new StringWriter();
			e.printStackTrace(new PrintWriter(bledy));
			request.setAttribute("stacktrace", bledy.toString());
			request.getRequestDispatcher("ShowError.jsp").forward(request, response);
		}
	}

}
