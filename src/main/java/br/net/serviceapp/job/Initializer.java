package br.net.serviceapp.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import br.net.serviceapp.model.Address;
import br.net.serviceapp.model.Message;
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
    private EmailService emailService  = new EmailService();
    
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
		StartAlert();
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
	
	private void StartAlert() {
		emailService.send(
				"milkpoint@serviceapp.net.br", 
				"leandrogrego@gmail.com", 
				"Alerta de Reinicializa�ao do Sistema ServiceApp",
				"O Sistema ServiceApp fpo omocoa�ozadp Corretamente em \n"
				+new Date()
				+"\n\nAtenciosamente:\nEquipe ServiceApp"
				);
	}

}

