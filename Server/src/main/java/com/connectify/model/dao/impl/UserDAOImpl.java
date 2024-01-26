package com.connectify.model.dao.impl;

import com.connectify.model.dao.UserDAO;
import com.connectify.controller.utils.DBConnection;
import model.entities.User;
import model.enums.Gender;
import model.enums.Mode;
import model.enums.Status;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private final DBConnection dbConnection;

    public UserDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(User user) {
        String query = "INSERT INTO users (phone_number, name, email, password, picture, gender, country, birth_date, bio, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPicture());
            preparedStatement.setString(6, user.getGender().toString());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setDate(8, new Date(user.getBirthDate().getTime()));
            preparedStatement.setString(9, user.getBio());
            preparedStatement.setString(10, user.getMode().toString());
            preparedStatement.setString(11, user.getStatus().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User get(String key) {
        String query = "SELECT * FROM users WHERE phone_number = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, key);
            User user = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setPicture(resultSet.getString("picture"));
                    user.setGender(Gender.valueOf(resultSet.getString("gender")));
                    user.setCountry(resultSet.getString("country"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setBio(resultSet.getString("bio"));
                    user.setMode(Mode.valueOf(resultSet.getString("mode")));
                    user.setStatus(Status.valueOf(resultSet.getString("status")));
                }
            }
            return user;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE users SET name = ?, email = ?, password = ?, picture = ?, gender = ?, " +
                "country = ?, birth_date = ?, bio = ?, mode = ?, status = ? WHERE phone_number = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPicture());
            preparedStatement.setString(5, user.getGender().toString());
            preparedStatement.setString(6, user.getCountry());
            preparedStatement.setDate(7, new java.sql.Date(user.getBirthDate().getTime()));
            preparedStatement.setString(8, user.getBio());
            preparedStatement.setString(9, user.getMode().toString());
            preparedStatement.setString(10, user.getStatus().toString());
            preparedStatement.setString(11, user.getPhoneNumber());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String key) {
        String query = "DELETE FROM users WHERE phone_number = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, key);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
