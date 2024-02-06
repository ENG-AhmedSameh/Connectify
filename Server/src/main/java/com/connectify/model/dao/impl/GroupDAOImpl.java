package com.connectify.model.dao.impl;

import com.connectify.dto.ContactsDTO;
import com.connectify.model.dao.GroupDAO;
import com.connectify.model.entities.Group;
import com.connectify.utils.DBConnection;

import java.sql.*;
import java.util.List;

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

    @Override
    public boolean createGroup(List<ContactsDTO> contactsDTOS, String groupName, String groupDescription, byte[] image) {
        String chatQuery = "INSERT INTO chat (is_Private_Chat, number_OF_Members) VALUES (?, ?)";
        String memberQuery = "INSERT INTO chat_members (chat_Id, member) VALUES (?, ?)";
        String groupQuery = "INSERT INTO `connectify_db`.`groups` (chat_id, name, picture, description) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);


            int chatId = insertIntoChatTable(connection, chatQuery, contactsDTOS.size());
            if (chatId == -1) {
                connection.rollback();
                return false;
            }

            if (!insertIntoChatMembersTable(connection, memberQuery, chatId, contactsDTOS)) {
                connection.rollback();
                return false;
            }

            if (!insertIntoGroupsTable(connection, groupQuery, chatId, groupName, groupDescription, image)) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to create group: " + e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Failed to rollback: " + ex.getMessage());
                e.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private int insertIntoChatTable(Connection connection, String chatQuery, int numberOfMembers) throws SQLException {
        try (PreparedStatement chatStatement = connection.prepareStatement(chatQuery, Statement.RETURN_GENERATED_KEYS)) {
            chatStatement.setInt(1, 0);
            chatStatement.setInt(2, numberOfMembers);

            int affectedRows = chatStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = chatStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        }
        return -1;
    }


    private boolean insertIntoChatMembersTable(Connection connection, String memberQuery, int chatId, List<ContactsDTO> contactsDTOS) throws SQLException {
        try (PreparedStatement memberStatement = connection.prepareStatement(memberQuery)) {
            for (ContactsDTO contactsDTO : contactsDTOS) {
                memberStatement.setInt(1, chatId);
                memberStatement.setString(2, contactsDTO.getPhoneNumber());
                memberStatement.addBatch();
            }
            memberStatement.executeBatch();
            return true;
        }
    }

    private boolean insertIntoGroupsTable(Connection connection,String groupQuery, int chatId,  String groupName, String groupDescription, byte[] image) throws SQLException {
        try (PreparedStatement groupStatement = connection.prepareStatement(groupQuery)) {
            groupStatement.setInt(1, chatId);
            groupStatement.setString(2, groupName);
            groupStatement.setBytes(3, image);
            groupStatement.setString(4, groupDescription);

            int rowsInserted = groupStatement.executeUpdate();
            return rowsInserted > 0;
        }
    }


}
