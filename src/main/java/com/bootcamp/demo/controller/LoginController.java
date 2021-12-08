package com.bootcamp.demo.controller;

import com.bootcamp.demo.business.AccountService;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Account;
import com.bootcamp.demo.model.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
@CrossOrigin
public class LoginController {

    AccountService service;
    private final ResponseHandler responseHandler;

    @Autowired
    public LoginController(AccountService service, ResponseHandler handler) {
        this.service = service;
        responseHandler = handler;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "register";
    }

    @RequestMapping("/register-error/")
    public String registerError(Model model) {
        model.addAttribute("registerError", true);
        model.addAttribute("error", "error");
        return "register";
    }

    @RequestMapping("/viewAccount")
    public String viewAccount(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        model.addAttribute("email", "");
        model.addAttribute("name", "");
        model.addAttribute("address", "");
        model.addAttribute("phoneNumber", "");
        model.addAttribute("birthdate", "");
        return "viewAccount";
    }

    @RequestMapping("/viewAccount-error/")
    public String viewAccountError(Model model) {
        model.addAttribute("viewError", true);
        model.addAttribute("error", "Invalid email");
        return "viewAccount";
    }
}
