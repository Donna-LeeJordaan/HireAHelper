//Ameeruddin Arai 230190839
package za.co.hireahelper.service;

import za.co.hireahelper.domain.Area;

import java.util.List;

public interface AreaService extends IService<Area, String> {
    List<Area> getAll();
}


