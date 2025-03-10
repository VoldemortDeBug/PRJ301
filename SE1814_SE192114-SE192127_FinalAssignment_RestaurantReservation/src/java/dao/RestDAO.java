/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.PhotoDTO;
import dto.RestDTO;
import dto.UserDTO;
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
public class RestDAO implements IDAO<RestDTO, Integer> {

    public int newID() {
        String sql = "  SELECT MAX(RestaurantID)as MAXID FROM [Restaurants]";
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
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public boolean create(RestDTO entity) {

        String sql = "INSERT INTO [Restaurants] ([RestaurantID], [Name], [Location], [OwnerID], [MainPhoto])"
                + " VALUES (?, ?, ?, ?, ? )";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getResID());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getLoc());
            ps.setInt(4, entity.getOwnerID());
            ps.setString(5, entity.getMainPhoto());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<RestDTO> readAll() {
        String sql = "SELECT * FROM [Restaurants]  ";
        List<RestDTO> lrest = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RestDTO rest = new RestDTO(
                        rs.getInt("RestaurantID"),
                        rs.getString("Name"),
                        rs.getString("Location"),
                        rs.getInt("OwnerID"),
                        rs.getString("MainPhoto")
                );
                lrest.add(rest);
            }
            return lrest;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public RestDTO searchByID(Integer id) {
        String sql = "SELECT * FROM [Restaurants] WHERE [RestaurantID] = ? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RestDTO rest = new RestDTO(
                        rs.getInt("RestaurantID"),
                        rs.getString("Name"),
                        rs.getString("Location"),
                        rs.getInt("OwnerID"),
                        rs.getString("MainPhoto")
                );
                return rest;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean update(RestDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM [Restaurants] WHERE [RestaurantID] = ? ;";
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
    public List<RestDTO> search(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<RestDTO> searchOwnedBy(Integer ownerID) {
        String sql = "SELECT * FROM [Restaurants] WHERE [OwnerID] = ? ";
        List<RestDTO> lrest = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ownerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RestDTO rest = new RestDTO(
                        rs.getInt("RestaurantID"),
                        rs.getString("Name"),
                        rs.getString("Location"),
                        rs.getInt("OwnerID"),
                        rs.getString("MainPhoto")
                );
                lrest.add(rest);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lrest;
    }

    public RestDTO restOwnedBy(int restID, int userID) {
        String sql = "SELECT * FROM [Restaurants] WHERE [OwnerID]=? AND [RestaurantID]=?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userID);
            ps.setInt(2, restID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RestDTO rest = new RestDTO(
                        rs.getInt("RestaurantID"),
                        rs.getString("Name"),
                        rs.getString("Location"),
                        rs.getInt("OwnerID"),
                        rs.getString("MainPhoto")
                );
                return rest;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateMainPhoto(int restID, String mainphoto) {
        String sql = " UPDATE [Restaurants]  SET MainPhoto= ? WHERE [RestaurantID] = ? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mainphoto);
            ps.setInt(2, restID);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(REntityDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
