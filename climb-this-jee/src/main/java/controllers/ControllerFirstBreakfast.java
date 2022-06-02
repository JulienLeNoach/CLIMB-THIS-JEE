package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BDDacces;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Servlet implementation class ControllerFirstBreakfast
 */
@WebServlet("/FirstBreakfast")
public class ControllerFirstBreakfast extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String strQueryListClubs = "SELECT club_name FROM club;";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerFirstBreakfast() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rsClub = null; 
		ArrayList<String> listNameClub = new ArrayList<String>();
		
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
                    "jdbc:mysql://localhost:3306/");
            
            Statement stmt = conn1.getConn().createStatement();
            rsClub = stmt.executeQuery(strQueryListClubs);
            
            while(rsClub.next()) {
            	listNameClub.add(rsClub.getString("club_name"));
            }
            
            request.setAttribute("listNameClub", listNameClub);
		} catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();       
        }
		 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

         // Set standard HTTP/1.0 no-cache header.
         response.setHeader("Pragma", "no-cache");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewConnection.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
