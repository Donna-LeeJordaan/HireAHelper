package za.co.hireahelper.service;
// Ameeruddin Arai 230190839

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
        areaRepository = mock(AreaRepository.class);
        areaService = new AreaServiceImpl(areaRepository);

        area = new Area.Builder()
                .setAreaId("A100")
                .setName("Cape Town North")
                .build();
    }

    @Test
    void testCreate() {
        when(areaRepository.save(area)).thenReturn(area);

        Area created = areaService.create(area);
        assertNotNull(created);
        assertEquals("A100", created.getAreaId());

        verify(areaRepository, times(1)).save(area);
    }

    @Test
    void testRead() {
        when(areaRepository.findById("A100")).thenReturn(Optional.of(area));

        Area found = areaService.read("A100");
        assertNotNull(found);
        assertEquals("Cape Town North", found.getName());

        verify(areaRepository, times(1)).findById("A100");
    }

    @Test
    void testUpdate() {
        when(areaRepository.existsById("A100")).thenReturn(true);
        when(areaRepository.save(area)).thenReturn(area);

        Area updated = areaService.update(area);
        assertNotNull(updated);
        assertEquals("A100", updated.getAreaId());

        verify(areaRepository, times(1)).save(area);
    }

    @Test
    void testDelete() {
        when(areaRepository.existsById("A100")).thenReturn(true);
        doNothing().when(areaRepository).deleteById("A100");

        boolean deleted = areaService.delete("A100");
        assertTrue(deleted);

        verify(areaRepository, times(1)).deleteById("A100");
    }

    @Test
    void testGetAll() {
        List<Area> areas = Arrays.asList(area);
        when(areaRepository.findAll()).thenReturn(areas);

        List<Area> all = areaService.getAll();
        assertEquals(1, all.size());
        assertEquals("A100", all.get(0).getAreaId());

        verify(areaRepository, times(1)).findAll();
    }
}

