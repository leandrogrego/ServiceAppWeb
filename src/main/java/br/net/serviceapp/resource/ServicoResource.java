package br.net.serviceapp.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	public ResponseEntity<Servico> add(Servico servico) {
		if (servico != null) {
			servicoService.save(servico);
			return ResponseEntity.ok(servico);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> listAll() {
		List<Servico> servicos = servicoService.findAll();
		if(servicos != null){
			return ResponseEntity.ok(servicos);
		}
		return ResponseEntity.ok(new ArrayList<>());
	}
	
	@GetMapping("/servico/{id}")
	public ResponseEntity<Servico> getServico(
			@PathVariable("id") Long id
			) {
		Servico servico = servicoService.findOne(id);
		if(servico != null){
			return ResponseEntity.ok(servico);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/servico/{id}/prestadores")
	public ResponseEntity<List<User>> getPrestadores(
			@PathVariable("id") Long id,
			@RequestParam double latitude,
			@RequestParam double longitude,
			@RequestParam double distancia
			) {
		Servico servico = servicoService.findOne(id);
		if(servico != null){
			List<User> users = userService.filterDistance(servico.getUsers(), latitude, longitude, distancia);
			return ResponseEntity.ok(users);
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping(path = "/servico")
	public ResponseEntity<Servico> servico(
			@RequestBody Servico body,
			@RequestHeader("token") String token
			){
		User user = userService.findByToken(token);
		if(user !=null) {
			Servico servico = new Servico(body.getName(), body.getDescricao());
			return add(servico);
		}
		return ResponseEntity.badRequest().build();
	}
}