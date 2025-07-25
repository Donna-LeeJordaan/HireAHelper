// Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Area;

import static org.junit.jupiter.api.Assertions.*;

public class AreaFactoryTest {

    @Test
    void testCreateAreaWithValidData() {
        System.out.println("\n--- Running testCreateAreaWithValidData ---");
        Area area = AreaFactory.createArea("AR001", "Cape Town North");

        assertNotNull(area);
        assertEquals("AR001", area.getAreaId());
        assertEquals("Cape Town North", area.getName());

        System.out.println("Area created successfully: " + area);
    }

    @Test
    void testCreateAreaWithNullId() {
        System.out.println("\n--- Running testCreateAreaWithNullId ---");
        Area area = AreaFactory.createArea(null, "Durban");

        assertNull(area);
        System.out.println("Area creation failed as expected due to null ID.");
    }

    @Test
    void testCreateAreaWithEmptyName() {
        System.out.println("\n--- Running testCreateAreaWithEmptyName ---");
        Area area = AreaFactory.createArea("AR002", "");

        assertNull(area);
        System.out.println("Area creation failed as expected due to empty name.");
    }

    @Test
    void testCreateAreaWithBlankId() {
        System.out.println("\n--- Running testCreateAreaWithBlankId ---");
        Area area = AreaFactory.createArea("", "Johannesburg");

        assertNull(area);
        System.out.println("Area creation failed as expected due to blank ID.");
    }

    @Test
    void testCreateAreaWithNullName() {
        System.out.println("\n--- Running testCreateAreaWithNullName ---");
        Area area = AreaFactory.createArea("AR003", null);

        assertNull(area);
        System.out.println("Area creation failed as expected due to null name.");
    }
}
