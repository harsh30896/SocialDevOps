package com.example.testApp.service;

import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

	private final UserAccountRepository userRepo;

	public AuthService(UserAccountRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Transactional
	public UserAccount register(String username, String password, String displayName) {
		userRepo.findByUsername(username).ifPresent(u -> {
			throw new IllegalArgumentException("Username already taken");
		});
		UserAccount user = new UserAccount();
		user.setUsername(username);
		user.setPassword(password); // Simplified for demo
		user.setDisplayName(displayName);
		return userRepo.save(user);
	}

	public Optional<UserAccount> login(String username, String password) {
		return userRepo.findByUsername(username)
				.filter(u -> u.getPassword().equals(password));
	}

	public Optional<UserAccount> findById(Long id) {
		return userRepo.findById(id);
	}
}


