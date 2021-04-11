package br.net.serviceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.net.serviceapp.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
	
	@Query(value="SELECT * FROM `servico` WHERE `name` = ? LIMIT 1", nativeQuery=true)
	public Servico findByName(String name);
	
	@Override
	@Query(value="SELECT * FROM `servico` ORDER BY `name`", nativeQuery=true)
	public List<Servico> findAll();

	@Query(value="SELECT * FROM `servico` WHERE `id` = ? LIMIT 1", nativeQuery=true)
	public Servico findOne(Long id);
	
}