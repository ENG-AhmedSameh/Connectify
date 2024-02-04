package com.connectify.services;

import com.connectify.dto.MessageSentDTO;
import com.connectify.model.dao.AttachmentDAO;
import com.connectify.model.dao.impl.AttachmentsDAOImpl;
import com.connectify.model.entities.Attachments;

import java.io.File;


public class AttachmentService {

    public Integer storeAttachment(MessageSentDTO message){
        File chatFolder = new File(getClass().getClassLoader().getResource("attachments").getPath() + "/" + message.getChatId());
        if (!chatFolder.exists()) {
            chatFolder.mkdirs();
        }

        File senderFolder = new File(chatFolder, message.getSender());
        if (!senderFolder.exists()) {
            senderFolder.mkdirs();
        }

        File attachmentFile = message.getAttachment();
        if (attachmentFile != null && attachmentFile.exists()) {
            File newFile = new File(senderFolder, attachmentFile.getName());
            Attachments attachment = new Attachments();
            attachment.setName(newFile.getPath());
            attachment.setExtension(newFile.getName().substring(newFile.getName().lastIndexOf(".") + 1));
            attachment.setSize((int)newFile.length());
            AttachmentDAO attachmentDAO = new AttachmentsDAOImpl();
            return attachmentDAO.insertAndReturnID(attachment);
        }
        return null;
    }

    public File getAttachment(Integer attachmentId) {
        AttachmentDAO attachmentDAO = new AttachmentsDAOImpl();
        Attachments attachment = attachmentDAO.get(attachmentId);
        if (attachment != null) {
            File attachmentFile = new File(attachment.getName());
            if (attachmentFile.exists()) {
                return attachmentFile;
            }
        }
        return null;
    }
}
