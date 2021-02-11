package br.net.serviceapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.net.serviceapp.model.Servico;
import br.net.serviceapp.repository.ServicoRepository;

@Service
public class ServicoService {
	@Autowired
	private ServicoRepository repository;
	
	public void save(Servico servico) {
		repository.save(servico);
	}
	
	public List<Servico> findAll() {
		return repository.findAll();
	}
	
	public Servico findOne(Long id) {
		return repository.getOne(id);
	}
	
	public Servico findByName(String name) {
		return repository.findByName(name);
	}
	
	public void delete(Long id) {
        repository.deleteById(id);
    }
	
}