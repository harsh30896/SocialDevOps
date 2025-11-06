package com.example.testApp.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "friend_requests", indexes = {
		@Index(name = "idx_friend_pair", columnList = "from_user_id,to_user_id", unique = true)
})
public class FriendRequest {

	public enum Status { PENDING, ACCEPTED, REJECTED }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id", nullable = false)
	private UserAccount fromUser;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id", nullable = false)
	private UserAccount toUser;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Status status = Status.PENDING;

	@Column(nullable = false)
	private Instant createdAt = Instant.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserAccount fromUser) {
		this.fromUser = fromUser;
	}

	public UserAccount getToUser() {
		return toUser;
	}

	public void setToUser(UserAccount toUser) {
		this.toUser = toUser;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}


