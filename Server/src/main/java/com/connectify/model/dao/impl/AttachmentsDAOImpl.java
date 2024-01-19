package com.connectify.model.dao.impl;

import com.connectify.model.dao.AttachmentDAO;
import com.connectify.model.entities.Attachments;
import com.connectify.controller.utils.DBConnection;
import java.sql.*;

public class AttachmentsDAOImpl implements AttachmentDAO{

    private final DBConnection dbConnection;

    public AttachmentsDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(Attachments attachments) {
        String query = "INSERT INTO attachments (name, extension, size) VALUES (?, ?, ?)";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, attachments.getName());
            preparedStatement.setString(2, attachments.getExtension());
            preparedStatement.setInt(3, attachments.getSize());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Attachments get(Integer key) {
        String query = "SELECT * FROM attachments WHERE attachment_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, key);
            Attachments attachments = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    attachments = new Attachments();
                    attachments.setAttachmentsId(resultSet.getInt("attachment_id"));
                    attachments.setName(resultSet.getString("name"));
                    attachments.setExtension(resultSet.getString("extension"));
                    attachments.setSize(resultSet.getInt("size"));
                }
            }
            return attachments;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Attachments attachments) {
        String query = "UPDATE attachments SET name = ?, extension = ?, size = ? WHERE attachment_id = ?";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, attachments.getName());
            preparedStatement.setString(2, attachments.getExtension());
            preparedStatement.setInt(3, attachments.getSize());
            preparedStatement.setInt(4, attachments.getAttachmentsId());
            int rowUpdated = preparedStatement.executeUpdate();
            return rowUpdated > 0;
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer key) {
        String query = "DELETE FROM attachments WHERE attachment_id = ?";
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
