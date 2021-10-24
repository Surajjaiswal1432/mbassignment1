package com.mbassignment.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mbassignment.constant.ConstantsURL;
import com.mbassignment.model.Manager;
import com.mbassignment.service.ManagerService;

@Controller
public class ManagerController {

	private static Logger log = Logger.getLogger(Manager.class.getName());

	@Autowired
	private ManagerService service;

	@GetMapping("/signin")
	public String signIn() {

		return ConstantsURL.login;

	}

	@GetMapping("/signup")
	public String managerSignUpPage(Manager managr, Model model) {

		return ConstantsURL.managerSignup;

	}

	@PostMapping("/storeInformation")
	public String storeRecored(@Valid @ModelAttribute("manager") Manager manager, BindingResult result,
			RedirectAttributes redirectAttributes, Model model) {
		manager.setRole("ROLE_MANAGER");
		service.checkAlredyEmailExits(service, manager.getEmail(), model);

		if (result.hasFieldErrors()) {
			model.addAttribute("manager", manager);
			return ConstantsURL.managerSignup;

		}
		service.registerInformation(manager, redirectAttributes, model);
		redirectAttributes.addFlashAttribute("message", "Data Suceessfully Submited");
		return ConstantsURL.redirectSignup;

	}

}
