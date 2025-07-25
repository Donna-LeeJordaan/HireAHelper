// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.repository.AreaRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AreaServiceTest {

    private AreaRepository areaRepository;
    private AreaService areaService;

    private Area area;

    @BeforeEach
    void setUp() {
        System.out.println("\n--- Setting up test environment ---");
        areaRepository = mock(AreaRepository.class);
        areaService = new AreaServiceImpl(areaRepository);

        area = new Area.Builder()
                .setAreaId("A100")
                .setName("Cape Town North")
                .build();
        System.out.println("Test Area created: " + area);
    }

    @Test
    void testCreate() {
        System.out.println("\n--- Running testCreate ---");
        when(areaRepository.save(area)).thenReturn(area);

        Area created = areaService.create(area);
        assertNotNull(created);
        assertEquals("A100", created.getAreaId());

        System.out.println("Area created successfully: " + created);
        verify(areaRepository, times(1)).save(area);
    }

    @Test
    void testRead() {
        System.out.println("\n--- Running testRead ---");
        when(areaRepository.findById("A100")).thenReturn(Optional.of(area));

        Area found = areaService.read("A100");
        assertNotNull(found);
        assertEquals("Cape Town North", found.getName());

        System.out.println("Area read successfully: " + found);
        verify(areaRepository, times(1)).findById("A100");
    }

    @Test
    void testUpdate() {
        System.out.println("\n--- Running testUpdate ---");
        when(areaRepository.existsById("A100")).thenReturn(true);
        when(areaRepository.save(area)).thenReturn(area);

        Area updated = areaService.update(area);
        assertNotNull(updated);
        assertEquals("A100", updated.getAreaId());

        System.out.println("Area updated successfully: " + updated);
        verify(areaRepository, times(1)).save(area);
    }

    @Test
    void testDelete() {
        System.out.println("\n--- Running testDelete ---");
        when(areaRepository.existsById("A100")).thenReturn(true);
        doNothing().when(areaRepository).deleteById("A100");

        boolean deleted = areaService.delete("A100");
        assertTrue(deleted);

        System.out.println("Area deleted successfully: " + deleted);
        verify(areaRepository, times(1)).deleteById("A100");
    }

    @Test
    void testGetAll() {
        System.out.println("\n--- Running testGetAll ---");
        List<Area> areas = Arrays.asList(area);
        when(areaRepository.findAll()).thenReturn(areas);

        List<Area> all = areaService.getAll();
        assertEquals(1, all.size());
        assertEquals("A100", all.get(0).getAreaId());

        System.out.println("All areas retrieved successfully:");
        all.forEach(System.out::println);

        verify(areaRepository, times(1)).findAll();
    }
}
