package nsai.spring.service.flat;

import nsai.spring.domain.Building;
import nsai.spring.domain.Flat;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface FlatService {

    Optional<Flat> getFlatById(long id);

    Optional<Flat> getFlatByNumber(int number);

    Collection<Flat> getAllFlats();

    Flat create(Flat flat);
    void delete(Long id);

}
