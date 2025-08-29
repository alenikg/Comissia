
package com.mycompany.comissiatask;


import java.util.UUID;

/**
 *
 * @author elenagoncarova
 */
public class User implements Subscriber {

    private UUID id;
    private String name;
    private String email;
    private UserType userType;

    
    public User(String name, String email, UserType userType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.userType = userType;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    @Override
    public void handleEvent() {
        if (userType == UserType.PREMIUM) {
            System.out.println("PREMIUM пользователь " + name + " получил уведомление");
        }
    }
    
    public String getStrUser() {
        return name + " | " + email;
    }
       
}

