package org.roleonce.examensarbete_3.controller;
/*
import org.roleonce.examensarbete_3.model.CustomUser;
import org.roleonce.examensarbete_3.model.Message;
import org.roleonce.examensarbete_3.service.MessageService;
import org.roleonce.examensarbete_3.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/inbox")
    public String showInbox(Model model, Principal principal) {
        CustomUser currentUser = userService.findByUsername(principal.getName());
        List<Message> receivedMessages = messageService.getReceivedMessages(currentUser);
        model.addAttribute("messages", receivedMessages);
        return "messages/inbox";
    }

    @GetMapping("/sent")
    public String showSentMessages(Model model, Principal principal) {
        CustomUser currentUser = userService.findByUsername(principal.getName());
        List<Message> sentMessages = messageService.getSentMessages(currentUser);
        model.addAttribute("messages", sentMessages);
        return "messages/sent";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Long recipientId,
                              @RequestParam String subject,
                              @RequestParam String content,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        try {
            CustomUser sender = userService.findByUsername(principal.getName());
            CustomUser recipient = userService.findById(recipientId);

            Message message = new Message();
            message.setSender(sender);
            message.setRecipient(recipient);
            message.setSubject(subject);
            message.setContent(content);
            message.setSentAt(LocalDateTime.now());
            message.setRead(false);

            messageService.saveMessage(message);
            redirectAttributes.addFlashAttribute("success", "Message sent successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send message");
        }
        return "redirect:/messages/sent";
    }

    @PostMapping("/{messageId}/read")
    @ResponseBody
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId, Principal principal) {
        try {
            CustomUser currentUser = userService.findByUsername(principal.getName());
            messageService.markAsRead(messageId, currentUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId, Principal principal) {
        try {
            CustomUser currentUser = userService.findByUsername(principal.getName());
            messageService.deleteMessage(messageId, currentUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

 */