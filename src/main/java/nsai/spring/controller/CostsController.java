package nsai.spring.controller;

import nsai.spring.domain.*;
import nsai.spring.service.costs.CostsService;
import nsai.spring.service.payment.PaymentService;
import nsai.spring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin on 15.06.2016.
 */

@Controller
public class CostsController {

    @Autowired
    private CostsService costsService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/costs/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage(ModelAndView model, @RequestParam(value = "id",required = false)Long id) {

        model.addObject("cost", new Costs());
        model.addObject("is",false);

        if(id != null){
            Costs b = costsService.getCostsById(id).get();
            if(b != null){
                model.addObject("cost",b);
                model.addObject("is",true);
            }
        }




        model.setViewName("cost_create");
        return model;
    }


    @RequestMapping(value = "/costs/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@ModelAttribute("cost") @Valid Costs cost, BindingResult result) {

        if(result.getErrorCount() == 0){

                SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
                cost.setPublished(new Date());
                cost.setAvilable(sdf.format(new Date()));
                costsService.create(cost);

                return "redirect:/admin";

        }
        return "redirect:/costs/create";
    }

    @RequestMapping(value = "/costs/delete/{id}",method = RequestMethod.GET)
    public String deleteCosts(@PathVariable Long id){
        costsService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/costs/view")
    public ModelAndView showCurrentCosts(ModelAndView model){
        model.setViewName("cost_view");

        Costs cost = costsService.getCurrentCost();
        model.addObject("cost",cost);

        return model;
    }

    @RequestMapping(value = "/costs/view/{id}")
    public ModelAndView showFlat(ModelAndView model, @PathVariable Long id){
        Costs costs = costsService.getCostsById(id).get();

        model.addObject("cost",costs);
        model.setViewName("cost_view");
        return model;
    }

    @RequestMapping(value = "/payment/view/{id}")
    public ModelAndView showPayment(ModelAndView model, @PathVariable Long id){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName()).get();
        Payment payment = paymentService.getPaymentById(id).get();

        Costs cost = payment.getCosts();

        model.setViewName("payment_view");

        model.addObject("user",user);
        model.addObject("flat",user.getFlat());
        model.addObject("payment",payment);
        model.addObject("cost",cost);

        return model;
    }


}
