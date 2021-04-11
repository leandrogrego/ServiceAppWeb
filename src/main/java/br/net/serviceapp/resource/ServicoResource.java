package br.net.serviceapp.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.net.serviceapp.model.Servico;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.ServicoService;
import br.net.serviceapp.service.UserService;

@RestController
@RequestMapping("/api")
public class ServicoResource {

	@Autowired
	private ServicoService servicoService;
 
	@Autowired
	private UserService userService;

	@GetMapping("/s")
	public ResponseEntity<List<Servico>> listAll(
			@AuthenticationPrincipal OAuth2User principal
			) {
		User user = userService.socialLogin(principal);
		List<Servico> servicos = servicoService.findAll();
		if(user != null && servicos != null) {
			return ResponseEntity.ok(servicos);
		}
		return ResponseEntity.ok(new ArrayList<>());
	}
	
	@GetMapping("/s/{id}")
	public ResponseEntity<Servico> getServico(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("id") Long id
			) {
		User user = userService.socialLogin(principal);
		Servico servico = servicoService.findOne(id);
		if(user != null && servico != null) {
			return ResponseEntity.ok(servico);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/s/{id}/p")
	public ResponseEntity<List<User>> getPrestadores(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("id") Long id,
			@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double distancia
			) {
		User user = userService.socialLogin(principal);
		Servico servico = servicoService.findOne(id);
		if(user != null && servico != null) {
			List<User> users = userService.filterDistance(servico.getUsers(), latitude, longitude, distancia);
			users.remove(user);
			return ResponseEntity.ok(users);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/s")
	public ResponseEntity<Servico> servico(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestBody Servico body
			){
		User user = userService.socialLogin(principal);
		if(user !=null) {
			Servico servico = new Servico(body.getName(), body.getDescricao());
			return ResponseEntity.ok(servicoService.save(servico));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/s")
	public ResponseEntity<Servico> update(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestBody Servico body
			){
		User user = userService.socialLogin(principal);
		Servico servico = servicoService.findOne(body.getId());
		if(user != null && servico != null) {
			servico.setName(body.getName());
			servico.setDescricao(body.getDescricao());
			return ResponseEntity.ok(servicoService.save(servico));
		}
		return ResponseEntity.badRequest().build();
	}
}