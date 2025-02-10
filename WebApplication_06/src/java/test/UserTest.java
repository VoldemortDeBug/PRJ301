/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.IDAO;
import dao.UserDAO;
import dto.UserDTO;

/**
 *
 * @author Admin
 */
public class UserTest {

    public static void main(String[] args) {

        UserDTO u1 = new UserDTO("NAT02", "Natrix", "AD", "nopass");
        UserDAO udao = new UserDAO();
        /*
        System.out.println(udao.create(u1));
        for(int i=0;i<10;i++){
            u1= new UserDTO("User"+i, "just a user", "US", "nopass");
            udao.create(u1);
        }*/
    }
}
