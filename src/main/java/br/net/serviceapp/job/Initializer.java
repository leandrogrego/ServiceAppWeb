package br.net.serviceapp.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.net.serviceapp.model.Servico;
import br.net.serviceapp.model.User;
import br.net.serviceapp.service.EmailService;
import br.net.serviceapp.service.ServicoService;
import br.net.serviceapp.service.UserService;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private EmailService emailService  = new EmailService();
    
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("----- Creating Default Services ------");
		
		createServico("Cabelereiro", "Corte de cabelo Masculino e Feminino.");
		createServico("Designer Gráfico", "Designer Gráfico");
		createServico("Eletricista", "Reparos Elétricos");
		createServico("Eletrônico", "Reparos em eletrodomésticos e demais equipamentos eletrônicos");
		createServico("Encanador", "Reparos hidráulicos");
		createServico("Entregador", "Entrega de encomendas em geral");
		createServico("Fachineira", "Fachina em geral");
		createServico("Jardineiro", "Jardinagem em geral");
		createServico("Lavadeira", "Lavação de roupas em geral");
		createServico("Manicure e Pedicure", "Manicure e Pedicure");
		createServico("Pedreiro", "Construção e obras.");
		createServico("Programador", "Desenvolvimento de softares para computadores e dispositivos moveis.");
		StartAlert();
		setAdmin("117578396764585597035", "google");
	}

	private Servico createServico(String name, String descricao) {
		Servico servico = servicoService.findByName(name);
		if (servico == null) {
			servico = new Servico(name, descricao, null);
			servicoService.save(servico);
			servico = servicoService.findByName(name);
		}
		
		System.out.println(servico.getName()+" added.");
		return servico;
	}
	
	private void StartAlert() {
		
		emailService.send(
				"noreply@serviceapp.net.br", 
				"leandrogrego@gmail.com", 
				"Alerta de Reinicialização do Sistema ServiceApp",
				("O Sistema ServiceApp foi reinicializado corretamente em \n"
				+new Date()
				+"\n\nAtenciosamente:\nEquipe ServiceApp")
				);
	}

	private void setAdmin(String socialId, String provider) {
		User user = userService.socialLogin(socialId, provider);
		user.setPerfil(7);
		userService.save(user);
	}
}

