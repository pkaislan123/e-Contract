package chat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

public class Cliente {
	private static final long serialVersionUID = 1L;
	
	private Socket socket;
	private OutputStream ou ;
	private Writer ouw;
	private BufferedWriter bfw;

	private String ip_local;
	private String porta_local;
	
	public void setParamentros(String ip, String porta) {
		
		this.ip_local = ip;
		this.porta_local = porta;
	}
	
	public void conectar() throws IOException{

		  socket = new Socket(ip_local,Integer.parseInt(porta_local));
		  ou = socket.getOutputStream();
		  ouw = new OutputStreamWriter(ou);
		  bfw = new BufferedWriter(ouw);
		  
		  bfw.flush();
	
	
	
	
}
	
	public void enviarMensagem(String msg) throws IOException{

	   
	      bfw.write(msg+"\r\n");
	   
	     bfw.flush();
	}
	
}
