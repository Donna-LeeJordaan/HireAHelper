// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import za.co.hireahelper.domain.Area;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AreaServiceTest {

    @Autowired
    private AreaService service;

    private static Area area;

    @BeforeAll
    public static void setUp() {
        area = new Area.Builder()
                .setAreaId("area001")
                .setName("Athlone")
                .build();
        assertNotNull(area, "Area creation failed in setup");
    }

    @Test
    void a_create() {
        Area created = service.create(area);
        assertNotNull(created);
        assertEquals(area.getAreaId(), created.getAreaId());
        System.out.println("Created: " + created);
    }

    @Test
    @Transactional
    void b_read() {
        Area read = service.read(area.getAreaId());
        assertNotNull(read);
        assertEquals(area.getName(), read.getName());
        System.out.println("Read: " + read);
    }

    @Test
    void c_update() {
        Area updated = new Area.Builder()
                .copy(area)
                .setName("Crawford")
                .build();

        Area result = service.update(updated);
        assertNotNull(result);
        assertEquals("Crawford", result.getName());
        System.out.println("Updated: " + result);
    }

    @Test
    @Transactional
    void d_getAll() {
        List<Area> all = service.getAll();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        System.out.println("All Areas: " + all);
    }

    @Test
    void e_delete() {
        boolean success = service.delete(area.getAreaId());
        assertTrue(success);
        System.out.println("Deleted: " + success);
    }
}
