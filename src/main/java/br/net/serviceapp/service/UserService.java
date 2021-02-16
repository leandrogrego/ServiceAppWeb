package br.net.serviceapp.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.User;
import br.net.serviceapp.repository.MessageRepository;
import br.net.serviceapp.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	@Autowired
	private MessageRepository messageRepository;
	
	public void save(User user) {
		repository.save(user);
	}
	
	public User socialLogin(String socialId, String provider) {
		
    	User user = repository.findBySocialIdAndProvider(socialId, provider);
    	if(user == null) {
    		user = new User();
    		user.setSocialId(socialId);
    		user.setProvider(provider);
    	}
    	return user;
	}
	
	public User socialLogin(OAuth2User principal) {
		String provider = "facebook";
		String nameLabel = "name";
		String emailLabel = "email";
		String idLabel = "id";
		String avatar_urlLabel = "email";
		String tokenLabel = "name";
		
		if(principal.getAttribute("node_id") != null) {
    		provider="github";
    		nameLabel = "login";
    		emailLabel = "email";
    		idLabel = "id";
    		avatar_urlLabel = "avatar_url";
    		tokenLabel = "node_id";
    	}
    	
    	if(principal.getAttribute("at_hash") != null) {
    		provider = "google";
    		nameLabel = "name";
    		emailLabel = "email";
    		idLabel = "sub";
    		avatar_urlLabel = "picture";
    		tokenLabel = "at_hash";
    	}
    	
    	String socialId = principal.getAttribute(idLabel).toString();
    	User user = repository.findBySocialIdAndProvider(socialId, provider);
    	if(user == null) {
    		user = new User();
    		user.setSocialId(socialId);
    		user.setProvider(provider);
    	}
    	user.setName(principal.getAttribute(nameLabel));
    	user.setEmail(principal.getAttribute(emailLabel));
    	user.setAvatar_url(principal.getAttribute(avatar_urlLabel));
    	user.setToken(principal.getAttribute(tokenLabel));
    	save(user);
    	return repository.findBySocialIdAndProvider(socialId, provider);
	}
	
	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findOne(Long id) {
		return repository.getOne(id);
	}
	
	public void delete(Long id) {
        repository.deleteById(id);
    }
	
	public User findByEmail(String email) {	
		return repository.findByEmailIgnoreCaseContaining(email);
	}

	public User findByToken(String token) {
		return repository.findByToken(token);
	}
	
	public User findByNumber(String number) {	
		return repository.findByNumber(number);
	}

	public User findByLogin(Long id, String password) {
		return repository.findByLogin(id, password);
	}

	public List<User> findByPerfil(int i) {
		return repository.findByPerfil(i);
	}

	public List<User> findAllPublic() {
		return repository.findAllPublic();
	}
	
	public List<User> findContacts(User user) {
		List<User> contacts = repository.findContacts(user.getId(), user.getId());
		contacts.forEach(contact -> {
			List<Message> messages = messageRepository.findNotRead(user.getId(), contact.getId(), user.getId(), contact.getId());
			messages.forEach( m -> {
				if( m.getTo().getId() == user.getId() && m.getRead() == null ) {
					contact.setNotRead(contact.getNotRead()+1);
				}
			});
		});
		return contacts;
	}
	
	public List<User> filterDistance(List<User> usersIn, double latitude, double longitude, double distance){
		List<User> usersOut = new ArrayList<>();
		double latimax = latitude + (distance / 110.574657);
		double latimin = latitude - (distance / 110.574657);
		double longmax = longitude + (distance / 111.319892);
		double longmin = longitude - (distance / 111.319892);
		usersIn.forEach( user -> {
			
			if(
				user.getLocation() == null || (
					user.getLocation().getLatitude() <= latimax &&
					user.getLocation().getLatitude() >= latimin &&
					user.getLocation().getLongitude() <= longmax &&
					user.getLocation().getLongitude() >= longmin
				)
			) {
				usersOut.add(user);
			}
		});
		return usersOut;
	}
}