package com.example.testApp.controller;

import com.example.testApp.entity.UserAccount;
import com.example.testApp.service.AuthService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam @NotBlank String username,
	                    @RequestParam @NotBlank String password,
	                    HttpSession session,
	                    Model model) {
		Optional<UserAccount> user = authService.login(username, password);
		if (user.isEmpty()) {
			model.addAttribute("error", "Invalid credentials");
			return "login";
		}
		session.setAttribute("userId", user.get().getId());
		return "redirect:/";
	}

	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}

	@PostMapping("/register")
	public String doRegister(@RequestParam String username,
	                        @RequestParam String password,
	                        @RequestParam String displayName,
	                        Model model) {
		try {
			authService.register(username, password, displayName);
			return "redirect:/login";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("error", ex.getMessage());
			return "register";
		}
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}


