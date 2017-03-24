package nsai.spring.repository;

import nsai.spring.domain.Building;
import nsai.spring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {

    Optional<Building> findOneByName(String name);
}
