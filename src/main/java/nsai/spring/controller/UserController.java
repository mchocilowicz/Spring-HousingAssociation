package nsai.spring.controller;

import nsai.spring.domain.Flat;
import nsai.spring.domain.Payment;
import nsai.spring.domain.User;
import nsai.spring.domain.UserCreateForm;
import nsai.spring.domain.validator.UserCreateFormValidator;
import nsai.spring.mail.SendMail;
import nsai.spring.service.costs.CostsService;
import nsai.spring.service.flat.FlatService;
import nsai.spring.service.payment.PaymentService;
import nsai.spring.service.user.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    private FlatService flatService;

    @Autowired
    private SendMail mail;


    @Autowired
    public UserController(UserService userService, UserCreateFormValidator userCreateFormValidator) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }


    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);
        return new ModelAndView("user", "user", userService.getUserById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User=%s not found", id))));
    }


    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage(ModelAndView model) {

        LOGGER.debug("Getting user create form");

        model.addObject("flats",flatService.getAllFlats());

        model.addObject("form",new UserCreateForm());

        model.setViewName("user_create");
        return model;


    }





    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm( @ModelAttribute("form") @Valid UserCreateForm form, BindingResult bindingResult) throws MessagingException {
        LOGGER.debug("Processing user create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {


            return "user_create";
        }
        try {
            form.getFlat().setUsed(true);
            userService.create(form);

        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exist", "Email already exists");
            return "user_create";
        }




        mail.send(form.getEmail(),"Witamy w spółdzielni","Dzień dobry, właśnie utowrzono konto na naszej spółdzielni. \n W celu zalogowania przypominamy potrzebne dane: \n Login: "+form.getEmail()+"\n Hasło: "+form.getPassword());
        return "redirect:/admin";
    }





    @RequestMapping(value = "/user/delete/{id}",method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id){

        Flat flat = flatService.getFlatById(userService.getUserById(id).get().getFlat().getId()).get();
        flat.setUsed(false);
        flat.setUser(null);
        flatService.create(flat);
        try {
            userService.delete(id);
            return "redirect:/admin";

        }catch (EmptyResultDataAccessException e){
            return "redirect:/admin";
        }
    }


}
