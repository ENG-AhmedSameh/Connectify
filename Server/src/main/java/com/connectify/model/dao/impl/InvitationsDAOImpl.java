package com.connectify.model.dao.impl;

import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.model.dao.InvitationsDAO;
import com.connectify.model.entities.Invitations;
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
    public int isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) {
        String query = "SELECT invitation_id FROM invitations WHERE sender = ? AND receiver = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, senderPhoneNumber);
            preparedStatement.setString(2, receiverPhoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("invitation_id");
                }
                return -1;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return -1;
        }
    }

    @Override
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
                        var invitation = mapResultSetToInvitationResponse(resultSet);
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

    @Override
    public IncomingFriendInvitationResponse getIncomingFriendRequest(String senderPhoneNumber, String receiverPhoneNumber) {
        String query = "SELECT u.name, u.picture, u.phone_number, i.invitation_id " +
                "FROM invitations i " +
                "JOIN users u ON i.sender = u.phone_number " +
                "WHERE i.receiver = ? AND i.sender = ? ";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, receiverPhoneNumber);
            preparedStatement.setString(2, senderPhoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToInvitationResponse(resultSet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Get incoming friend request error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private IncomingFriendInvitationResponse mapResultSetToInvitationResponse(ResultSet resultSet) throws SQLException {
        IncomingFriendInvitationResponse invitation = new IncomingFriendInvitationResponse();
        invitation.setName(resultSet.getString("name"));
        invitation.setPicture(resultSet.getBytes("picture"));
        invitation.setPhoneNumber(resultSet.getString("phone_number"));
        invitation.setInvitationId(resultSet.getInt("invitation_id"));
        return invitation;
    }


    @Override
    public boolean acceptFriendRequest(int invitationId) {
        Invitations invitations = this.get(invitationId);

        String insertQuery = "INSERT INTO contacts (user, contact) VALUES (?, ?)";
        String deleteQuery = "DELETE FROM invitations WHERE invitation_id = ?";

        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                 PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

                insertStatement.setString(1, invitations.getSender());
                insertStatement.setString(2, invitations.getReceiver());
                insertStatement.addBatch();

                deleteStatement.setInt(1, invitationId);
                deleteStatement.addBatch();

                int[] insertResults = insertStatement.executeBatch();
                int[] deleteResults = deleteStatement.executeBatch();

                boolean success = insertResults[0] > 0 && deleteResults[0] > 0;

                if (success) {
                    connection.commit();
                } else {
                    connection.rollback();
                }

                connection.setAutoCommit(true);

                return success;

            } catch (SQLException e) {
                connection.rollback();
                System.err.println("SQLException: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }



}
