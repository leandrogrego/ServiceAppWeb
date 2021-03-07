package br.net.serviceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.serviceapp.job.Notifiication;
import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.User;
import br.net.serviceapp.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository repository;
	
	public Message save(Message message) {
		if(message.getId() == null) {
			notify(message);
		}
		repository.save(message);
		return message;
	}
	
	public boolean notify(Message message) {
		Notifiication notification = new Notifiication(message);
		if(notification.success()) {
			return true;
		}
		return false; 		
	}
	
	public List<Message> findAll() {
		return repository.findAll();
	}
	
	public Message findOne(Long id) {
		return repository.findById(id).get();
	}
	
	public List<Message> findByUser(User user){
		return repository.findByUser(user.getId(), user.getId());
	};
	
	public List<Message> findByUser(User user, User prest){
		return repository.findByUsers(user.getId(), prest.getId(), user.getId(), prest.getId());
	};

	public List<Message> findByUserFrom(User user){
		return repository.findByUserFrom(user);
	};

	public List<Message> findByUserTo(User user){
		return repository.findByUserTo(user);
	};

	public void delete(Long id) {
        repository.deleteById(id);
    }
	
}