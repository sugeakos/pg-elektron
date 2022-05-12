package diploma.pgelektron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;

import static diploma.pgelektron.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
public class PgElektronApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgElektronApplication.class, args);
        new File(USER_FOLDER).mkdirs();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
