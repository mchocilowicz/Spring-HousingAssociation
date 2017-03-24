package nsai.spring.controller;

import nsai.spring.domain.CurrentUser;
import nsai.spring.domain.User;
import nsai.spring.service.building.BuildingService;
import nsai.spring.service.costs.CostsService;
import nsai.spring.service.counter.CounterService;
import nsai.spring.service.flat.FlatService;
import nsai.spring.service.payment.PaymentService;
import nsai.spring.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;

@Controller
public class UsersController {



    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    @Autowired
    private FlatService flatService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private CostsService costsService;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CounterService counterService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/users")
    public ModelAndView getUsersPage() {
        LOGGER.debug("Getting users page");
        return new ModelAndView("users", "users", userService.getAllUsers());
    }

    @RequestMapping("/admin")
    public ModelAndView getAdminPage(ModelAndView model) {
        LOGGER.debug("Getting users page");

        model.addObject("flats",flatService.getAllFlats());
        model.addObject("buildings",buildingService.getAllBuildings());
        model.addObject("costs",costsService.getAllCosts());
        model.addObject("users",userService.getAllUsers());
        model.addObject("payments",paymentService.getAllPayments());
        model.addObject("counters",counterService.getAllCounters());


        model.setViewName("admin");

        return model;
    }


}
