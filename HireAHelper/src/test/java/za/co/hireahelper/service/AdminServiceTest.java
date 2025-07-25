// Ameeruddin Arai 230190839
// 24 July 2025

package za.co.hireahelper.service;

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
            "fatima.patel@example.com",
            "securePass123",
            "0712345678"
    );

    @Test
    void a_create() {
        System.out.println("\n--- Running CREATE test ---");
        Admin created = service.create(admin);
        assertNotNull(created);
        System.out.println("Created Admin: " + created);
    }

    @Test
    void b_read() {
        System.out.println("\n--- Running READ test ---");
        Admin read = service.read(admin.getAdminId());
        assertNotNull(read);
        System.out.println("Read Admin: " + read);
    }

    @Test
    void d_update() {
        System.out.println("\n--- Running UPDATE test ---");
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("Fatima P. Updated")
                .build();

        System.out.println("Updating Admin with new name: " + updatedAdmin.getName());
        Admin updated = service.update(updatedAdmin);
        assertNotNull(updated);
        System.out.println("Updated Admin: " + updated);
    }

    @Test
    void e_getAll() {
        System.out.println("\n--- Running GET ALL test ---");
        List<Admin> allAdmins = service.getAll();
        assertNotNull(allAdmins);
        System.out.println("All Admins retrieved: ");
        allAdmins.forEach(System.out::println);
    }

    @Test
    void f_delete() {
        System.out.println("\n--- Running DELETE test ---");
        boolean deleted = service.delete(admin.getAdminId());
        assertTrue(deleted);
        System.out.println("Deleted Admin ID " + admin.getAdminId() + ": " + deleted);
    }
}
