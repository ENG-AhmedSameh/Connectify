package com.connectify.model.dao;


import com.connectify.model.entities.Attachments;

public interface AttachmentDAO extends DAO<Attachments, Integer> {
    int insertAndReturnID(Attachments attachments);
}
