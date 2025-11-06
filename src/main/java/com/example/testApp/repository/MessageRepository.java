package com.example.testApp.repository;

import com.example.testApp.entity.Message;
import com.example.testApp.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findBySenderAndRecipientOrderByCreatedAtAsc(UserAccount sender, UserAccount recipient);
	List<Message> findBySenderAndRecipientOrSenderAndRecipientOrderByCreatedAtAsc(UserAccount a, UserAccount b, UserAccount c, UserAccount d);
}


