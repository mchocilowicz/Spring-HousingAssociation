package nsai.spring.service.building;

import nsai.spring.domain.Building;
import nsai.spring.domain.User;
import nsai.spring.repository.BuildingRepository;
import nsai.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private  BuildingRepository buildingRepository;

    @Override
    public Optional<Building> getBuildingById(long id) {
        return Optional.ofNullable(buildingRepository.findOne(id));
    }

    @Override
    public Optional<Building> getBuildingByName(String name) {
        return buildingRepository.findOneByName(name);
    }

    @Override
    public Collection<Building> getAllBuildings() {
        return buildingRepository.findAll(new Sort("name"));
    }

    @Override
    public Building create(Building build) {
        return buildingRepository.save(build);
    }

    @Override
    public void delete(Long id){
        buildingRepository.delete(id);
    }
}
