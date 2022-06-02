package models;

public class User {
  private int id_member;
  private String account_name;
  private String password;
  private String name;
  private String first_name;
  private String email;
  public String club_member;
  public String user_role;
  public String nickname;
  public String club_name;
  public Integer nbLock;


  public User(int id_member, String account_name, String user_role, String name, String first_name, String email,
      String nickname, String club_member, String password, String club_name,Integer nbLock) {
    this.id_member = id_member;
    this.account_name = account_name;
    this.name = name;
    this.first_name = first_name;
    this.email = email;
    this.nickname = nickname;
    this.club_member = club_member;
    this.user_role = user_role;
    this.password = password;
    this.club_name = club_name;
    this.nbLock = nbLock;
  }

  public int getId_member() {
    return this.id_member;
  }

  public String getAccount_name() {
    return this.account_name;
  }

  public void setAccount_name(String user_name) {
    this.account_name = user_name;
  }

  public String getPassword() {
    return this.password;
  }
  
  public Integer getnbLock() {
	    return this.nbLock;
	  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }
  

  public void setName(String name) {
    this.name = name;
  }

  public String getFirst_name() {
    return this.first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getClub_member() {
    return this.club_member;
  }

  public void setClub_member(String club_member) {
    this.club_member = club_member;
  }

  public String getUser_role() {
    return this.user_role;
  }

  public void setUser_role(String user_role) {
    this.user_role = user_role;
  }

  
  public String getClub_name() {
	    return this.club_name;
	  }
}