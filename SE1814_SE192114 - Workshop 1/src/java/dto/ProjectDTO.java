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
public class ProjectDTO {
    private int id;
    private String name;
    private String description;
    private String status;
    private Date est_launch;

    public ProjectDTO() {
    }

    public ProjectDTO(int id, String name, String description, String status, Date est_launch) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.est_launch = est_launch;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Date getEst_launch() {
        return est_launch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEst_launch(Date est_launch) {
        this.est_launch = est_launch;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" + "id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + ", est_launch=" + est_launch + '}';
    }
    
    
}

