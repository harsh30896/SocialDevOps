package com.example.testApp.controller;

import com.example.testApp.entity.FriendRequest;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.UserAccountRepository;
import com.example.testApp.service.AuthService;
import com.example.testApp.service.FriendService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendController {

	private final AuthService authService;
	private final FriendService friendService;
	private final UserAccountRepository userRepo;

	public FriendController(AuthService authService, FriendService friendService, UserAccountRepository userRepo) {
		this.authService = authService;
		this.friendService = friendService;
		this.userRepo = userRepo;
	}

	@GetMapping
	public String list(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		List<FriendRequest> incoming = friendService.incomingPending(me);
		
		// Filter out current user from users list
		List<UserAccount> allUsers = userRepo.findAll();
		List<UserAccount> otherUsers = allUsers.stream()
				.filter(u -> !u.getId().equals(me.getId()))
				.toList();
		
		model.addAttribute("me", me);
		model.addAttribute("incoming", incoming != null ? incoming : java.util.Collections.emptyList());
		model.addAttribute("users", otherUsers);
		return "friends";
	}

	@PostMapping("/request")
	public String request(@RequestParam Long toUserId, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		
		try {
			UserAccount me = authService.findById(userId).orElseThrow();
			UserAccount to = userRepo.findById(toUserId)
					.orElseThrow(() -> new IllegalArgumentException("User not found"));
			
			if (toUserId.equals(userId)) {
				model.addAttribute("error", "Cannot send friend request to yourself");
				return list(session, model);
			}
			
			friendService.sendRequest(me, to);
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return list(session, model);
		}
		
		return "redirect:/friends";
	}

	@PostMapping("/respond")
	public String respond(@RequestParam Long requestId, @RequestParam boolean accept, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		
		try {
			UserAccount me = authService.findById(userId).orElseThrow();
			friendService.respond(requestId, accept, me);
		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return list(session, model);
		}
		
		return "redirect:/friends";
	}
}


