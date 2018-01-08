package com.manager.chat.entity;

import javax.persistence.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


@Entity
@Table(name = "MESSAGE_FILE")
public class MessageFile extends BaseEntity{


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="MESSAGE_ID")
    private Message message;


    @Column(name = "PATH")
    private String filePath;

    @Transient
    private File file;
    
    private byte[] fileData;
    
    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public MessageFile() {
    }

    public MessageFile(File file) {
        this.setFile(file);
    }

    public MessageFile(Message message, String filePath) {
        this.message = message;
        this.filePath = filePath;
        this.file = new File(this.filePath);
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        this.file = new File(this.filePath);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.filePath = this.file.getPath();
        //Path path = Paths.get("path/to/file");
        //fileData = Files.readAllBytes(Paths.get("path/to/file"));
    }
}
