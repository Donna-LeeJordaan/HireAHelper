// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import za.co.hireahelper.domain.Area;
import java.util.List;

public interface AreaService {
    Area create(Area area);
    Area read(String areaId);
    Area update(Area area);
    boolean delete(String areaId);
    List<Area> getAll();
}

