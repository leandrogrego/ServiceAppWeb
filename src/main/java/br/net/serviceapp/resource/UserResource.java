package br.net.serviceapp.resource;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.net.serviceapp.model.Address;
import br.net.serviceapp.model.Location;
import br.net.serviceapp.model.Servico;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.ServicoService;
import br.net.serviceapp.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {

	@Autowired
	private UserService userService;
	@Autowired
	private ServicoService servicoService;

	public User add(User user) {
		if(user != null ) {
			userService.save(user);
			return user;
		}
		return null;
	}
	
    @GetMapping("/u")
    public ResponseEntity<User> user(
    		@AuthenticationPrincipal OAuth2User principal
    		) {
    	User user = userService.socialLogin(principal);
    	return ResponseEntity.ok(user);
    }
    
	@GetMapping("/us")
	public ResponseEntity<List<User>> userPublic(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double distancia
	) {
		User user = userService.socialLogin(principal);
		List<User> users = userService.filterDistance(userService.findAllPublic(), latitude, longitude, distancia);		
		if(user != null && users != null){
			users.remove(user);
			return ResponseEntity.ok(users);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/u/{id}")
	public ResponseEntity<User> getUser(
			@PathVariable("id") Long id
			) {
		User user = userService.findOne(id);
		if(user != null){
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/u/{id}/s")
	public ResponseEntity<List<Servico>> getUserServicos(
			@PathVariable("id") Long id
			) {
		List<Servico> servicos = userService.findOne(id).getServicos();
		if(servicos != null){
			return ResponseEntity.ok(servicos);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/u/s")
	public ResponseEntity<List<List<Servico>>> getUserServicosAndNotAddeds(
			@AuthenticationPrincipal OAuth2User principal
			) {
		List<Servico> servicos = userService.socialLogin(principal).getServicos();
		if(servicos != null){
			List<Servico> notAdded = servicoService.findAll();
			for(int i=0; i<servicos.size(); i++){
				for(int j=0; j<notAdded.size(); j++){
					if(servicos.get(i).getId() == notAdded.get(j).getId()) {
						notAdded.remove(servicos.get(i));
					}
				}
			}
			List<List<Servico>> list = new ArrayList<List<Servico>>();
			list.add(servicos);
			list.add(notAdded);
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/u/{id}/s")
	public ResponseEntity<List<Servico>> setUserServicos(
			@PathVariable("id") Long id,
			@RequestHeader("token") String token,
			@RequestBody List<Servico> servicos
			) {
		token = token.substring(1, token.length()-1);
		User user = userService.findOne(id);
		if(
			user != null &&
			user.getToken().equals(token) &&
			servicos != null
		){
			user.setServicos(servicos);
			userService.save(user);
			return ResponseEntity.ok(servicos);
		}
		return ResponseEntity.notFound().build();
	}
	

	@PutMapping("/u/s/{servicoId}")
	public ResponseEntity<List<List<Servico>>> addUserServico(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("servicoId") Long servicoId
			) {
		User user = userService.socialLogin(principal);
		Servico servico = servicoService.findOne(servicoId);
		if(
			user != null &&
			servico != null
		){
			boolean exists = false;
			for(int i=0; i<user.getServicos().size(); i++){
				if(user.getServicos().get(i).getId() == servico.getId()) {
					exists = true;
				}
			};
			if(!exists) user.getServicos().add(servico);
			userService.save(user);
			List<Servico> notAdded = servicoService.findAll();
			for(int i=0; i<user.getServicos().size(); i++){
				for(int j=0; j<notAdded.size(); j++){
					if(user.getServicos().get(i).getId() == notAdded.get(j).getId()) {
						notAdded.remove(user.getServicos().get(i));
					}
				}
			}
			List<List<Servico>> list = new ArrayList<List<Servico>>();
			list.add(user.getServicos());
			list.add(notAdded);
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/u/l")
	public ResponseEntity<Location> location(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestBody Location location
			){
		User user = userService.socialLogin(principal);
		user.setLocation(location);
		userService.save(user);
		return ResponseEntity.ok(location);
	}
	
	@DeleteMapping("/u/s/{servicoId}")
	public ResponseEntity<List<List<Servico>>> delUserServico(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("servicoId") Long servicoId
			) {
		User user = userService.socialLogin(principal);
		Servico servico = servicoService.findOne(servicoId);
		if(
			user != null &&
			servico != null
		){
			boolean exists = false;
			for(int i=0; i<user.getServicos().size(); i++){
				if(user.getServicos().get(i).getId() == servico.getId()) {
					exists = true;
				}
			};
			if(exists) user.getServicos().remove(servico);
			userService.save(user);
			List<Servico> notAdded = servicoService.findAll();
			for(int i=0; i<user.getServicos().size(); i++){
				for(int j=0; j<notAdded.size(); j++){
					if(user.getServicos().get(i).getId() == notAdded.get(j).getId()) {
						notAdded.remove(user.getServicos().get(i));
					}
				}
			}
			List<List<Servico>> list = new ArrayList<List<Servico>>();
			list.add(user.getServicos());
			list.add(notAdded);
			return ResponseEntity.ok(list);
		}
		return ResponseEntity.notFound().build();
	}
	
	//LISTA DE CONTATOS
	@GetMapping("/u/c")
	public ResponseEntity<List<User>> getUserContacts(
			@AuthenticationPrincipal OAuth2User principal
			) {
		User user = userService.socialLogin(principal);
		if( user != null){
			return ResponseEntity.ok(userService.findContacts(user));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/u/{id}/a")
	public ResponseEntity<Address> getUserAddress(
			@PathVariable("id") Long id
			) {
		Address address = userService.findOne(id).getAddress();
		if(address != null){
			return ResponseEntity.ok(address);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/u/{id}/a")
	public ResponseEntity<Address> setUserAddress(
			@RequestHeader("token") String token,
			@RequestBody Address address
			) {
		User user = userService.findByToken(token);
		user.setAddress(address);
		if(user.getAddress() != null){
			userService.save(user);
			return ResponseEntity.ok(address);
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/u/{id}/p")
	public ResponseEntity<String> getUserPhoto(
			@PathVariable("id") Long id
			) {
		String photo = userService.findOne(id).getPhoto();
		if(photo != null){
			return ResponseEntity.ok(photo);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/u/{id}/p")
	public ResponseEntity<String> setUserPhoto(
			@RequestHeader("token") String token,
			@RequestBody String photo
			) {
		User user = userService.findByToken(token);
		user.setPhoto(photo);
		if(user.getPhoto() != null){
			userService.save(user);
			return ResponseEntity.ok(photo);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/u/{id}/p")
	public ResponseEntity<String> clenarUserPhoto(
			@RequestHeader("token") String token
			) {
		User user = userService.findByToken(token);
		user.setPhoto(null);
		userService.save(user);
		return ResponseEntity.ok("");
	}
	
	@PostMapping("/u")
	public ResponseEntity<User> user(
			@RequestBody User user
			){
		if(userService.findByNumber(user.getNumber())!=null) user.setNumber(null);; 
		if(userService.findByEmail(user.getEmail())!=null) user.setEmail(null);; 		
		if(user.getNumber() != null && user.getEmail() != null) {
			user.resetToken();
			user = add(user);
		}
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/u")
	public ResponseEntity<User> change(
			@RequestBody User body,
			@RequestHeader("token") String token
			) {
		User user = userService.findByToken(token);
		if(user != null &&
				body.getNumber() != null &&
				body.getEmail() != null &&
				body.getName() != null 
				) {
			user.setName(body.getName());
			user.setNumber(body.getNumber());
			user.setEmail(body.getEmail());
			userService.save(user);
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.status(403).build();
	}
	
	@PatchMapping("/u")
	public ResponseEntity<Token> token(
			@RequestParam(value="number", required=true) String number,
			@RequestParam(value="code", required=true) int code,
			@RequestParam(value="newToken", required=false) final  boolean newToken
			) {
		if(number.substring(0,1).equals(" ")) {
			number = "+"+ number.substring(1);
		}
		User user = userService.findByNumber(number);
		if(user !=  null) {
			if(code == 0) {
				User u = userService.findByPerfil(0).get(0);
				//ShotService.save(new Shot(u, user.getNumber(), "ServiceApp validation code: "+user.newCode()));
				return	ResponseEntity.ok(new Token(""));
			}
			if (user.checkCode(code)) {
				if(user.getToken() == "" || newToken == true) {
					user.resetToken();
					userService.save(user);
				}
				return	ResponseEntity.ok(new Token(user.getToken()));
			} else {
				return ResponseEntity.status(403).build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/u/push/{sid}")
	public ResponseEntity<String> sid(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("sid") String sid
			){
		User user = userService.socialLogin(principal);
		user.setPushId(sid);
		userService.save(user);
		return	ResponseEntity.ok(user.getPushId());
	}
}

class Token {
	public String token;	
	public Token(String token) {
		this.token = token;
	}
}