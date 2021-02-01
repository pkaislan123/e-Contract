package outros;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import classesExtras.Endereco;


public class BuscarCep {
	
	int cep;


	public BuscarCep(int cep)
	{
		this.cep = cep;
	}
	
	public Endereco buscar() {
		
		Endereco endereco;
		String json;      
		String strCep = String.valueOf(cep);
		String logradouro;      
		String bairro;
		String cidade;
		String uf;
		
		 try {
	            URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
	            URLConnection urlConnection = url.openConnection();
	            InputStream is = urlConnection.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));

	            StringBuilder jsonSb = new StringBuilder();

	            br.lines().forEach(l -> jsonSb.append(l.trim()));
	            json = jsonSb.toString();
	            
	            // JOptionPane.showMessageDialog(null, json);
	            
	            json = json.replaceAll("[{},:]", "");
	            json = json.replaceAll("\"", "\n");                       
	            String array[] = new String[30];
	            array = json.split("\n");
	            
	            // JOptionPane.showMessageDialog(null, array);
	            endereco = new Endereco();                 
	            endereco.setLogradouro(array[7]);     
	            endereco.setBairro(array[15]);
	            endereco.setCidade(array[19]);
	            endereco.setUf(array[23]);
	            
	          
              return endereco;	            
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }

		
	}
	
	
    public Endereco buscarCep(Endereco _endereco) {
		
		String json;      
		String strCep;
		
		
		 try {
	            URL url = new URL("https://viacep.com.br/ws/" + _endereco.getUf() + "/" + _endereco.getCidade().replaceAll(" ", "%20") + "/" + _endereco.getLogradouro().replaceAll(" ", "%20") + "/json");
	            URLConnection urlConnection = url.openConnection();
	            InputStream is = urlConnection.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));

	            StringBuilder jsonSb = new StringBuilder();

	            br.lines().forEach(l -> jsonSb.append(l.trim()));
	            json = jsonSb.toString();
	            
	            
	            json = json.replaceAll("[{},:\\[\"]", "");
	          
	            TratarDados tratar = new TratarDados(json);
	            
	            String cep = tratar.tratar("cep ", "logradouro");
	            _endereco.setCep(cep);
	          
              return _endereco;	            
	        } catch (Exception e) {
	           JOptionPane.showMessageDialog(null, "Erro ao realizar consulta!\nErro:" + e.getMessage());
	           return null;
	        }

		
	}
	
	
}
