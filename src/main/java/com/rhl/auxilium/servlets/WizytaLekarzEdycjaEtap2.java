package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.entities.Wizyta;


@WebServlet("/PrzejdzDoEtapu2Lekarz.do")
public class WizytaLekarzEdycjaEtap2 extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WizytaLekarzEdycjaEtap2() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static Logger log = Logger.getLogger(WizytaLekarzEdycjaEtap2.class);
	List<String> errors = new ArrayList<String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		request.setAttribute("errors", errors);
		
		try{
				String pobranaData = request.getParameter("wybranaData");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date aktualnaData = sdf.parse(pobranaData);
				
				HttpSession session = request.getSession();
				Uzytkownik zalogowanyLekarz = (Uzytkownik) session.getAttribute("uzytkownik");
								
				WizytaDAO wizytaDAO = new WizytaDAO();
				System.out.println("pobranaData: " + pobranaData);
				System.out.println("aktualnaData: " + aktualnaData);
				
				List<Wizyta> listaWizytPoDacieIGabinecie = wizytaDAO.getListaWizytPoDacieILekarzu(aktualnaData, zalogowanyLekarz);
				System.out.println(listaWizytPoDacieIGabinecie);
				
				session.setAttribute("listaWizyt", listaWizytPoDacieIGabinecie);
				
				request.setAttribute("zrodlo","panelLekarza"); 
				request.setAttribute("edytowanaData", request.getParameter("wybranaData"));
				//request.setAttribute("listaWizyt",listaWizytPoDacieIGabinecie);
				
				request.getRequestDispatcher("edycjaWizyty-Lekarz-etap2.jsp").forward(request, response);
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
