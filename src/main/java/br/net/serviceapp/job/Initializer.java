package br.net.serviceapp.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.net.serviceapp.model.Address;
import br.net.serviceapp.model.Servico;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.ServicoService;
import br.net.serviceapp.service.UserService;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private ServicoService servicoService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("----- Creating Default Services ------");
		createServico("Cabelereiro", "Corte de cabelo Masculino e Feminino.");
		createServico("Designer Gr�fico", "Designer Gr�fico");
		createServico("Eletricista", "Reparos El�tricos");
		createServico("Eletr�nico", "Reparos em eletrodom�sticos e demais equipamentos eletr�nicos");
		createServico("Encanador", "Reparos hidr�ulicos");
		createServico("Entregador", "Entrega de encomendas em geral");
		createServico("Fachineira", "Fachina em geral");
		createServico("Jardineiro", "Jardinagem em geral");
		createServico("Lavadeira", "Lava��o de roupas em geral");
		createServico("Manicure e Pedicure", "Manicure e Pedicure");
		createServico("Pedreiro", "Constru��o e obras.");
		Servico servico = createServico("Programador", "Desenvolvimento de programas de computador.");
		//User user = createUser("Leandro Rego", "+5584994331500", "leandrogrego@gmail.com", "Rua Jos� Holanda Pinto", "105", "S�o Benedito", "", "Pau dos Ferros", "RN", "59.900-000", "Brasil");
		//user.addServico(servico);
		//userService.save(user);
		//System.out.println(user.getId()+" : "+user.getToken());
		System.out.println("----- Users created Successful! -----");
	}

	private User createUser(String name, String number, String email, String logradouro, String numero, String bairro, String complemento, String cidade, String estado, String cep, String pais) {

		User user = userService.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setName(name);
			user.setNumber(number);
			user.setEmail(email);
			user.setPerfil(0);
			user.resetToken();
			userService.save(user);
			user = userService.findByEmail(email);
			Address address = new Address(logradouro, numero, bairro, complemento, cidade, estado, cep, pais);
			user.setAddress(address);
		}
		
		return user;
	}

	private Servico createServico(String name, String descricao) {
		Servico servico = servicoService.findByName(name);
		if (servico == null) {
			servico = new Servico(name, descricao);
			servicoService.save(servico);
			servico = servicoService.findByName(name);
		}
		
		System.out.println(servico.getName()+" added.");
		return servico;
	}

}

