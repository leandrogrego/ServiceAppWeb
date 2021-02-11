package br.net.serviceapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.Servico;
import br.net.serviceapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
		
	@Query(value="SELECT * FROM `user` WHERE `email` = ? LIMIT 1", nativeQuery=true)
	public User findByEmailIgnoreCaseContaining(String email);
	
	@Query(value="SELECT * FROM `user` WHERE `name` = %?% LIMIT 1", nativeQuery=true)
	public List<User> findByNameIgnoreCaseContaining(String name);

	public User findByToken(String token);
	
	@Query(value="SELECT * FROM `user` ORDER BY `name`", nativeQuery=true)
	public List<User> findAllPublic();

	@Query(value="SELECT * FROM `user` WHERE `social_id` = ? AND provider = ? LIMIT 1", nativeQuery=true)
	public User findBySocialIdAndProvider(String socialId, String provider);
	
	@Query(value="SELECT * FROM `user` WHERE `number` = ? LIMIT 1", nativeQuery=true)
	public User findByNumber(String number);

	@Query(value="SELECT * FROM `user` WHERE `id` = ? AND `password` = ? LIMIT 1", nativeQuery=true)
	public User findByLogin(Long id, String password);
	
	public List<User> findByPerfil(int i);
	
	@Query(value="SELECT * FROM `user` WHERE `id` in (SELECT from_id FROM `message` WHERE to_id = ? GROUP by to_id) or `id` in (SELECT to_id FROM `message` WHERE from_id = ? GROUP by from_id)", nativeQuery=true)
	public List<User> findContacts(Long toId, Long fromId2);
	
	@Query(value = "SELECT * FROM user u where u.role = \"ADMIN\";", nativeQuery = true)
	User findByRoleAdmin();

}


