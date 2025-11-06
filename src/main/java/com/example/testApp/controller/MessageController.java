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
		model.addAttribute("me", me);
		model.addAttribute("users", userRepo.findAll());
		if (with != null) {
			UserAccount other = userRepo.findById(with).orElseThrow();
			List<Message> history = messageService.conversation(me, other);
			model.addAttribute("other", other);
			model.addAttribute("history", history);
		}
		return "messages";
	}

	@PostMapping
	public String send(@RequestParam Long toUserId, @RequestParam String content, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		UserAccount to = userRepo.findById(toUserId).orElseThrow();
		messageService.send(me, to, content);
		return "redirect:/messages?with=" + toUserId;
	}
}


