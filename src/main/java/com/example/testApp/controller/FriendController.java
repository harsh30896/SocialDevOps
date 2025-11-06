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
		model.addAttribute("me", me);
		model.addAttribute("incoming", incoming);
		model.addAttribute("users", userRepo.findAll());
		return "friends";
	}

	@PostMapping("/request")
	public String request(@RequestParam Long toUserId, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		UserAccount to = userRepo.findById(toUserId).orElseThrow();
		friendService.sendRequest(me, to);
		return "redirect:/friends";
	}

	@PostMapping("/respond")
	public String respond(@RequestParam Long requestId, @RequestParam boolean accept, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		friendService.respond(requestId, accept);
		return "redirect:/friends";
	}
}


