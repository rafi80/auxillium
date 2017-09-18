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

import com.rhl.auxilium.entities.Uzytkownik;

/**
 * Servlet implementation class GabinetDodaj
 */

@WebServlet("/GoHome.do")
public class GoHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoHome() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		Uzytkownik curUser = (Uzytkownik) session.getAttribute("uzytkownik");
		
		try{	
				if (curUser.getCzyPracownik()!=1) {
					request.getRequestDispatcher("panelPacjenta.jsp").forward(request, response);
				} else if (curUser.getTypPracownika() == 1) {
					request.getRequestDispatcher("panelAdministratora.jsp").forward(request, response);					
				} else if (curUser.getTypPracownika() == 2) {
					request.getRequestDispatcher("panelRecepcjonisty.jsp").forward(request, response);
				} else if (curUser.getTypPracownika() == 3) {
					request.getRequestDispatcher("panelLekarza.jsp").forward(request, response);
				}
			
				
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
