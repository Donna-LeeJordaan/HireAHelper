package za.co.hireahelper.factory;

//Ameeruddin Arai  student no 230190839
//19/05/2025

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
