package com.project.utils;

import io.vertx.core.json.JsonObject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class AdminUtils {
    private static final String RESULT_CODE = "resultCode";
    private static final String MESSAGE_CODE = "messageCode";

    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    public int getLimit(int limit) {
        int data = 24;
        if (limit != 0) {
            data = limit;
        }
        return data;
    }

    public boolean verifyPassword(String password) {
        return password.matches("^[a-fA-F0-9]{32}$");
    }

    public JsonObject statusResponse(int resultCode, String messageCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put(RESULT_CODE, resultCode);
        jsonObject.put(MESSAGE_CODE, messageCode);
        return jsonObject;
    }
}
