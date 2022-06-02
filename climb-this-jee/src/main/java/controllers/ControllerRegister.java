package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BDDacces;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ControllerRegister
 */
@WebServlet("/Register")
public class ControllerRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String strQueryRegister = "INSERT INTO utilisateur (user_name,user_password,first_name,last_name,email,nickname,id_club,user_role,nbEssai,nbLock) VALUES(?,?,?,?,?,?,?,'user',0,0);";
	private final String strQueryCheckiD = "SELECT user_name,email,nickname FROM utilisateur WHERE user_name = ? OR email = ? OR  nickname = ? ;";
	private final String strQueryGetIdClub = "SELECT id_club FROM club where club_name = ?;";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String btn_name = request.getParameter("btn_submit");
		
		if (btn_name == null) {
		    System.out.println("error");
		} else if (btn_name.equals("Retour")) {
			 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

	         // Set standard HTTP/1.0 no-cache header.
	         response.setHeader("Pragma", "no-cache");
			this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
		} else if (btn_name.equals("S'incrire")) {
			String last_name = request.getParameter("lastname");
			String first_name = request.getParameter("firstname");
			String user_name = request.getParameter("user_name");
			String email = request.getParameter("email");
			String password = request.getParameter("pwd");
			String passwordCheck = request.getParameter("pwdCheck");
			String hashPw = "";
			String nickname = request.getParameter("nickname");
			String club_name = request.getParameter("selected_club_name");
			
			Boolean emptyForm = true;
			Boolean badForm = true;
			
			if (!(user_name.isEmpty() || user_name.isBlank() 
				|| password.isEmpty() || password.isBlank() 
				|| passwordCheck.isEmpty() || passwordCheck.isBlank() 
				|| last_name.isEmpty() || last_name.isBlank() 
			    || first_name.isEmpty() || first_name.isBlank() 
			    || email.isEmpty() || email.isBlank() 
			    || nickname.isEmpty() || nickname.isBlank())
			    && (user_name.matches("^[a-zA-Z0-9]*$") 
			    && password.matches("^[a-zA-Z0-9]*$")) 
			    && email.matches("^(.+)@(.+)$") 
			    && password.equals(passwordCheck)) {
			    
				emptyForm = false;
				
				ResultSet rsCheckiD = null;
				ResultSet rsCheckIdClub = null;
			      
			    try {
			      /* création de la connection de la BDD */
			      BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb", "jdbc:mysql://localhost:3306/");
			        
			      conn1.setPreparedStatement(strQueryCheckiD);
			      conn1.getPreparedStatement().setString(1, user_name);
			      conn1.getPreparedStatement().setString(2, email);
			      conn1.getPreparedStatement().setString(3, nickname);	
			      rsCheckiD = conn1.getPreparedStatement().executeQuery();
			        
			      if (!(rsCheckiD.next())) {
			    	  badForm = false;
			    	  try {
			    		  MessageDigest digest = MessageDigest.getInstance("SHA-1");
			    		  digest.reset();
			    		  digest.update(password.getBytes("utf8"));
			    		  hashPw = String.format("%040x", new BigInteger(1, digest.digest()));
			    	  } catch (Exception e) {
			    		  e.printStackTrace();
			    	  }
			    	  
			    	  conn1.setPreparedStatement(strQueryGetIdClub);
				      conn1.getPreparedStatement().setString(1, club_name);
				      rsCheckIdClub = conn1.getPreparedStatement().executeQuery();
				      
			    	  while(rsCheckIdClub.next()) {
			    		  int id_club = rsCheckIdClub.getInt("id_club");
			    		  
			    		  conn1.setPreparedStatement(strQueryRegister);
				    	  conn1.getPreparedStatement().setString(1, user_name);
				    	  conn1.getPreparedStatement().setString(2, hashPw);
				    	  conn1.getPreparedStatement().setString(3, last_name);
				    	  conn1.getPreparedStatement().setString(4, first_name);
				    	  conn1.getPreparedStatement().setString(5, email);
				    	  conn1.getPreparedStatement().setString(6, nickname);
				    	  conn1.getPreparedStatement().setInt(7, id_club);
				    	  conn1.getPreparedStatement().executeUpdate();
				    	  
				    	  request.setAttribute("badForm", badForm);
				    	  response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

				          // Set standard HTTP/1.0 no-cache header.
				          response.setHeader("Pragma", "no-cache");
				    	  this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
			    	  }			          
			      }
			      conn1.disconnectDB();
			      } catch (SQLException e) {
			    	  e.printStackTrace();
			      } catch (ClassNotFoundException e1) {
			    	  e1.printStackTrace();
			      }
			  }
			
			if(badForm || emptyForm) {
				request.setAttribute("badForm", badForm);
				request.setAttribute("emptyForm", emptyForm);
				 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		         // Set standard HTTP/1.0 no-cache header.
		         response.setHeader("Pragma", "no-cache");
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRegister.jsp").forward(request, response);
			}
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
