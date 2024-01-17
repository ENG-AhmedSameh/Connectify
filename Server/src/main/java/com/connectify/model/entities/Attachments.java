package com.connectify.model.entities;

public class Attachments {
    private int attachmentsId;
    private int name;
    private String extension;
    private int size;

    public int getAttachmentsId() {
        return attachmentsId;
    }

    public void setAttachmentsId(int attachmentsId) {
        this.attachmentsId = attachmentsId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
