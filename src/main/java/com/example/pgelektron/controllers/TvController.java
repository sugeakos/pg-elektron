package com.example.pgelektron.controllers;

import com.example.pgelektron.person.Person;
import com.example.pgelektron.person.role.PersonRole;
import com.example.pgelektron.tv.TV;
import com.example.pgelektron.tv.TvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api/v1/tv")
@RequiredArgsConstructor
public class TvController {
    private final TvService tvService;

    @GetMapping("/all-tv")
    public ResponseEntity<List<TV>> getAllTVs(){
        return ResponseEntity.ok().body(tvService.findAllTvs());
    }

    @PostMapping("/new")
    public ResponseEntity<TV> saveNewTV(@RequestBody TV tv) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/tv/new").toUriString());
        return ResponseEntity.created(uri).body(tvService.saveTv(tv));
    }
}
