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
    private List<PhotoDTO> lphoto;

    public RestDTO(int resID, String name, String loc, int OwnerID) {
        this.restID = resID;
        this.name = name;
        this.loc = loc;
        this.OwnerID = OwnerID;
    }

    public RestDTO() {
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

    public List<PhotoDTO> getLphoto() {
        return lphoto;
    }

    public void setRestID(int restID) {
        this.restID = restID;
    }

    public void setLphoto(List<PhotoDTO> lphoto) {
        this.lphoto = lphoto;
    }

}
