package principal;

import mensageria.Enviador
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext

public class MainTeste {


	 public MainTeste() {
		 
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("mensageria/spring.xml")
		 Enviador enviador = ctx.getBean("enviador")
		 enviador.send("ola mundo mom")
		 Thread.sleep(2000)
		 ((ConfigurableApplicationContext)ctx).close();
	 }
	 
	
}
