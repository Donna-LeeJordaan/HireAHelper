//Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import za.co.hireahelper.domain.Admin;
import java.util.List;

public interface AdminService extends IService<Admin, String> {
    List<Admin> getAll();
}


