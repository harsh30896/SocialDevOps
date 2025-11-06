package com.example.testApp.repository;

import com.example.testApp.entity.FriendRequest;
import com.example.testApp.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
	List<FriendRequest> findByToUserAndStatus(UserAccount toUser, FriendRequest.Status status);
	List<FriendRequest> findByFromUserAndStatus(UserAccount fromUser, FriendRequest.Status status);
	Optional<FriendRequest> findByFromUserAndToUser(UserAccount fromUser, UserAccount toUser);
	List<FriendRequest> findByFromUserAndStatusOrToUserAndStatus(UserAccount fromUser, FriendRequest.Status status1, UserAccount toUser, FriendRequest.Status status2);
}


