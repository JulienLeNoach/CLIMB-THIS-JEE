package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.BDDacces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ControllerMailBox
 */
@WebServlet("/MailBox")
public class ControllerMailBox extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private final String strQueryContact = "SELECT utilisateur.nickname,mod_request.subject,mod_request.object,mod_request.id_request FROM mod_request JOIN utilisateur ON utilisateur.id_member = mod_request.id_member ";
	  // Ajout de la contraite de suppression en cascade sur les 3 tables
	private final String strDeleteAlbum = "DELETE  FROM mod_request WHERE id_request = ?";
	Boolean MailDeleted = false;
       

    public ControllerMailBox() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession mySession = request.getSession();

		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
				try {
	      /* création de la connection de la BDD */
	      BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
	          "jdbc:mysql://localhost:3306/");
	      ResultSet rsiD = null;
	      conn1.setPreparedStatement(strQueryContact);
	      rsiD = conn1.getPreparedStatement().executeQuery();
	      
	      String data[][] = new String[25][5];// Creation du tableau

	      int i = 1;
	      while (rsiD.next()) {// On recupère les données avec le while et on les intègre dans le tableau
	        String nickname = rsiD.getString("nickname");
	        String sub = rsiD.getString("subject");
	        String obj = rsiD.getString("object");
	        int id_request = rsiD.getInt("id_request");
	        data[i][0] = nickname;
	        data[i][1] = sub;
	        data[i][2] = obj;
	        data[i][3] = id_request + "";
	        i++;
	      }
	      request.setAttribute("i",i);
	      request.setAttribute("data",data);
	      response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

          // Set standard HTTP/1.0 no-cache header.
          response.setHeader("Pragma", "no-cache");
	      this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewMailBox.jsp").forward(request, response);

	    }

	    catch (SQLException e) {
	      e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
	}
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
		System.out.println("pas de token");
	}}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession mySession = request.getSession();
//		String id_request = request.getParameter("messageSelected");
		Boolean checkForm;
		
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
	    try {
	      /* création de la connection de la BDD */
	      BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
	          "jdbc:mysql://localhost:3306/");
	
	 	 if(request.getParameterValues("messageSelected")==null) {
			 checkForm = false;
			request.setAttribute("checkForm", checkForm);
				doGet(request, response);    
				}
		 else {
				String[] selectedMessage = request.getParameterValues("messageSelected");

	for(int i=0;i<selectedMessage.length;i++) {
	      
		   	conn1.setPreparedStatement(strDeleteAlbum);
		      conn1.getPreparedStatement().setString(1, selectedMessage[i]);
		      conn1.getPreparedStatement().executeUpdate();
		    
		      
	      MailDeleted = true; 
	  	  request.setAttribute("MailDeleted", MailDeleted);
	}
	  conn1.disconnectDB(); }
	  	 
	    } catch (SQLException e) {
	      e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    doGet(request, response);
		 
	  }else {
			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
	}}	  
	


