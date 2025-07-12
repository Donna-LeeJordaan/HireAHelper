package za.co.hireahelper.service;
//Ameeruddin Arai 23019083

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.factory.AdminFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminServiceTest {

    @Autowired
    private AdminService service;

    private static final Admin admin = AdminFactory.createAdmin(
            "admin123",
            "Fatima Patel",
            "fatima.patel@example.com"
    );

    @Test
    void a_create() {
        Admin created = service.create(admin);
        assertNotNull(created);
        System.out.println("Created: " + created);
    }

    @Test
    void b_read() {
        Admin read = service.read(admin.getAdminId());
        assertNotNull(read);
        System.out.println("Read: " + read);
    }

    @Test
    void d_update() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        Admin updated = service.update(updatedAdmin);
        assertNotNull(updated);
        System.out.println("Updated: " + updated);
    }

    @Test
    void e_getAll() {
        List<Admin> allAdmins = service.getAll();
        assertNotNull(allAdmins);
        System.out.println("All admins: " + allAdmins);
    }

    @Test
    void f_delete() {
        boolean deleted = service.delete(admin.getAdminId());
        assertTrue(deleted);
        System.out.println("Deleted: " + deleted);
    }
}
