package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Album;
import models.Club;
import utils.BDDacces;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ControllerAddAlbum
 */
@WebServlet("/AddAlbum")

public class ControllerAddAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String ShowRequestAlbum = "SELECT utilisateur.nickname,mailbox.subject,mailbox.object,mailbox.id_album,album.color,wall.id_wall,album.id_lane FROM mailbox JOIN utilisateur ON utilisateur.id_member = mailbox.id_member LEFT JOIN album ON album.id_album = mailbox.id_album JOIN lane on album.id_lane = lane.id_lane JOIN wall ON wall.id_wall = lane.id_wall  WHERE album.is_checked='EnCours'";
	private final String strDeleteAlbum = "DELETE mailbox,photo,album FROM mailbox INNER JOIN photo ON photo.id_album = mailbox.id_album INNER JOIN album ON mailbox.id_album = album.id_album WHERE mailbox.id_album = ?";
	private final String strAddalbum = "UPDATE album SET is_checked = 'Valide' WHERE id_album = ? ";
	private Boolean checkForm;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerAddAlbum() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
					"jdbc:mysql://localhost:3306/");
			ResultSet rsiD = null;
			conn1.setPreparedStatement(ShowRequestAlbum);
			rsiD = conn1.getPreparedStatement().executeQuery();
			ResultSet rsPhoto = null;
			String data[][] = new String[300][8];
			String albumPhoto[][] = new String[300][2];

			int i = 1;
			int j = 1;
			while (rsiD.next()) {
				String nickname = rsiD.getString("nickname");
				String sub = rsiD.getString("subject");
				String obj = rsiD.getString("object");
				String id_album = rsiD.getString("id_album");
				String lane = rsiD.getString("color");
				String wall = rsiD.getString("id_wall");
				String id_lane = rsiD.getString("id_lane");
				data[i][0] = nickname;
				data[i][1] = sub;
				data[i][2] = obj;
				data[i][3] = id_album + "";
				data[i][4] = lane;
				data[i][5] = wall;
				data[i][6] = id_lane;
				conn1.setPreparedStatement("SELECT photo.photo,mailbox.id_album " + "FROM mailbox "
						+ "LEFT JOIN album ON album.id_album = mailbox.id_album "
						+ "LEFT JOIN photo ON photo.id_album = album.id_album "
						+ "WHERE album.is_checked='EnCours' AND album.id_album =" + id_album);
				rsPhoto = conn1.getPreparedStatement().executeQuery();
				while (rsPhoto.next()) {
					String photo = rsPhoto.getString("photo");
					String id_album2 = rsPhoto.getString("id_album");
					albumPhoto[j][0] = id_album2;
					albumPhoto[j][1] = "./Images/userAlbums/" + id_album2 + "/" + photo;
					j++;
				}
				i++;
			}

			request.setAttribute("i", i);
			request.setAttribute("j", j);
			request.setAttribute("data", data);
			request.setAttribute("albumPhoto", albumPhoto);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/ViewAddAlbum.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			BDDacces conn1 = new BDDacces("com.mysql.cj.jdbc.Driver", "climb_this_web", "climb", "climb",
					"jdbc:mysql://localhost:3306/");
			String submitBtnValue = request.getParameter("submit");

			HttpSession mySession = request.getSession();
			if (request.getParameterValues("albumSelected") == null) {
				checkForm = false;
				request.setAttribute("checkForm", checkForm);
				doGet(request, response);
			} else {
				String[] selectedAlbum = request.getParameterValues("albumSelected");

				if (submitBtnValue.equals("Supprimer la demande")) {
					for (int i = 0; i < selectedAlbum.length; i++) {
						conn1.setPreparedStatement(strDeleteAlbum);
						conn1.getPreparedStatement().setString(1, selectedAlbum[i]);
						conn1.getPreparedStatement().executeUpdate();
						String userDirectory = new File("").getAbsolutePath()
								+ "\\climb-this-jee\\src\\main\\webapp\\Images\\userAlbums\\" + selectedAlbum[i];
						File path = new File(userDirectory);
						if (path.exists()) {
							File[] files = path.listFiles();
							for (int k = 0; k < files.length; k++) {
								files[k].delete();

							}
							path.delete();
						}
					}
				} else if (submitBtnValue.equals("Accepter la demande")) {
					int id_lane = 0;
					int id_wall = 0;
					for (int i = 0; i < selectedAlbum.length; i++) {
						conn1.setPreparedStatement(strAddalbum);
						conn1.getPreparedStatement().setString(1, selectedAlbum[i]);
						conn1.getPreparedStatement().executeUpdate();

						String strQueryListPics = "SELECT lane.id_lane , photo.photo , wall.id_wall FROM album JOIN photo ON album.id_album = photo.id_album JOIN lane on album.id_lane = lane.id_lane JOIN wall ON wall.id_wall = lane.id_wall WHERE album.id_album = ?;";
						conn1.setPreparedStatement(strQueryListPics);
						conn1.getPreparedStatement().setString(1, selectedAlbum[i]);
						ResultSet rsPics = null;
						rsPics = conn1.getPreparedStatement().executeQuery();
						Album album = new Album(Integer.valueOf(selectedAlbum[i]));

						while (rsPics.next()) {
							album.addPics(rsPics.getString("photo"));
							id_lane = rsPics.getInt("id_lane");
							id_wall = rsPics.getInt("id_wall");
						}
						int ListWallSize = ((Club) mySession.getAttribute("club")).getList_wall().size();

						for (int j = 0; j < ListWallSize; j++) {
							if (((Club) mySession.getAttribute("club")).getList_wall().get(j).getId_wall() == id_wall) {
								for (int h = 0; h < ((Club) mySession.getAttribute("club")).getList_wall().get(j)
										.getList_lane().size(); h++) {
									if (((Club) mySession.getAttribute("club")).getList_wall().get(j).getList_lane()
											.get(h).getid_lane() == id_lane) {
										((Club) mySession.getAttribute("club")).getList_wall().get(j).getList_lane()
												.get(h).addAlbum(album);
									}
								}
							}
						}
					}

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}

}
