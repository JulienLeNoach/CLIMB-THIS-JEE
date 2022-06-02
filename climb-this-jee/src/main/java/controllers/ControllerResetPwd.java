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
 * Servlet implementation class ControllerResetPwd
 */
@WebServlet("/ResetPwd")
public class ControllerResetPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String strQueryCheckUser = "SELECT user_name, email FROM utilisateur WHERE user_name = ?;";
    private final String strQueryChangepassword = "UPDATE utilisateur SET user_password = ? WHERE user_name = ?;";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerResetPwd() {
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
			this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
		} else if (btn_name.equals("Réinitialiser le mot de passe")) {
			String user_name = request.getParameter("user_name");
			String email = request.getParameter("email");
		    String pwd = request.getParameter("newPwd");
		    String sha1 = "";
		    
		    ResultSet rsUsers = null;
	        Boolean idFoundReset = false;
			
	        if (!(user_name.isEmpty() || pwd.isEmpty() || email.isEmpty() 
	        	|| user_name.isBlank() || pwd.isBlank()) || email.isBlank() 
	        	&& (user_name.matches("^[a-zA-Z0-9]*$") && pwd.matches("^[a-zA-Z0-9]*$") && email.matches("^(.+)@(.+)$"))) {	        	
	        	
	            try {
	                /* création de la connection de la BDD */
	                BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb", "jdbc:mysql://localhost:3306/");

	                conn1.setPreparedStatement(strQueryCheckUser);
	                conn1.getPreparedStatement().setString(1, user_name);
	                rsUsers = conn1.getPreparedStatement().executeQuery();

	                try {
	                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
	                    digest.reset();
	                    digest.update(pwd.getBytes("utf8"));
	                    sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }

	                while (rsUsers.next()) {
	                    if (rsUsers.getString("user_name").equals(user_name) && rsUsers.getString("email").equals(email)) {
	                        /* si utilisateur est reconnu, on change le mot de passe */
	                        idFoundReset = true;

	                        conn1.setPreparedStatement(strQueryChangepassword);
	                        conn1.getPreparedStatement().setString(1, sha1);
	                        conn1.getPreparedStatement().setString(2, user_name);
	                        conn1.getPreparedStatement().executeUpdate();
	                        request.setAttribute("idFoundReset", idFoundReset);
	                        this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
	                    }

	                }
	                
	                conn1.disconnectDB();
	            }catch (SQLException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	        }
	        
	        if(idFoundReset == false) {
            	request.setAttribute("idFoundReset", idFoundReset);
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewResetPwd.jsp").forward(request, response);
            }
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
