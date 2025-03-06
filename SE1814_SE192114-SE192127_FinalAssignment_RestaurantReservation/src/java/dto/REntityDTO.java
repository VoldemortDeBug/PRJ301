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
public class REntityDTO {

    private int enID;
    private int restID;
    private String enType;
    private double enFee;

    public REntityDTO() {
    }

    public REntityDTO(int enID, int restID, String enType, double enFee) {
        this.enID = enID;
        this.restID = restID;
        this.enType = enType;
        this.enFee = enFee;
    }

    public int getEnID() {
        return enID;
    }

    public int getRestID() {
        return restID;
    }

    public String getEnType() {
        return enType;
    }

    public double getEnFee() {
        return enFee;
    }

    public void setEnID(int enID) {
        this.enID = enID;
    }

    public void setRestID(int restID) {
        this.restID = restID;
    }

    public void setEnType(String enType) {
        this.enType = enType;
    }

    public void setEnFee(double enFee) {
        this.enFee = enFee;
    }

    @Override
    public String toString() {
        return "ReservedEntityDTO{" + "enID=" + enID + ", restID=" + restID + ", enType=" + enType + ", enFee=" + enFee + '}';
    }

    
    
    
}
