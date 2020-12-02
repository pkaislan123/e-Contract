package outros;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import classesExtras.Endereco;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetSintegra {
	
	private String cpf;
	private String uf;
	String json;      

	
	private String plugin = "ST";
	
	public  GetSintegra(String cpf, String uf)
	{
		this.cpf = cpf;
		this.uf = uf;
	}
	

	public String captura() {
	OkHttpClient client = new OkHttpClient();
	String token = "B2925B10-A525-423D-B302-F3B73169A92A";

	  String url_ = "https://www.sintegraws.com.br/api/v1/execute-api.php?token=" + token + "&cpf=" + cpf + "&uf=" + uf + "&plugin=" + plugin;
      //var response = client.GetAsync(url).Result;
	
	//Request request = new Request.Builder()
	  //.url(url_)
	  //.get().build();
/*
	try {
		Response response = client.newCall(request).execute();
	
        String result = response.body().string();
        
	    result = result.replaceAll(",", "\n");    
        System.out.println(result);


        return result;

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}*/
	 try {
         URL url = new URL(url_);
         URLConnection urlConnection = url.openConnection();
         InputStream is = urlConnection.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));

         StringBuilder jsonSb = new StringBuilder();

         br.lines().forEach(l -> jsonSb.append(l.trim()));
         json = jsonSb.toString();
         
         return json;
         // JOptionPane.showMessageDialog(null, json);
         
         
         
         // JOptionPane.showMessageDialog(null, array);
                   
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
	
	
	}
	
	
}
