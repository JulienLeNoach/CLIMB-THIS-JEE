package utils;

import java.sql.*;
import java.util.Scanner;

public class BDDacces {
  private String strClassName;
  private String dbName;
  private String login;
  private String motdepasse;
  private String strUrl;
  private Connection conn;
  private Statement stmt;
  private PreparedStatement prs;

  public BDDacces() {
  }

  public BDDacces(String driver, String dbName, String login, String motdepasse, String strUrl)
      throws ClassNotFoundException, SQLException {
    this.strClassName = driver;
    this.dbName = dbName;
    this.login = login;
    this.motdepasse = motdepasse;
    this.strUrl = strUrl + dbName;
    Class.forName(strClassName);
    this.conn = DriverManager.getConnection(this.strUrl, login, motdepasse);
    this.stmt = this.conn.createStatement();
  }

  public String getStrClassName() {
    return this.strClassName;
  }

  public String getDbName() {
    return this.dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getLogin() {
    return this.login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getMotdepasse() {
    return this.motdepasse;
  }

  public void setMotdepasse(String motdepasse) {
    this.motdepasse = motdepasse;
  }

  public String getStrUrl() {
    return this.strUrl;
  }

  public Connection getConn() {
    return this.conn;
  }

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public Statement getStmt() {
    return this.stmt;
  }

  public void setStmt(Statement stmt) {
    this.stmt = stmt;
  }

  public void setPreparedStatement(String quary) {
    try {
      this.prs = conn.prepareStatement(quary);
    } catch (SQLException e) {

      e.printStackTrace();
    }
  }

  public PreparedStatement getPreparedStatement() {
    return this.prs;
  }

  public void disconnectDB() throws SQLException {
    this.conn.close();

  }

  public void afficher() throws SQLException {
    ResultSet RS;
    try (Scanner sc = new Scanner(System.in)) {
      System.out.print("Entrez une table : ");
      String table = sc.nextLine();
      System.out.print("Entrez un champ : ");
      String champ = sc.nextLine();
      String commande = ("SELECT " + champ + " FROM " + table);
      RS = stmt.executeQuery(commande);
      while (RS.next()) {
        System.out.println(RS.getString(champ));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("Voici les résultats obtenus...");
  }

  public void supprimer() throws SQLException {
    System.out.print("Ecrire un ID: ");
    try (Scanner sc = new Scanner(System.in)) {
      int ID = sc.nextInt();
      System.out.print("Ecrire une table: ");
      try (Scanner dc = new Scanner(System.in)) {
        String table = dc.next();
        String commande = ("DELETE FROM " + table + " WHERE id= " + ID);
        stmt.executeUpdate(commande);
      }
    }
    System.out.println("Les valeurs ont été supprimés de la table...");
  }
}