package nsai.spring.controller;

import nsai.spring.domain.Payment;
import nsai.spring.domain.User;
import nsai.spring.mail.SendMail;
import nsai.spring.service.payment.PaymentService;
import nsai.spring.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin on 07.07.2016.
 */
@Controller
public class ProfileController{

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SendMail mail;



    @RequestMapping(value = "/profile/flat")
    public ModelAndView showFlat(ModelAndView model){

        model.setViewName("flat_view");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByEmail(authentication.getName()).get();


        model.addObject("flat",user.getFlat());
        model.addObject("counter", user.getFlat().getCounterList());



        return model;
    }

    @RequestMapping(value = "/profile/user")
    public ModelAndView showProfile(ModelAndView model){
        model.setViewName("user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName()).get();
        model.addObject("user",user);

        return model;
    }

        @RequestMapping(value = "/profile/updatePassword",method = RequestMethod.POST)
        public String updatePassword(HttpServletRequest request) throws MessagingException {
            String password = request.getParameter("password");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByEmail(authentication.getName()).get();
            user.setPasswordHash(new BCryptPasswordEncoder().encode(password));
            userService.update(user);

            mail.send(user.getEmail(),"Zmiana Hasła","Na Twoim koncie skorzystano z zmiany hasła na : "+password);

            return "redirect:/";
        }


        @RequestMapping(value = "/profile/payment", method = RequestMethod.GET)
        public ModelAndView getUserPayment(ModelAndView model) {
            model.setViewName("user_payments");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            User user = userService.getUserByEmail(authentication.getName()).get();
            Collection<Payment> pay = paymentService.getAllPayments();
            List<Payment> s = new ArrayList<Payment>();
            for(Payment p : pay){
                if(p.getUser().getId() == user.getId()){
                    s.add(p);
                }
            }

            model.addObject("pay",s);



            return model;


        }

        @RequestMapping(value = "/profile/payment/{id}", method = RequestMethod.GET)
        public ModelAndView pay(ModelAndView model,@PathVariable Long id ) {
            model.setViewName("home");

            Payment p = paymentService.getPaymentById(id).get();
            p.setPaid(true);
            p.setPaidDate(new Date());
            paymentService.update(p);
            return model;


        }

}
