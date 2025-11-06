package com.example.testApp.service;

import com.example.testApp.entity.FriendRequest;
import com.example.testApp.entity.UserAccount;
import com.example.testApp.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FriendService {

	private final FriendRequestRepository friendRepo;

	public FriendService(FriendRequestRepository friendRepo) {
		this.friendRepo = friendRepo;
	}

	@Transactional
	public FriendRequest sendRequest(UserAccount from, UserAccount to) {
		if (from.getId().equals(to.getId())) {
			throw new IllegalArgumentException("Cannot friend yourself");
		}
		
		// Check if request already exists in either direction
		Optional<FriendRequest> existing1 = friendRepo.findByFromUserAndToUser(from, to);
		if (existing1.isPresent()) {
			FriendRequest req = existing1.get();
			if (req.getStatus() == FriendRequest.Status.ACCEPTED) {
				throw new IllegalArgumentException("Already friends with this user");
			}
			if (req.getStatus() == FriendRequest.Status.PENDING) {
				throw new IllegalArgumentException("Friend request already sent");
			}
			// If rejected, allow resending by updating status
			req.setStatus(FriendRequest.Status.PENDING);
			return friendRepo.save(req);
		}
		
		// Check reverse direction
		Optional<FriendRequest> existing2 = friendRepo.findByFromUserAndToUser(to, from);
		if (existing2.isPresent()) {
			FriendRequest req = existing2.get();
			if (req.getStatus() == FriendRequest.Status.ACCEPTED) {
				throw new IllegalArgumentException("Already friends with this user");
			}
			if (req.getStatus() == FriendRequest.Status.PENDING) {
				throw new IllegalArgumentException("This user has already sent you a friend request. Please accept it instead.");
			}
		}
		
		// Create new request
		FriendRequest fr = new FriendRequest();
		fr.setFromUser(from);
		fr.setToUser(to);
		fr.setStatus(FriendRequest.Status.PENDING);
		return friendRepo.save(fr);
	}

	@Transactional
	public FriendRequest respond(Long requestId, boolean accept, UserAccount currentUser) {
		FriendRequest fr = friendRepo.findById(requestId)
				.orElseThrow(() -> new IllegalArgumentException("Friend request not found"));
		
		// Validate that the request is for the current user
		if (!fr.getToUser().getId().equals(currentUser.getId())) {
			throw new IllegalArgumentException("You can only respond to requests sent to you");
		}
		
		// Check if already responded
		if (fr.getStatus() != FriendRequest.Status.PENDING) {
			throw new IllegalArgumentException("This request has already been " + fr.getStatus().name().toLowerCase());
		}
		
		fr.setStatus(accept ? FriendRequest.Status.ACCEPTED : FriendRequest.Status.REJECTED);
		return friendRepo.save(fr);
	}

	public List<FriendRequest> incomingPending(UserAccount user) {
		return friendRepo.findByToUserAndStatus(user, FriendRequest.Status.PENDING);
	}

	public Set<UserAccount> getFriends(UserAccount user) {
		List<FriendRequest> accepted = friendRepo.findByFromUserAndStatusOrToUserAndStatus(user, FriendRequest.Status.ACCEPTED, user, FriendRequest.Status.ACCEPTED);
		Set<UserAccount> result = new HashSet<>();
		for (FriendRequest fr : accepted) {
			if (fr.getFromUser().getId().equals(user.getId())) {
				result.add(fr.getToUser());
			} else {
				result.add(fr.getFromUser());
			}
		}
		return result;
	}
}


