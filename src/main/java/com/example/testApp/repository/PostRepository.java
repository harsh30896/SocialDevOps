package com.example.testApp.repository;

import com.example.testApp.entity.Post;
import com.example.testApp.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByAuthorOrderByCreatedAtDesc(UserAccount author);
	List<Post> findByAuthorInOrderByCreatedAtDesc(Collection<UserAccount> authors);
}


