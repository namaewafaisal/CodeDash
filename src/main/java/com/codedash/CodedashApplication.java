package com.codedash;

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

	public static void main(String[] args) {
		SpringApplication.run(CodedashApplication.class, args);
	}


    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.findByEmail("master@codedash.com").isEmpty()) {
                User master = new User();
                master.setEmail("master@codedash.com");
                master.setPassword(encoder.encode("admin123"));
                master.setRole(Role.MASTER);
                master.setInstitution(null);

                userRepository.save(master);
            }
        };
    }
}
