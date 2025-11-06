package com.example.testApp.dto;

import java.time.Instant;

public class ChatMessage {
	private Long senderId;
	private String senderName;
	private Long recipientId;
	private String content;
	private Instant timestamp;
	private String type; // MESSAGE, TYPING, etc.

	public ChatMessage() {
		this.timestamp = Instant.now();
	}

	public ChatMessage(Long senderId, String senderName, Long recipientId, String content) {
		this.senderId = senderId;
		this.senderName = senderName;
		this.recipientId = recipientId;
		this.content = content;
		this.timestamp = Instant.now();
		this.type = "MESSAGE";
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

