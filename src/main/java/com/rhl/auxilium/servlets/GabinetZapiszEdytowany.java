package com.rhl.auxilium.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rhl.auxilium.dao.GabinetDAO;
import com.rhl.auxilium.entities.Gabinet;


@WebServlet("/ZapiszGabinet.do")
public class GabinetZapiszEdytowany extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GabinetZapiszEdytowany() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{	
				//int idDoEdycji = Integer.parseInt(request.getParameter("edytowany"));
				request.setAttribute("zrodlo",request.getParameter("zrodlo")); 
				GabinetDAO gabinetDoEdycji = new GabinetDAO();
				Gabinet gabinet = new Gabinet();
				gabinet.setId(Integer.parseInt(request.getParameter("edytowaneID")));
				gabinet.setOpis(request.getParameter("opisGabinetu")); 
				gabinet.setNumer(request.getParameter("numerGabinetu"));
				gabinet.setCzyAktywny(Byte.parseByte("1"));
				System.out.println("Numer gabinetu wynosi:" +request.getParameter("numerGabinetu"));
				System.out.println(gabinet.toString());
				gabinetDoEdycji.edytuj(gabinet);
				request.getRequestDispatcher("wyswietlGabinety.jsp").forward(request, response);
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
