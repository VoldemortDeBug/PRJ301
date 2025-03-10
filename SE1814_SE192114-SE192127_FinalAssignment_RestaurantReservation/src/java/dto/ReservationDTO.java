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
public class ReservationDTO {

    private int rsvID;
    private int userID;
    private int entID;
    private int hours;
    private Date date;
    private int seats;

    public ReservationDTO() {
    }

    public ReservationDTO(int rsvID, int userID, int entID, int hours, Date date, int seats) {
        this.rsvID = rsvID;
        this.userID = userID;
        this.entID = entID;
        this.hours = hours;
        this.date = date;
        this.seats = seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public int getRsvID() {
        return rsvID;
    }

    public int getUserID() {
        return userID;
    }

    public int getEntID() {
        return entID;
    }

    public int getHours() {
        return hours;
    }

    public Date getDate() {
        return date;
    }

    public void setRsvID(int rsvID) {
        this.rsvID = rsvID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEntID(int entID) {
        this.entID = entID;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" + "rsvID=" + rsvID + ", userID=" + userID + ", entID=" + entID + ", hours=" + hours + ", date=" + date + ", seats=" + seats + '}';
    }

}
