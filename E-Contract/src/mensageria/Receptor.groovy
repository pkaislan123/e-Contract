package mensageria;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;



@Named
 class Receptor implements MessageListener{


	@Override
	public void onMessage(Message message) {
		 TextMessage msg = (TextMessage) message
	     println "--> Receptor: recebeu mensagem fila - " + msg.text
	
	}
	

}
