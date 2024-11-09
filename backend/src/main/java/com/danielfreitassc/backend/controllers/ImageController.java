package com.danielfreitassc.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danielfreitassc.backend.services.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/foto")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;


    @PostMapping
    public String updaloadImage(@RequestParam("file") MultipartFile multipartFile) {
        return imageService.uploadImage(multipartFile);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getAll(@PathVariable Long id) {
        return imageService.getAll(id);
    }
}
