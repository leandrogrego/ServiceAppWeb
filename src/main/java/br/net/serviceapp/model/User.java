package br.net.serviceapp.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column
	private boolean enabled = true;
	
	@Column
	private String type = "user";

	@Column
	private String socialId;
	
	@Column
	private String provider;
	
	@Column
	private String avatar_url;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private boolean emailValidated;
	
	@Column
	private String number;
	
	@Column
	private boolean numberValidated;
	
	@Lob
	@Column
	private String photo;
	
	@JsonIgnore
	@OneToOne(cascade=CascadeType.ALL)
	private Address address;
	
	@Column
	private Date created = new Date();
	
	@JsonIgnore
	@Column
	private String pushId;

	@JsonIgnore
	@Column
	private int code;
	
	@JsonIgnore
	@Column
	private String token = "";
	
	@JsonIgnore
	@Column
	private int perfil = 1; // 1 to user, 0 to admin
	
	@JsonIgnore
	@OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
	private List<Message> messages;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Location location;
	
	private int notRead;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name="user_servicos", 
    	joinColumns={@JoinColumn(name="user_id")}, 
    	inverseJoinColumns={@JoinColumn(name="servico_id")})
	private List<Servico> servicos = new ArrayList<>();

	private double distancia;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
		
	public int newCode() {
		if(this.number != null) {
			Random rand = new Random(); 
			this.code = rand.nextInt(1000000)+1;
			return this.code;
		}
		return 0;
	}
	
	public boolean checkCode(int code) {
 		if(this.code == code) {
 			return true;
 		} 
		return false;
	}
	
	public void resetToken() {
		String s = email+new Date().toString();
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
	       	this.token = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public String getToken() {
		return token;
	}

	public Long getId() {
		return id;
	}
	
	public String getSocialId() {
		return socialId;
	}
	
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	public String getProvider() {
		return provider;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getAvatar_url() {
		return avatar_url;
	}
	
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}
	
	public void addServico(Servico servico) {
		boolean exists = false;
		for(int i=0; i<servicos.size(); i++) {
			if(servicos.get(i).getId() == servico.getId()) {
				exists = true;
				break;
			}
		};
		if(!exists) {
			servicos.add(servico);
		}
	}
	
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public int getNotRead() {
		return notRead;
	}
	
	public void setNotRead(int i) {
		this.notRead = i;
	}

	public boolean getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean b) {
		this.enabled = b;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public double getDistancia() {
		return distancia;
	}
	
	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

}
