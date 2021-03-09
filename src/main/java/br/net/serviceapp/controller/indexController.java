package br.net.serviceapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.net.serviceapp.model.UserInfo;
import br.net.serviceapp.service.SessionService;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.UserService;

@Controller
public class indexController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService<UserInfo> sessionService;

	@GetMapping("/")
	public String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
		if(principal!=null){
			return home(model, principal);
		}
		return "index";
	}
	
	@GetMapping("/home")
	public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
    	User user = userService.socialLogin(principal);
    	model.addAttribute(user);
		return "home";
	}
    
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
    	String message = (String) request.getSession().getAttribute("error.message");
    	request.getSession().removeAttribute("error.message");
    	return message;
    }
    
	@RequestMapping("/logout")
	public String logout() {
		sessionService.clearSession();
		return "index";
	}
    
}
