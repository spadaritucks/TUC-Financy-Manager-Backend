package com.tucfinancymanager.backend.config;

import io.github.cdimascio.dotenv.Dotenv;




public class EnvConfig {

    public static void loadEnvironmentVariables() {
        Dotenv dotenv = Dotenv.configure().load();
        String profile = dotenv.get("SPRING_PROFILES_ACTIVE");

        if("dev".equals(profile)){
            System.setProperty("DATABASE_URL", dotenv.get("DATABASE_URL"));
            System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
            System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
            System.setProperty("AWS_REGION", dotenv.get("AWS_REGION"));
            System.setProperty("AWS_BUCKETNAME", dotenv.get("AWS_BUCKETNAME"));
            System.setProperty("API_TOKEN_SECRET", dotenv.get("API_TOKEN_SECRET"));
            System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
            System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        }
    }

}
