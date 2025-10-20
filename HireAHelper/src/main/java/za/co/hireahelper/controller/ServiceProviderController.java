/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.domain.UserPrincipal;
import za.co.hireahelper.service.ServiceProviderService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/serviceProvider")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceProviderController {

    private final ServiceProviderService service;

    @Autowired
    public ServiceProviderController(ServiceProviderService service) {
        this.service = service;
    }

    @PostMapping(value ="/create", consumes = "multipart/form-data")
    public ServiceProvider create(@RequestPart("serviceProvider")  ServiceProvider serviceProvider,
                                  @RequestPart("profileImage") MultipartFile profileImage) {

        try {
            ServiceProvider sp = new ServiceProvider.Builder()
                    .copy(serviceProvider)
                    .setProfileImage(profileImage.getBytes())
                    .build();

            return service.create(sp);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read profile image",e);
        }
    }

    @GetMapping("/read/{userId}")
    public ServiceProvider read(@PathVariable String userId) {return service.read(userId);}

    @PutMapping("/update")
    public ServiceProvider update(@RequestBody ServiceProvider serviceProvider) {return service.update(serviceProvider);}

    @DeleteMapping("/delete/{userId}")
    public boolean delete(@PathVariable String userId) {return service.delete(userId);}

    @GetMapping("/all")
    public List<ServiceProvider> getAll() {return service.getAll();}

    @GetMapping("/{id}/profileImage")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        ServiceProvider sp = service.read(id);
        if (sp == null || sp.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(sp.getProfileImage());
    }
    @GetMapping("/current-serviceProvider")
    public ResponseEntity<?> getCurrentServiceProvider(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String userId = principal.getUser().getUserId();
        ServiceProvider serviceProvider = service.read(userId);
        return ResponseEntity.ok(serviceProvider);
    }
}
