package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;



import com.rhl.auxilium.dao.OpisWizytyDAO;
import com.rhl.auxilium.dao.WizytaDAO;
import com.rhl.auxilium.entities.OpisWizyty;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.entities.Wizyta;

@WebServlet("/EdytujOpisWizytyLekarz.do")
public class OpisWizytyLekarzEdytuj extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpisWizytyLekarzEdytuj() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static Logger log = Logger.getLogger(OpisWizytyLekarzEdytuj.class);
	List<String> errors = new ArrayList<String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		request.setAttribute("errors", errors);
		
		try{	
				HttpSession session = request.getSession();
				Uzytkownik edytujacy = (Uzytkownik) session.getAttribute("uzytkownik");
				String opis = request.getParameter("opis");
				Integer idWizyta = Integer.parseInt((String) request.getParameter("idWizyta")) ;
				Integer idPacjenta = Integer.parseInt((String)request.getParameter("idPacjenta"));
				
				Wizyta wizyta = new Wizyta();
				WizytaDAO wizytaDAO = new WizytaDAO();
				
				wizyta = wizytaDAO.getWizytaPoId(idWizyta);
				
				OpisWizyty opisWizyty = new OpisWizyty();
				OpisWizytyDAO opisWizytyDAO = new OpisWizytyDAO();
				
				
				opisWizyty.setOpis(opis);
				opisWizyty.setWizyta(wizyta);
				
				// jezeli dla wizyty istniej juz opis, zmodyfikuje ten opis
				if (opisWizytyDAO.getOpisWizytyPoWizycie(wizyta)!=null) {
					opisWizyty.setId(opisWizytyDAO.getOpisWizytyPoWizycie(wizyta).getId());
					opisWizyty.setStworzonyPrzezUzytkownika(opisWizytyDAO.getOpisWizytyPoWizycie(wizyta).getStworzonyPrzezUzytkownika());
					opisWizyty.setZmodyfikowanyPrzezUzytkownika(edytujacy);
					opisWizytyDAO.edytuj(opisWizyty);					
				// jezeli opis nie istnieje, dodaj nowy opis z parametrami: 
				//	opis, stworzony_podczas (wizyta) oraz stworzony_przez 
				}else{
					opisWizyty.setStworzonyPrzezUzytkownika(edytujacy);
					opisWizytyDAO.dodaj(opisWizyty);
				}
		
				
				
				request.setAttribute("zrodlo","panelLekarza"); 
				request.setAttribute("dataWizyty", request.getParameter("dataWizyty"));
				request.setAttribute("idPacjenta", idPacjenta);
				request.setAttribute("idWizyta",request.getParameter("idWizyta"));
				request.setAttribute("wizyta",wizyta); 
				request.getRequestDispatcher("opisWizyty.jsp").forward(request, response);
								
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
