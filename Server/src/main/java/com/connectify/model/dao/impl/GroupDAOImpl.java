package com.connectify.model.dao.impl;

import com.connectify.controller.utils.DBConnection;
import com.connectify.model.dao.GroupDAO;
import com.connectify.model.entities.Group;
import com.connectify.model.entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDAOImpl implements GroupDAO {
    private final DBConnection dbConnection;

    public GroupDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(Group group) {
        String query = "INSERT INTO Groups (chat_id, name, picture, description) VALUES (?, ?, ?,?)";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, group.getChatId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.setBytes(3, group.getPicture());
            preparedStatement.setString(4, group.getDescription());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Group get(Integer key) {
        String query = "SELECT * FROM Chats WHERE group_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, key);
            Group group = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    group = new Group();
                    group.setGroupId(resultSet.getInt("group_id"));
                    group.setChatId(resultSet.getInt("chat_id"));
                    group.setName(resultSet.getString("name"));
                    group.setPicture(resultSet.getBytes("picture"));
                    group.setDescription(resultSet.getString("description"));
                }
            }
            return group;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Group group) {
        String query = "UPDATE groups SET name, picture, description WHERE group_id = ?";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, group.getName());
            preparedStatement.setBytes(2, group.getPicture());
            preparedStatement.setString(3, group.getDescription());
            int rowUpdated = preparedStatement.executeUpdate();
            return rowUpdated > 0;
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer key) {
        String query = "DELETE * FROM groups WHERE group_id = ?";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, key);
            int rowDeleted = preparedStatement.executeUpdate();
            return rowDeleted > 0;
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
