package com.example.testApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id", nullable = false)
	private UserAccount sender;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "recipient_id", nullable = false)
	private UserAccount recipient;

	@NotBlank
	@Column(nullable = false, length = 2000)
	private String content;

	@Column(nullable = false)
	private Instant createdAt = Instant.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getSender() {
		return sender;
	}

	public void setSender(UserAccount sender) {
		this.sender = sender;
	}

	public UserAccount getRecipient() {
		return recipient;
	}

	public void setRecipient(UserAccount recipient) {
		this.recipient = recipient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}


