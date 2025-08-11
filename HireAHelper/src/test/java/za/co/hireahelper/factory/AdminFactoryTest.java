// Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import org.junit.jupiter.api.*;
import za.co.hireahelper.domain.Admin;
import za.co.hireahelper.domain.Area;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminFactoryTest {

    // Generic placeholder Area for all tests
    private static final Area genericArea = new Area.Builder()
            .setAreaId("area001")
            .setName("Athlone")
            .build();

    private static final Admin a1 = AdminFactory.createAdmin(
            "admin001", "Santiago Alvarez", "s.alvarez@example.com", "pass1234", "0712345678",
            genericArea);

    private static final Admin a2 = AdminFactory.createAdmin(
            "admin002", "Aisha Khan", "a.khan@example.com", "secure567", "0723456789",
            genericArea);

    private static final Admin a3 = AdminFactory.createAdmin(
            "admin003", "Mikhail Petrov", "m.petrov@example.com", "lock789", "0734567890",
            genericArea);

    @Test
    @Order(1)
    public void testCreateAdmin() {
        assertNotNull(a1);
        System.out.println(a1.toString());
    }

    @Test
    @Order(2)
    public void testCreateAdminWithAllAttributes() {
        assertNotNull(a2);
        System.out.println(a2.toString());
    }

    @Test
    @Order(3)
    public void testCreateAdminThatFailsInvalidEmail() {
        Admin a4 = AdminFactory.createAdmin(
                "admin004", "Leila Mansouri", "leila-at-example.com", "badpass", "0745678901",
                genericArea);
        System.out.println("Admin a4 = " + a4);
        assertNull(a4);
    }

    @Test
    @Order(4)
    public void testCreateAdminThatFailsInvalidMobile() {
        Admin a5 = AdminFactory.createAdmin(
                "admin005", "Omar Farouk", "omar.farouk@example.com", "pw12345", "abcd567890",
                genericArea);
        System.out.println("Admin a5 = " + a5);
        assertNull(a5);
    }

    @Test
    @Order(5)
    public void testCreateAdminThatFailsNullFields() {
        Admin a6 = AdminFactory.createAdmin(
                null, "Yara Haddad", "yara@example.com", "secret999", "0756789012",
                genericArea);
        System.out.println("Admin a6 = " + a6);
        assertNull(a6);
    }

    @Test
    @Order(6)
    @Disabled
    public void testNotImplementedYet() {
        // Todo: Add future test cases here
    }
}
