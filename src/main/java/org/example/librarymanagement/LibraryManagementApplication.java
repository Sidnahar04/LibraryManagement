package org.example.librarymanagement;

import org.example.librarymanagement.entity.ERole;
import org.example.librarymanagement.entity.Role;
import org.example.librarymanagement.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(RoleRepository roleRepository) {
        return args -> {
            // Create roles if they don't exist
            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
            }
            if (roleRepository.findByName(ERole.ROLE_LIBRARIAN).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_LIBRARIAN));
            }
            if (roleRepository.findByName(ERole.ROLE_MEMBER).isEmpty()) {
                roleRepository.save(new Role(ERole.ROLE_MEMBER));
            }
        };
    }
}
