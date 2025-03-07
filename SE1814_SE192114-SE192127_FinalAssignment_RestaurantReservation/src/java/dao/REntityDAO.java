/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.REntityDTO;
import dto.RestDTO;
import java.sql.Connection;
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
public class REntityDAO implements IDAO<REntityDTO, Integer> {

    public int newID() {
        String sql = "  SELECT MAX(EntityID)as MAXID FROM [ReservedEntites]";
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
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public boolean create(REntityDTO entity) {
        String sql = "INSERT INTO [ReservedEntities] ([EntityID], [RestaurantID], [Type], [ReservationFee], [ActiveTill], [SeatCap], [ForwardLim], [Daily],[Weekly])"
                + " VALUES (?, ?, ?, ?, ?, ? ,? ,?, ? )";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getEnID());
            ps.setInt(2, entity.getRestID());
            ps.setString(3, entity.getEnType());
            ps.setDouble(4, entity.getEnFee());
            ps.setDate(5, entity.getActiveTill());
            ps.setInt(6, entity.getSeatCap());
            ps.setInt(7, entity.getForwardLim());
            ps.setInt(8, entity.getDaily());
            ps.setInt(9, entity.getWeekly());
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
    public List<REntityDTO> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public REntityDTO searchByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(REntityDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<REntityDTO> search(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public REntityDTO GetResvEntity(Integer restID, Integer entID) {
        String sql = "SELECT * FROM [ReservedEntites] WHERE [RestaurantID]=? AND [EntityID]=?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, restID);
            ps.setInt(2, entID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new REntityDTO(
                        rs.getInt("EntityID"),
                        rs.getInt("RestaurantID"), rs.getString("Type"),
                        rs.getDouble("ReservationFee"),
                        rs.getDate("ActiveTill"),
                        rs.getInt("SeatCap"),
                        rs.getInt("ForwardLim"),
                        rs.getInt("Daily"),
                        rs.getInt("Weekly")
                );
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<REntityDTO> EntitiesAtRest(Integer restID) {
        String sql = "SELECT * FROM [ReservedEntities] WHERE [RestaurantID] = ? ";
        List<REntityDTO> lrest = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, restID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                REntityDTO rent = new REntityDTO(
                        rs.getInt("EntityID"),
                        rs.getInt("RestaurantID"), rs.getString("Type"),
                        rs.getDouble("ReservationFee"),
                        rs.getDate("ActiveTill"),
                        rs.getInt("SeatCap"),
                        rs.getInt("ForwardLim"),
                        rs.getInt("Daily"),
                        rs.getInt("Weekly"));
                lrest.add(rent);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lrest;
    }

}
