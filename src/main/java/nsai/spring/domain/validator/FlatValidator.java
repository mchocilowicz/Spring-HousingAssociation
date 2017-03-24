package nsai.spring.domain.validator;

import nsai.spring.domain.Building;
import nsai.spring.domain.Flat;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.service.building.BuildingService;
import nsai.spring.service.flat.FlatService;
import nsai.spring.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Set;

@Component
public class FlatValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatValidator.class);
    private final FlatService flatService;

    @Autowired
    public FlatValidator(FlatService flatService) {
        this.flatService = flatService;
    }

    @Autowired
    public BuildingService buildingService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Flat.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        Flat form = (Flat) target;
        validateNumber(errors, form);
    }



    private void validateNumber(Errors errors, Flat form) {
        Building b = buildingService.getBuildingById(form.getBuilding().getId()).get();
        List<Flat> f = b.getFltas();
        boolean is = false;
        for(Flat c : f){
            if(c.getNumber() == form.getNumber()){
                is = true;
            }
        }
        if(is){
            errors.reject("flat.exists", "Flat with this number already exists");
        }

    }
}
