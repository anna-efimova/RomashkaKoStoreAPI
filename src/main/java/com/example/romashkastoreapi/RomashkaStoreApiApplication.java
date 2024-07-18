package com.example.romashkastoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.*")
public class RomashkaStoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RomashkaStoreApiApplication.class, args);
    }

}
