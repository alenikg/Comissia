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
    private DisplayStrategy displayStrategy;

    public Controller() {
        this.faker = new Faker();
        this.users = new ArrayList<>();
        this.publisher = new Publisher();
        this.displayStrategy = new SimpleDisplayStrategy();
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
        StringBuilder ctreatedUsers = new StringBuilder();
        for (User user : users) {
            ctreatedUsers.append(user.getSimpleStrUser())
                    .append(" | Тип: ")
                    .append(user.getUserType())
                    .append("\n");
        }
        view.setTextArea(ctreatedUsers.toString());
    }

    public void setDisplayStrategy(DisplayStrategy strategy) {
        this.displayStrategy = strategy;
    }

    public void handleButtonGetUsersClick() {
        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            if (user.getUserType() == UserType.PREMIUM) {
                sb.append(displayStrategy.format(user)).append("\n");
            }
        }

        view.setTextArea(sb.toString());
        publisher.unsubscribeAll();
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
