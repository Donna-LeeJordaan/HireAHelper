package za.co.hireahelper.service;
//Ameeruddin Arai 230190839
//14 June 2025

import za.co.hireahelper.domain.Area;

import java.util.List;

public interface AreaService extends IService<Area, String> {
    List<Area> getAll();
}


