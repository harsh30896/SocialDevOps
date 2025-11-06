package com.example.testApp.controller;

import com.example.testApp.dto.ChatMessage;
import com.example.testApp.entity.Message;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.UserAccountRepository;
import com.example.testApp.service.AuthService;
import com.example.testApp.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

	private final SimpMessagingTemplate messagingTemplate;
	private final MessageService messageService;
	private final AuthService authService;
	private final UserAccountRepository userRepo;

	public ChatWebSocketController(SimpMessagingTemplate messagingTemplate,
	                               MessageService messageService,
	                               AuthService authService,
	                               UserAccountRepository userRepo) {
		this.messagingTemplate = messagingTemplate;
		this.messageService = messageService;
		this.authService = authService;
		this.userRepo = userRepo;
	}

	@MessageMapping("/chat.send")
	public void sendMessage(@Payload ChatMessage chatMessage) {
		try {
			// Validate and save message to database
			if (chatMessage.getContent() == null || chatMessage.getContent().trim().isEmpty()) {
				return;
			}
			
			if (chatMessage.getContent().length() > 2000) {
				return;
			}

			if (chatMessage.getSenderId() == null || chatMessage.getRecipientId() == null) {
				return;
			}

			UserAccount sender = authService.findById(chatMessage.getSenderId())
					.orElseThrow(() -> new IllegalArgumentException("Sender not found"));
			UserAccount recipient = userRepo.findById(chatMessage.getRecipientId())
					.orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

			// Save to database
			Message savedMessage = messageService.send(sender, recipient, chatMessage.getContent().trim());
			
			// Update chat message with saved data
			chatMessage.setSenderName(sender.getDisplayName());
			chatMessage.setTimestamp(savedMessage.getCreatedAt());

			// Send to both users via topic (simpler approach)
			// Topic format: /topic/user/{userId}
			messagingTemplate.convertAndSend("/topic/user/" + chatMessage.getSenderId(), chatMessage);
			messagingTemplate.convertAndSend("/topic/user/" + chatMessage.getRecipientId(), chatMessage);
		} catch (Exception e) {
			// Log error but don't crash
			System.err.println("Error sending message: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

