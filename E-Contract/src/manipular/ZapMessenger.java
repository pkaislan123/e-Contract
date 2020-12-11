package manipular;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import outros.TratarDados;

public class ZapMessenger {

	
	public ZapMessenger() {
		
	}
	
	
	   public boolean enviarMensagem(String celular, String msg) {
			HttpClient httpclient = HttpClients.createDefault();
	          
			HttpPost httppost = new HttpPost("https://rest.messengerpeople.com/api/v14/chat");
			httppost.setHeader("Content-Type", "application/json");
		
			
			
			try { 
			    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			    valores.add(new BasicNameValuePair("id", celular));
			    valores.add(new BasicNameValuePair("message", msg));


			    valores.add(new BasicNameValuePair("apikey", "2bab9632786a28e1b293a997f12453e8_39549_053220610bd14bab5877227df"));

			    
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
	
		public boolean enviarArquivo(String celular, String msg, String url) {
			HttpClient httpclient = HttpClients.createDefault();
				          
			HttpPost httppost = new HttpPost("https://rest.messengerpeople.com/api/v14/chat");
			httppost.setHeader("Content-Type", "application/json");
		
			
			
			try { 
			    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			    valores.add(new BasicNameValuePair("id", celular));
			    valores.add(new BasicNameValuePair("message", msg));
			    valores.add(new BasicNameValuePair("attachment", url));


			    valores.add(new BasicNameValuePair("apikey", "2bab9632786a28e1b293a997f12453e8_39549_053220610bd14bab5877227df"));

			    
			    httppost.setEntity( new UrlEncodedFormEntity( valores ) );
			    HttpResponse response = httpclient.execute( httppost );
			               
			    HttpEntity entity = response.getEntity();
			    String content = EntityUtils.toString(entity);
			    System.out.println( content );
			    TratarDados tratar = new TratarDados(content);
			    String code = tratar.tratar("code\":", ",");
			    System.out.println("Codigo:" + code);
			    if(code.equals("400")) {
					System.out.println("erro ao enviar arquivo via api whatsapp: ");
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
		
		public boolean logar() {
			HttpClient httpclient = HttpClients.createDefault();
	          
			HttpPost httppost = new HttpPost("https://rest.messengerpeople.com/api/v14/login");
			httppost.setHeader("Content-Type", "application/json");
		
			
			
			try { 
			    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			    valores.add(new BasicNameValuePair("username", "comprasaislan@gmail.com"));
			    valores.add(new BasicNameValuePair("password", "titaniwm2014"));

			    valores.add(new BasicNameValuePair("apikey", "2bab9632786a28e1b293a997f12453e8_39549_053220610bd14bab5877227df"));


			    
			    httppost.setEntity( new UrlEncodedFormEntity( valores ) );
			    HttpResponse response = httpclient.execute( httppost );
			               
			    HttpEntity entity = response.getEntity();
			    String content = EntityUtils.toString(entity);
			    System.out.println( content );
			    TratarDados tratar = new TratarDados(content);
			    String code = tratar.tratar("code\":", ",");
			    System.out.println("Codigo:" + code);
			    if(code.equals("400")) {
					System.out.println("erro ao logar: ");
                       return false;
			    }else 
			    	 return true;
			              
			} catch (ClientProtocolException e) {
				System.out.println("erro ao logar: " + e.getMessage());
			    e.printStackTrace();
			    return false;
			} catch (IOException e) {
				System.out.println("erro ao logarp: " + e.getMessage());
			    e.printStackTrace();
			    return false;			} finally {
			    httppost.releaseConnection();;
			}
		}
	
	
}
