package za.co.hireahelper.service;
//Ameeruddin Arai 230190839

import za.co.hireahelper.domain.Admin;
import java.util.List;

public interface AdminService {
    Admin create(Admin admin);
    Admin read(String adminId);
    Admin update(Admin admin);
    boolean delete(String adminId);
    List<Admin> getAll();
}

