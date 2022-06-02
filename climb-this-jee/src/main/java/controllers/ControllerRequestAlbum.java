package controllers;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import models.User;
import models.Wall;
import utils.BDDacces;


@WebServlet("/RequestAlbum")
@MultipartConfig( fileSizeThreshold = 1024 * 1024, 
                  maxFileSize = 1024 * 1024 * 5,
                  maxRequestSize = 1024 * 1024 * 5 * 5 )
public class ControllerRequestAlbum extends HttpServlet {

    private static final long serialVersionUID = 1273074928096412095L;
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd_MM_yyyy-HH-mm-SS");
    String formattedDate = myDateObj.format(myFormatObj);
    

    private final String strQueryColor = "SELECT id_lane FROM lane WHERE color = ? AND  id_wall = ?";
    private final String strQueryContact = "INSERT INTO mailbox (subject,object,id_member,id_album) VALUES(?,?,?,?);";
    private final String strUpdateAlbum = "INSERT INTO `album` (`id_lane`,`color`, `id_member`,`is_checked`) VALUES (?,?,?,'EnCours');";
    private final String strQueryidAlbum = "SELECT id_album FROM album ORDER BY id_album DESC LIMIT 1";
    private final String strUpdatePhoto = "INSERT INTO `photo` (`photo`,`id_album`) VALUES (?,?);";
    private Boolean checkPhoto;
    private Boolean checkForm;
    private int id_lane;
    private int id_album;

    @Override

    public void init() throws ServletException {

    }
    private String getFileName( Part part ) {
        for ( String content : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( content.trim().startsWith( "filename" ) )
                return content.substring( content.indexOf( "=" ) + 2, content.length() - 1 );
        }
        return "Default.file";
    }
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    	
    	checkForm=false;
    	checkPhoto=false;

		HttpSession mySession = request.getSession();
		
		if (!(mySession.getAttribute("token")==null)) {
			if( !(mySession.getAttribute("token").equals(""))){
				
			
		String id_member =  Integer.toString(((User) mySession.getAttribute("user")).getId_member());
		String object = request.getParameter("object");	
		String subject = request.getParameter("subject");
		if (!(object.isEmpty() || object.isBlank()
		        || subject.isEmpty() || subject.isBlank())
		        && (object.matches("^[a-zA-Z0-9 ]*$")
		            && subject.matches("^[a-zA-Z0-9 ]*$"))) {
			
        try {
        	
        	String color = request.getParameter("color");
    		String id_wall = request.getParameter("id_wall");
            Class.forName("com.mysql.cj.jdbc.Driver");
            BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb", "jdbc:mysql://localhost:3306/");
            
			ResultSet rsIdColor = null;
	          conn1.setPreparedStatement(strQueryColor);
	          conn1.getPreparedStatement().setString(1, color);
	          conn1.getPreparedStatement().setString(2, id_wall);
	          rsIdColor = conn1.getPreparedStatement().executeQuery();
	          
	          while (rsIdColor.next()) {
	            id_lane = rsIdColor.getInt("id_lane");
	          }

  	          Collection<Part> checkBtnUpload = request.getParts();
  	          for (Part part : checkBtnUpload) {
  	        	  
  	        	  if (part.getName().equals("photo")) {
  	        		  
  	        		  String fileName = getFileName( part );
  	        		  
  	        		  if(!(fileName=="")) {
  	        			  
  	        	    checkPhoto=true;
  	        		  }
  	        	  }
  	          }
  	          
  	        if(checkPhoto==true) {
  	        	
  	          conn1.setPreparedStatement(strUpdateAlbum);
  	          conn1.getPreparedStatement().setInt(1, id_lane);
  	          conn1.getPreparedStatement().setString(2, color);
  	          conn1.getPreparedStatement().setString(3, id_member);
  	          conn1.getPreparedStatement().executeUpdate();
  
  	        
	          ResultSet rsiDAlbum = null;
	          conn1.setPreparedStatement(strQueryidAlbum);
	          rsiDAlbum = conn1.getPreparedStatement().executeQuery();
	          
    	      while (rsiDAlbum.next()) {
    	        	id_album = rsiDAlbum.getInt("id_album");
    	            	}  	 
  	          String uploadPath2 = System.getProperty("user.dir") + "/climb-this-jee/src/main/webapp/Images/userAlbums/"+id_album;

              int i =0;
              for ( Part checkNameRequest : request.getParts() ) {
            		  if (checkNameRequest.getName().equals("photo")) {
            			  
            			  File uploadDir = new File( uploadPath2 );
            			  if ( ! uploadDir.exists() ) uploadDir.mkdir();
            			  
            			  String fileName = getFileName( checkNameRequest );
            			  fileName= (i + 1)+ "_" + formattedDate+".png";
            			  String fullPath = uploadPath2 + File.separator + fileName;
            			  checkNameRequest.write(fullPath);
            			  
            			  conn1.setPreparedStatement(strUpdatePhoto);
            			  conn1.getPreparedStatement().setString(1, fileName);
            			  conn1.getPreparedStatement().setInt(2, id_album);
            			  conn1.getPreparedStatement().executeUpdate();
            			  i++;
            		  }
            	  }

              		conn1.setPreparedStatement(strQueryContact);
              		conn1.getPreparedStatement().setString(1, subject);
              		conn1.getPreparedStatement().setString(2, object);
              		conn1.getPreparedStatement().setString(3, id_member);
              		conn1.getPreparedStatement().setInt(4, id_album);
              		conn1.getPreparedStatement().executeUpdate();
                    conn1.disconnectDB();
              		checkForm=true;
              		
              		}
        	} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        			}
				}else {
					
					checkForm = false;
					request.setAttribute("checkForm", checkForm);
					 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                     // Set standard HTTP/1.0 no-cache header.
                     response.setHeader("Pragma", "no-cache");
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRequestAlbum.jsp").forward(request, response);
					}
        		if (checkPhoto == false) {
        			
        			request.setAttribute("checkPhoto", checkPhoto);
        			 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                     // Set standard HTTP/1.0 no-cache header.
                     response.setHeader("Pragma", "no-cache");
        			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRequestAlbum.jsp").forward(request, response);
        			}
        		
        		if(checkPhoto == true || checkForm == true) {
        			
        			request.setAttribute("checkForm", checkForm);
        			 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

                     // Set standard HTTP/1.0 no-cache header.
                     response.setHeader("Pragma", "no-cache");
        			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRequestAlbum.jsp").forward(request, response);
        		}else {
        			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
        		}
        		}else {
        			response.sendRedirect(request.getContextPath() + "/FirstBreakfast");
        			
        		}}
    		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
         // Set standard HTTP/1.0 no-cache header.
         response.setHeader("Pragma", "no-cache");
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewRequestAlbum.jsp").forward(request, response);
		
	}
		}