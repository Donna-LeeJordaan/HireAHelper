//Gabriel Kiewietz
// 230990703
// 10 July 2025

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.service.ServiceTypeService;
import java.util.List;

@RestController
@RequestMapping("/api/service-types")
public class ServiceTypeController {
    @Autowired
    private ServiceTypeService service;

    @PostMapping
    public ServiceType create(@RequestBody ServiceType serviceType) {
        return service.create(serviceType);
    }

    @GetMapping("/{typeId}")
    public ServiceType read(@PathVariable String typeId) {
        return service.read(typeId);
    }

    @PutMapping
    public ServiceType update(@RequestBody ServiceType serviceType) {
        return service.update(serviceType);
    }

    @DeleteMapping("/{typeId}")
    public boolean delete(@PathVariable String typeId) {
        return service.delete(typeId);
    }

    @GetMapping
    public List<ServiceType> getAll() {
        return service.getAll();
    }
}