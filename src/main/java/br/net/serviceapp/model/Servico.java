package br.net.serviceapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Servico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column
	private String name;
	
	@Column
	private String descricao;
	
	@Column
	private String foto;
	
	@JsonIgnore
	@ManyToMany(mappedBy="servicos")
	private List<User> users = new ArrayList<>();

	@Column
	private Date created = new Date();
	
	@Column
	private boolean status = false;
		
	public Servico(){}
	
	public Servico(String name, String descricao, String foto) {
		this.name = name;
		this.descricao = descricao;
	}
	
	public Long getId() {
		return this.id;
	};

	public String getName() {
		return this.name;
	};
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescricao() {
		return this.descricao;
	};
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getFoto() {
		return this.foto;
	};
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public List<User> getUsers() {
		return this.users;
	};
	
	public Date getCreated() {
		return this.created;
	};
	
	public boolean  getStatus() {
		return this.status;
	}

	public void Activate() {
		this.status = true;
	};
	
	public void Deactivate() {
		this.status = false;
	}

	

}
