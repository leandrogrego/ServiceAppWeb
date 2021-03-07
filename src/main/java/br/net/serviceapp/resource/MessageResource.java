
package br.net.serviceapp.resource;

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

import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.MessageService;
import br.net.serviceapp.service.UserService;

@RestController
@RequestMapping("/api")
public class MessageResource {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	public Message add(Message message) {
		if (message != null) {
			return messageService.save(message);
		}
		return null;
	}

	//LISTA DE MENSAGENS DE UM USURIO
	@GetMapping("/m")
	public ResponseEntity<List<Message>> listAll(
			@AuthenticationPrincipal OAuth2User principal
		){
		User user = userService.socialLogin(principal);
		if(user != null ){
			List<Message> messages = messageService.findByUser(user);
			if(messages != null ){
				return ResponseEntity.ok(messages);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	//MENSAGEM ESPECIFICA
	@GetMapping("/m/{id}")
	public ResponseEntity<Message> getMessage(
			@PathVariable("id") Long id, 
			@RequestHeader("token") String token
			) {
		User user = userService.findByToken(token);
		Message message = messageService.findOne(id);
		if(message != null && (
				message.getFrom().getId() == user.getId() ||
				message.getTo().getId() == user.getId() 
			)){
			return ResponseEntity.ok(message);
		}
		return ResponseEntity.notFound().build();
	}
	
	//LISTA DE MENSAGENS DE UM CONTATO
	@GetMapping("/m/p/{id}")
	public ResponseEntity<List<Message>> getMessagesPrestador(
			@AuthenticationPrincipal OAuth2User principal,
			@PathVariable("id") Long id
			) {
		User user = userService.socialLogin(principal);
		User prest = userService.findOne(id);
		if(user != null && prest != null){
			List<Message> messages = messageService.findByUser(user, prest);
			if(messages != null ){
				return ResponseEntity.ok(messages);
			}
		}
		return ResponseEntity.notFound().build();
	}

	//NOVA MENSAGEM
	@PostMapping("/m")
	public ResponseEntity<Message> newMessage(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestParam Long id,
			@RequestParam String text
			){
		User user = userService.socialLogin(principal);
		User prest = userService.findOne(id);
		if(user !=null && prest !=null) {
			Message mess = add(new Message(user, prest, text));
			return ResponseEntity.ok(mess);
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	@PutMapping("/m")
	public ResponseEntity<List<Message>> setRead(
			@AuthenticationPrincipal OAuth2User principal,
			@RequestBody List<Message> body
			){
		User user = userService.socialLogin(principal);
		//System.out.println(body.toString());
		if(user !=null && body != null) {
			body.forEach( mess -> {
				if( user.getId() == mess.getTo().getId()) {
					Message message = messageService.findOne(mess.getId());
					if(message != null && message.getRead() == null) {
						message.setRead();
						messageService.save(message);
					}
				}
			});
			return ResponseEntity.ok(body);
		};
		return ResponseEntity.badRequest().build();
	};
}

