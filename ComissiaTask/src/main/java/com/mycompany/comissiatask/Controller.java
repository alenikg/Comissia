
package com.mycompany.comissiatask;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author elenagoncarova
 */
public class Controller {

    private NewJFrame view;
    private Faker faker;
    private ArrayList<User> users;
    private Publisher publisher;

    public Controller() {
        this.faker = new Faker();
        this.users = new ArrayList<>();
        this.publisher = new Publisher();
    }

    public void setView(NewJFrame view) {
        this.view = view;
    }
    
    public void addUsers() {
        view.clearField();
        users.clear();
        
        for (int i = 0; i < 5; i++) {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            UserType userType = new Random().nextBoolean() ? UserType.PREMIUM : UserType.REGULAR;
            
            User user = UserFactory.createUser(name, email, userType);
            users.add(user);
            
            if (userType == UserType.PREMIUM) {
                publisher.subscribe(user);
            }
        }
        
        view.setTextArea("Create 5");
    }

    public void handleButtonGetUsersClick() {
        StringBuilder sb = new StringBuilder();
        sb.append("Premium:\n");
        
        for (User user : users) {
            if (user.getUserType() == UserType.PREMIUM) {
                sb.append(user.getStrUser()).append("\n");
            }
        }
        
        view.setTextArea(sb.toString());
        
        publisher.unsubscribeAll();
    }
    
    public Publisher getPublisher() {
        return publisher;
    }
}
    



