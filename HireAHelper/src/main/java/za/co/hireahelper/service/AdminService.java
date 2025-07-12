package za.co.hireahelper.service;

import za.co.hireahelper.domain.Admin;
import java.util.List;

public interface AdminService {
    Admin create(Admin admin);
    Admin read(String adminId);
    Admin update(Admin admin);
    boolean delete(String adminId);
    List<Admin> getAll();
}

