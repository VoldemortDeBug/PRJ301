/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.PhotoDTO;
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
public class RestPhotoDAO implements IDAO<PhotoDTO, Integer> {

    public int newID() {
        String sql = "  SELECT MAX(PhotoID) as MAXID FROM [RestaurantPhoto]";
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
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    @Override
    public boolean create(PhotoDTO entity) {
        String sql = "INSERT INTO [RestaurantPhoto] ([PhotoID], [RestaurantID], [Photo])"
                + " VALUES (?, ?, ?)";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getPhotoID());
            ps.setInt(2, entity.getRestID());
            ps.setString(3, entity.getPhoto());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<PhotoDTO> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PhotoDTO searchByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(PhotoDTO entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        String sql="DELETE FROM [RestaurantPhoto] WHERE [PhotoID]=?;";
         try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(RestPhotoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<PhotoDTO> search(String searchTerm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<PhotoDTO> getAllPhoto(int restID) {
        String sql = "SELECT * FROM [RestaurantPhoto] WHERE [RestaurantID] = ? ";
        List<PhotoDTO> lphoto = new ArrayList<>();
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, restID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhotoDTO photo = new PhotoDTO(
                        rs.getInt("PhotoID"),
                        rs.getInt("RestaurantID"),
                        rs.getString("Photo")
                );
                lphoto.add(photo);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lphoto;
    }
}
