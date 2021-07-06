package dev.shouryapunj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Texas Hamburger Company Web Applications starting... " + LocalDateTime.now());
        SpringApplication.run(Application.class, args);
        logger.info("Texas Hamburger Company Web Applications started... " + LocalDateTime.now());
    }
}
