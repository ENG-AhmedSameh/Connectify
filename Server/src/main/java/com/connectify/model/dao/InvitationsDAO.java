package com.connectify.model.dao;


import com.connectify.model.entities.Invitations;

public interface InvitationsDAO extends DAO<Invitations, Integer> {
    boolean isInvitationSent(String senderPhoneNumber, String receiverPhoneNumber);
}
