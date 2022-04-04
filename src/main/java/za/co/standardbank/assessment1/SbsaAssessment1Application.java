package za.co.standardbank.assessment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SbsaAssessment1Application {

    public static void main(String[] args) {
        SpringApplication.run(SbsaAssessment1Application.class, args);
    }

}
