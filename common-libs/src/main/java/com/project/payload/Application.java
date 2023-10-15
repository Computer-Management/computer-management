package com.project.payload;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Application implements QuarkusApplication {
    @Override
    public int run(String... args) throws Exception {
        Log.info("APPLICATION START SUCCESSFULLY!!!");
        Quarkus.waitForExit();
        return 0;
    }
}
