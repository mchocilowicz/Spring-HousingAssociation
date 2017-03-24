package nsai.spring.service.costs;

import nsai.spring.domain.Building;
import nsai.spring.domain.Costs;
import nsai.spring.repository.BuildingRepository;
import nsai.spring.repository.CostsRepository;
import nsai.spring.service.building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */

@Service
public class CostsServiceImpl implements CostsService {

    @Autowired
    private CostsRepository costsRepository;

    @Override
    public Optional<Costs> getCostsById(long id) {
        return Optional.ofNullable(costsRepository.findOne(id));
    }


    @Override
    public Collection<Costs> getAllCosts() {
        return costsRepository.findAll();
    }

    @Override
    public Costs getCurrentCost() {

        Collection<Costs> c = costsRepository.findAll();
        Costs a = new Costs();
        for(Costs x : c){
            a = x;
        }
        System.out.println(a.getPublished());

        return a;

    }

    @Override
    public Costs create(Costs cost) {
        return costsRepository.save(cost);
    }

    @Override
    public void delete(Long id){
        costsRepository.delete(id);
    }
}
