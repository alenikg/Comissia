/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

/**
 *
 * @author elenagoncarova
 */

public class UserFactory {
    
    public static User createUser(String name, String email, UserType userType) {
        return new User(name, email, userType);
    }
}
