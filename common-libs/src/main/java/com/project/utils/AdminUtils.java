package com.project.utils;

import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class AdminUtils {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public boolean verifyPassword(String password) {
        return password.matches("^[a-fA-F0-9]{32}$");
    }
}
