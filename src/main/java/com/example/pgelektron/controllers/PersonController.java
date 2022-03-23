package com.example.pgelektron.controllers;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.person.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person-list")
    public String listAllPersons(Model model) {
        List<Person> personList = personService.getAllPersons();
        model.addAttribute("persons", personList);
        return "person/listPersons";
    }

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok().body(personService.getAllPersons());
    }
}
