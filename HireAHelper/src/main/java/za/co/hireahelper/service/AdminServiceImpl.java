package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.repository.AdminRepository;
import za.co.hireahelper.service.AdminService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(String adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (adminRepository.existsById(admin.getAdminId())) {
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
