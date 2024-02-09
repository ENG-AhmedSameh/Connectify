package com.connectify.services;

import com.connectify.dto.MessageSentDTO;
import com.connectify.model.dao.AttachmentDAO;
import com.connectify.model.dao.impl.AttachmentsDAOImpl;
import com.connectify.model.entities.Attachments;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.*;
import java.util.concurrent.ExecutionException;


public class AttachmentService {

    private final ExecutorService executor;

    public AttachmentService() {
        executor = Executors.newCachedThreadPool();
    }

    public Integer storeAttachment(MessageSentDTO message){
        Callable<Integer> callable = () -> {
            String path = System.getProperty("user.dir") + File.separator + "attachments";
            Path attachmentsPath = Paths.get(path);
            Path chatFolderPath = attachmentsPath.resolve(String.valueOf(message.getChatId()));
            if (!Files.exists(chatFolderPath)) {
                Files.createDirectories(chatFolderPath);
            }

            Path senderFolderPath = chatFolderPath.resolve(message.getSender());
            if (!Files.exists(senderFolderPath)) {
                Files.createDirectories(senderFolderPath);
            }

            Path newFilePath = senderFolderPath.resolve(message.getContent());
            Files.write(newFilePath, message.getAttachment());

            Attachments attachment = new Attachments();
            attachment.setName(newFilePath.toString());
            attachment.setExtension(getExtension(newFilePath.toString()));
            attachment.setSize((int) Files.size(newFilePath));
            System.out.println(newFilePath);
            AttachmentDAO attachmentDAO = new AttachmentsDAOImpl();
            return attachmentDAO.insertAndReturnID(attachment);
        };

        try {
            return executor.submit(callable).get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Exception: " + e.getMessage());
            return null;
        }
    }

    public byte[] getAttachment(Integer attachmentId) {
            Callable<byte[]> callable = () -> {
                AttachmentDAO attachmentDAO = new AttachmentsDAOImpl();
                Attachments attachment = attachmentDAO.get(attachmentId);
                if(attachment != null){
                    Path filePath = Paths.get(attachment.getName());
                    System.out.println("downloading: " + filePath);
                    return Files.readAllBytes(filePath);
                }
                return null;
            };
            try {
                return executor.submit(callable).get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        return null;
    }


    private String getExtension(String path) {
        int index = path.lastIndexOf('.');
        return path.substring(index + 1);
    }

}
