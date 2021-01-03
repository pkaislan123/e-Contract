package mensageria;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.jms.core.JmsTemplate;



@Named
public class Enviador {

	
	@Inject
	JmsTemplate jms
	
	
	void send(Object mensagem) {
		println "Enviador: enviando mensagem.."
		jms.convertAndSend(mensagem);
	}
	

}
