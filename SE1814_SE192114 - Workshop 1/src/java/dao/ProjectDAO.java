/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.ProjectDTO;
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
public class ProjectDAO implements IDAO<ProjectDTO, String> {

    private int newID() {
        String sql = "  SELECT MAX(project_id)as MAXID FROM [tblStartupProjects]";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int res=-1;
            rs.next();
            if(rs.getInt("MAXID")>-1){
                return rs.getInt("MAXID")+1;
            }
            return res;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public boolean create(ProjectDTO entity) {
        String sql = "INSERT INTO [tblStartupProjects] ([project_id], [project_name], [Description], [Status], [estimated_launch]) "
                + "VALUES ( ? , ? , ?, ?, ?)";

        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newID());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            ps.setString(4, entity.getStatus());
            ps.setDate(5, entity.getEst_launch());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public List<ProjectDTO> readAll() {
        List<ProjectDTO> list = new ArrayList< ProjectDTO>();
        String sql = "SELECT * FROM [tblStartupProjects]";
        try {
            Connection conn = DBUtils.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ProjectDTO project = new ProjectDTO(
                        rs.getInt("project_id"),
                        rs.getString("project_name"),
                        rs.getString("Description"),
                        rs.getString("Status"),
                        rs.getDate("estimated_launch"));
                list.add(project);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public ProjectDTO searchByID(String id) {
        return null;
    }

    @Override
    public boolean update(ProjectDTO entity) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<ProjectDTO> search(String searchTerm) {
        return null;
    }

    public List<ProjectDTO> searchByName(String searchTerm) {

        List<ProjectDTO> list = new ArrayList< ProjectDTO>();
        String sql = "SELECT * FROM [tblStartupProjects] WHERE [project_name] LIKE ?";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProjectDTO project = new ProjectDTO(
                        rs.getInt("project_id"),
                        rs.getString("project_name"),
                        rs.getString("Description"),
                        rs.getString("Status"),
                        rs.getDate("estimated_launch"));
                list.add(project);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean updateStatus(int id, String newInfo) {
        String sql = " UPDATE [tblStartupProjects]  SET Status = ? WHERE [project_id] = ? ";
        try {
            Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newInfo);
            ps.setInt(2, id);
            int res = ps.executeUpdate();
            return res > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
