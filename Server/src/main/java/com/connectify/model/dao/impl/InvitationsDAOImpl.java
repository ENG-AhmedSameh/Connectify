package com.connectify.model.dao.impl;

import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.model.dao.InvitationsDAO;
import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;
import com.connectify.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvitationsDAOImpl implements InvitationsDAO {

    private final DBConnection dbConnection;

    public InvitationsDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(Invitations invitations) {
        String query = "INSERT INTO invitations (sender, receiver) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, invitations.getSender());
            preparedStatement.setString(2, invitations.getReceiver());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Invitations get(Integer key) {
        String query = "SELECT * FROM invitations WHERE invitation_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, key);
            Invitations invitations = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    invitations = new Invitations();
                    invitations.setInvitationId(resultSet.getInt("invitation_id"));
                    invitations.setSender(resultSet.getString("sender"));
                    invitations.setReceiver(resultSet.getString("receiver"));
                }
            }
            return invitations;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Invitations invitations) {
        String query = "UPDATE invitations SET sender = ?, receiver = ? WHERE invitation_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, invitations.getSender());
            preparedStatement.setString(2, invitations.getReceiver());
            preparedStatement.setInt(3, invitations.getInvitationId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer key) {
        String query = "DELETE FROM invitations WHERE invitation_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, key);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) {
        String query = "SELECT invitation_id FROM invitations WHERE sender = ? AND receiver = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, senderPhoneNumber);
            preparedStatement.setString(2, receiverPhoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }
    public List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String receiverPhoneNumber) {
        String query = "SELECT u.name, u.picture, u.phone_number, i.invitation_id " +
                "FROM invitations i " +
                "JOIN users u ON i.sender = u.phone_number " +
                "WHERE i.receiver = ? ";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, receiverPhoneNumber);
            List<IncomingFriendInvitationResponse> list = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null) {
                    while (resultSet.next()) {
                        var invitation = new IncomingFriendInvitationResponse();
                        invitation.setName(resultSet.getString("name"));
                        invitation.setPicture(resultSet.getBytes("picture"));
                        invitation.setPhoneNumber(resultSet.getString("phone_number"));
                        invitation.setInvitationId(resultSet.getInt("invitation_id"));
                        list.add(invitation);
                    }
                }
            }
            return list;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


}
