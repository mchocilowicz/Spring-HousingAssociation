package nsai.spring.service.counter;

import nsai.spring.domain.Costs;
import nsai.spring.domain.Counter;
import nsai.spring.repository.CostsRepository;
import nsai.spring.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterRepository counterRepository;

    @Override
    public Optional<Counter> getCounterById(long id) {
        return Optional.ofNullable(counterRepository.findOne(id));
    }

    @Override
    public Optional<Counter> getCounterByRoom(String name) {
        return counterRepository.findOneByRoom(name);
    }

    @Override
    public Collection<Counter> getAllCounters() {
        return counterRepository.findAll(new Sort("id"));
    }

    @Override
    public Counter create(Counter counter) {
        return counterRepository.save(counter);
    }

    @Override
    public void delete(Long id){
        counterRepository.delete(id);
    }
}
