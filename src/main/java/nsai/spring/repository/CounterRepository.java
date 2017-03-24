package nsai.spring.repository;

import nsai.spring.domain.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Marcin on 18.06.2016.
 */
public interface CounterRepository extends JpaRepository<Counter, Long> {

    Optional<Counter> findOneByRoom(String room);

}