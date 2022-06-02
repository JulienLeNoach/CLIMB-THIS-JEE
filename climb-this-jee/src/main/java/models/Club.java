package models;

import java.util.ArrayList;

public class Club {
    
    private ArrayList<Wall> list_wall;
    private int id_club;
    private String club_name;
  

    public Club(ArrayList<Wall> list_wall, int id_club, String club_name) {
        this.list_wall = list_wall;
        this.id_club = id_club;
        this.club_name = club_name;
    }

    public ArrayList<Wall> getList_wall() {
        return this.list_wall;
    }

    public void setList_lane(ArrayList<Wall> list_wall) {
        this.list_wall = list_wall;
    }

    public int getId_club() {
        return this.id_club;
    }

    public void setId_wall(int id_club) {
        this.id_club = id_club;
    }
    
    public String getClub_name() {
        return this.club_name;
    }
    
    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

}