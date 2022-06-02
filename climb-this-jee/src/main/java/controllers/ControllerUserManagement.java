package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Club;
import models.User;
import models.Wall;
import utils.BDDacces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/UserManagement")

public class ControllerUserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControllerUserManagement() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession mySession = request.getSession();
		String strQueryListUser = "SELECT id_member,nickname, user_role FROM utilisateur ORDER BY user_role;";

		ResultSet listUser = null;

		try {
			BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
					"jdbc:mysql://localhost:3306/");

			conn1.setPreparedStatement(strQueryListUser);
			listUser = conn1.getPreparedStatement().executeQuery();

			int cpt = 0;
			while (listUser.next()) {
				cpt++;
			}

			String data[][] = new String[cpt][3];
			conn1.setPreparedStatement(strQueryListUser);
			listUser = conn1.getPreparedStatement().executeQuery();
			int i = 0;
			while (listUser.next()) {
				String id_member = listUser.getString("id_member");
				String nickname = listUser.getString("nickname");
				String user_role = listUser.getString("user_role");
				data[i][0] = id_member;
				data[i][1] = nickname;
				data[i][2] = user_role;
				i++;

			}

			request.setAttribute("cpt", cpt);
			request.setAttribute("data", data);

			conn1.disconnectDB();

			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewUserManagement.jsp").forward(request,
					response);

		} catch (SQLException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String strQueryUpdate = "UPDATE utilisateur SET user_role = ? WHERE id_member = ? ;";
		 String strQueryDeleteUser = "DELETE FROM utilisateur WHERE id_member = ?;";
		 

		 if (request.getParameter("action1") != null) {
		
			 HttpSession mySession = request.getSession();
			 int id_member = Integer.parseInt(request.getParameter("id_person"));
			 int id_member2 = ((User) mySession.getAttribute("user")).getId_member();
			 
			if(id_member == id_member2){
				request.setAttribute("error", "Vous ne pouvez pas modifier votre propre comtpe");
			  
			 	} else {

			 		try {
						      BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
						          "jdbc:mysql://localhost:3306/");
						 	 String user_role= request.getParameter("user_role");
						     			     
						      conn1.setPreparedStatement(strQueryUpdate);
						      conn1.getPreparedStatement().setString(1, user_role);
						      conn1.getPreparedStatement().setInt(2, id_member);
						      conn1.getPreparedStatement().executeUpdate();
			
						    } catch (SQLException e) {
						      e.printStackTrace();
						    } catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
		 			}
		
		 
		 if (request.getParameter("action2") != null) {
			 HttpSession mySession = request.getSession();
			 int id_member = Integer.parseInt(request.getParameter("id_person"));
			 int id_member2 = ((User) mySession.getAttribute("user")).getId_member();
			 
			if(id_member == id_member2){
				request.setAttribute("error", "Vous ne pouvez pas modifier votre propre comtpe");
			  
			 	}
			
			else {
				  try {
				      BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
				      "jdbc:mysql://localhost:3306/");
				      conn1.setPreparedStatement(strQueryDeleteUser);
				      conn1.getPreparedStatement().setInt(1, id_member);
				      conn1.getPreparedStatement().executeUpdate();
	
				    } catch (SQLException e) {
				      e.printStackTrace();
				    } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
				
			 }
			
			doGet(request, response);
		}

}