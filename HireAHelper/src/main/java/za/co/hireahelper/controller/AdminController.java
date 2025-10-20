//Ameeruddin Arai 230190839

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.domain.UserPrincipal;
import za.co.hireahelper.service.AdminService;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public Admin create(@RequestBody Admin admin) {
        return adminService.create(admin);
    }

    @GetMapping("/read/{adminId}")
    public Admin read(@PathVariable String adminId) {
        return adminService.read(adminId);
    }

    @PutMapping("/update")
    public Admin update(@RequestBody Admin admin) {
        return adminService.update(admin);
    }

    @DeleteMapping("/delete/{adminId}")
    public boolean delete(@PathVariable String adminId) {
        return adminService.delete(adminId);
    }

    @GetMapping("/all")
    public List<Admin> getAll() {
        return adminService.getAll();
    }

    @GetMapping("/current-admin")
    public ResponseEntity<?> getCurrentAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String userId = principal.getUser().getUserId();
        Admin admin = adminService.read(userId);
        return ResponseEntity.ok(admin);
    }
    
}
