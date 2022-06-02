package models;

import java.util.ArrayList;

public class Wall {
    private ArrayList<Lane> list_lane;
    private int id_wall;
    private int id_club;

    public Wall(ArrayList<Lane> list_lane, int id_wall, int id_club) {
        this.list_lane = list_lane;
        this.id_wall = id_wall;
        this.id_club = id_club;
    }

    public ArrayList<Lane> getList_lane() {
        return this.list_lane;
    }

    public void setList_lane(ArrayList<Lane> list_lane) {
        this.list_lane = list_lane;
    }

    public int getId_wall() {
        return this.id_wall;
    }
    
   public int getId_club() {
        return this.id_club;
    }


    public void setId_wall(int id_wall) {
        this.id_wall = id_wall;
    }

    /*
     * public void addLane(Lane l) {
     * ((Object) this.list_lane).add(l);
     * }
     * 
     * public void laneDelete(Lane l) {
     * ((Object) this.list_lane).delete(l);
     * }
     */
}