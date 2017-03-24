package nsai.spring.domain.validator;

import nsai.spring.domain.Building;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.service.building.BuildingService;
import nsai.spring.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BuildingValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingValidator.class);
    private final BuildingService buildingService;

    @Autowired
    public BuildingValidator(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Building.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        Building building = (Building) target;
        validateName(errors, building);
    }


    private void validateName(Errors errors, Building building) {
        if (buildingService.getBuildingByName(building.getName()).isPresent()) {
            errors.reject("building.exists", "Building with this name already exists");
        }
    }
}
