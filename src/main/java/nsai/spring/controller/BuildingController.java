package nsai.spring.controller;

import nsai.spring.domain.Building;
import nsai.spring.domain.Flat;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.domain.validator.BuildingValidator;
import nsai.spring.domain.validator.UserCreateFormValidator;
import nsai.spring.service.building.BuildingService;
import nsai.spring.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


/**
 * Created by Marcin on 17.05.2016.
 */
@Controller
public class BuildingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildingController.class);

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingValidator buildingValidator;

    @InitBinder("build")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(buildingValidator);
    }


    @RequestMapping(value = "/building/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage(ModelAndView model, @RequestParam(value = "id",required = false)Long id) {

        model.addObject("build", new Building());
        model.addObject("is",false);

        if(id != null){
            Building b = buildingService.getBuildingById(id).get();
            if(b != null){
                model.addObject("build",b);
                model.addObject("is",true);
            }
        }

        model.setViewName("building_create");
        return model;
    }


    @RequestMapping(value = "/building/create", method = RequestMethod.POST)
    public String handleUserCreateForm( @ModelAttribute("build") @Valid Building build, BindingResult result) {
        if(result.getErrorCount()==0){
            buildingService.create(build);
            return "redirect:/admin";
        }
        return "building_create";
    }

    @RequestMapping(value = "/building/delete/{id}",method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id){
        buildingService.delete(id);
        return "redirect:/admin";
    }


}
