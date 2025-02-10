/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Admin
 */
public class UserDTO {

    private String userID;
    private String fullname;
    private String roleID;
    private String password;

    public UserDTO() {
    }

    public UserDTO(String userIDl, String fullname, String roleID, String password) {
        this.userID = userIDl;
        this.fullname = fullname;
        this.roleID = roleID;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRoleID() {
        return roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setUserIDl(String userIDl) {
        this.userID = userIDl;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
