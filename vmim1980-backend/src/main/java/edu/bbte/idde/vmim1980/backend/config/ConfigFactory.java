package edu.bbte.idde.vmim1980.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigFactory {
    private static Config config;
    public static final Logger LOGGER = LoggerFactory.getLogger(ConfigFactory.class);

    public static synchronized Config getConfig() {
        if (config == null) {
            String filename = getConfigFileName();
            LOGGER.info("Attempting to load properties from file: {}", filename);
            InputStream input = ConfigFactory.class.getResourceAsStream(filename);
            try {
                config = new ObjectMapper(new YAMLFactory()).readValue(input,Config.class);
            } catch (IOException e) {
                config = new Config();
                LOGGER.error("Error: could not load configurations from yaml input.");
            }
        }
        return config;
    }

    private static String getConfigFileName() {
        final StringBuilder sb = new StringBuilder("/application");
        String profile = System.getenv("profile");

        if (profile == null || profile.isEmpty()) {
            LOGGER.info("profile not detected: {}", profile);
        } else {
            LOGGER.info("Detected profile {}", profile);
            sb.append('-').append(profile);
        }

        return sb.append(".yaml").toString();
    }
}
