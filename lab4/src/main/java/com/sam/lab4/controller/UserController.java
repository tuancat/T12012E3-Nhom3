package com.sam.lab4.controller;

import com.sam.lab4.model.User;
import com.sam.lab4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("userList", userService.userList());
        return "/user/user-list";
    }

    @GetMapping("/add-user")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "/user/add-user";
    }

    @PostMapping("/save-user")
    public String saveAddUser(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap modelMap){
        userService.addUser(user);
        return "redirect:/user/user-list";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "/user/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "/user/logoutSuccess";
    }

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        String message = "Hi " + getPrincipal() //
                + "<br> You do not have permission to access this page!";
        model.addAttribute("message", message);
        return "/user/accessDenied";
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }

        return userName;
    }
}
