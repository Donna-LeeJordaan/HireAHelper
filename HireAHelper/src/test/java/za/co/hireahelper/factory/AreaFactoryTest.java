//Ameeruddin Arai 230190839

package za.co.hireahelper.factory;

import org.junit.jupiter.api.Test;
import za.co.hireahelper.domain.Area;
import static org.junit.jupiter.api.Assertions.*;

public class AreaFactoryTest {

    @Test
    void testCreateAreaWithValidData() {
        Area area = AreaFactory.createArea("AR001", "Cape Town North");

        assertNotNull(area);
        assertEquals("AR001", area.getAreaId());
        assertEquals("Cape Town North", area.getName());
    }

    @Test
    void testCreateAreaWithNullId() {
        Area area = AreaFactory.createArea(null, "Durban");

        assertNull(area);
    }

    @Test
    void testCreateAreaWithEmptyName() {
        Area area = AreaFactory.createArea("AR002", "");

        assertNull(area);
    }

    @Test
    void testCreateAreaWithBlankId() {
        Area area = AreaFactory.createArea("", "Johannesburg");

        assertNull(area);
    }

    @Test
    void testCreateAreaWithNullName() {
        Area area = AreaFactory.createArea("AR003", null);

        assertNull(area);
    }
}
