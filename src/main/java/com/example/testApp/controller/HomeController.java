package com.example.testApp.controller;

import com.example.testApp.entity.Post;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.service.AuthService;
import com.example.testApp.service.FriendService;
import com.example.testApp.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

	private final AuthService authService;
	private final FriendService friendService;
	private final PostService postService;

	public HomeController(AuthService authService, FriendService friendService, PostService postService) {
		this.authService = authService;
		this.friendService = friendService;
		this.postService = postService;
	}

	@GetMapping("/")
	public String home(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		Set<UserAccount> friends = friendService.getFriends(me);
		List<UserAccount> authors = new ArrayList<>(friends);
		authors.add(me);
		List<Post> feed = postService.feedForAuthors(authors);
		model.addAttribute("me", me);
		model.addAttribute("feed", feed);
		return "feed";
	}

	@PostMapping("/post")
	public String post(@RequestParam String content, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) return "redirect:/login";
		UserAccount me = authService.findById(userId).orElseThrow();
		postService.create(me, content);
		return "redirect:/";
	}
}


