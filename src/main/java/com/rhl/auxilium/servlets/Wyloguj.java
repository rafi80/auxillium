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

@WebServlet("/Wyloguj.do")
public class Wyloguj extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Wyloguj() {
        super();
        // TODO Auto-generated constructor stub
    }

	static Logger log = Logger.getLogger(Wyloguj.class);
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
			
			session.setAttribute("uzytkownik", null);
			request.getRequestDispatcher("logowanie.jsp").forward(request, response);
				
				
		} finally {
			out.close();
			errors.clear();
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
