package controllers;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Club;
import models.Wall;
import utils.BDDacces;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Servlet implementation class ControllerAlbums
 */
@WebServlet("/Album")
public class ControllerAlbums extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	public ControllerAlbums() {
        super();
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
		HttpSession mySession = request.getSession();
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
							
if (request.getParameter("showAlbums") != null) {	
	
	System.out.println("premier if");
		Integer i = 0;
		
		while(true) {
			String btn_name = request.getParameter("showAlbums");
			if (btn_name == null) {
				System.out.println("error == null");
				break;
			}
			else if(btn_name.equals(i.toString())) {		
				response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                // Set standard HTTP/1.0 no-cache header.
                response.setHeader("Pragma", "no-cache");
                
                mySession.setAttribute("albumIndex", i);
                
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewPics.jsp").forward(request, response);
				break;
			}
			else {
				i+=1;
			}
		}
	}


if (request.getParameter("deleteAlbum") != null) {	

	final String strQueryDeleteAlbum = "DELETE FROM album WHERE id_album = ?;";
	
	
	String albumID1 = request.getParameter("idAlbum");
	String wallIndex1 = (String) mySession.getAttribute("wallIndex");
	String laneIndex1= (String) mySession.getAttribute("laneIndex");
	String albumIndex1 = request.getParameter("albumindex");

	

	int albumID = Integer.parseInt(albumID1);
	int wallIndex = Integer.parseInt(wallIndex1);
	int laneIndex = Integer.parseInt(laneIndex1);
	int albumIndex = Integer.parseInt(albumIndex1);
	
	mySession.setAttribute("albumIndex", albumIndex1);


	try {
        BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
                "jdbc:mysql://localhost:3306/");

        conn1.setPreparedStatement(strQueryDeleteAlbum);
        conn1.getPreparedStatement().setInt(1, albumID);
        conn1.getPreparedStatement().executeUpdate();
        
        ((Club) mySession.getAttribute("club")).getList_wall().get(wallIndex).getList_lane().get(laneIndex).getList_albums().remove(albumIndex);
        
        String userDirectory = new File("").getAbsolutePath()
				+ "\\climb-this-jee\\src\\main\\webapp\\Images\\userAlbums\\" + albumID;
		File path = new File(userDirectory);
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int k = 0; k < files.length; k++) {
				files[k].delete();

			}
			path.delete();
		}
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewAlbum.jsp").forward(request, response);
	} catch(Exception e) {

    } 
	
		}

			else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
			}
			}else {
				response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
				System.out.println("pas de token");
			}}
	}}
		
	
