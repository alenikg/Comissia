/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

import java.util.ArrayList;
import java.util.List;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author elenagoncarova
 */

public class UserFactory {
    private static YamlService yamlService;
    private static DatabaseService databaseService;
    private static Faker faker = new Faker();
    private static Random random = new Random();

    public static void setYamlService(YamlService service) {
        yamlService = service;
    }

    public static void setDatabaseService(DatabaseService service) {
        databaseService = service;
    }

    public static User createUser(String name, String email, UserType userType, String dataSource) {
        User user = new User(name, email, userType);
        if (databaseService != null) {
            databaseService.saveUser(user, dataSource);
        }
        return user;
    }

    public static List<User> createUsers() {
        List<User> users = new ArrayList<>();

        try {
            if (yamlService != null) {
                List<User> yamlUsers = yamlService.loadUsersFromYaml(3);
                if (yamlUsers != null) {
                    for (User user : yamlUsers) {
                        if (databaseService != null) {
                            databaseService.saveUser(user, "YAML");
                        }
                    }
                    users.addAll(yamlUsers);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки из YAML: " + e.getMessage());
        }

        for (int i = 0; i < 2; i++) {
            String name = faker.name().fullName();
            String email = faker.internet().emailAddress();
            UserType userType = random.nextBoolean() ? UserType.PREMIUM : UserType.REGULAR;
            
            User user = createUser(name, email, userType, "FAKER");
            users.add(user);
        }

        return users;
    }
}