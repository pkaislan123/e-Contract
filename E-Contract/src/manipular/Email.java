package manipular;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email
{
	
	
	 Properties props ;
	 Session session ;
	 
  public  Email() {
     props = new Properties();
    /** Parâmetros de conexão com servidor Gmail */
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class",
    "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    
    
  }
  
  public void logar(String email, String senha) {

     session = Session.getDefaultInstance(props,
      new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication()
           {
                 return new PasswordAuthentication(email,
                senha);
           }
      });

    /** Ativa Debug para sessão */
    session.setDebug(true);
    
  }

  
  public boolean enviar(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura) {
    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(remetente));
      //Remetente

      Address[] toUser = InternetAddress //Destinatário(s)
                 .parse(destinatario);

      message.setRecipients(Message.RecipientType.TO, toUser);
      message.setSubject(assunto);//Assunto
      message.setText(saudacao + mensagem + assinatura);

      /**Método para enviar a mensagem criada*/
      Transport.send(message);

      System.out.println("Feito!!!");
      return true;

     } catch (MessagingException mex) {
    	 mex.printStackTrace();
         Exception ex = null;
         if ((ex = mex.getNextException()) != null) {
       ex.printStackTrace();
         }
      return false;
    }
  }
  
  public boolean enviarAnexo(String remetente, String destinatario, String assunto, String saudacao, String mensagem, String assinatura, ArrayList<File> anexos) {
	  try 
	  {
	      // cria a mensagem
	      MimeMessage msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress(remetente));
	      InternetAddress[] address = {new InternetAddress(destinatario)};
	      msg.setRecipients(Message.RecipientType.TO, address);
	      msg.setSubject(assunto);

	      // cria a primeira parte da mensagem
	      MimeBodyPart mbp1 = new MimeBodyPart();
	      mbp1.setText(saudacao + mensagem + assinatura);

	    
	    
	      
	      ArrayList<MimeBodyPart> partes_anexos = new ArrayList<>();
	            // anexa o arquivo na mensagem
	      for(File arquivo : anexos) {
		    MimeBodyPart anexo = new MimeBodyPart();

	      FileDataSource fds = new FileDataSource(arquivo);
	      anexo.setDataHandler(new DataHandler(fds));
	      anexo.setFileName(fds.getName());
	      partes_anexos.add(anexo);
	      }
	      // cria a Multipart
	      Multipart mp = new MimeMultipart();
	      mp.addBodyPart(mbp1); //assunto
	      
	      
	      for(MimeBodyPart anexo : partes_anexos) {
	    	  mp.addBodyPart(anexo);
	      }

	      // adiciona a Multipart na mensagem
	      msg.setContent(mp);

	      // configura a data: cabecalho
	      msg.setSentDate(new Date());
	      
	      // envia a mensagem
	      Transport.send(msg);
	      return true;
	      
	  } catch (MessagingException mex) {
	    	 mex.printStackTrace();
	         Exception ex = null;
	         if ((ex = mex.getNextException()) != null) {
	       ex.printStackTrace();
	         }
	         return false;
	      
	    }
	 
  }

}



  