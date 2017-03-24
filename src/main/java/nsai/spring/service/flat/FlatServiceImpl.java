package nsai.spring.service.flat;

import nsai.spring.domain.Flat;
import nsai.spring.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */

@Service
public class FlatServiceImpl implements FlatService {

    @Autowired
    private FlatRepository flatRepository;



    @Override
    public Optional<Flat> getFlatById(long id) {
        return Optional.ofNullable(flatRepository.findOne(id));
    }

    @Override
    public Optional<Flat> getFlatByNumber(int number) {
        return flatRepository.findOneByNumber(number);
    }

    @Override
    public Collection<Flat> getAllFlats() {
        return flatRepository.findAll(new Sort("number"));
    }

    @Override
    public Flat create(Flat flat) {
      return  flatRepository.save(flat);
    }

    @Override
    public void delete(Long id){
        flatRepository.delete(id);
    }
}
