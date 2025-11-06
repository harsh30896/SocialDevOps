package com.example.testApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id", nullable = false)
	private UserAccount author;

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

	public UserAccount getAuthor() {
		return author;
	}

	public void setAuthor(UserAccount author) {
		this.author = author;
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


