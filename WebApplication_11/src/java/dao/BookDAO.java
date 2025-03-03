/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Admin
 */
public class BookDAO implements IDAO<BookDTO, String>{

    @Override
    public boolean create(BookDTO entity) {
        return false;
    }

    @Override
    public List<BookDTO> readAll() {
        return null;
    }

    @Override
    public BookDTO searchByID(String id) {
        return null;
    }

    @Override
    public boolean update(BookDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "UPDATE tblBooks SET Quantity = 0 WHERE BookID = ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int i = ps.executeUpdate();
            return i>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<BookDTO> search(String searchTerm) {
        return null;
    }
    
    public List<BookDTO> searchByTitle(String searchTitle){
        List<BookDTO> res = new ArrayList<>();
        String sql = "SELECT * FROM tblBooks WHERE title LIKE ? AND Quantity>0";
        
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+searchTitle+"%");
            System.out.println("searching by title "+searchTitle);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                BookDTO newbook = new BookDTO(
                        rs.getString("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity")
                );
                res.add(newbook);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<BookDTO> searchByTitleOLDVER(String searchTitle){
        List<BookDTO> res = new ArrayList<>();
        String sql = "SELECT * FROM tblBooks WHERE title LIKE ?";
        
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+searchTitle+"%");
            System.out.println("searching by title "+searchTitle);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                BookDTO newbook = new BookDTO(
                        rs.getString("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("PublishYear"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity")
                );
                res.add(newbook);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
