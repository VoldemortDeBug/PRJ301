/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.RestDAO;
import dto.REntityDTO;
import dto.ReservationDTO;
import dto.RestDTO;
import dto.UserDTO;
import static java.lang.Math.abs;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
public class Utils {

    public static final int[] PO2 = {
        1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
        1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072,
        262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432
    };

    public static boolean validString(String s) {
        return (!s.equals("") && s.trim().length() > 0);
    }
    

    public static Date xDaysFromy(int x, Date y) {
        LocalDate localDate = y.toLocalDate().plusDays(x);
        return Date.valueOf(localDate);
    }

    public static Date xdaysFromNow(int x) {
        Date date = new Date(System.currentTimeMillis());
        LocalDate localDate = date.toLocalDate().plusDays(x);
        return Date.valueOf(localDate);
    }

    public static boolean isActiveEntity(String date) {
        try {
            if (date != null && !date.equals("")) {
                Date edate = Date.valueOf(date);
                Date currentSqlDate = new Date(System.currentTimeMillis());
                System.out.println(edate + " compare to" + currentSqlDate);
                if (!currentSqlDate.after(edate)) {
                    return true;
                }
            }

        } catch (Exception e) {
        }
        return false;
    }

    public static int daily(int x) {
        if (x < 0) {
            return -1;
        }
        return PO2[x];
    }

    public static String weekday(int x) {
        switch (x) {
            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
            default:
                return "Invalid day"; // Handles invalid input
        }
    }

    public static int weekly(String d) {
        if (d.equals("Monday")) {
            return PO2[0];
        }
        if (d.equals("Tuesday")) {
            return PO2[1];
        }
        if (d.equals("Wednesday")) {
            return PO2[2];
        }
        if (d.equals("Thursday")) {
            return PO2[3];
        }
        if (d.equals("Friday")) {
            return PO2[4];
        }
        if (d.equals("Saturday")) {
            return PO2[5];
        }
        if (d.equals("Sunday")) {
            return PO2[6];
        }
        return -1;
    }
    
    public static int toWeekDay(Date d){
        LocalDate date = d.toLocalDate();
        DayOfWeek dow = date.getDayOfWeek();
        String weekday = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return weekly(weekday);
    }

    public static boolean hasbit(int x, int y) {
        for (int i = 0; i < 24; i++) {
            int a = abs(x / PO2[i]);
            int b = abs(y / PO2[i]);
            if (b % 2 == 1 && a % 2 != 1) {
                return false;
            }
        }
        return true;
    }
    
    public static int reservationAtTime(List<ReservationDTO> lresv ,int time, Date date) {
        //time = 0~23
        for(ReservationDTO i:lresv){
            if(i.getDate().equals(date) && Utils.hasbit(i.getHours(), PO2[time]) ){
                return i.getRsvID();
            }
        }
        return -1;
    }

}
