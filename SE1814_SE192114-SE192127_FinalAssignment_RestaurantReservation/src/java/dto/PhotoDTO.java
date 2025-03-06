/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Admin
 */
public class PhotoDTO {

    private int photoID;
    private int restID;
    private String photo;

    public PhotoDTO() {
    }


    public PhotoDTO(int photoID, int restID, String photo) {
        this.photoID = photoID;
        this.restID = restID;
        this.photo = photo;
    }

    public int getRestID() {
        return restID;
    }

    public void setRestID(int restID) {
        this.restID = restID;
    }

    public int getPhotoID() {
        return photoID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PhotoDTO{" + "photoID=" + photoID + ", restID=" + restID + ", photo=" + photo + '}';
    }

}
