package manipular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import cadastros.CadastroLogin;
import outros.DadosGlobais;
import outros.TratarDados;
import tratamento_proprio.Log;

public class ArquivoConfiguracoes {

	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String conteudo_arquivo;
	private Properties propriedades_local;
	
	public ArquivoConfiguracoes() {
		
		
			try {
				fazer_leitura();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		getDadosGlobais();
	}
	

	public  void fazer_leitura() throws IOException {
	
			propriedades_local  = new Properties();
		
		FileInputStream file;
		
			file = new FileInputStream("C:\\ProgramData\\E-Contract\\configs\\configs.properties");
			 propriedades_local.load(file);
		
		}
		
		
	

	

	
	public String getConteudoArquivo() {
		return conteudo_arquivo;
	}
	
	/*
	public boolean fazer_leitura() {
		boolean retorno_positivo = false;
		
		ManipularTxt manipularTxt = new ManipularTxt();
		File arquivo_configs = manipularTxt.abrirArquivo("C:\\ProgramData\\E-Contract\\configs\\configs.ini");
		if(arquivo_configs != null) {
		 String conteudo = manipularTxt.lerArquivo(arquivo_configs);
		  conteudo_arquivo = conteudo;
		 System.out.println("Conteudo: " + conteudo);
		 TratarDados tratar = new TratarDados(conteudo);
		 String pasta_raiz = tratar.tratar("pasta_raiz=", ";");
		 System.out.println("Caminho pasta raiz: " + pasta_raiz);
		
		 if(pasta_raiz != null && !pasta_raiz.equals(" ") && !pasta_raiz.equals("") && pasta_raiz.length() == 6 ) {
			 System.out.println("A pasta raiz e valida!");
			 configs_globais.setRaiz(pasta_raiz);
			 retorno_positivo = true;
		 }else {
			 System.out.println("A pasta raiz e invalida!" + "\n tamanho da String: " + pasta_raiz.length());
			 retorno_positivo = false;

		 }

		}else {
			System.out.println("Arquivo de configurações não encontrado!");
			 retorno_positivo = false;

		}
		
		
		return retorno_positivo;
	}
	*/
	public void getDadosGlobais() {
		//gerenciador de log
				DadosGlobais dados = DadosGlobais.getInstance();
				 GerenciadorLog = dados.getGerenciadorLog();
				 if(dados.getConfigs_globais() == null) {
					 configs_globais = new ConfiguracoesGlobais();
					 dados.setConfigs_globais(configs_globais);
				 }else
				 configs_globais = dados.getConfigs_globais();
				 
				 //usuario logado
				  login = dados.getLogin();
		
	}
	
	
	/*public boolean salvarNovasConfigs(String novas_configs) {
		ManipularTxt manipularTxt = new ManipularTxt();
		File arquivo_configs = manipularTxt.abrirArquivo("C:\\ProgramData\\E-Contract\\configs\\configs.ini");
		if(arquivo_configs != null) {
			if( manipularTxt.rescreverArquivo(arquivo_configs, novas_configs)){
				//arquivo reescrito
				 fazer_leitura();
				return true;

			}else {
				return false;
			}
			
		}else {
			return false;
		}
	}*/
	
	
	public int getCodigoSequencial() {
		int cod ;
		Properties prop;
		try {
		cod = Integer.parseInt(propriedades_local.getProperty("prop.codigo.sequencial"));
		System.out.println("Codigo sequencial em inteiros: " + cod);

		return cod;
		}catch(Exception e ) {
			return -1;
		}
		
	}
	
	
	public String getPastaRaiz() {
		String pasta_raiz = "";
		Properties prop;
		
		pasta_raiz = propriedades_local.getProperty("prop.server.baseDados");
		return pasta_raiz;

		
	}
	
	public boolean setPastaRaiz(String pastaRaiz) {
		String pasta_raiz = "";
		propriedades_local.setProperty("prop.server.baseDados", pastaRaiz);

		return true;
	}
	
	public boolean setCodidoSequencial(int codigo) {
		
		propriedades_local.setProperty("prop.codigo.sequencial", Integer.toString(codigo));

		return true;
	}
	
	public boolean salvarNovasConfiguragoes() {
		try { 
			FileOutputStream fos = new FileOutputStream("C:\\ProgramData\\E-Contract\\configs\\configs.properties");
			

			propriedades_local.store(fos, "\"Configuracoes\""); 
			fos.close(); 
			recarregarDadosGlobais();
			return true;
			}
		catch (IOException ex) 
		{ 
			System.out.println(ex.getMessage()); ex.printStackTrace(); 
			return false;
		}
	}
	
	
	public void recarregarDadosGlobais() {
		try {
			fazer_leitura();
			testeConfiguragoes();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean testeConfiguragoes() {
		String config_pasta_raiz = getPastaRaiz();
		
		System.out.println("pasta raiz: " + config_pasta_raiz);
		boolean retorno_positivo = false;
		
		if(config_pasta_raiz != null && !config_pasta_raiz.equals("") && !config_pasta_raiz.equals(" ") ) {
			configs_globais.setRaiz(config_pasta_raiz);
			

			 if(getCodigoSequencial() != -1) {
                      configs_globais.setCodigoSequencial(getCodigoSequencial());
                      DadosGlobais dados = DadosGlobais.getInstance();

         			 dados.setConfigs_globais(configs_globais);	 
         			retorno_positivo = true;
			 }else {
					retorno_positivo = false;

			 }
		}else {
			retorno_positivo = false;

		}
		
		return retorno_positivo;
		
	}
	
	
	
	
	
}
