package nsai.spring.service.building;

import nsai.spring.domain.Building;
import nsai.spring.domain.User;
import nsai.spring.domain.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface BuildingService {

    Optional<Building> getBuildingById(long id);

    Optional<Building> getBuildingByName(String name);

    Collection<Building> getAllBuildings();

    Building create(Building build);

    void delete(Long id);
}
