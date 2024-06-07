package cn.edu.ustc.sscs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SscsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SscsApplication.class, args);
    }

}
