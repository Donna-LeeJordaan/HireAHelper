// Ameeruddin Arai 230190839

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Area;
import za.co.hireahelper.repository.AreaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public Area create(Area area) {
        return areaRepository.save(area);
    }

    @Override
    public Area read(String areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        return area.orElse(null);
    }

    @Override
    public Area update(Area area) {
        if (areaRepository.existsById(area.getAreaId())) {
            return areaRepository.save(area);
        }
        return null;
    }

    @Override
    public boolean delete(String areaId) {
        if (areaRepository.existsById(areaId)) {
            areaRepository.deleteById(areaId);
            return true;
        }
        return false;
    }

    @Override
    public List<Area> getAll() {
        return areaRepository.findAll();
    }
}

