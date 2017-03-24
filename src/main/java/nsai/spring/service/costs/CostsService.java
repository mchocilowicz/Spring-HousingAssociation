package nsai.spring.service.costs;

import nsai.spring.domain.Building;
import nsai.spring.domain.Costs;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface CostsService {

    Optional<Costs> getCostsById(long id);

     Costs getCurrentCost();

    Collection<Costs> getAllCosts();

    Costs create(Costs cost);

    void delete(Long id);
}
