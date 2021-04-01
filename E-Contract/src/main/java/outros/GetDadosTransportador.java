package main.java.outros;

import java.awt.MediaTracker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;

public class GetDadosTransportador {
	
	private String cpf;
	private String cnpj, s_rntrc;
	String json;      

	
	
/*	public  GetDadosTransportador (String cpf, String cnpj, String s_rntrc)
	{
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.s_rntrc = s_rntrc;
	}
	*/


	   public boolean captura( ) {
		  
			   HttpClient httpclient = HttpClients.createDefault();
		          
				HttpPost httppost = new HttpPost("https://api.xdata.com.br/json/reply/i2cANTT");
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-Type", "application/json");

				 
				
				
				try { 
				    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
				    valores.add(new BasicNameValuePair("rntrc", "052211700"));



				    
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
			   
			
			
	   
	
	
}
