package br.net.serviceapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Message  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@ManyToOne
	private User from;
	
	@ManyToOne
	private User to;
	
	@Column
	private String text;
	
	@Column
	private Date created = new Date();
	
	@Column
	private Date delivery = null;
	
	@Column
	private Date ready = null;
	
	
public Message(){}
	
	public Message(User from, User to, String text) {
		this.from = from;
		this.to = to;
		this.text = text;
	}
	
	public Long getId() {
		return id;
	}
	
	public User getFrom() {
		return from;
	}
	
	public User getTo() {
		return to;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public String getText() {
		return text;
	}
	
	public void setDelivery() {
		this.delivery = new Date();
	}
	
	public Date getDelivery() {
		return delivery;
	}
	
	public void setRead() {
		this.ready = new Date();
	}
	
	public Date getRead() {
		return ready;
	}
}
