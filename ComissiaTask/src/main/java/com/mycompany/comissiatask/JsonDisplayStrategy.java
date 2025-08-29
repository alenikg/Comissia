/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

/**
 *
 * @author elenagoncarova
 */
public class JsonDisplayStrategy implements DisplayStrategy {
    @Override
    public String format(User user) {
        return "{\"id\": \"" + user.getId() + "\", \"name\": \"" + user.getName() + 
               "\", \"email\": \"" + user.getEmail() + "\", \"userType\": \"" + 
               user.getUserType() + "\"}";
    } 
}
