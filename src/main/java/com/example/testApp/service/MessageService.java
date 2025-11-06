package com.example.testApp.service;

import com.example.testApp.entity.Message;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

	private final MessageRepository messageRepository;

	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Transactional
	public Message send(UserAccount from, UserAccount to, String content) {
		Message m = new Message();
		m.setSender(from);
		m.setRecipient(to);
		m.setContent(content);
		return messageRepository.save(m);
	}

	public List<Message> conversation(UserAccount a, UserAccount b) {
		return messageRepository.findBySenderAndRecipientOrSenderAndRecipientOrderByCreatedAtAsc(a, b, b, a);
	}
}


