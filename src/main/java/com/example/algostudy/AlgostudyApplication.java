package com.example.algostudy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@EnableBatchProcessing
@SpringBootApplication
public class AlgostudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgostudyApplication.class, args);
    }

}
