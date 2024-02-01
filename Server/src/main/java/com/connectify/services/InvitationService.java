package com.connectify.services;

import com.connectify.dto.FriendToAddResponse;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.InvitationsDAO;
import com.connectify.model.dao.UserDAO;
import com.connectify.model.dao.impl.InvitationsDAOImpl;
import com.connectify.model.dao.impl.UserDAOImpl;
import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;

public class InvitationService {
    public boolean sendInvitation(String senderPhoneNumber, String receiverPhoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        Invitations invitations = new Invitations();
        invitations.setSender(senderPhoneNumber);
        invitations.setReceiver(receiverPhoneNumber);
        return invitationsDAO.insert(invitations);
    }
}
