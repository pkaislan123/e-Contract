package main.java.outros;





import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;




public class GetHttp {
	
	private String cnpj;
	
	public  GetHttp(String cnpj)
	{
		this.cnpj = cnpj;
	}
	

	public String captura() {
	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
	  .url("https://api.cnpja.com.br/companies/"+cnpj+"?sintegra_max_age=120")
	  .get()
	  .addHeader("authorization", "4ed320e8-b364-450d-9903-f99bbcaa7633-170b4e8c-7f67-442e-a718-96c595c0a787")
	  .build();

	try {
		Response response = client.newCall(request).execute();
        String result = response.body().string();
        
	    result = result.replaceAll(",", "\n");                       

        return result;

	} 
	catch (SocketTimeoutException f) {
		// TODO Auto-generated catch block
        System.out.println("Sem Conexão com a internet!");

		f.printStackTrace();
		return null;
	}
	catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	
	
	
	}
}
