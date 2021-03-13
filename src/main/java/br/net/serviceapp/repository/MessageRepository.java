package br.net.serviceapp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.net.serviceapp.model.Message;
import br.net.serviceapp.model.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	//MENSAGENS DE OU PARA USUARIO ESPECIFICO
	@Query(value="SELECT * FROM `message` WHERE `from` = ? || `to ` = ?", nativeQuery=true)
	public List<Message> findByUser(User user);
	
	//MENSAGENS DE UM USUARIO ESPECIFICO PARA OUTRO
	@Query(value="SELECT * FROM `message` WHERE `from_id` = ? or `to_id` = ?", nativeQuery=true)
	public List<Message> findByUser(Long userId, Long userId2);
	
	//MENSAGENS DE OU PARA USUARIO ESPECIFICO COM ID MAIOR QUE startin
	@Query(value="SELECT * FROM `message` WHERE (`from_id` = ? or `to_id` = ?  and `id` > ?)", nativeQuery=true)
	public List<Message> findNewByUser(Long userId, Long userId2, Long startin); 
	
	@Query(value="SELECT * FROM `message` WHERE (`from_id` = ? and `to_id` = ?) or (`to_id` = ? and `from_id` = ?)", nativeQuery=true)
	public List<Message> findByUsers(Long userId, Long prestId, Long userId2, Long prestId2);
	
	@Query(value="SELECT * FROM `message` WHERE ((`from_id` = ? and `to_id` = ?) or (`to_id` = ? and `from_id` = ?)) and `id` > ?", nativeQuery=true)
	public List<Message> findNewByUsers(Long userId, Long prestId, Long userId2, Long prestId2, Long startin);
	
	@Query(value="SELECT * FROM `message` WHERE `ready` IS NULL AND((`from_id` = ? and `to_id` = ?) or (`to_id` = ? and `from_id` = ?))", nativeQuery=true)
	public List<Message> findNotRead(Long userId, Long prestId, Long userId2, Long prestId2);
	
	@Query(value="SELECT * FROM `message` WHERE `from` = ?", nativeQuery=true)
	public List<Message> findByUserFrom(User user);

	@Query(value="SELECT * FROM `message` WHERE `to` = ?", nativeQuery=true)
	public List<Message> findByUserTo(User user);

	//MENSAGENS POR INTERVALO DE DATAS
	@Query(value="SELECT * FROM `message` WHERE (`created` between ? and ?) and `delivery` is NULL group by from_id", nativeQuery=true)
	public List<Message> findByDateInterval(Date startDdate, Date endDate);

	@Query(value="SELECT count(id) FROM `message` WHERE `to_id` = ? and `delivery` is NULL", nativeQuery=true)
	public Long countNew(Long id);
	
	@Query(value="SELECT count(id) FROM `message` WHERE `to_id` = ? and `from_id` = ? and `delivery` is NULL", nativeQuery=true)
	public Long countNewByFrom(Long toId, Long fromId);

}
