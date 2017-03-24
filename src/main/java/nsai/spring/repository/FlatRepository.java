package nsai.spring.repository;

import nsai.spring.domain.Building;
import nsai.spring.domain.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Marcin on 17.05.2016.
 */
public interface FlatRepository extends JpaRepository<Flat, Long> {

    Optional<Flat> findOneByNumber(int number);
}
