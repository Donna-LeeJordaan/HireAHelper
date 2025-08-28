package za.co.hireahelper.service;

import za.co.hireahelper.domain.User;

public interface AuthService {
    User login(String email, String password);
}
