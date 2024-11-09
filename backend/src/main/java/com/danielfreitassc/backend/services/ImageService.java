package com.danielfreitassc.backend.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.models.ImageEntity;
import com.danielfreitassc.backend.repositories.ImagemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImagemRepository imagemRepository;


    public String uploadImage(MultipartFile multipartFile) {
        try {     
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setName(multipartFile.getOriginalFilename());
            imageEntity.setData(multipartFile.getBytes());
            imagemRepository.save(imageEntity);
            return "Salvo";
        } catch (IOException e) {
            return "Erro";
        }
    } 

    public ResponseEntity<byte[]> getAll(Long id) {
        Optional<ImageEntity> imageData = imagemRepository.findById(id);
        if(imageData.isEmpty()) throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"Foto não encontrada");
        HttpHeaders httpHeaders = new HttpHeaders();
        byte[] imageBytes = imageData.get().getData();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, httpHeaders, HttpStatus.OK);
    }
}
