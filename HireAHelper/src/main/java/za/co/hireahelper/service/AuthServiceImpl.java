package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.User;
import za.co.hireahelper.repository.ClientRepository;
import za.co.hireahelper.repository.AdminRepository;
import za.co.hireahelper.repository.ServiceProviderRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepo;
    private final AdminRepository adminRepo;
    private final ServiceProviderRepository serviceProviderRepo;

    @Autowired
    public AuthServiceImpl(ClientRepository clientRepo, AdminRepository adminRepo, ServiceProviderRepository serviceProviderRepo) {
        this.clientRepo = clientRepo;
        this.adminRepo = adminRepo;
        this.serviceProviderRepo = serviceProviderRepo;
    }

    @Override
    public User login(String email, String password) {
        var client = clientRepo.findByEmailAndPassword(email, password);
        if (client.isPresent()) return client.get();

        var admin = adminRepo.findByEmailAndPassword(email, password);
        if (admin.isPresent()) return admin.get();

        var serviceProvider = serviceProviderRepo.findByEmailAndPassword(email, password);
        if (serviceProvider.isPresent()) return serviceProvider.get();

        return null;
    }
}