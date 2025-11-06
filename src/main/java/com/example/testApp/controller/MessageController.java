package com.example.testApp.controller;

import com.example.testApp.entity.Message;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.UserAccountRepository;
import com.example.testApp.service.AuthService;
import com.example.testApp.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

	private final AuthService authService;
	private final MessageService messageService;
	private final UserAccountRepository userRepo;

	public MessageController(AuthService authService, MessageService messageService, UserAccountRepository userRepo) {
		this.authService = authService;
		this.messageService = messageService;
		this.userRepo = userRepo;
	}

	@GetMapping
	public String convo(@RequestParam(required = false) Long with, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		
		// Filter out current user from users list
		List<UserAccount> allUsers = userRepo.findAll();
		List<UserAccount> otherUsers = allUsers.stream()
				.filter(u -> !u.getId().equals(me.getId()))
				.toList();
		
		model.addAttribute("me", me);
		model.addAttribute("users", otherUsers);
		
		if (with != null && !with.equals(userId)) {
			try {
				UserAccount other = userRepo.findById(with)
						.orElseThrow(() -> new IllegalArgumentException("User not found"));
				List<Message> history = messageService.conversation(me, other);
				model.addAttribute("other", other);
				model.addAttribute("history", history != null ? history : java.util.Collections.emptyList());
			} catch (Exception e) {
				model.addAttribute("error", "Failed to load conversation: " + e.getMessage());
			}
		}
		return "messages";
	}

	@PostMapping
	public String send(@RequestParam Long toUserId, @RequestParam String content, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		
		if (content == null || content.trim().isEmpty()) {
			model.addAttribute("error", "Message cannot be empty");
			return convo(toUserId, session, model);
		}
		
		if (content.length() > 2000) {
			model.addAttribute("error", "Message is too long (max 2000 characters)");
			return convo(toUserId, session, model);
		}
		
		if (toUserId.equals(userId)) {
			model.addAttribute("error", "Cannot send message to yourself");
			return convo(null, session, model);
		}
		
		try {
			UserAccount me = authService.findById(userId).orElseThrow();
			UserAccount to = userRepo.findById(toUserId)
					.orElseThrow(() -> new IllegalArgumentException("User not found"));
			messageService.send(me, to, content.trim());
		} catch (Exception e) {
			model.addAttribute("error", "Failed to send message: " + e.getMessage());
			return convo(toUserId, session, model);
		}
		
		return "redirect:/messages?with=" + toUserId;
	}
}


