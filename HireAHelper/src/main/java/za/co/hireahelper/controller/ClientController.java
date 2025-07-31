/* ClientController.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025
*/

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Client;
import za.co.hireahelper.service.ClientService;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Client create(@RequestBody Client client) {
        return service.create(client);
    }

    @GetMapping("/read/{userId}")
    public Client read(@PathVariable String userId) {
        return service.read(userId);
    }

    @PutMapping("/update")
    public Client update(@RequestBody Client client) {
        return service.update(client);
    }

    @DeleteMapping("/delete/{userId}")
    public boolean delete(@PathVariable String userId) {
        return service.delete(userId);
    }

    @GetMapping("/all")
    public List<Client> getAll() {
        return service.getAll();
    }
}
