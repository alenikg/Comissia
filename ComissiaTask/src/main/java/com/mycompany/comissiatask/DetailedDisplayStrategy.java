/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

/**
 *
 * @author elenagoncarova
 */
public class DetailedDisplayStrategy implements DisplayStrategy {
    @Override
    public String format(User user) {
        return "[ID: " + user.getId() + "] " + user.getName() + 
               " (" + user.getEmail() + ") | Status: " + user.getUserType();
    }
    
}
