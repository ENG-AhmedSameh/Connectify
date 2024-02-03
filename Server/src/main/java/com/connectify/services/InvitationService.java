package com.connectify.services;

import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.InvitationsDAO;
import com.connectify.model.dao.impl.InvitationsDAOImpl;
import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;

import java.util.ArrayList;
import java.util.List;

public class InvitationService {
    public boolean sendInvitation(String senderPhoneNumber, String receiverPhoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        Invitations invitations = new Invitations();
        invitations.setSender(senderPhoneNumber);
        invitations.setReceiver(receiverPhoneNumber);
        return invitationsDAO.insert(invitations);
    }

    public boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        return invitationsDAO.isInvitationSent(senderPhoneNumber, receiverPhoneNumber);
    }

    public List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String phoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        List<User> userList = invitationsDAO.getIncomingFriendRequests(phoneNumber);

        if (userList == null || userList.isEmpty()) {
            return null;
        }

        List<IncomingFriendInvitationResponse> responseList = new ArrayList<>();

        for (User user : userList) {
            IncomingFriendInvitationResponse response = UserMapper.INSTANCE.userToIncomingFriendInvitationResponse(user);
            responseList.add(response);
        }

        return responseList;
    }
}
