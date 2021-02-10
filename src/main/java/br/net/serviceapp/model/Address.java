package br.net.serviceapp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Address implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String logradouro;
	private String numero;
	private String bairro;
	private String complemento;
	private String cidade;
	private String estado;
	private String cep;
	private String pais;
	
	public Address(){};
	
	public Address(
			String logradouro,
			String numero,
			String bairro,
			String complemento,
			String cidade,
			String estado,
			String cep,
			String pais
			) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.complemento = complemento;
		this.cidade = cidade;
		this.estado = estado;
		this.pais = pais;
		this.cep = cep;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogradouro() {
		return logradouro;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public String getBairro() {
		return bairro;
	}
	
	public String getComplemnto() {
		return complemento;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public String getCep() {
		return cep;
	}
	
	public String getPais() {
		return pais;
	}
	
}