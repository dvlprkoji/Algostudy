package com.example.algostudy.repository;

import com.example.algostudy.domain.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Resource findByResourceName(String resourceName);
}
