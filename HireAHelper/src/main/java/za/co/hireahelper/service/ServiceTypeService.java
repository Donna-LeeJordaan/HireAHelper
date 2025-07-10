//Gabriel Kiewietz
//230990703
//11 July 2025

package za.co.hireahelper.service;

import za.co.hireahelper.domain.ServiceType;
import java.util.List;

public interface ServiceTypeService extends IService<ServiceType, String> {
    List<ServiceType> getAll();
}

