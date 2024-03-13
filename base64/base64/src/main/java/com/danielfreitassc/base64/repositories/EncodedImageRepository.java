package com.danielfreitassc.base64.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielfreitassc.base64.models.EncodedImage;

public interface EncodedImageRepository extends JpaRepository<EncodedImage, Long> {
    
}
