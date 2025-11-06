package com.example.testApp.service;

import com.example.testApp.entity.Post;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public Post create(UserAccount author, String content) {
		Post p = new Post();
		p.setAuthor(author);
		p.setContent(content);
		return postRepository.save(p);
	}

	public List<Post> findByAuthor(UserAccount author) {
		return postRepository.findByAuthorOrderByCreatedAtDesc(author);
	}

	public List<Post> feedForAuthors(Collection<UserAccount> authors) {
		return postRepository.findByAuthorInOrderByCreatedAtDesc(authors);
	}
}


