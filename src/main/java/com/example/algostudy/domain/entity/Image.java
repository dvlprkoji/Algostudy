package com.example.algostudy.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id @GeneratedValue
    private Long id;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "image_url")
    private String imageUrl;
}
