package org.yyx.netty.study;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StudyClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
