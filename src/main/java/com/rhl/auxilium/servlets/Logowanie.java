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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.rhl.auxilium.dao.UzytkownikDAO;
import com.rhl.auxilium.entities.Uzytkownik;

@WebServlet("/Logowanie.do")
public class Logowanie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logowanie() {
        super();
        // TODO Auto-generated constructor stub
    }

	static Logger log = Logger.getLogger(Logowanie.class);
	List<String> errors = new ArrayList<String>();
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	PrintWriter out = response.getWriter();
    	//List<String> errors = new ArrayList<String>();
    	request.setAttribute("errors", errors);
		
		try{	
			
			HttpSession session = request.getSession();
			
			// jezeli uzytkownik jest juz zalogowany, przekieruj na odpowiednie panel w zaleznosci od posiadanej 
			// roli
			if (session.getAttribute("uzytkownik") != null) {
				request.getRequestDispatcher("GoHome.do").forward(request, response);
				return;
			}
			
			
			//pobierz uzytkownika o podanym loginie
			String podanyLogin = (String)request.getParameter("login");
			String podaneHaslo = (String)request.getParameter("haslo");
			UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
			
			//Autoryzacja 
			Uzytkownik pobranyZBazyUzytkownik = new Uzytkownik();
			pobranyZBazyUzytkownik = (Uzytkownik)uzytkownikDAO.getUzytkownikPoLoginie(podanyLogin);
			
			//Przy pomocy metody wykonajAutoryzacje wykonujemy autoryzacje i w zaleznosci
			//od jej wyniku przekierowujemy wywolanie w aplikacji albo z powrotem na
			//ekran logowania (blad przy logowaniu) albo do wlasciwego panelu uzytkownika
			request.getRequestDispatcher(wykonajAutoryzacje(pobranyZBazyUzytkownik, podaneHaslo, session)).forward(request, response);
				
				
		} finally {
			out.close();
			errors.clear();
		}
	}
    
    private String wykonajAutoryzacje(Uzytkownik uzytkownikDoAutoryzacji, String podaneHaslo, HttpSession session) {
		
		//1. sprawdź, czy uzytkownik istnieje. Jezeli nie - wyslij komunikat, jezeli tak, kontynuuj
				if (uzytkownikDoAutoryzacji == null) {
					errors.add("Nie znaleziono U&zdot;ytkownika o podanym loginie.");
					return("logowanie.jsp");
				} 
			
				//2. sprawdz, czy podane haslo jest prawidlowe
				if (!uzytkownikDoAutoryzacji.czyZgodneHaslo(podaneHaslo)) {
					errors.add("Niepoprawne has&lstrok;o.");
					return("logowanie.jsp");
				}
				
				//3. Sprawdz, czy uzytkownik jest aktywny
				if (uzytkownikDoAutoryzacji.getCzyAktywny()==0) {
					errors.add("Konto U&zdot;ytkownika jest niaktywne. Skontaktuj si&eogon; z administratorem.");
					return("logowanie.jsp");
				}
				
				//Jezeli uzytkownik istniej, haslo sie zgadza i uzytkownik jest aktywny >> udane logowanie
				//zapisz uzytkownika do sesji i wyslij do do wlasciwego panelu
				session.setAttribute("uzytkownik", uzytkownikDoAutoryzacji);
				//przekierujDoPaneluDomowego(request);
				return("GoHome.do");
	}
    
    // metoda do bezposredniego wywolania servletu z servletu
    // jest uzyta do przekierowania uzytkownika do wlasciwego panelu, jezeli jest juz zalogowany
/*    public String przekierujDoPaneluDomowego(HttpServletRequest request) {
    	return "GoHome.do";    
    }*/

/*    public String przekierujDoFormularzaLogowania(HttpServletRequest request) {
    	return "LogowaniePrzetworzRequest.do";    
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
