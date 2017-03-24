package nsai.spring.service.payment;


import nsai.spring.domain.Payment;
import nsai.spring.domain.User;
import nsai.spring.domain.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

public interface PaymentService {

    Optional<Payment> getPaymentById(long id);

    Collection<Payment> getAllPayments();

    Collection<Payment> getAllbyUserId(Long id);

    Payment create(Payment payment);

    void delete(Long id);

    void update(Payment payment);

}
