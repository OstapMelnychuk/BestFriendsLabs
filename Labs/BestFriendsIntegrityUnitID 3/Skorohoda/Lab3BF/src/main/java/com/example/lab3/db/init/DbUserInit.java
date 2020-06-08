package com.example.lab3.db.init;

import com.example.lab3.entity.User;
import com.example.lab3.repository.ApartmentTenantRepository;
import com.example.lab3.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class DbUserInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApartmentTenantRepository apartmentTenantRepository;

    public DbUserInit(UserRepository userRepository, PasswordEncoder passwordEncoder,
                      ApartmentTenantRepository apartmentTenantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.apartmentTenantRepository = apartmentTenantRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        apartmentTenantRepository.deleteAll();
        userRepository.deleteAll();
        User dispatcher = new User(null, "newUser@gmail.com", passwordEncoder.encode("SomePass"),
                "DISPATCHER", "", true);
        User tenant = new User(null, "newUser111@gmail.com", passwordEncoder.encode("SomePass1"),
                "TENANT", "", true);
        User worker = new User(null, "newUser11@gmail.com", passwordEncoder.encode("SomePass2"),
                "WORKER", "", true);
        User admin = new User(null, "newUser1@gmail.com", passwordEncoder.encode("SomePass3"),
                "ADMIN", "", true);

        List<User> userList = Arrays.asList(dispatcher, tenant, worker, admin);

        userRepository.saveAll(userList);
    }
}
