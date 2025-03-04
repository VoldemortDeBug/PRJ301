/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DBUtils;

/**
 *
 * @author tungi
 */
public class UserDAO implements IDAO<UserDTO, Integer> {

    public int newID() {
        String sql = "  SELECT MAX(UserID)as MAXID FROM [Users]";
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
    public boolean create(UserDTO entity) {
        String sql = "INSERT INTO [Users] ([UserID], [Name], [Username], [Email], [Phone], [Password], [Coins], [Profile_Picture])"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ? )";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getUserID());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getUserName());
            ps.setString(4, entity.getEmail());
            ps.setString(5, entity.getPhone());
            ps.setString(6, entity.getPassword());
            ps.setInt(7, entity.getCoins());
            ps.setString(8, entity.getProfilepic());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<UserDTO> readAll() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [Users]";
        try {
            Connection conn = DBUtils.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getInt("UserID"),
                        rs.getString("Name"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Password"),
                        rs.getInt("Coins"),
                        rs.getString("Profile_Picture"));
                list.add(user);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public boolean update(UserDTO entity) {
        System.out.println("Updating for user...new data: "+entity);
        String sql = "   UPDATE [Users]  SET [Name] = ?, [Email] =?, [Phone] =? ,[Profile_Picture]=? WHERE [UserID] = ?;";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPhone());
            ps.setString(4, entity.getProfilepic());
            ps.setInt(5, entity.getUserID());
            int res = ps.executeUpdate();
            return res > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<UserDTO> search(String searchTerm) {
        return null;
    }

    @Override
    public UserDTO searchByID(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UserDTO searchByUsername(String username) {
        String sql = "SELECT * FROM [Users] WHERE [Username] = ? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO user = new UserDTO(
                        rs.getInt("UserID"),
                        rs.getString("Name"),
                        rs.getString("Username"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Password"),
                        rs.getInt("Coins"),
                        rs.getString("Profile_Picture"));
                return user;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
