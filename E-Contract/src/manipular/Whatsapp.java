package manipular;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import cadastros.CadastroLogin;
import cadastros.CadastroZapMessenger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import outros.DadosGlobais;
import outros.TratarDados;

public class Whatsapp {

	
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	
	private String chave = "";
	private String usuario  = "";
	private String senha = "";
 
	
	
	//"{\"menssage\": \"Teste de envio pela API\"  \"number\": \"16997102424\"}"
	public Whatsapp() {
		getDadosGlobais();

	}
	
	public boolean enviarArquivo(String msg, String numero, String url) {
		OkHttpClient client = new OkHttpClient();
        
		String saida = ""; 
		//"{\"caption\": \""  + msg     +"\" , \"number\": \"" + numero +  "\" , \"url\": \""   + caminhoArquivo + "\" }"

		MediaType mediaType = MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType,
				"{ \"caption\": \""  + msg     +"\" , \"number\": \"" + numero +  "\" , \"url\": \"" + url + "\" }"

		);
		Request request = new Request.Builder()
		  .url("https://v4.chatpro.com.br/" + senha + "/api/v1/send_message_file_from_url")
		  .post(body)
		  .addHeader("Authorization", chave)
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("resposta: " + response.message());
			 saida = response.message();
			 return true;
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage() + " causa: " + e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
			saida = "Erro " + e.getMessage() + " causa: " + e.getCause();
			return false;
		}
		
		
	}
	/*
public boolean enviarMensagem(String numero, String msg) {
				HttpClient httpclient = HttpClients.createDefault();
		          
				HttpPost httppost = new HttpPost("http://v4.chatpro.com.br/chatpro-j4wjjxrsd6/api/v1/send_message");

				httppost.addHeader("Authorization", "np23dvdvdpamuw0ll9fh1wcwqz5nz7");
				httppost.addHeader("Content-Type", "application/json");

				
				try { 
				    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
				    valores.add(new BasicNameValuePair("message", msg));
				    valores.add(new BasicNameValuePair("number", numero));



				    
				    httppost.setEntity( new UrlEncodedFormEntity( valores ) );
				    HttpResponse response = httpclient.execute( httppost );
				               
				    HttpEntity entity = response.getEntity();
				    String content = EntityUtils.toString(entity);
				    System.out.println( content );
				    TratarDados tratar = new TratarDados(content);
				    String code = tratar.tratar("code\":", ",");
				    System.out.println("Codigo:" + code);
				    if(code.equals("400")) {
						System.out.println("erro ao enviar mensagem via api whatsapp: ");
	                       return false;
				    }else 
				    	 return true;
				              
				} catch (ClientProtocolException e) {
					System.out.println("erro ao enviar arquivo via api whatsapp: " + e.getMessage());
				    e.printStackTrace();
				    return false;
				} catch (IOException e) {
					System.out.println("erro ao enviar arquivo via api whatsapp: " + e.getMessage());
				    e.printStackTrace();
				    return false;			} finally {
				    httppost.releaseConnection();;
				}
		   }
		   
	   */
	public boolean enviarMensagem(String numero, String msg) {
		OkHttpClient client = new OkHttpClient();
		String saida = ""; 

		MediaType mediaType = MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType,
				"{\"menssage\": \""  + msg     +"\" , \"number\": \"" + numero +"\"}"
		);
		Request request = new Request.Builder()
		  .url("http://v4.chatpro.com.br/" + senha  +"/api/v1/send_message")
		  .post(body)
		  .addHeader("Authorization", chave)
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("resposta: " + response.message());
			if(response.message().contains("Bad Request")) {
				 return false;

			}else {
			 saida = response.message();
			 return true;
			}
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage() + " causa: " + e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
			saida = "Erro " + e.getMessage() + " causa: " + e.getCause();
           return false;
		}
		

	}
	
	public String status() {
		OkHttpClient client = new OkHttpClient();
		String saida = ""; 

		MediaType mediaType = MediaType.parse("application/octet-stream");
		
		Request request = new Request.Builder()
		  .url("http://v4.chatpro.com.br/" + senha + "/api/v1/status")
		  .addHeader("Authorization", chave)
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("resposta: " + response.message() );
			 saida = response.message();

		
			 return saida;
			
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage() + " causa: " + e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
			saida = "Erro " + e.getMessage() + " causa: " + e.getCause();
           return saida;
		}
		
		
	}
	
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 configs_globais = dados.getConfigs_globais();
				 CadastroZapMessenger zap = configs_globais.getZap_zap();
				 this.chave = zap.getApi_key();
				 this.usuario = zap.getEmail();
				 this.senha = zap.getSenha();
		
	}

}
