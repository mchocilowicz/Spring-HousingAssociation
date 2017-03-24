package nsai.spring.service.counter;

import nsai.spring.domain.Costs;
import nsai.spring.domain.Counter;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface CounterService {

    Optional<Counter> getCounterById(long id);

    Optional<Counter> getCounterByRoom(String room);

    Collection<Counter> getAllCounters();

    Counter create(Counter counter);

    void delete(Long id);
}
