package com.connectify.model.dao.impl;

import com.connectify.model.dao.MessageDAO;
import com.connectify.model.entities.Message;
import com.connectify.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAOImpl implements MessageDAO{
    private final DBConnection dbConnection;

    public MessageDAOImpl() {
        dbConnection = DBConnection.getInstance();
    }

    @Override
    public boolean insert(Message message) {
        String query = "INSERT INTO Message (sender, chat_id, content, attachement_id) VALUES (?,?,?,?)";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, message.getSender());
            preparedStatement.setInt(2, message.getChatId());
            preparedStatement.setString(3, message.getContent());
            preparedStatement.setInt(4, message.getAttachmentId());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }
    @Override
    public Message insertSentMessage(Message clientMessage) {
        String query = "INSERT INTO Message (sender, chat_id,timestamp, content, attachment_id) VALUES (?,?,?,?,?)";

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            String sender = clientMessage.getSender();
            int chatID = clientMessage.getChatId();
            Timestamp timestamp = clientMessage.getTimestamp();
            String content = clientMessage.getContent();
            Integer attachmentID =clientMessage.getAttachmentId();
            preparedStatement.setString(1, sender);
            preparedStatement.setInt(2, chatID);
            preparedStatement.setTimestamp(3,timestamp);
            preparedStatement.setString(4, content);
            preparedStatement.setObject(5,attachmentID);
            int rowsInserted = preparedStatement.executeUpdate();
            Message insertedMessage=null;
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int messageId = rs.getInt(1);
                    insertedMessage = generateMessage(messageId,sender,chatID,content,timestamp,attachmentID);
                }
            }

            return insertedMessage;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Message> getAllChatMessagesUntilLimit(int chatID, Integer idLimit) {
        String query = "SELECT * FROM message WHERE chat_id = ? AND message_id< ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, chatID);
            preparedStatement.setInt(2, idLimit);
            List<Message> messagesList = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setSender(resultSet.getString("sender"));
                    message.setChatId(resultSet.getInt("chat_id"));
                    message.setTimestamp(resultSet.getTimestamp("timestamp"));
                    message.setContent(resultSet.getString("content"));
                    message.setAttachmentId((Integer) resultSet.getObject("attachment_id"));
                    messagesList.add(message);
                }
            }
            return messagesList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Message> getAllChatMessages(int chatID) {
        String query = "SELECT * FROM message WHERE chat_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, chatID);
            List<Message> messagesList = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setSender(resultSet.getString("sender"));
                    message.setChatId(resultSet.getInt("chat_id"));
                    message.setTimestamp(resultSet.getTimestamp("timestamp"));
                    message.setContent(resultSet.getString("content"));
                    message.setAttachmentId((Integer)resultSet.getObject("attachment_id"));
                    messagesList.add(message);
                }
            }
            return messagesList;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Message generateMessage(int messageID, String sender, int chatID, String content, Timestamp timestamp, Integer attachmentID){
        Message message = new Message();
        message.setMessageId(messageID);
        message.setSender(sender);
        message.setChatId(chatID);
        message.setTimestamp(timestamp);
        message.setContent(content);
        message.setAttachmentId(attachmentID);
        return message;
    }

    @Override
    public Message get(Integer key) {
        String query = "SELECT * FROM message WHERE chat_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, key);
            Message message = null;
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    message = new Message();
                    message.setMessageId(resultSet.getInt("message_id"));
                    message.setSender(resultSet.getString("sender"));
                    message.setChatId(resultSet.getInt("chat_id"));
                    message.setTimestamp(resultSet.getTimestamp("timeStamp"));
                    message.setContent(resultSet.getString("content"));
                    message.setAttachmentId((Integer) resultSet.getObject("attachment_id"));
                }
            }
            return message;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Message message) {
        String query = "UPDATE message SET content WHERE message_id = ?";
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, message.getContent());
            int rowUpdated = preparedStatement.executeUpdate();
            return rowUpdated > 0;
        } catch (SQLException e){
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Integer key) {
        String query = "DELETE * FROM message WHERE message_id = ?";
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
