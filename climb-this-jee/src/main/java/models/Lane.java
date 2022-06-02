package models;

import java.util.ArrayList;

public class Lane {
    private String color;
    private int id_wall;
    private int id_lane;
    private ArrayList<Album> list_albums;

    public Lane(String color, int id_wall, int id_lane) {
        this.color = color;
        this.id_lane = id_lane;
        this.id_wall = id_wall;
        this.list_albums = new ArrayList<Album>();
    }

    public String getColor() {
        return this.color;
    }

    public int getid_lane() {
        return this.id_lane;
    }

    public void changeColor(String color) {
        this.color = color;
    }

    public int getId_wall() {
        return this.id_wall;
    }

    public void changeId_wall(int id_wall) {
        this.id_wall = id_wall;
    }

    public ArrayList<Album> getList_albums() {
        return this.list_albums;
    }

    public void addAlbum(Album album) {
        this.list_albums.add(album);
    }

    public void changeList_albums(ArrayList<Album> list_albums) {
        this.list_albums = list_albums;
    }


    public void delte_Album(Album album) {
        this.list_albums.remove(album); 
      }

}
