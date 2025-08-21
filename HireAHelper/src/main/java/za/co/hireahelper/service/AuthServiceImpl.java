package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.User;
import za.co.hireahelper.repository.AdminRepository;
import za.co.hireahelper.repository.ClientRepository;
import za.co.hireahelper.repository.ServiceProviderRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepo;
    private final ServiceProviderRepository spRepo;
    private final AdminRepository adminRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthServiceImpl(ClientRepository clientRepo,
                           ServiceProviderRepository spRepo,
                           AdminRepository adminRepo,
                           BCryptPasswordEncoder encoder) {
        this.clientRepo = clientRepo;
        this.spRepo = spRepo;
        this.adminRepo = adminRepo;
        this.encoder = encoder;
    }

    @Override
    public User login(String email, String password) {
        // Check Client
        Client client = clientRepo.findAll().stream()
                .filter(c -> c.getEmail().equals(email) && encoder.matches(password, c.getPassword()))
                .findFirst().orElse(null);
        if (client != null) return client;

        // Check ServiceProvider
        ServiceProvider sp = spRepo.findAll().stream()
                .filter(s -> s.getEmail().equals(email) && encoder.matches(password, s.getPassword()))
                .findFirst().orElse(null);
        if (sp != null) return sp;

        // Check Admin
        Admin admin = adminRepo.findAll().stream()
                .filter(a -> a.getEmail().equals(email) && encoder.matches(password, a.getPassword()))
                .findFirst().orElse(null);
        if (admin != null) return admin;

        return null; // not found
    }
}


