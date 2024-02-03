package com.connectify.model.dao;


import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;

import java.util.List;

public interface InvitationsDAO extends DAO<Invitations, Integer> {
    boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber);

    List<User> getIncomingFriendRequests(String receiverPhoneNumber);
}
