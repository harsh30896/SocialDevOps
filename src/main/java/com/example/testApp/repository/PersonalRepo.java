package com.example.testApp.repository;

import com.example.testApp.entity.PersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepo extends JpaRepository<PersonInfo,Integer> {
}
