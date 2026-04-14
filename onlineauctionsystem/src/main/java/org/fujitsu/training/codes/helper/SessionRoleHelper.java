package org.fujitsu.training.codes.helper;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionRoleHelper {

	public boolean isAdmin(HttpSession session) {
		return hasRole(session, "admin");
	}

	public boolean isSeller(HttpSession session) {
		return hasRole(session, "seller");
	}

	public boolean isBidder(HttpSession session) {
		return hasRole(session, "bidder");
	}

	public boolean isLoggedIn(HttpSession session) {
		return session.getAttribute("loggedInUsername") != null && session.getAttribute("loggedInUserType") != null;
	}

	public String getLoggedInUsername(HttpSession session) {
		return (String) session.getAttribute("loggedInUsername");
	}

	public String getSellerUsername(HttpSession session) {
		return isSeller(session) ? getLoggedInUsername(session) : null;
	}

	public String getBidderUsername(HttpSession session) {
		return isBidder(session) ? getLoggedInUsername(session) : null;
	}

	private boolean hasRole(HttpSession session, String expectedRole) {
		String username = (String) session.getAttribute("loggedInUsername");
		String userType = (String) session.getAttribute("loggedInUserType");

		return username != null && userType != null && expectedRole.equalsIgnoreCase(userType);
	}
}
