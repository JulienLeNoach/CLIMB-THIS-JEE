package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Club;
import models.Lane;
import models.User;
import models.Wall;
import utils.BDDacces;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Servlet implementation class ControllerWall
 */
@WebServlet("/Wall")
public class ControllerWall extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerWall() {
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
				
				 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
                 // Set standard HTTP/1.0 no-cache header.
                 response.setHeader("Pragma", "no-cache");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewWall.jsp").forward(request, response);	
				
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
	}
	}else {
		response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
		
	}}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		HttpSession mySession = request.getSession();
		
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
				
				if (request.getParameter("action1") != null) {
					
					String wallIndex = request.getParameter("listWall");
					
					mySession.setAttribute("wallIndex", wallIndex);
					response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
					 
			        // Set standard HTTP/1.0 no-cache header.
			        response.setHeader("Pragma", "no-cache");
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewLane.jsp").forward(request, response);
				}
					
	
				if (request.getParameter("action2") != null) {
					
					final String strQueryAddWall = "INSERT INTO wall (id_club) VALUES (?) ;";	
					final String strgetidWall = "SELECT * FROM wall;";
			
					try {
						 BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
				                    "jdbc:mysql://localhost:3306/");
						
						 
						int id_Club=  ((Club) mySession.getAttribute("club")).getId_club();
						
						conn1.setPreparedStatement(strQueryAddWall);
			            conn1.getPreparedStatement().setInt(1, id_Club);
			            conn1.getPreparedStatement().executeUpdate();
			     
			            
			            ResultSet idWall = null;

			            conn1.setPreparedStatement(strgetidWall);
			            idWall = conn1.getPreparedStatement().executeQuery();

			            ArrayList<Lane> listLane = new ArrayList<Lane>();
			           			            
			            while (idWall.next()) {
			                if (idWall.isLast()) {
			                	((Club) mySession.getAttribute("club")).getList_wall().add(new Wall(listLane, idWall.getInt("id_wall"), id_Club) );
			                }
			            }
			            
			            conn1.disconnectDB();
	    
					} catch(Exception e) {}
					
					response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
	                // Set standard HTTP/1.0 no-cache header.
	                response.setHeader("Pragma", "no-cache");
	                doGet(request, response);
				}

		
				if (request.getParameter("btnDeleteWall") != null) {
					final String strQueryDeleteWall = "DELETE FROM wall WHERE id_wall = ?;";
					String wallIndex = request.getParameter("listWall");
					
					int wallIndex2 = Integer.parseInt(wallIndex);
				
					int idwall = ((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex2).getId_wall();
					
					try {
			            BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
			                    "jdbc:mysql://localhost:3306/");

			            conn1.setPreparedStatement(strQueryDeleteWall);
			            conn1.getPreparedStatement().setInt(1, idwall);
			            conn1.getPreparedStatement().executeUpdate();
			            
						((Club) mySession.getAttribute("club")).getList_wall().remove(wallIndex2);
					} catch(Exception e) {} 
					
					response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
	                // Set standard HTTP/1.0 no-cache header.
	                response.setHeader("Pragma", "no-cache");
	                doGet(request, response);
	                 
				}else {
					response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
				}
			}else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
				System.out.println("pas de token");
			}
		}
	}
}

