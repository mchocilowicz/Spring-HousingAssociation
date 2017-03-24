package nsai.spring.service.payment;


import nsai.spring.domain.Payment;
import nsai.spring.domain.User;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.repository.PaymentRepository;
import nsai.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> getPaymentById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(paymentRepository.findOne(id));
    }


    @Override
    public Collection<Payment> getAllPayments() {
        LOGGER.debug("Getting all users");
        return paymentRepository.findAll();
    }

    @Override
    public Collection<Payment> getAllbyUserId(Long id){
        return paymentRepository.findAllByUserId(id);
    }

    @Override
    public Payment create(Payment payment) {

        return paymentRepository.save(payment);


    }

    public void delete(Long id){
        paymentRepository.delete(id);
    }

    public void update(Payment payment){
        paymentRepository.save(payment);
    }
}
