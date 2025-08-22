package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.User;
import za.co.hireahelper.dto.LoginRequest;
import za.co.hireahelper.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        User user = authService.login(request.getEmail(), request.getPassword());
        if (user == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(user);
    }
}
