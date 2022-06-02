package controllers;
import models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Club;
import utils.BDDacces;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Servlet implementation class ControllerLane
 */
@WebServlet("/Lane")
public class ControllerLane extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerLane() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doPost(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession mySession = request.getSession();
		
		final String strQueryAddLane = "INSERT INTO lane (color, id_wall) VALUES (?, ?) ;";
		final String strgetidlane = "SELECT * FROM lane;";
		final String strQueryDeleteLane = "DELETE FROM lane WHERE id_lane = ?;";
		
		String laneIndex = request.getParameter("listLane");

		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
				
				if (request.getParameter("showLane") != null) {
				
					mySession.setAttribute("laneIndex", laneIndex);

					//request.setAttribute("wallIndex", LaneIndex);
					response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
					// Set standard HTTP/1.0 no-cache header.
					response.setHeader("Pragma", "no-cache");
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewAlbum.jsp").forward(request, response);	
				}						
						
				if (request.getParameter("addLane") != null) {
					String wallIndex = (String) mySession.getAttribute("wallIndex"); 
					int wallIndex2 = Integer.parseInt(wallIndex);
						
					String color = request.getParameter("color");
					int id_Wall= ((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex2).getId_wall();
						
					try {
					BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb", "jdbc:mysql://localhost:3306/");				
						
					conn1.setPreparedStatement(strQueryAddLane);
			        conn1.getPreparedStatement().setString(1, color);
			        conn1.getPreparedStatement().setInt(2, id_Wall);
			        conn1.getPreparedStatement().executeUpdate();          
			           
			        ResultSet idLane = null;
		
			        conn1.setPreparedStatement(strgetidlane);
			        idLane = conn1.getPreparedStatement().executeQuery(); 
			            
			        while (idLane.next()) {
			        	if (idLane.isLast()) {
			        		((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex2).getList_lane().add(new Lane(idLane.getString("color"), idLane.getInt("id_wall"), idLane.getInt("id_lane")));
			            }   
			        }
			            
			        conn1.disconnectDB();
		
			        this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewLane.jsp").forward(request, response);
			            
					} catch(Exception e) {} 
				}
				
				if (request.getParameter("deleteLane") != null) {
		
					String wallIndex = (String) mySession.getAttribute("wallIndex"); 
					int wallIndex2 = Integer.parseInt(wallIndex);
					int laneIndex2 = Integer.parseInt(laneIndex);
					int idlane = ((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex2).getList_lane().get(laneIndex2).getid_lane();
					
					try {
			            BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb", "jdbc:mysql://localhost:3306/");
		
			            conn1.setPreparedStatement(strQueryDeleteLane);
			            conn1.getPreparedStatement().setInt(1, idlane);
			            conn1.getPreparedStatement().executeUpdate();
			            
						((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex2).getList_lane().remove(laneIndex2);
						
						this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewLane.jsp").forward(request, response);
			        
					} catch(Exception e) {} 
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
		}
	}
}

