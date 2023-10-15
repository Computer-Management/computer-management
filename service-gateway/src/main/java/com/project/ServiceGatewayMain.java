package com.project;

import com.project.payload.Application;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ServiceGatewayMain {
    public static void main(String... args) {
        Quarkus.run(Application.class, args);
    }
}
