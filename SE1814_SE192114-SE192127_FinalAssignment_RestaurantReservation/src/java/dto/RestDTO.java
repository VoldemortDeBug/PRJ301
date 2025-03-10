/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 *
 * @author Admin
 */
public class RestDTO {

    private int restID;
    private String name;
    private String loc;
    private int OwnerID;
    private String mainPhoto = null;
    private int entites = 0;
    private int reservations = 0;

    public RestDTO(int resID, String name, String loc, int OwnerID, String mainphoto) {
        this.restID = resID;
        this.name = name;
        this.loc = loc;
        this.OwnerID = OwnerID;
        this.mainPhoto = mainphoto;
    }

    public int getEntites() {
        return entites;
    }

    public void setEntites(int entites) {
        this.entites = entites;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public RestDTO() {
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public int getResID() {
        return restID;
    }

    public String getName() {
        return name;
    }

    public String getLoc() {
        return loc;
    }

    public int getOwnerID() {
        return OwnerID;
    }

    public void setResID(int resID) {
        this.restID = resID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public void setOwnerID(int OwnerID) {
        this.OwnerID = OwnerID;
    }

    public int getRestID() {
        return restID;
    }

    public void setRestID(int restID) {
        this.restID = restID;
    }

    @Override
    public String toString() {
        return "RestDTO{" + "restID=" + restID + ", name=" + name + ", loc=" + loc + ", OwnerID=" + OwnerID + ", mainPhoto=" + mainPhoto + '}';
    }
}
