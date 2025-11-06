package com.example.testApp.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {

	@Override
	protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		String userId = (String) attributes.get("userId");
		if (userId != null) {
			return new StompPrincipal(userId);
		}
		return super.determineUser(request, wsHandler, attributes);
	}

	public static class StompPrincipal implements Principal {
		private final String name;

		public StompPrincipal(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
}

