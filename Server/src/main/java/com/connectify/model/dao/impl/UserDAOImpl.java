package com.connectify.model.dao.impl;

import com.connectify.model.dao.UserDAO;
import com.connectify.model.entities.User;
import com.connectify.model.enums.Gender;
import com.connectify.model.enums.Mode;
import com.connectify.model.enums.Status;
import com.connectify.utils.DBConnection;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private final DBConnection dbConnection;

    public UserDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(User user) {
        String query = "INSERT INTO users (phone_number, name, email, password, salt, gender, country, birth_date, mode, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBytes(5, user.getSalt());
            preparedStatement.setString(6, user.getGender().toString().equals("Male") ? "M" : "F");
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setDate(8, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(9, user.getMode().toString());
            preparedStatement.setString(10, user.getStatus().toString());
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
                    user.setSalt(resultSet.getBytes("salt"));
                    user.setPicture(resultSet.getString("picture"));
                    user.setGender(Gender.valueOf(resultSet.getString("gender").equals("M") ? "MALE" : "FEMALE"));
                    user.setCountry(resultSet.getString("country"));
                    user.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
                    user.setBio(resultSet.getString("bio"));
                    user.setStatus(Status.valueOf(resultSet.getString("status")));
                    user.setMode(Mode.valueOf(resultSet.getString("mode")));
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
        String query = "UPDATE users SET name = ?, email = ?, password = ?, salt = ?, picture = ?, gender = ?, " +
                "country = ?, birth_date = ?, bio = ?, mode = ?, status = ? WHERE phone_number = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setBytes(4, user.getSalt());
            preparedStatement.setString(5, user.getPicture());
            preparedStatement.setString(6, user.getGender().toString().equals("Male") ? "M" : "F");
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setObject(8, user.getBirthDate());
            preparedStatement.setString(9, user.getBio());
            preparedStatement.setString(10, user.getMode().toString());
            preparedStatement.setString(11, user.getStatus().toString());
            preparedStatement.setString(12, user.getPhoneNumber());

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
