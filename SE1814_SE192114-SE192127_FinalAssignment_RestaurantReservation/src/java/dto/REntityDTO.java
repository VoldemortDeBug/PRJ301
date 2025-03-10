/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class REntityDTO {

    private int enID;
    private int restID;
    private String enType;
    private int enFee;
    private Date activeTill;
    private int seatCap; //maximium seat
    private int forwardLim;  //maximum days to the future that can make a reservation
    private int daily; //max = 16777215
    private int weekly; //max = 127

    public REntityDTO() {
    }

    public REntityDTO(int enID, int restID, String enType, int enFee, Date activeTill, int seatCap, int forwardLim, int daily, int weekly) {
        this.enID = enID;
        this.restID = restID;
        this.enType = enType;
        this.enFee = enFee;
        this.activeTill = activeTill;
        this.seatCap = seatCap;
        this.forwardLim = forwardLim;
        this.daily = daily;
        this.weekly = weekly;
    }

    public void setForwardLim(int forwardLim) {
        this.forwardLim = forwardLim;
    }

    public int getForwardLim() {
        return forwardLim;
    }

    public int getDaily() {
        return daily;
    }

    public int getWeekly() {
        return weekly;
    }

    public void setDaily(int daily) {
        this.daily = daily;
    }

    public void setWeekly(int weekly) {
        this.weekly = weekly;
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

    public void setEnFee(int enFee) {
        this.enFee = enFee;
    }

    public void setActiveTill(Date activeTill) {
        this.activeTill = activeTill;
    }

    public void setSeatCap(int seatCap) {
        this.seatCap = seatCap;
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

    public int getEnFee() {
        return enFee;
    }

    public Date getActiveTill() {
        return activeTill;
    }

    public int getSeatCap() {
        return seatCap;
    }

    @Override
    public String toString() {
        return "REntityDTO{" + "enID=" + enID + ", restID=" + restID + ", enType=" + enType + ", enFee=" + enFee + ", activeTill=" + activeTill + ", seatCap=" + seatCap + ", forwardLim=" + forwardLim + ", daily=" + daily + ", weekly=" + weekly + '}';
    }

}
