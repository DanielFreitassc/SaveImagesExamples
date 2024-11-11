package com.danielfreitassc.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielfreitassc.backend.models.ImageEntity;

public interface ImagemRepository extends JpaRepository<ImageEntity, Long>{
    
}