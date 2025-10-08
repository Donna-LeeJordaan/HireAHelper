/* ServiceTypeController.java
   Author: Gabriel Kiewietz (230990703)
   Date: 31 July 2025
*/

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.service.ServiceTypeService;
import java.util.List;

@RestController
@RequestMapping("/serviceType")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceTypeController {

    private final ServiceTypeService service;

    @Autowired
    public ServiceTypeController(ServiceTypeService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ServiceType create(@RequestBody ServiceType serviceType) {
        return service.create(serviceType);
    }

    @GetMapping("/read/{typeId}")
    public ServiceType read(@PathVariable String typeId) {
        return service.read(typeId);
    }

    @PutMapping("/update")
    public ServiceType update(@RequestBody ServiceType serviceType) {
        return service.update(serviceType);
    }

    @DeleteMapping("/delete/{typeId}")
    public boolean delete(@PathVariable String typeId) {
        return service.delete(typeId);
    }

    @GetMapping("/all")
    public List<ServiceType> getAll() {
        return service.getAll();
    }
}
