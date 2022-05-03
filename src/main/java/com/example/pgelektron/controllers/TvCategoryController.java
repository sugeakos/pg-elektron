package com.example.pgelektron.controllers;

import com.example.pgelektron.domain.TVCategory;
import com.example.pgelektron.service.impl.TvCategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Controller
@RequestMapping("/api/v1")
public class TvCategoryController {
    private final TvCategoryServiceImpl tvCategoryService;

    public TvCategoryController(TvCategoryServiceImpl tvCategoryService) {
        this.tvCategoryService = tvCategoryService;
    }

    @GetMapping("/tv-categories")
    public String getAllTvCategories(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<TVCategory> categories = tvCategoryService.getAllTvCategories();
        log.error("Request cumo: " + request.getHeader(AUTHORIZATION));
        model.addAttribute("categories", categories);
        return "tvcategories/listTvCategories";

    }

    @PostMapping("/tv-categories/new")
    public ResponseEntity<TVCategory> createNewTvCategory(@RequestBody TVCategory tvCategory) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/tv-categories/new").toUriString());
        return ResponseEntity.created(uri).body( tvCategoryService.saveTvCategory(tvCategory));
    }
}
