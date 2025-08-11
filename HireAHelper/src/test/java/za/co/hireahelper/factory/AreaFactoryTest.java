// Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Area;
import static org.junit.jupiter.api.Assertions.*;

public class AreaFactoryTest {

    @Test
    void testCreateAreaWithValidData() {
        System.out.println("\n--- Running testCreateAreaWithValidData ---");
        Area area = AreaFactory.createArea("area001", "Athlone");

        assertNotNull(area);
        assertEquals("area001", area.getAreaId());
        assertEquals("Athlone", area.getName());

        System.out.println("Area created successfully: " + area);
    }

    @Test
    void testCreateAreaWithNullId() {
        System.out.println("\n--- Running testCreateAreaWithNullId ---");
        Area area = AreaFactory.createArea(null, "Grassy Park");

        assertNull(area);
        System.out.println("Area creation failed as expected due to null ID.");
    }

    @Test
    void testCreateAreaWithEmptyName() {
        System.out.println("\n--- Running testCreateAreaWithEmptyName ---");
        Area area = AreaFactory.createArea("area002", "");

        assertNull(area);
        System.out.println("Area creation failed as expected due to empty name.");
    }

    @Test
    void testCreateAreaWithBlankId() {
        System.out.println("\n--- Running testCreateAreaWithBlankId ---");
        Area area = AreaFactory.createArea("", "Grassy Park");

        assertNull(area);
        System.out.println("Area creation failed as expected due to blank ID.");
    }

    @Test
    void testCreateAreaWithNullName() {
        System.out.println("\n--- Running testCreateAreaWithNullName ---");
        Area area = AreaFactory.createArea("area003", null);

        assertNull(area);
        System.out.println("Area creation failed as expected due to null name.");
    }
}
