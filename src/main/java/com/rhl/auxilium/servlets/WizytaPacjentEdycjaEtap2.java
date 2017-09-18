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

import com.rhl.auxilium.dao.WizytaDAO;
import com.rhl.auxilium.entities.Wizyta;
import com.rhl.auxilium.entities.Uzytkownik;


@WebServlet("/PrzejdzDoEtapu2Pacjent.do")
public class WizytaPacjentEdycjaEtap2 extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WizytaPacjentEdycjaEtap2() {
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
				
				HttpSession session = request.getSession();
				Uzytkownik pacjent = (Uzytkownik) session.getAttribute("uzytkownik");
				
								
				WizytaDAO wizytaDAO = new WizytaDAO();
				
				List<Wizyta> listaWizytPacjentaIWizytWolnychPoDacie = wizytaDAO.getListaWolnychWizytPoDacieIPacjencie(aktualnaData, pacjent);
				System.out.println("Lista wizyt z DAO: "+ listaWizytPacjentaIWizytWolnychPoDacie);
				
				// posortuj liste wizyt po bloku czasu
				sortWizytyPoBlokachCzasu(listaWizytPacjentaIWizytWolnychPoDacie);				
				
				request.setAttribute("zrodlo","panelPacjenta"); 
				request.setAttribute("edytowanaData", request.getParameter("edytowanaData"));
				System.out.println("Przeslana data: " + request.getParameter("edytowanaData"));
				request.setAttribute("listaWizyt",listaWizytPacjentaIWizytWolnychPoDacie);
				System.out.println("Przeslana lista Wizyt: " + listaWizytPacjentaIWizytWolnychPoDacie);
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
