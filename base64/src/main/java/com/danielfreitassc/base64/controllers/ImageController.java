package com.danielfreitassc.base64.controllers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import com.danielfreitassc.base64.models.EncodedImage;
import com.danielfreitassc.base64.repositories.EncodedImageRepository;
import java.util.Base64;
import java.util.Optional;

@RestController
public class ImageController {

    @Autowired
    private EncodedImageRepository imageRepository;

    @PostMapping("/encode")
    public ResponseEntity<Object> encodeImageToBase64(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao enviar foto");
        }
        
        byte[] fileContent = IOUtils.toByteArray(file.getInputStream());
        String base64String = Base64.getEncoder().encodeToString(fileContent);
        
        EncodedImage encodedImage = new EncodedImage();
        encodedImage.setFileName(file.getOriginalFilename());
        encodedImage.setBase64String(base64String);
        imageRepository.save(encodedImage);
        return ResponseEntity.status(HttpStatus.OK).body(encodedImage);
    }

    @GetMapping("/decode/{id}")
public ResponseEntity<Object> decodeBase64ToImage(@PathVariable(value = "id") Long id) throws IOException {
    Optional<EncodedImage> optionalEncodedImage = imageRepository.findById(id);
    if (optionalEncodedImage.isPresent()) {
        EncodedImage encodedImage = optionalEncodedImage.get();
        byte[] decodedBytes = Base64.getDecoder().decode(encodedImage.getBase64String());
        FileUtils.writeByteArrayToFile(new File(encodedImage.getFileName()), decodedBytes);
        return ResponseEntity.ok().body(decodedBytes);
    } else {
        return ResponseEntity.notFound().build();
    }
}

}