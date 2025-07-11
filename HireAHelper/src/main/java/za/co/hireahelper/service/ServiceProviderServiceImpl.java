/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.repository.ServiceProviderRepository;
import java.util.List;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository repository;

    @Autowired
    public ServiceProviderServiceImpl(ServiceProviderRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceProvider create(ServiceProvider serviceProvider) {
        return this.repository.save(serviceProvider);
    }

    @Override
    public ServiceProvider read(String userId) {
        return this.repository.findById(userId).orElse(null);
    }

    @Override
    public ServiceProvider update(ServiceProvider serviceProvider) {
        return this.repository.save(serviceProvider);
    }

    @Override
    public boolean delete(String userId) {
        if (this.repository.existsById(userId)) {
            this.repository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<ServiceProvider> getAll() {
        return this.repository.findAll();
    }
}