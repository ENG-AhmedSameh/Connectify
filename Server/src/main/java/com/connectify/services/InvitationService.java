package com.connectify.services;

import com.connectify.Interfaces.ConnectedUser;
import com.connectify.Server;
import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.mapper.UserMapper;
import com.connectify.model.dao.ContactsDAO;
import com.connectify.model.dao.InvitationsDAO;
import com.connectify.model.dao.impl.ContactsDAOImpl;
import com.connectify.model.dao.impl.InvitationsDAOImpl;
import com.connectify.model.entities.Contacts;
import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class InvitationService {
    public boolean sendInvitation(String senderPhoneNumber, String receiverPhoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();

        Invitations friendInvitation = new Invitations();
        friendInvitation.setSender(senderPhoneNumber);
        friendInvitation.setReceiver(receiverPhoneNumber);

        boolean isInvitationSent = invitationsDAO.insert(friendInvitation);

        if (isInvitationSent) {
            try {
                IncomingFriendInvitationResponse receivedInvitation = invitationsDAO
                        .getIncomingFriendRequest(senderPhoneNumber, receiverPhoneNumber);

                ConnectedUser receiver = Server.getConnectedUsers().get(receiverPhoneNumber.substring(3));
                if (receiver != null) {
                    receiver.receiveFriendRequest(receivedInvitation);
                }
            } catch (RemoteException e) {
                System.err.println("Error sending friend invitation. case:" + e.getMessage());
            }
        }

        return isInvitationSent;
    }

    public boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        return invitationsDAO.isInvitationSent(senderPhoneNumber, receiverPhoneNumber);
    }

    public List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String phoneNumber) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        return invitationsDAO.getIncomingFriendRequests(phoneNumber);
    }

    public boolean acceptFriendRequest(int invitationId) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        return invitationsDAO.acceptFriendRequest(invitationId);
    }

    public boolean cancelFriendRequest(int invitationId) {
        InvitationsDAO invitationsDAO = new InvitationsDAOImpl();
        return invitationsDAO.delete(invitationId);
    }
}
