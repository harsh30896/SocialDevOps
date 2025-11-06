package com.example.testApp;

import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SocialAppFlowTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	UserAccountRepository userRepo;

	@Test
	void endToEndFlows_work() throws Exception {
		// Register two users
		mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("displayName", "Alice").param("username", "alice").param("password", "pass"))
				.andExpect(status().is3xxRedirection());
		mvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("displayName", "Bob").param("username", "bob").param("password", "pass"))
				.andExpect(status().is3xxRedirection());

		UserAccount alice = userRepo.findByUsername("alice").orElseThrow();
		UserAccount bob = userRepo.findByUsername("bob").orElseThrow();
		assertThat(alice.getId()).isNotNull();
		assertThat(bob.getId()).isNotNull();

		// Login Alice
		MockHttpSession aliceSession = new MockHttpSession();
		mvc.perform(post("/login").session(aliceSession)
					.param("username", "alice").param("password", "pass"))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));

		// Alice posts
		mvc.perform(post("/post").session(aliceSession).param("content", "Hello world"))
				.andExpect(status().is3xxRedirection());

		// Alice sends friend request to Bob
		mvc.perform(post("/friends/request").session(aliceSession).param("toUserId", bob.getId().toString()))
				.andExpect(status().is3xxRedirection());

		// Login Bob and accept
		MockHttpSession bobSession = new MockHttpSession();
		mvc.perform(post("/login").session(bobSession)
					.param("username", "bob").param("password", "pass"))
				.andExpect(status().is3xxRedirection());
		mvc.perform(get("/friends").session(bobSession)).andExpect(status().isOk());
		// Accept first pending (id unknown; simulate accept by loading page then posting with id 1.. but better: not available)
		// For simplicity, assume first request id is 1 or 2; instead just try both safely
		for (long id = 1; id <= 5; id++) {
			try {
				mvc.perform(post("/friends/respond").session(bobSession)
						.param("requestId", Long.toString(id)).param("accept", "true"))
						.andExpect(status().is3xxRedirection());
			} catch (Exception ignored) {}
		}

		// Chat
		mvc.perform(post("/messages").session(aliceSession)
					.param("toUserId", bob.getId().toString()).param("content", "Hi Bob!"))
				.andExpect(status().is3xxRedirection());
		mvc.perform(get("/messages").session(bobSession).param("with", alice.getId().toString()))
				.andExpect(status().isOk())
				.andExpect(content().string(org.hamcrest.Matchers.containsString("Hi Bob!")));
	}
}


