package nsai.spring.controller;


import nsai.spring.domain.Counter;
import nsai.spring.domain.Flat;
import nsai.spring.domain.validator.FlatValidator;
import nsai.spring.repository.BuildingRepository;
import nsai.spring.repository.FlatRepository;
import nsai.spring.service.flat.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Marcin on 17.05.2016.
 */
@Controller
public class FlatController {

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private FlatService flatService;
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private FlatValidator flatValidator;

    @InitBinder("flat")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(flatValidator);
    }



    @RequestMapping(value = "/flat/create", method = RequestMethod.GET)
    public ModelAndView getFlatCreatePage(ModelAndView model, @RequestParam(value = "id",required = false)Long id) {

        model.addObject("flat", new Flat());
        model.addObject("is",false);

        if(id != null){
            Flat flat = flatRepository.findOne(id);
            if(flat != null){
                model.addObject("flat",flat);
                model.addObject("is",true);
            }
        }


        model.addObject("buildings",buildingRepository.findAll());

        model.setViewName("flat_create");
        return model;
    }


    @RequestMapping(value = "/flat/create", method = RequestMethod.POST)
    public String handleFlatCreateForm(@ModelAttribute("flat") @Valid Flat flat ,BindingResult result) {
        if(result.getErrorCount() != 0){
            if(flat.getId() != 0){
                flatService.create(flat);
                return "redirect:/admin";
            }
            return "flat_create";
        }
        flatService.create(flat);
        return "redirect:/admin";

    }

    @RequestMapping(value = "/flat/delete/{id}",method = RequestMethod.GET)
    public String deleteFlat(@PathVariable Long id){
        flatService.delete(id);
        return "redirect:/";
    }

    @RequestMapping(value = "/flat/view/{id}")
    public ModelAndView showFlat(ModelAndView model, @PathVariable Long id){
        Flat flat = flatRepository.findOne(id);
        List<Counter> counter = flat.getCounterList();

        model.setViewName("flat_view");
        model.addObject("flat",flat);
        model.addObject("counter",counter);

        return model;
    }
}
