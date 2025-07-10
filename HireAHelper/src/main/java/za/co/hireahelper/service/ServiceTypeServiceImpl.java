//Gabriel Kiewietz
//230990703
// 11 July 2025

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.ServiceType;
import za.co.hireahelper.repository.ServiceTypeRepository;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepository repository;

    @Autowired
    public ServiceTypeServiceImpl(ServiceTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceType create(ServiceType serviceType) {
        return this.repository.save(serviceType);
    }

    @Override
    public ServiceType read(String serviceTypeId) {
        return this.repository.findById(serviceTypeId).orElse(null);
    }

    @Override
    public ServiceType update(ServiceType serviceType) {
        return this.repository.save(serviceType);
    }

    @Override
    public boolean delete(String serviceTypeId) {
        if (this.repository.existsById(serviceTypeId)) {
            this.repository.deleteById(serviceTypeId);
            return true;
        }
        return false;
    }

    @Override
    public List<ServiceType> getAll() {
        return this.repository.findAll();
    }
}
