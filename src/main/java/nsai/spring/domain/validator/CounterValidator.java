package nsai.spring.domain.validator;

import nsai.spring.domain.Counter;
import nsai.spring.domain.Flat;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.service.counter.CounterService;
import nsai.spring.service.flat.FlatService;
import nsai.spring.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CounterValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterValidator.class);

    @Autowired
    private FlatService flatService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Counter.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        Counter form = (Counter) target;
        validateCounter(errors, form);
        validateNumber(errors,form);
    }

    private void validateNumber(Errors errors, Counter form) {
        if (form.counterNumber()){
            errors.reject("counter.many","You can not add more counter to this room");
        }
    }

    private void validateCounter(Errors errors, Counter form) {
        Flat f = flatService.getFlatById(form.getId()).get();
        if (f.getCounterList().contains(form)) {
            errors.reject("counter.exists", "This counter alredy exists in this Flat");
        }
    }

}
