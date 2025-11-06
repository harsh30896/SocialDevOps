package com.example.testApp;

import com.example.testApp.entity.PersonInfo;
import com.example.testApp.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonalController {

    @Autowired
    PersonalService personalService;

    @PostMapping("/add")
    public String addData(@RequestBody PersonInfo personInfo) {
        personalService.add(personInfo);
        return "Data added successfully!";
    }

}
