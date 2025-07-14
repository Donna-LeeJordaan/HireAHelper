//Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Admin;
import static org.junit.jupiter.api.Assertions.*;

public class AdminFactoryTest {

    @Test
    void testCreateAdminWithValidData() {
        Admin admin = AdminFactory.createAdmin(
                "A001",
                "John Doe",
                "admin@example.com"
        );

        assertNotNull(admin);
        assertEquals("A001", admin.getUserId());
        assertEquals("John Doe", admin.getName());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("securePass123", admin.getPassword());
        assertEquals("0712345678", admin.getMobileNumber());
    }

    @Test
    void testCreateAdminWithMissingName() {
        Admin admin = AdminFactory.createAdmin(
                "A002",
                "",
                "admin@example.com"
        );

        assertNull(admin);
    }

    @Test
    void testCreateAdminWithNullEmail() {
        Admin admin = AdminFactory.createAdmin(
                "A003",
                "Jane Admin",
                null
        );

        assertNull(admin);
    }

    @Test
    void testCreateAdminWithBlankPassword() {
        Admin admin = AdminFactory.createAdmin(
                "A004",
                "Jane Admin",
                "jane@example.com"
        );

        assertNull(admin);
    }
}

