package org.example.statify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatifyApplication {

  public static void main(String[] args) {
    SpringApplication.run(StatifyApplication.class, args);
  }
}
