package nsai.spring.repository;

import nsai.spring.domain.Building;
import nsai.spring.domain.Costs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Marcin on 15.06.2016.
 */
public interface CostsRepository extends JpaRepository<Costs, Long> {


}

