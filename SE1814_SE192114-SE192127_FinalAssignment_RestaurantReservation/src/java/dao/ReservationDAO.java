/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.ReservationDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBUtils;

/**
 *
 * @author Admin
 */
public class ReservationDAO implements IDAO<ReservationDTO, Integer> {

    public int newID() {
        String sql = "  SELECT MAX(ReserveID) as MAXID FROM [Reservations]";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int res = -1;
            rs.next();
            if (rs.getInt("MAXID") > -1) {
                return rs.getInt("MAXID") + 1;
            }
            return res;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public boolean create(ReservationDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ReservationDTO> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReservationDTO searchByID(Integer id) {
        String sql = "SELECT * FROM [Reservations] WHERE [ReserveID]=? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean update(ReservationDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM [Reservations] WHERE ReserveID= ? ;";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(REntityDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(REntityDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<ReservationDTO> search(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ReservationDTO> GetReservationsByUser(Integer userID) {
        String sql = "SELECT * FROM [Reservations] WHERE [UserID]=? AND [Date] >= CAST(GETDATE() AS DATE) ";
        try {
            List<ReservationDTO> lresv = new ArrayList<>();
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservationDTO resv = new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
                lresv.add(resv);
            }
            return lresv;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ReservationDTO GetReservationByUser(Integer userID, Integer rsvID) {
        String sql = "SELECT * FROM [Reservations] WHERE [UserID]=? AND [ReserveID]=? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, rsvID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<ReservationDTO> GetReservationsAtDate(Date date, int entID) {
        String sql = "SELECT * FROM [Reservations] WHERE [Date]=? AND [EntityID] = ?";
        List<ReservationDTO> lresv = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setInt(2, entID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservationDTO resv = new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
                lresv.add(resv);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lresv;
    }

    public List<ReservationDTO> GetReservationsAtEntity(int entID) {
        String sql = "SELECT * FROM [Reservations] WHERE [EntityID] = ?";
        List<ReservationDTO> lresv = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservationDTO resv = new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
                lresv.add(resv);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lresv;
    }

    public List<ReservationDTO> GetActiveReservationsAtEntity(int entID) {
        String sql = "SELECT * FROM [Reservations] WHERE [EntityID] = ? AND [Date] >= CAST(GETDATE() AS DATE)";
        List<ReservationDTO> lresv = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservationDTO resv = new ReservationDTO(
                        rs.getInt("ReserveID"),
                        rs.getInt("UserID"),
                        rs.getInt("EntityID"),
                        rs.getInt("Hours"),
                        rs.getDate("Date"),
                        rs.getInt("Seats")
                );
                lresv.add(resv);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lresv;
    }

    public boolean makeReservation(ReservationDTO entity, int totalFee, int ownerID) {
        String sql = "{CALL MakeReservationAndPay(?, ?, ?, ?, ?, ?, ?, ?)}";
        try {
            Connection conn = DBUtils.getConnection();
            CallableStatement stmt = conn.prepareCall(sql); // Use CallableStatement for stored procedures

            stmt.setInt(1, entity.getRsvID());
            stmt.setInt(2, entity.getUserID());
            stmt.setInt(3, entity.getEntID());
            stmt.setInt(4, entity.getHours());
            stmt.setDate(5, entity.getDate());
            stmt.setInt(6, entity.getSeats());
            stmt.setInt(7, totalFee);
            stmt.setInt(8, ownerID);

            stmt.execute();
            return true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
