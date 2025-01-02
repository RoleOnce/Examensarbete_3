package org.roleonce.examensarbete_3.service;
/*
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Message;
import org.roleonce.examensarbete_3.repository.MessageRepository;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getReceivedMessages(CustomUser user) {
        return messageRepository.findByRecipientOrderBySentAtDesc(user);
    }

    public List<Message> getSentMessages(CustomUser user) {
        return messageRepository.findBySenderOrderBySentAtDesc(user);
    }

    public void saveMessage(Message message) {
        if (message.getSender().isEmpty() || message.getRecipient() == null) {
            throw new IllegalArgumentException("Both sender and recipient are required");
        }
        messageRepository.save(message);
    }

    public void markAsRead(Long messageId, CustomUser currentUser) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));



        message.setRead(true);
        messageRepository.save(message);
    }

    public void deleteMessage(Long messageId, CustomUser currentUser) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));



        messageRepository.delete(message);
    }

    public long getUnreadCount(CustomUser user) {
        return messageRepository.countByRecipientAndReadFalse(user);
    }
}

 */