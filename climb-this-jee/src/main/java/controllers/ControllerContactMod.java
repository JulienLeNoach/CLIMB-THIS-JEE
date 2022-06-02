package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.io.IOException;

import java.sql.*;

import models.User;
import models.Wall;
import utils.*;


/**
 * Servlet implementation class ControllerContactMod
 */
@WebServlet("/ContactMod")
public class ControllerContactMod extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public ControllerContactMod() {
        super();
        // TODO Auto-generated constructor stub
    }

	  private final String strQueryContact = "INSERT INTO mod_request (subject,object,id_member) VALUES(?,?,?);";
	  private String subject;
	  private String object;
	  Boolean messageSent = false;
	  
	  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewContactMod.jsp").forward(request, response);
	}  

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession mySession = request.getSession();
		
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
			
				
		String id_member =  Integer.toString(((User) mySession.getAttribute("user")).getId_member());
		
		 subject= request.getParameter("subject");
         object= request.getParameter("object");
	 
		    if (!(this.subject.isEmpty() || this.subject.isBlank()
		        || this.object.isEmpty() || this.object.isBlank())
		        && (this.subject.matches("^[a-zA-Z0-9 ]*$")
		            && this.object.matches("^[a-zA-Z0-9 ]*$"))) {
		      try {
		        /* création de la connection de la BDD */
		        BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web",
		            "climb", "climb",
		            "jdbc:mysql://localhost:3306/");

		        conn1.setPreparedStatement(strQueryContact);
		        conn1.getPreparedStatement().setString(1, subject);
		        conn1.getPreparedStatement().setString(2, object);
		        conn1.getPreparedStatement().setString(3, id_member);
		        conn1.getPreparedStatement().executeUpdate();
		        
		        conn1.disconnectDB();
		        
		        messageSent = true;
		        request.setAttribute("messageSent", messageSent);
		        response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                // Set standard HTTP/1.0 no-cache header.
                response.setHeader("Pragma", "no-cache");
		        this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewContactMod.jsp").forward(request, response);


		      } catch (SQLException | ClassNotFoundException e) {
		        e.printStackTrace();
		      }
		    } else {
		    	messageSent = false;
		    	request.setAttribute("messageSent", messageSent);
		    	 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                 // Set standard HTTP/1.0 no-cache header.
                 response.setHeader("Pragma", "no-cache");
		    	 this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewContactMod.jsp").forward(request, response);

		    	}
		      
		
		doGet(request, response);
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
	}
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
		System.out.println("pas de token");
	}}

}
