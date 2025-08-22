/* ClientService.java
   Author: S Hendricks (221095136)
   Date: 09 July 2025
*/

package za.co.hireahelper.service;

import za.co.hireahelper.domain.Client;
import java.util.List;

public interface ClientService extends IService<Client, String> {
    List<Client> getAll();

    // Login method
    Client login(String email, String password);
}

