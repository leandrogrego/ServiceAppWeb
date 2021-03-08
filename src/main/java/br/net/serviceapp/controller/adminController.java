package br.net.serviceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.net.serviceapp.model.User;
import br.net.serviceapp.service.UserService;

@Controller
public class adminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	public String admin(Model model, @AuthenticationPrincipal OAuth2User principal) {
		if(principal!=null){
	    	User user = userService.socialLogin(principal);
	    	if(user.getPerfil() == 7) {
	    		model.addAttribute(user);
				return "admin";
	    	}
		}
		return "index";
	}
}
