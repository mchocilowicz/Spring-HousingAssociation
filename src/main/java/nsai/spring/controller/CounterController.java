package nsai.spring.controller;

import nsai.spring.domain.Costs;
import nsai.spring.domain.Counter;
import nsai.spring.domain.Flat;
import nsai.spring.service.costs.CostsService;
import nsai.spring.service.counter.CounterService;
import nsai.spring.service.flat.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Marcin on 18.06.2016.
 */

@Controller
public class CounterController {


    @Autowired
    private CounterService counterService;

    @Autowired
    private FlatService flatService;


    @RequestMapping(value = "/counter/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage(ModelAndView model, @RequestParam(value = "id",required = false)Long id) {

        model.addObject("counter",new Counter());
        model.addObject("is",false);

        if(id != null){
            Counter c = counterService.getCounterById(id).get();
            if(c != null){
                model.addObject("counter",c);
                model.addObject("is",true);
                System.out.println("Jest tutaj ID : "+c.getId());
            }
        }



        model.setViewName("counter_create");

        model.addObject("flats",flatService.getAllFlats());

        return model;
    }


    @RequestMapping(value = "/counter/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@ModelAttribute("counter") @Valid Counter counter, BindingResult result) {

        if(result.getErrorCount() == 0){
            if(counter.getId() != null){
                Counter c = counterService.getCounterById(counter.getId()).get();
                c.updateCounter(counter);
                counterService.create(c);
                return "redirect:/admin";
            }else{
                counterService.create(counter);
                return "redirect:/admin";
            }


        }else{
            return "redirect:/counter/create";
        }


    }





    @RequestMapping(value = "/counter/delete/{id}",method = RequestMethod.GET)
    public String deleteCounter(@PathVariable Long id){
        counterService.delete(id);
        return "redirect:/admin";
    }
}


