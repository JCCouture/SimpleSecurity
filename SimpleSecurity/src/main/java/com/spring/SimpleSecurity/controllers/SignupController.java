package com.spring.SimpleSecurity.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.SimpleSecurity.dao.Account;
import com.spring.SimpleSecurity.repository.AccountRepository;
import com.spring.SimpleSecurity.service.UserService;
import com.spring.SimpleSecurity.support.message.*;
import com.spring.SimpleSecurity.viewModel.SignupViewModel;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupViewModel());
        return SIGNUP_VIEW_NAME;
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupViewModel signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepository.save(signupForm.createAccount());
		userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
