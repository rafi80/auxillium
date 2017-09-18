package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Hasher
 */
@WebServlet("/WrocDoPanelu.do")
public class WrocDoServlet extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WrocDoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			String zrodlo = request.getParameter("zrodlo");
			System.out.println(zrodlo);
			//Sterowanie ekranami do powrotu
			if (zrodlo.equals("panelAdministratora")) {
				request.getRequestDispatcher("panelAdministratora.jsp").forward(request, response);
			} else if(zrodlo.equals("panelRecepcjonisty")) { 
				request.getRequestDispatcher("panelRecepcjonisty.jsp").forward(request, response);
			} else if(zrodlo.equals("panelLekarza")) { 
				request.getRequestDispatcher("panelLekarza.jsp").forward(request, response);
			} else if(zrodlo.equals("panelPacjenta")) { 
				request.getRequestDispatcher("panelPacjenta.jsp").forward(request, response);
			}
			try {
     			out.println("<html>");
				out.println("<head>");
				out.println("<title>O, nie! Nie znaleziono strony!</title>");
				out.println("<style type='text/css'> body {background-color: white;}"
						+ "div { width: 600px;"
						+"margin-left: auto;"
						+"margin-right: auto;"
						+ "display: block;}"
						+ " </style> ");			    
				out.println("</head>");
				out.println("<body>");
				out.println("<div>");
				out.println("<a href='panelAdministratora.jsp'><img src='images/Rocket-404-Error-Page.jpg'></a>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

			} finally {
				out.close();
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
