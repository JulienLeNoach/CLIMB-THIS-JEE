package models;

import java.util.ArrayList;

/**
 * Returns an Album object
 * This class is creating an album composed of an ID and an Array of String
 * which will include the Photo list
 * The album is also part of the Lane object
 */

public class Album {
  private int id_album;
  private ArrayList<String> list_photo;

  /**
   * Create an album Object
   * 
   * @return Album
   */
  public Album() {
  }

  public Album(int id_album) {
    this.id_album = id_album;
    this.list_photo = new ArrayList<String>();
  }

  /**
   * get the ID of an album
   * 
   * @return int
   */
  public int getId_album() {
    return this.id_album;
  }

  /**
   * get the Photo list from the Album object
   * 
   * @return ArrayList<String>
   */
  public ArrayList<String> getList_photo() {
    return this.list_photo;
  }


  /**
   * add a photo to the album list
   * 
   * @param photo
   */
  public void addPics(String photo) {
	  
    this.list_photo.add(photo);
    
  }


  /**
   * add a list to an album
   * 
   * @param list_photo
   */
  public void setList_photo(ArrayList<String> list_photo) {
    this.list_photo = list_photo;
  }  


}