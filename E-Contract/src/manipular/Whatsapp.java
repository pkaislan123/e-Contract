package manipular;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Whatsapp {

	
	
 
	
	
	//"{\"menssage\": \"Teste de envio pela API\"  \"number\": \"16997102424\"}"
	public Whatsapp() {
	
	}
	
	public String enviarArquivo(String msg, String numero, String url) {
		OkHttpClient client = new OkHttpClient();
        
		String saida = ""; 
		//"{\"caption\": \""  + msg     +"\" , \"number\": \"" + numero +  "\" , \"url\": \""   + caminhoArquivo + "\" }"

		MediaType mediaType = MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType,
				"{ \"caption\": \""  + msg     +"\" , \"number\": \"" + numero +  "\" , \"url\": \"" + url + "\" }"

		);
		Request request = new Request.Builder()
		  .url("http://v4.chatpro.com.br/chatpro-6t45781ket/api/v1/send_message")
		  .post(body)
		  .addHeader("Authorization", "5f9igam7fe4pc152xldt718nz2qmqt")
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("resposta: " + response.message());
			 saida = response.message();
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage() + " causa: " + e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
			saida = "Erro " + e.getMessage() + " causa: " + e.getCause();
		}
		
		return saida;
		
	}
	
	public String enviarMensagem(String msg, String numero) {
		OkHttpClient client = new OkHttpClient();
		String saida = ""; 

		MediaType mediaType = MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType,
				"{\"menssage\": \""  + msg     +"\" , \"number\": \"" + numero +"\"}"
		);
		Request request = new Request.Builder()
		  .url("http://v4.chatpro.com.br/chatpro-6t45781ket/api/v1/send_message")
		  .post(body)
		  .addHeader("Authorization", "5f9igam7fe4pc152xldt718nz2qmqt")
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println("resposta: " + response.message());
			 saida = response.message();
		} catch (IOException e) {
			System.out.println("Erro " + e.getMessage() + " causa: " + e.getCause());
			// TODO Auto-generated catch block
			e.printStackTrace();
			saida = "Erro " + e.getMessage() + " causa: " + e.getCause();

		}
		
		return saida;

	}
	
}
