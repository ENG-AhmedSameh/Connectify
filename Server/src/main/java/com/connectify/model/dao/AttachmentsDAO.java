package com.connectify.model.dao;

import com.connectify.model.entities.Attachments;
import com.connectify.controller.utils.DBConnection;
import java.sql.*;

public class AttachmentsDAO implements DAO<Attachments, Integer> {

    private final DBConnection dbConnection;

    public AttachmentsDAO() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(Attachments attachments) {
        String query = "INSERT INTO attachments (attachment_id, name, extension, size) VALUES (?, ?, ?, ?)";
        try
        {
            Connection connection = dbConnection.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, attachments.getAttachmentsId());
            preparedStatement.setInt(2, attachments.getName());
            preparedStatement.setString(3, attachments.getExtension());
            preparedStatement.setInt(4, attachments.getSize());
            int rowsInserted = preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnection.closeConnection();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Attachments get(Integer key) {
        String query = "SELECT * FROM attachments WHERE attachment_id = ?";
        try {
            Connection connection = dbConnection.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, key);
            Attachments attachments = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    attachments = new Attachments();
                    attachments.setAttachmentsId(resultSet.getInt("attachment_id"));
                    attachments.setName(resultSet.getInt("name"));
                    attachments.setExtension(resultSet.getString("extension"));
                    attachments.setSize(resultSet.getInt("size"));
                }
            }
            preparedStatement.close();
            dbConnection.closeConnection();
            return attachments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Attachments attachments) {
        String query = "UPDATE attachments SET name = ?, extension = ?, size = ? WHERE attachment_id = ?";
        try{
            Connection connection = dbConnection.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, attachments.getName());
            preparedStatement.setString(2, attachments.getExtension());
            preparedStatement.setInt(3, attachments.getSize());
            preparedStatement.setInt(4, attachments.getAttachmentsId());
            int rowUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnection.closeConnection();
            return rowUpdated > 0;
        }catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer key) {
        String query = "DELETE FROM attachments WHERE attachment_id = ?";
        try{
            Connection connection = dbConnection.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int rowDeleted = preparedStatement.executeUpdate();
            preparedStatement.close();
            dbConnection.closeConnection();
            return rowDeleted > 0;
        }catch (SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
