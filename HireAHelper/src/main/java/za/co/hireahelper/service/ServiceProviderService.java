/* ServiceProvider.java

   Author: MT Osman (230599125)

   Date: 11 July 2025 */

package za.co.hireahelper.service;

import za.co.hireahelper.domain.ServiceProvider;
import java.util.List;

public interface ServiceProviderService extends IService<ServiceProvider, String>{
    List<ServiceProvider> getAll();
    List<ServiceProvider> findByServiceTypeName(String typeName);
}
