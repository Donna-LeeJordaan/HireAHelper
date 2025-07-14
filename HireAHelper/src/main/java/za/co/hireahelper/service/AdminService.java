package za.co.hireahelper.service;
///Ameeruddin Arai 230190939
//14 June 2025

import za.co.hireahelper.domain.Admin;

import java.util.List;

public interface AdminService extends IService<Admin, String> {
    List<Admin> getAll();
}


