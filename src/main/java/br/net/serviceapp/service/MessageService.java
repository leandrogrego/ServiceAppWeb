package br.net.serviceapp.service;

import java.util.Date;
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
	
	//SALVA MENSAGEM
	public Message save(Message message) {
		if(message.getId() == null) {
			notify(message);
		}
		repository.save(message);
		return message;
	}
	
	//ENVIA NOTIFICACAO DE NOVA MENSAGEM
	public boolean notify(Message message) {
		Notifiication notification = new Notifiication(message);
		if(notification.success()) {
			return true;
		}
		return false; 		
	}
	
	//TODAS AS MENSAGENS
	public List<Message> findAll() {
		return repository.findAll();
	}
	
	public Message findOne(Long id) {
		return repository.findById(id).get();
	}
	
	//MESAGEM DE E PARA USUARIO ESPECIFICO
	public List<Message> findByUser(User user){
		return repository.findByUser(user.getId(), user.getId());
	};
	
	//MESAGEM DE E PARA USUARIO ESPECIFICO COM ID MAIOR QUE startin 
	public List<Message> findNewByUser(User user, Long startin){
		return repository.findNewByUser(user.getId(), user.getId(), startin);
	};
	
	//MESAGENS DE UM USUARIO PARA OUTRO 
	public List<Message> findByUser(User user, User prest){
		return repository.findByUsers(user.getId(), prest.getId(), user.getId(), prest.getId());
	};
	
	//MESAGENS DE UM USUARIO PARA OUTRO COM ID MAIOR QUE startin
	public List<Message> findNewByUsers(User user, User prest, Long startin){
		return repository.findNewByUsers(user.getId(), prest.getId(), user.getId(), prest.getId(), startin);
	};
	
	//MENSAGENS ENVIARDO PELO USUARIO
	public List<Message> findByUserFrom(User user){
		return repository.findByUserFrom(user);
	};

	//MENSAGENS DESTINADA AO UAUARIO
	public List<Message> findByUserTo(User user){
		return repository.findByUserTo(user);
	};

	//REMOVER MENSAGEM
	public void delete(Long id) {
        repository.deleteById(id);
    }
	
	//MENSAGENS POR INTERVALO DE DATAS
	public List<Message> findByDateInterval(Date startDate, Date endDate) {
		System.out.println(startDate+" - "+endDate);
		return repository.findByDateInterval(startDate, endDate);
	}
	
}