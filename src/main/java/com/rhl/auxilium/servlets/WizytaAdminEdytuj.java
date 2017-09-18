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

import org.apache.log4j.Logger;

import com.rhl.auxilium.dao.BlokCzasuDAO;
import com.rhl.auxilium.dao.GabinetDAO;
import com.rhl.auxilium.dao.UzytkownikDAO;
import com.rhl.auxilium.dao.WizytaDAO;
import com.rhl.auxilium.entities.BlokCzasu;
import com.rhl.auxilium.entities.Gabinet;
import com.rhl.auxilium.entities.Uzytkownik;
import com.rhl.auxilium.entities.Wizyta;




@WebServlet("/EdytujWizyte.do")
public class WizytaAdminEdytuj extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WizytaAdminEdytuj() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static Logger log = Logger.getLogger(WizytaAdminEdytuj.class);
	List<String> errors = new ArrayList<String>();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		request.setAttribute("errors", errors);
		
		try{
				Integer idGabinetu = Integer.parseInt(request.getParameter("idGabinetu")) ;
				String pobranaData = request.getParameter("edytowanaData");
				Integer idLekarz = Integer.parseInt(request.getParameter("lekarz")) ;
				Integer idBlokCzasu=  Integer.parseInt(request.getParameter("blokCzasu")) ;
				
				Wizyta wizyta = new Wizyta();
				
				Gabinet gabinet = new Gabinet();
				GabinetDAO gabinetDAO = new GabinetDAO();
				gabinet = gabinetDAO.zaladujDoEdycji(idGabinetu);
				wizyta.setGabinet(gabinet);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date aktualnaData = sdf.parse(pobranaData);
				wizyta.setDataWizyty(aktualnaData);
				
				Uzytkownik lekarz = new Uzytkownik();
				UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
				lekarz = uzytkownikDAO.zaladujDoEdycji(idLekarz);
				wizyta.setLekarz(lekarz);
				
				BlokCzasu blokCzasu = new BlokCzasu();
				BlokCzasuDAO blokCzasuDAO = new BlokCzasuDAO();
				blokCzasu = blokCzasuDAO.zaladujDoEdycji(idBlokCzasu);
				wizyta.setBlokCzasu(blokCzasu);
				
				WizytaDAO wizytaDAO = new WizytaDAO();
				
				if (idLekarz != 0) {
					//System.out.println("Oto moja wizyta do zapisania na bazie: " + wizyta.toString());
					Integer idDoEdycji = wizytaDAO.getIdWizytyPoDacieGabinecieIBlokuCzasu(gabinet,aktualnaData,blokCzasu);
					if (idDoEdycji!=null){
						wizyta.setId(idDoEdycji);
						wizytaDAO.edytuj(wizyta);
					} else {
						wizytaDAO.dodaj(wizyta);
					}
				} else {
					System.out.println("Kasuje wizyte o id: " + wizytaDAO.getIdWizytyPoDacieGabinecieIBlokuCzasu(gabinet,aktualnaData,blokCzasu));
					wizytaDAO.usun(wizytaDAO.getIdWizytyPoDacieGabinecieIBlokuCzasu(gabinet,aktualnaData,blokCzasu));
				}
												
				List<Wizyta> listaWizytPoDacieIGabinecie = wizytaDAO.getListaWizytPoDacieIGabinecie(gabinet,aktualnaData);
				System.out.println(listaWizytPoDacieIGabinecie);
				
				request.setAttribute("listaWizyt",listaWizytPoDacieIGabinecie);
				request.setAttribute("zrodlo","panelAdministratora"); 
				request.setAttribute("edytowanaData", request.getParameter("edytowanaData"));
				request.setAttribute("idGabinetu", request.getParameter("idGabinetu"));
				request.getRequestDispatcher("edycjaWizyty-Administrator-etap2.jsp").forward(request, response);
				
				
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
