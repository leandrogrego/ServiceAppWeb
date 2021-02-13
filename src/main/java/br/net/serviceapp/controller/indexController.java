package br.net.serviceapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.net.serviceapp.model.UserInfo;
import br.net.serviceapp.service.SessionService;
import br.net.serviceapp.model.User;
import br.net.serviceapp.repository.MessageRepository;
import br.net.serviceapp.repository.ServicoRepository;
import br.net.serviceapp.repository.UserRepository;
import br.net.serviceapp.resource.UserResource;
import br.net.serviceapp.service.EmailService;
import br.net.serviceapp.service.MessageService;
import br.net.serviceapp.service.ServicoService;
import br.net.serviceapp.service.UserService;

@Controller
public class indexController {
	
	@Autowired 
	private EmailService emailService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserResource userResource;
	@Autowired
	private ServicoService servicoService;
	@Autowired
	private MessageService MessageService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private MessageRepository messageRepository;
	
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
    	model.addAttribute("user", user);
		return "home";
	}
    
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
    	String message = (String) request.getSession().getAttribute("error.message");
    	request.getSession().removeAttribute("error.message");
    	return message;
    }
    
    @GetMapping("/login/oauth2/code/{provider}")
    public User socialLogin(
    		OAuth2User principal
    		) {
    	//return Collections.singletonMap("name", principal.getAttribute("name"));
    	String provider="google";
		String name = "name";
		String email = "email";
		String idLabel = "sub";
		String avatar_url = "picture";
		String token = "at_hash";
		
    	if(principal.getAttribute("node_id") != null) {
    		provider="github";
    		name = "login";
    		email = "email";
    		idLabel = "id";
    		avatar_url = "avatar_url";
    		token = "node_id";
    	}
    		
    	System.out.println(principal.getAttribute(name).toString()+" - ");
    	User user = userService.socialLogin(principal.getAttribute(idLabel), provider);
    	user.setName(principal.getAttribute(name));
    	user.setEmail(principal.getAttribute(email));
    	user.setAvatar_url(principal.getAttribute(avatar_url));
    	user.setToken(principal.getAttribute(token));
    	userService.save(user);
    	return userService.socialLogin(principal.getName(), provider);
    }
    
	@RequestMapping("/logout")
	public String logout(Model model) {
		sessionService.clearSession();
		model.addAttribute("user", null);
		return "index";
	}
    
}
