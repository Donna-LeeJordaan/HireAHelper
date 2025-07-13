package za.co.hireahelper.controller;
// Ameeruddin Arai 230190839

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.service.AreaService;

import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping("/create")
    public Area create(@RequestBody Area area) {
        return areaService.create(area);
    }

    @GetMapping("/read/{areaId}")
    public Area read(@PathVariable String areaId) {
        return areaService.read(areaId);
    }

    @PutMapping("/update")
    public Area update(@RequestBody Area area) {
        return areaService.update(area);
    }

    @DeleteMapping("/delete/{areaId}")
    public boolean delete(@PathVariable String areaId) {
        return areaService.delete(areaId);
    }

    @GetMapping("/all")
    public List<Area> getAll() {
        return areaService.getAll();
    }
}

