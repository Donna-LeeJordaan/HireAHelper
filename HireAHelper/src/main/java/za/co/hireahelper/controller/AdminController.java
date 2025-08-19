//Ameeruddin Arai 230190839

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.service.AdminService;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin
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
}
