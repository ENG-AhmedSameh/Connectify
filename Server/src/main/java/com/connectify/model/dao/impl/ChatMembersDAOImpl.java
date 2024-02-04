package com.connectify.model.dao.impl;

import com.connectify.dto.ChatCardsInfoDTO;
import com.connectify.model.dao.ChatMembersDAO;
import com.connectify.model.entities.ChatMember;
import com.connectify.model.entities.User;
import com.connectify.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMembersDAOImpl implements ChatMembersDAO {
    private final DBConnection dbConnection;

    public ChatMembersDAOImpl() {
        dbConnection = DBConnection.getInstance();;
    }



    public ChatMember findChatMember(int chatId, String member) throws SQLException {

        return null;
    }

    @Override
    public List<ChatMember> getAllUserChats(String userId) throws SQLException {
        List<ChatMember> chatMembers = new ArrayList<>();
        String query = "SELECT chat_id, member FROM chat_members WHERE member = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){;
             preparedStatement.setString(1, userId);
             try(ResultSet rs = preparedStatement.executeQuery()){
                 while (rs.next()) {
                     ChatMember chatMember = new ChatMember(
                             rs.getInt("chat_Id"),
                             rs.getString("member")
//                             rs.getBoolean("is_Open"),
//                             rs.getTimestamp("last_Opened_Time"),
//                             rs.getInt("unread_Messages_Number")
                     );
                     chatMembers.add(chatMember);
                 }
             }
        }
        return chatMembers;
    }

    @Override
    public List<ChatCardsInfoDTO> getAllUserChatsInfo(String userId) throws SQLException {
        List<ChatCardsInfoDTO> chatsCardsList = new ArrayList<>();
        String query = "SELECT DISTINCT cm.chat_id,cm2.Unread_Messages_number, COALESCE (u.name,g.name) AS name, COALESCE(u.picture,g.picture) AS picture ,m.content,m.`timestamp` " +
                " FROM chat_members cm " +
                "    LEFT JOIN chat_members cm2 ON cm.chat_id = cm2.chat_id AND cm2.member = ? " +
                " LEFT JOIN chat c ON c.chat_id = cm.chat_id\n" +
                " LEFT JOIN users u ON cm.member = u.phone_number AND c.is_Private_Chat = 1 " +
                "    LEFT JOIN `groups` g ON c.chat_id = g.chat_id AND c.is_Private_Chat = 0 " +
                " LEFT JOIN message m ON c.chat_id = m.chat_id AND m.message_id IN (SELECT m1.message_id FROM message m1 JOIN ( SELECT chat_id, MAX(`timestamp`) AS latest_time FROM message GROUP BY chat_id ) m2 ON m1.chat_id = m2.chat_id AND m1.`timestamp` = m2.latest_time  ORDER BY m1.message_id DESC ) " +
                " WHERE cm.chat_id IN (SELECT chat_id FROM chat_members WHERE member = ? ) AND cm.member != ? " +
                "    AND cm.chat_id IN (SELECT DISTINCT chat_id FROM chat_members)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ;
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, userId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ChatCardsInfoDTO chatCardsInfoDTO = new ChatCardsInfoDTO(
                            rs.getInt("chat_Id"),
                            rs.getInt("Unread_Messages_number"),
                            rs.getString("name"),
                            rs.getBytes("picture"),
                            rs.getString("content"),
                            rs.getTimestamp("timestamp")
                    );
                    chatsCardsList.add(chatCardsInfoDTO);
                }
            }
        }
        return chatsCardsList;
    }


    @Override
    public List<ChatMember> getAllChatMembers(int chatID) throws SQLException {
        List<ChatMember> chatMembers = new ArrayList<>();
        String query = "SELECT * FROM chat_members WHERE chat_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){;
            preparedStatement.setInt(1, chatID);
            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()) {
                    ChatMember chatMember = new ChatMember(
                            rs.getInt("chat_Id"),
                            rs.getString("member"),
                            rs.getBoolean("is_Open"),
                            rs.getTimestamp("last_Opened_Time"),
                            rs.getInt("unread_Messages_Number")
                    );
                    chatMembers.add(chatMember);
                }
            }
        }
        return chatMembers;
    }

    @Override
    public List<ChatMember> getAllOtherChatMembers(int chatId, String sender) {
        List<ChatMember> chatMembers = new ArrayList<>();
        String query = "SELECT * FROM chat_members WHERE chat_id = ? AND member != ? ";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chatId);
            preparedStatement.setString(2, sender);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ChatMember chatMember = new ChatMember();
                    chatMember.setMember(rs.getString("member"));
                    chatMembers.add(chatMember);
                }
                return chatMembers;
            }
        } catch (SQLException e) {
            System.err.println("Sql Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void prepareCurrentChat(ChatMember chatMember) {
        String query1 = "UPDATE CHAT_MEMBERS SET is_open = 0 WHERE member = ? AND is_open = 1;";
        String query2 = "UPDATE CHAT_MEMBERS SET is_open = 1, Unread_Messages_number = 0 WHERE chat_id = ? AND member = ?;"; // Fixed the query to correctly update both columns
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                 PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {

                preparedStatement1.setString(1, chatMember.getMember());
                preparedStatement1.executeUpdate();

                preparedStatement2.setInt(1, chatMember.getChatId());
                preparedStatement2.setString(2, chatMember.getMember());
                preparedStatement2.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }

    }

    @Override
    public List<User> getAllOtherChatMembersInfo(int chatID, String member) {
        List<User> membersInfo = new ArrayList<>();
        String query = "SELECT member,name,picture,bio FROM chat_members LEFT JOIN users ON member = phone_number WHERE chat_id = ? AND member != ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chatID);
            preparedStatement.setString(2, member);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setPhoneNumber(rs.getString("member"));
                    user.setName(rs.getString("name"));
                    //TODO load picture as file from db
                    //byte[] picBlob = rs.getBytes("picture");
                    //user.setPicture(rs.getString("picture"));
                    user.setBio(rs.getString("bio"));
                    membersInfo.add(user);
                }
                return membersInfo;
            }
        } catch (SQLException e) {
            System.err.println("Sql Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean insert(ChatMember chatMember) {
        String query = "INSERT INTO chat_members (chat_Id, member) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chatMember.getChatId());
            preparedStatement.setString(2, chatMember.getMember());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ChatMember get(ChatMember chatMember) {
        String query = "SELECT * FROM chat_members WHERE chat_Id = ? AND member = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, chatMember.getChatId());
            preparedStatement.setString(2, chatMember.getMember());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new ChatMember(
                            rs.getInt("chat_Id"),
                            rs.getString("member"),
                            rs.getBoolean("is_Open"),
                            rs.getTimestamp("last_Opened_Time"),
                            rs.getInt("unread_Messages_Number")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean update(ChatMember chatMember) {
        String query = "UPDATE chat_members SET is_Open = ?, last_Opened_Time = ?, unread_Messages_Number = ? WHERE chat_Id = ? AND member = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, chatMember.getIsOpen());
            preparedStatement.setTimestamp(2, chatMember.getLastOpenedTime());
            preparedStatement.setInt(3, chatMember.getUnreadMessagesNumber());
            preparedStatement.setInt(4, chatMember.getChatId());
            preparedStatement.setString(5, chatMember.getMember());
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows >0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(ChatMember chatMember) {
        String query = "DELETE FROM chat_members WHERE chat_Id = ? AND member = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chatMember.getChatId());
            preparedStatement.setString(2, chatMember.getMember());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        }
    }
}
