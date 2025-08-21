// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.repository.AdminRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, BCryptPasswordEncoder encoder) {
        this.adminRepository = adminRepository;
        this.encoder = encoder;
    }

    @Override
    public Admin create(Admin admin) {
        // Hash the password before saving
        admin.setPassword(encoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(String adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (adminRepository.existsById(admin.getUserId())) {
            // Hash the password before updating
            admin.setPassword(encoder.encode(admin.getPassword()));
            return adminRepository.save(admin);
        }
        return null;
    }

    @Override
    public boolean delete(String adminId) {
        if (adminRepository.existsById(adminId)) {
            adminRepository.deleteById(adminId);
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }
}

