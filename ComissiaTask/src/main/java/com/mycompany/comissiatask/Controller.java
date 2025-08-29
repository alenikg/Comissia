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
    private YamlService yamlService;
    private DatabaseService databaseService;

    public Controller() {
        this.faker = new Faker();
        this.users = new ArrayList<>();
        this.publisher = new Publisher();
        this.yamlService = new YamlService();
        this.databaseService = new DatabaseService();
        
        UserFactory.setYamlService(yamlService);
        UserFactory.setDatabaseService(databaseService);
        this.displayStrategy = new SimpleDisplayStrategy();
    }

    public void setView(NewJFrame view) {
        this.view = view;
    }

    public void addUsers() {
        view.clearField();
        users.clear();

        users.addAll(UserFactory.createUsers());

        for (User user : users) {
            if (user.getUserType() == UserType.PREMIUM) {
                publisher.subscribe(user);
            }
        }

        StringBuilder createdUsers = new StringBuilder();
        for (User user : users) {
            createdUsers.append(user.getSimpleStrUser())
                    .append(" | Тип: ")
                    .append(user.getUserType())
                    .append("\n");
        }
        view.setTextArea(createdUsers.toString());
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
    
    public void handleClearDatabase() {
        databaseService.clearDatabase();
        view.setTextArea("База данных очищена. Начните новый сеанс.");
    }

    public void close() {
        if (databaseService != null) {
            databaseService.close();
        }
    }
}

