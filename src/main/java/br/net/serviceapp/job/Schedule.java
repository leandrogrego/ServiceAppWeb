package br.net.serviceapp.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.net.serviceapp.model.Message;
import br.net.serviceapp.service.EmailService;
import br.net.serviceapp.service.MessageService;

@Component
@EnableScheduling
public class Schedule {
    private final long SEGUNDO = 1000; 
    private final long MINUTO = SEGUNDO * 60; 
    private final long HORA = MINUTO * 60;
    
    @Autowired
    private MessageService messageService = new MessageService();
    
    @Autowired
    private EmailService emailService  = new EmailService();
    
	@Scheduled(fixedDelay = HORA) 
    public void verificaPorHora() {
		
		Date endDate = new Date();
        Long now = endDate.getTime();
        Long time = now - HORA;
        Date startDate = new Date(time);
        List<Message> messages = messageService.findByDateInterval(startDate, endDate);
        messages.forEach(m->{
        	if(m.getTo().getEmail()!=null) {
        		emailService.send(
        				"noreply@serviceapp.net.br", 
        				m.getTo().getEmail(), 
        				"Você tem mensagens não lidas",
        				"Você tem mensages não lidas de "+m.getFrom().getName()
        				+"\n\nAcesse: https://www.serviceapp.net.br para ler suas mensagens."
        				+"\n\nAtenciosamente:\nEquipe ServiceApp"
        				);
        	}
        });
         
    }
}
