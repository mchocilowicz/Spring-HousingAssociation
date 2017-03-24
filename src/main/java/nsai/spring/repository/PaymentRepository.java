package nsai.spring.repository;

import nsai.spring.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Marcin on 19.06.2016.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Collection<Payment> findAllByUserId(Long id);
}
