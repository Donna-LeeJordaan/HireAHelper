package za.co.hireahelper.factory;

//ameeruddin arai 230190839

import za.co.hireahelper.domain.Area;
import za.co.hireahelper.util.Helper;

public class AreaFactory {

    public static Area createArea(String areaId, String name) {
        if (Helper.isNullOrEmpty(areaId) || Helper.isNullOrEmpty(name)) {
            return null;
        }

        return new Area.Builder()
                .setAreaId(areaId)
                .setName(name)
                .build();
    }
}
