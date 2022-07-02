package com.vangelis.repository;

import com.vangelis.VangelisApplication;
import com.vangelis.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class UserRepository implements IUserRepository {
    public UserRepository() {
    }

    public void prueba() throws Exception {
        Properties properties = new Properties();
        properties.load(VangelisApplication.class.getClassLoader().getResourceAsStream("application.properties"));
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
        Scanner scanner = new Scanner(VangelisApplication.class.getClassLoader().getResourceAsStream("schema.sql"));
        Statement statement = connection.createStatement();

        while(scanner.hasNextLine()) {
            statement.execute(scanner.nextLine());
        }

        User user = new User(1L, "Mariano Rojas", "mariano@gmail.com", "1145856996");
        insertUser(user, connection);
        user = readUser(connection);
        user.setEmail("rojas1@gmail.com");
        updateUser(user, connection);
        connection.close();
    }

    private static void insertUser(User user, Connection connection) throws SQLException {
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO users (id, name, email, phone) VALUES (?, ?, ?, ?);");
        insertStatement.setLong(1, user.getId());
        insertStatement.setString(2, user.getName());
        insertStatement.setString(3, user.getEmail());
        insertStatement.setString(4, user.getPhone());
        insertStatement.executeUpdate();
    }

    private static User readUser(Connection connection) throws SQLException {
        PreparedStatement readStatement = connection.prepareStatement("SELECT * FROM users;");
        ResultSet resultSet = readStatement.executeQuery();
        if (!resultSet.next()) {
            return null;
        } else {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getString("phone"));
            return user;
        }
    }

    private static void updateUser(User user, Connection connection) throws SQLException {
        PreparedStatement updateStatement = connection.prepareStatement("UPDATE users SET name = ?, email = ?, phone = ? WHERE id = ?;");
        updateStatement.setString(1, user.getName());
        updateStatement.setString(2, user.getEmail());
        updateStatement.setString(3, user.getPhone());
        updateStatement.setLong(4, user.getId());
        updateStatement.executeUpdate();
        readUser(connection);
    }

    private static void deleteUser(User user, Connection connection) throws SQLException {
        PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?;");
        deleteStatement.setLong(1, user.getId());
        deleteStatement.executeUpdate();
        readUser(connection);
    }
}
