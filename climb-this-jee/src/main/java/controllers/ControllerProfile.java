package controllers;
import models.*;


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
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Servlet implementation class ControllerProfile
 */
@WebServlet("/Profile")
public class ControllerProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String strQueryProfile = "UPDATE utilisateur SET first_name = ?, last_name = ?, email = ?, nickname = ? WHERE id_member = ? ;";
    private final String strQueryDelete = "DELETE FROM utilisateur WHERE id_member = ?;";
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
   
    public ControllerProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession mySession = request.getSession();
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
		
				String id_member =  Integer.toString(((User) mySession.getAttribute("user")).getId_member());

				try {
			
                BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
                        "jdbc:mysql://localhost:3306/");

                
                String first_name= request.getParameter("firstname");
                String last_name= request.getParameter("last_name");
                String email = request.getParameter("email");
                String nickname = request.getParameter("nickname");

                conn1.setPreparedStatement(strQueryProfile);
               
                conn1.getPreparedStatement().setString(1, first_name);
                conn1.getPreparedStatement().setString(2, last_name);
                conn1.getPreparedStatement().setString(3, email);
                conn1.getPreparedStatement().setString(4, nickname);
                conn1.getPreparedStatement().setString(5, id_member);
                conn1.getPreparedStatement().executeUpdate();

                conn1.disconnectDB();
                
                
                ((User) mySession.getAttribute("user")).setFirst_name(first_name);
                ((User) mySession.getAttribute("user")).setName(last_name);
                ((User) mySession.getAttribute("user")).setEmail(email);
                ((User) mySession.getAttribute("user")).setNickname(nickname);
                
                this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewProfile.jsp").forward(request, response);
              
            } catch (SQLException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }
		}else {
			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
		}else {
			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
		}
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession mySession = request.getSession();
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
				 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                 // Set standard HTTP/1.0 no-cache header.
                 response.setHeader("Pragma", "no-cache");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewProfile.jsp").forward(request, response);
		
	}
			else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
				}
			}else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
				}
			}

}
