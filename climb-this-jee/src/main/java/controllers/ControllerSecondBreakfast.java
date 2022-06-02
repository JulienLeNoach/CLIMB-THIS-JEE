package controllers;

import models.*; 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.BDDacces;
import utils.ConnectionToken;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 * Servlet implementation class ControllerSecondBreakfast
 */
@WebServlet("/Connection")
public class ControllerSecondBreakfast extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String strQueryCheckConnect = "SELECT * FROM utilisateur WHERE user_name = ?;";
	private final String strQueryCheckClub_name = "SELECT * FROM Club WHERE id_club = ?;";
	private final String strQueryListWalls = "SELECT id_wall FROM wall WHERE id_club = ?;";
	private final String strQueryClub = "SELECT id_club FROM club WHERE club_name = ?;";
	private final String strQueryListClubs = "SELECT club_name FROM club;";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerSecondBreakfast() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String btn_name = request.getParameter("btn_submit");
		
		if (btn_name == null) {
		    System.out.println("error == null");
		} else if (btn_name.equals("S'incrire")) {
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
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRegister.jsp").forward(request, response);
		} else if (btn_name.equals("Se connecter")) {
			String user_id = request.getParameter("user_name");
			String selected_club_name = request.getParameter("selected_club_name");
	        String pwd = request.getParameter("pwd");
	        
	        Boolean idFound = false;
	        Boolean cptLock = false;
	        String user_role;
	        String lastname;
	        String firstname;
	        String email;
	        String nickname;
	        String club_member;
	        String pwdUser;
	        String sha1 = "";
	        String user_name; 
	        String club_name = null;
	        int nbEssai = 0;
	        int id_member;
	        int idlane;
	        Integer nbLock=0;
	        ResultSet rsUsers = null;
	        ResultSet rsLane = null;
	        ResultSet rsAlbum = null;
	        ResultSet rsPics = null;
	        ResultSet rsWall = null;
	        ResultSet rsClub = null; 
	        ResultSet rsnomduclub = null;
	      
	        
	        try {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
	                    "jdbc:mysql://localhost:3306/");
	            
	            conn1.setPreparedStatement(strQueryCheckConnect);
	            conn1.getPreparedStatement().setString(1, user_id);
	            rsUsers = conn1.getPreparedStatement().executeQuery();
	            
	            while (rsUsers.next()) {
	            	idFound = true;
	                if (rsUsers.getString("user_name").equals(user_id)) {
	                	try {
	                        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	                        digest.reset();
	                        digest.update(pwd.getBytes("utf8"));
	                        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                	
	                	
	        	            
                        }
	                	if (rsUsers.getString("user_password").equals(sha1)) {
	                		HttpSession mySession = request.getSession();
	                		final String strQueryReset = "UPDATE `utilisateur` SET `nbEssai` = 0 WHERE `utilisateur`.`user_name` = ? AND nbEssai < 3";/*Ici on remet le compteur des tentatives a 0 si les identifiants sont correct*/
	            	        int rsReset = 0;
	            	        conn1.setPreparedStatement(strQueryReset);
	        	            conn1.getPreparedStatement().setString(1, user_id);
	        	            rsReset = conn1.getPreparedStatement().executeUpdate();
	                        pwdUser = rsUsers.getString("user_password");
	                        id_member = rsUsers.getInt("id_member");
	                        user_role = rsUsers.getString("user_role");
	                        user_name = rsUsers.getString("user_name");
	                        lastname = rsUsers.getString("last_name");
	                        firstname = rsUsers.getString("first_name");
	                        email = rsUsers.getString("email");
	                        nickname = rsUsers.getString("nickname");
	                        club_member = rsUsers.getString("id_club");
	                        nbLock = rsUsers.getInt("nbLock");
	                        
	                        ArrayList<Wall> listWall = new ArrayList<Wall>();
	                        
	                        conn1.setPreparedStatement(strQueryClub);                    
	                        conn1.getPreparedStatement().setString(1, selected_club_name);
	                        rsClub = conn1.getPreparedStatement().executeQuery();
	                        
	                        while (rsClub.next()) {
	                        	int idClub = rsClub.getInt("id_club");
	                        	conn1.setPreparedStatement(strQueryListWalls);                    
	                            conn1.getPreparedStatement().setInt(1, idClub);
	                            rsWall = conn1.getPreparedStatement().executeQuery();
	                            
	                            while (rsWall.next()) {                       	  
	                            	int idWall = rsWall.getInt("id_wall");
	                            	//   listWall.add(new Wall(rsLane.getString("color"), rsLane.getInt("id_wall"),
	                            	ArrayList<Lane> listLane = new ArrayList<Lane>();
	                            	  
	                            	String strQueryListLanes = "SELECT * FROM lane WHERE id_wall ="+ idWall +";" ;
	                            	conn1.setPreparedStatement(strQueryListLanes);
	                            	rsLane = conn1.getPreparedStatement().executeQuery();
	                            	  
	                            	int i = 0;

	                            	while (rsLane.next()) {                       	
	                            		int j = 0;
	                            		idlane = rsLane.getInt("id_lane");
	                            		listLane.add(new Lane(rsLane.getString("color"), rsLane.getInt("id_wall"),
	                                    rsLane.getInt("id_lane")));

	                            		String strQueryListAlbum = "SELECT * FROM album WHERE id_lane =" + idlane + " AND is_checked='Valide';";
	                            		conn1.setPreparedStatement(strQueryListAlbum);
	                            		rsAlbum = conn1.getPreparedStatement().executeQuery();

	                            		while (rsAlbum.next()) {                            	
	                            			int idAlbum = rsAlbum.getInt("id_album");                      			  
	                            			listLane.get(i).addAlbum(new Album(idAlbum));
	                            			  
	                            			String strQueryListPics = "SELECT * FROM photo WHERE id_album =" + idAlbum;                        			 
	                            			conn1.setPreparedStatement(strQueryListPics);                        			  
	                            			rsPics = conn1.getPreparedStatement().executeQuery();
	                            			  
	                            			while (rsPics.next()) {                               	
	                            				listLane.get(i).getList_albums().get(j).addPics(rsPics.getString("photo"));
	                            			}
	                            			  
	                            			j++;                           
	                            			rsPics = null;
	                            		}
	                            		  
	    	                        rsAlbum = null;	                        
	    	                        i++;
	    	                        }
	                            	
	                            	rsLane = null;                       
	                            	listWall.add(new Wall(listLane, idWall, idClub));
	                            }
	                            
	                            Club club = new Club(listWall, idClub, selected_club_name);        
     
	                            mySession.setAttribute("club", club);
	                            
	                            conn1.setPreparedStatement(strQueryCheckClub_name);
		        	            conn1.getPreparedStatement().setString(1, club_member);
		        	            rsnomduclub = conn1.getPreparedStatement().executeQuery();
		        	            
		        	           
								while (rsnomduclub.next()) {
		        	            	 club_name = rsnomduclub.getString("club_name");
		        	            }
		        	            

	                            if (user_role.equals("user")) {
	                                User user = new User(id_member, user_name, user_role, lastname, firstname, email, nickname, club_member, pwdUser, club_name,nbLock);
	                                mySession.setAttribute("user", user);
	                            }
	                            
	                            
	                            if (user_role.equals("moderator")) {
	                                Moderator moderator = new Moderator(id_member, user_name, user_role, lastname, firstname, email, nickname, club_member, pwdUser, club_name,nbLock); 
	                                mySession.setAttribute("user", moderator);
	                            }
	                            
	                            if (user_role.equals("admin")) {
	                                Admin admin = new Admin(id_member, user_name, user_role, lastname, firstname, email, nickname, club_member, pwdUser, club_name,nbLock); 
	                                mySession.setAttribute("user", admin);
	                            }
	                                                   
	                            int n = 20;
	                            ConnectionToken connectiontoken = new ConnectionToken();
	                           String token = connectiontoken.getAlphaNumericString(n);
	                           
	                           mySession.setAttribute("token", token);
	                          
	                           response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

	                           // Set standard HTTP/1.0 no-cache header.
	                           response.setHeader("Pragma", "no-cache");
	                            this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewHomePage.jsp").forward(request, response);

	                        }                                                                      
	                		
	                	}else {
	                		idFound = false;
	                		final String strQueryEssai = "UPDATE `utilisateur` SET `nbEssai` = nbEssai+1 WHERE `utilisateur`.`user_name` = ? ";/*Ici on incrémente le compteur d'essai en bdd"*/
	            	        int rsEssai = 0;
	            	        conn1.setPreparedStatement(strQueryEssai);
	        	            conn1.getPreparedStatement().setString(1, user_id);
	        	            rsEssai = conn1.getPreparedStatement().executeUpdate();
	        	            
	        	            nbEssai = rsUsers.getInt("nbEssai");
	                        if(nbEssai>=3) {/*Ici on viens verifier le nombre d'essai*/
	                        	final String strQueryLock = "UPDATE `utilisateur` SET `nbLock` = nbLock+1 WHERE `utilisateur`.`user_name` = ? ";
		            	        int rsLock = 0;
		            	        conn1.setPreparedStatement(strQueryLock);
		        	            conn1.getPreparedStatement().setString(1, user_id);
		        	            rsLock = conn1.getPreparedStatement().executeUpdate();/*Si le nombre d'essai est supérieur ou égale a 3 on incrémente le compteur de compte bloqué*/
		        	            cptLock=true;
		        	            request.setAttribute("cptLock", cptLock);
		    	            	this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
	                	}
	                }
	            }
	            
	            if (idFound == false) {
	            	request.setAttribute("idFound", idFound);
	            	this.getServletContext().getRequestDispatcher("/FirstBreakfast").forward(request, response);
	            }
	            
	            conn1.disconnectDB();
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();       
	        }
		} else if (btn_name.equals("Mot de passe oublié ?")) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewResetPwd.jsp").forward(request, response);
		} else {
			System.out.println("error");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewHomePage.jsp").forward(request, response);
	}

}