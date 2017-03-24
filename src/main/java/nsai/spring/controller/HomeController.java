package nsai.spring.controller;

import nsai.spring.domain.Costs;
import nsai.spring.domain.Payment;
import nsai.spring.domain.User;
import nsai.spring.mail.SendMail;
import nsai.spring.service.costs.CostsService;
import nsai.spring.service.payment.PaymentService;
import nsai.spring.service.user.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CostsService costsService;

    @Autowired
    private PaymentService paymentService;



    @Autowired
    private SendMail mail;

    @RequestMapping("/")
    public ModelAndView getHomePage(ModelAndView model,@RequestParam Optional<String> error) {
        LOGGER.debug("Getting home page");
        Calendar cal = Calendar.getInstance();





        if(cal.get(Calendar.DAY_OF_MONTH) >= 20 && cal.get(Calendar.DAY_OF_MONTH) <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) ){
           refresh();
        }


        model.setViewName("home");
        return model;
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }



    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    public String reset(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("email");
        User u = userService.getUserByEmail(email).get();


        String password = RandomStringUtils.randomAlphabetic(10);


        System.out.println(password);
        u.setPasswordHash(new BCryptPasswordEncoder().encode(password));

        System.out.println("Nowe haslo : "+ password);

        userService.update(u);
        System.out.println("Zmienono haslo");
        mail.send(u.getEmail(),"zmiana hasla","Nowe haslo to:"+"haslo");

        return "redirect:/";
    }


    public void refresh(){
        System.out.println("Dzialam");

        Collection<User> users = userService.getAllUsers();
        Calendar calendar = Calendar.getInstance();

        Costs cost = costsService.getCurrentCost();


            for(User u : users){

                Set<Payment> payments = u.getPayments();

                if(payments.size() == 0){

                    generatePayment(u,cost,u.getFlat().isGarage());
                }
                else if(payments.size() % 6 == 0){

                    Payment pay = new Payment();
                    double c = 0;
                    c = c + (cost.getCentralHeating() * u.getFlat().getArea());
                    c = c + (cost.getHotWater() * u.getFlat().getCounterValue("Goraca Woda"));
                    c = c + (cost.getRepairFund() * u.getFlat().getArea());
                    c = c + (cost.getOperation() * u.getFlat().getArea());
                    c = c + (cost.getExtraSpace() * u.getFlat().getExtraSpace());
                    c = c + (cost.getCounter() * u.getFlat().getCountersLeng());
                    c = c + (cost.getUrbanWaste() * u.getInhabitants());
                    c = c + (cost.getColdWater() * u.getFlat().getCounterValue("Zimna Woda"));
                    c = c + (cost.getFee() * u.getFlat().getArea());
                    c = c + (cost.getReadingCounter() * u.getFlat().getCountersLeng());
                    c = c + (cost.getElectricity() * u.getFlat().getCounterValue("Prad"));
                    c = c + (cost.getGas() * u.getFlat().getCounterValue("Gaz"));
                    if (u.getFlat().isGarage()) {
                        c = c + (cost.getGarage() * 3.5);
                    }
                    c = c + (cost.getParkingSlot() * 3.5 * u.getAutos());


                    pay.setUser(u);
                    pay.setDay(calendar.get(Calendar.DAY_OF_MONTH));
                    pay.setMonth(calendar.get(Calendar.MONTH));
                    pay.setYear(calendar.get(Calendar.YEAR));
                    pay.setCosts(cost);
                    pay.setPrice(c);
                    paymentService.create(pay);

                }
                else {
                    for (Payment p : payments) {


                        if(p.getDay() >= 25 && p.getDay() <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){

                            if(p.getMonth() != calendar.get(Calendar.MONTH)){
                                generatePayment(u,cost,u.getFlat().isGarage());
                            }


                        }



                    }
                }


            }





    }

    private void generatePayment(User u, Costs cost, boolean garage) {
        Calendar calendar = Calendar.getInstance();

        Payment pay = new Payment();
        double c = 0;
        c = c + (cost.getCentralHeating() * u.getFlat().getArea());

        c = c + (cost.getHotWater() * u.getFlat().getCounterValue("Goraca Woda"));
        c = c + (cost.getRepairFund() * u.getFlat().getArea());
        c = c + (cost.getOperation() * u.getFlat().getArea());
        c = c + (cost.getExtraSpace() * u.getFlat().getExtraSpace());

        c = c + (cost.getCounter() * u.getFlat().getCountersLeng());
        c = c + (cost.getUrbanWaste() * u.getInhabitants());
        c = c + (cost.getColdWater() * u.getFlat().getCounterValue("Zimna Woda"));
        c = c + (cost.getFee() * u.getFlat().getArea());
        c = c + (cost.getReadingCounter() * u.getFlat().getCountersLeng());
        c = c + (cost.getGas() * u.getFlat().getCounterValue("Gaz"));
        if (garage) {
            c = c + (cost.getGarage() * 3.5);
        }
        c = c + (cost.getParkingSlot() * 3.5 * u.getAutos());

        pay.setUser(u);
        pay.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        pay.setMonth(calendar.get(Calendar.MONTH));
        pay.setYear(calendar.get(Calendar.YEAR));
        pay.setCosts(cost);
        pay.setPrice(c);
        paymentService.create(pay);
    }


}
