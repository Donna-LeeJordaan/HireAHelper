// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.factory.AdminFactory;
import za.co.hireahelper.repository.AreaRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class AdminServiceTest {

    @Autowired
    private AdminService service;

    @Autowired
    private AreaRepository areaRepository;

    private static Admin admin;

    @BeforeAll
    static void setup(@Autowired AreaRepository areaRepository) {

        final Area area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();

        // Save the Area entity first so it exists in the DB for FK reference
        areaRepository.save(area);

        admin = AdminFactory.createAdmin(
                "admin001",
                "Santiago Alvarez",
                "s.alvarez@example.com",
                "pass1234",
                "0712345678",
                area
        );
    }

    @Test
    void a_create() {
        Admin created = service.create(admin);
        assertNotNull(created);
        assertNotNull(created.getArea()); // Ensure area is set
        System.out.println("Created Admin: " + created);
    }

    @Test
    void b_read() {
        Admin read = service.read(admin.getUserId());
        assertNotNull(read);
        assertNotNull(read.getArea());
        System.out.println("Read Admin: " + read);
    }

    @Test
    void d_update() {
        Admin updatedAdmin = new Admin.Builder()
                .copy(admin)
                .setName("S.Alvarez Updated")
                .build();

        System.out.println("Updating Admin with new name: " + updatedAdmin.getName());
        Admin updated = service.update(updatedAdmin);
        assertNotNull(updated);
        System.out.println("Updated Admin: " + updated);
    }

    @Test
    void e_getAll() {
        List<Admin> allAdmins = service.getAll();
        assertNotNull(allAdmins);
        assertFalse(allAdmins.isEmpty());
        System.out.println("All Admins retrieved: ");
        allAdmins.forEach(System.out::println);
    }

    @Test
    void f_delete() {
        boolean deleted = service.delete(admin.getUserId());
        assertTrue(deleted);
        System.out.println("Deleted Admin ID " + admin.getUserId() + ": " + deleted);
    }
}
