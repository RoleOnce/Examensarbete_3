package org.roleonce.examensarbete_3.service;

import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Message;
import org.roleonce.examensarbete_3.repository.MessageRepository;
import org.roleonce.examensarbete_3.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message saveMessage(Message message) {
        if (message.getSender() == null || message.getRecipient() == null) {
            throw new IllegalArgumentException("Both sender and recipient are required");
        }
        return messageRepository.save(message);
    }

    public CustomUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public CustomUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public List<Message> getReceivedMessages(Optional<CustomUser> user) {
        return messageRepository.findByRecipientOrderBySentAtDesc(user);
    }

    public List<Message> getSentMessages(Optional<CustomUser> user) {
        return messageRepository.findBySenderOrderBySentAtDesc(user);
    }

    public void markAsRead(Long messageId, Optional<CustomUser> currentUser) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        message.setRead(true);
        messageRepository.save(message);
    }

    public void deleteMessage(Long messageId, Optional<CustomUser> currentUser) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found"));

        messageRepository.delete(message);
    }

    public long getUnreadCount(CustomUser user) {
        return messageRepository.countByRecipientAndReadFalse(user);
    }
}
