package org.roleonce.examensarbete_3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Optional;
/*
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private CustomUser sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private CustomUser recipient;

    @NotBlank
    private String subject;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private boolean read;

    public Message(CustomUser sender, CustomUser recipient, String subject, String content, LocalDateTime sentAt, boolean read) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sentAt = sentAt;
        this.read = read;
    }

    public Message() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomUser getSender() {
        return sender;
    }

    public void setSender(CustomUser sender) {
        this.sender = sender;
    }

    public CustomUser getRecipient() {
        return recipient;
    }

    public void setRecipient(CustomUser recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}

 */