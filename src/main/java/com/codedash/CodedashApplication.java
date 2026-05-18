package com.codedash;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codedash.user.Role;
import com.codedash.user.User;
import com.codedash.user.UserRepository;


@SpringBootApplication
@EnableScheduling
public class CodedashApplication {
    
    @Value("${app.master.email}")
    private String MASTER_EMAIL;

    @Value("${app.master.password}")
    private String MASTER_PASSWORD;

    public static void main(String[] args) {
		SpringApplication.run(CodedashApplication.class, args);
	}


    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByEmail(MASTER_EMAIL).isEmpty()) {
                User master = new User();
                master.setEmail(MASTER_EMAIL);
                master.setPassword(encoder.encode(MASTER_PASSWORD));
                master.setRole(Role.MASTER);
                master.setInstitution(null);

                userRepository.save(master);
            }
        };
    }
}
