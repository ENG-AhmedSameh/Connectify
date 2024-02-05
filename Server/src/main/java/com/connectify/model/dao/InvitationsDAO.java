package com.connectify.model.dao;


import com.connectify.dto.IncomingFriendInvitationResponse;
import com.connectify.model.entities.Invitations;
import com.connectify.model.entities.User;

import java.util.List;

public interface InvitationsDAO extends DAO<Invitations, Integer> {
    int isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber);

    List<IncomingFriendInvitationResponse> getIncomingFriendRequests(String receiverPhoneNumber);

    IncomingFriendInvitationResponse getIncomingFriendRequest(String senderPhoneNumber, String receiverPhoneNumber);

    boolean acceptFriendRequest(int invitationId);
}
