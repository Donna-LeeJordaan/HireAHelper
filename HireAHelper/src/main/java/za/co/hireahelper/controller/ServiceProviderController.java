/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.service.ServiceProviderService;
import java.util.List;

@RestController
@RequestMapping("/serviceProvider")
public class ServiceProviderController {

    private final ServiceProviderService service;

    @Autowired
    public ServiceProviderController(ServiceProviderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ServiceProvider create(@RequestBody ServiceProvider serviceProvider) {return service.create(serviceProvider);}

    @GetMapping("/read/{userId}")
    public ServiceProvider read(@PathVariable String userId) {return service.read(userId);}

    @PutMapping("/update")
    public ServiceProvider update(@RequestBody ServiceProvider serviceProvider) {return service.update(serviceProvider);}

    @DeleteMapping("/delete/{userId}")
    public boolean delete(@PathVariable String userId) {return service.delete(userId);}

    @GetMapping("/all")
    public List<ServiceProvider> getAll() {return service.getAll();}
}
