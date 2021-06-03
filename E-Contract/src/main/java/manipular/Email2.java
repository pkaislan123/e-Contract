package main.java.manipular;

import java.util.Properties;

import javax.mail.Session;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email2 {


	

	 Session session ;
	 private String email_local, senha_local;
	 
	 
	 public void logar(String email, String senha) {

		  this.email_local = email;
		  this.senha_local = senha;
	
		  
	 }
	
	public boolean  abrirEmail() {
	
		  if(email_local.contains("gmail")) {

			
			    
			    SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.googlemail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator(email_local, senha_local));
				email.setSSLOnConnect(true);
				try {
					email.setFrom(email_local);
					email.setSubject("TestMail");
					email.setMsg("Email para teste de autentificação");
					email.addTo(email_local);
					email.send();
					return true;

				} catch (EmailException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;

				}
			    
			    
			     }else if(email_local.contains("hotmail") || email_local.contains("live")) {

			    	 SimpleEmail email = new SimpleEmail();
						email.setHostName("smtp.live.com");
						email.setSmtpPort(587);
						email.setAuthenticator(new DefaultAuthenticator(email_local, senha_local));
						email.setSSLOnConnect(true);
						try {
							email.setFrom(email_local);
							email.setSubject("TestMail");
							email.setMsg("Email para teste de autentificação");
							email.addTo(email_local);
							email.send();
							return true;

						} catch (EmailException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return false;

						}

			     }else {
			    	 return false;
			     }
		
	
		
		
	}
	
	
}
