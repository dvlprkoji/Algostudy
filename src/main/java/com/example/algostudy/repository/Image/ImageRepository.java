package com.example.algostudy.repository.Image;

import com.example.algostudy.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
