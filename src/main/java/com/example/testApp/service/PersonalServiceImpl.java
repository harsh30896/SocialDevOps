package com.example.testApp.service;

import com.example.testApp.entity.PersonInfo;
import com.example.testApp.repository.PersonalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalServiceImpl implements PersonalService{

    PersonalRepo personalRepo;

    public PersonalServiceImpl(PersonalRepo personalRepo) {
        this.personalRepo = personalRepo;
    }

    @Override
    public void add(PersonInfo personInfo) {
        personalRepo.save(personInfo);
    }
}
