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
		Optional<FriendRequest> existing = friendRepo.findByFromUserAndToUser(from, to);
		if (existing.isPresent()) return existing.get();
		FriendRequest fr = new FriendRequest();
		fr.setFromUser(from);
		fr.setToUser(to);
		fr.setStatus(FriendRequest.Status.PENDING);
		return friendRepo.save(fr);
	}

	@Transactional
	public FriendRequest respond(Long requestId, boolean accept) {
		FriendRequest fr = friendRepo.findById(requestId).orElseThrow();
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


