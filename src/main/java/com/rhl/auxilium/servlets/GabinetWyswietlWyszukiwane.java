package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rhl.auxilium.dao.GabinetDAO;
import com.rhl.auxilium.entities.Gabinet;


@WebServlet("/WyszukajGabinet.do")
public class GabinetWyswietlWyszukiwane extends HttpServlet {
	private static final long serialVersionUID = 12L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GabinetWyswietlWyszukiwane() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{	
				String numerDoWyszukania =request.getParameter("numerGabinetu");
				request.setAttribute("zrodlo",request.getParameter("zrodlo")); 
				GabinetDAO gabinetDAO = new GabinetDAO();
				List<Gabinet> listaZnalezionychGabinetow =  gabinetDAO.getListaGabinetowPoNumerze(numerDoWyszukania);
				request.setAttribute("listaZnalezionychGabinetow",listaZnalezionychGabinetow);
				request.setAttribute("wyszukiwanyNumer",numerDoWyszukania); 
				request.getRequestDispatcher("wyswietlWyszukiwaneGabinety.jsp").forward(request, response);
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
