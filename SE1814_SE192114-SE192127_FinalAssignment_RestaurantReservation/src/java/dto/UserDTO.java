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
public class UserDTO {

    private String name;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private int coins;
    private String profilepic;
    
    
    public UserDTO() {
    }

    public UserDTO(String name, String userName, String email, String phone, String password, int coins, String profilepic) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.coins = coins;
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public int getCoins() {
        return coins;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "name=" + name + ", userName=" + userName + ", email=" + email + ", phone=" + phone + ", password=" + password + ", coins=" + coins + ", profilepic=" + profilepic + '}';
    }
    

}
