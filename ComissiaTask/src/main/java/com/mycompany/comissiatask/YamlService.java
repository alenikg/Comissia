/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.comissiatask;

import com.github.javafaker.Faker;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author elenagoncarova
 */
public class YamlService {

    private static final String CONFIG_DIR = "config";
    private String yamlFilePath;

    public YamlService() {
        createConfigDirectory();
        generateYamlFile();
    }

    private void createConfigDirectory() {
        File configDir = new File(CONFIG_DIR);
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
    }

    private void generateYamlFile() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        yamlFilePath = CONFIG_DIR + "/users_data_" + timestamp + ".yaml";

        List<Map<String, Object>> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("id", UUID.randomUUID().toString());
            user.put("name", new Faker().name().fullName());
            user.put("email", new Faker().internet().emailAddress());
            user.put("userType", random.nextBoolean() ? "PREMIUM" : "REGULAR");
            users.add(user);
        }

        try (Writer writer = new FileWriter(yamlFilePath)) {
            Yaml yaml = new Yaml();
            yaml.dump(users, writer);
            System.out.println("File: " + yamlFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public List<User> loadUsersFromYaml(int count) {
        List<User> users = new ArrayList<>();

        if (!Files.exists(Paths.get(yamlFilePath))) {
            System.err.println("YAML файл не найден: " + yamlFilePath);
            return users;
        }

        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath));
            List<Map<String, Object>> userData = yaml.load(inputStream);

            int usersToLoad = Math.min(count, userData.size());

            for (int i = 0; i < usersToLoad; i++) {
                Map<String, Object> data = userData.get(i);
                User user = UserFactory.createUser(
                        (String) data.get("name"),
                        (String) data.get("email"),
                        UserType.valueOf((String) data.get("userType")),
                        "YAML"
                );
                users.add(user);
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        return users;
    }

    public String getYamlFilePath() {
        return yamlFilePath;
    }
}
