package models;

public class Moderator extends User {


public Moderator(int id_member, String account_name, String user_role, String name, String first_name, String email,
      String pseudo, String club_member, String password, String club_name,Integer nbLock) {
    super(id_member, account_name, user_role, name, first_name, email, pseudo, club_member, password, club_name,nbLock);

  }
}