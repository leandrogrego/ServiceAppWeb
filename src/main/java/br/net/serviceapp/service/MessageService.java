package br.net.serviceapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.User;
import br.net.serviceapp.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository repository;
	
	public Message save(Message message) {
		return repository.save(message);
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