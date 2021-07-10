package main.java.manipular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroContrato.Recebimento;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.CadastroZapMessenger;
import main.java.cadastros.ContaBancaria;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class ArquivoConfiguracoes {

	
	private Log GerenciadorLog;
	private CadastroLogin login;
	private ConfiguracoesGlobais configs_globais;
	private String conteudo_arquivo;
	private Properties propriedades_local;
	private CadastroBaseDados minha_base;
	
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
	
	
	public CadastroZapMessenger getConfigsZapMessenger() {
		String api_key, senha, email;
		CadastroZapMessenger zap = new CadastroZapMessenger();
		Properties prop;
		try {
			api_key = propriedades_local.getProperty("prop.zapmessenger.apikey");
			System.out.println("Api key zap messenger: " + api_key);

			
			email = propriedades_local.getProperty("prop.zapmessenger.mailzap");
			System.out.println("email zap messenger: " + email);
			

			senha = propriedades_local.getProperty("prop.zapmessenger.zapkey");
			System.out.println("email zap messenger: " + senha);
			
			zap.setApi_key(api_key);
			zap.setEmail(email);
			zap.setSenha(senha);
			
		return zap;
		}catch(Exception e ) {
			return null;
		}
		
	}
	
	
	public CadastroNuvem getConfigsNuvem() {
		String app_key, app_secret, token;
		CadastroNuvem nuvem = new CadastroNuvem();
		Properties prop;
		try {
			app_key = propriedades_local.getProperty("prop.nuvem.appkey");
			System.out.println("App key nuvem: " + app_key);

			
			app_secret = propriedades_local.getProperty("prop.nuvem.appsecret");
			System.out.println("app secret  nuvem: " + app_secret);
			

			token = propriedades_local.getProperty("prop.nuvem.token");
			System.out.println("token  nuvem: " + token);
			
			nuvem.setApp_key(app_key);
			nuvem.setApp_secret(app_secret);
			nuvem.setToken(token);
			
		return nuvem;
		}catch(Exception e ) {
			return null;
		}
		
	}
	
	public String getPastaRomaneios() {
		String  pasta_romaneio = "";
		Properties prop;
		try {
			pasta_romaneio = propriedades_local.getProperty("prop.romaneios.pasta_romaneios");
			System.out.println("App key nuvem: " + pasta_romaneio);
			
			return pasta_romaneio;

		}catch(Exception e ) {
			return null;
		}
		
	}
	
	
	public String getIpRelogio() {
		String  ip_relogio = "";
		Properties prop;
		try {
			ip_relogio = propriedades_local.getProperty("prop.relogio.ip");
			System.out.println("Ip do relogio: " + ip_relogio);
			
			return ip_relogio;

		}catch(Exception e ) {
			return null;
		}
		
	}
	
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
	
	
	public CadastroBaseDados getDadosBaseDados() {
		String host, porta, nome_banco, nome_usuario, senha;
		
		
		/*prop.bd.host=Desktop-6jh0ag4
				prop.bd.porta=3306
				prop.bd.nomebase=bd_ldarmazens
				prop.bd.usuariobd=root
				prop.bd.senhabd=1234
				*/

		CadastroBaseDados base_dados = new CadastroBaseDados();
	    host = propriedades_local.getProperty("prop.bd.host");
		porta = propriedades_local.getProperty("prop.bd.porta");
		nome_banco = propriedades_local.getProperty("prop.bd.nomebase");
		nome_usuario = propriedades_local.getProperty("prop.bd.usuariobd");
		senha = propriedades_local.getProperty("prop.bd.senhabd");

		base_dados.setHost(host);
		base_dados.setPorta(porta);
		base_dados.setNome_banco(nome_banco);
		base_dados.setNome_usuario(nome_usuario);
		base_dados.setSenha(senha);
		
		 System.out.println("host: " + base_dados.getHost());
		 System.out.println("porta: " + base_dados.getPorta());
		 System.out.println("nome-Banco: " + base_dados.getNome_banco());
		 System.out.println("nome usuario: " + base_dados.getNome_usuario());

		 
		 
		return base_dados;

		
		
	}
	
	public boolean setDadosBaseDados(CadastroBaseDados cb_bd)
	{
		try {
			
		propriedades_local.setProperty("prop.bd.host", cb_bd.getHost());
		     propriedades_local.setProperty("prop.bd.porta", cb_bd.getPorta());
		    	propriedades_local.setProperty("prop.bd.nomebase", cb_bd.getNome_banco());
		     propriedades_local.setProperty("prop.bd.usuariobd", cb_bd.getNome_usuario());
			 propriedades_local.setProperty("prop.bd.senhabd", cb_bd.getSenha());
			 propriedades_local.setProperty("prop.bd.senhabd", cb_bd.getSenha());

			 
			 
		return true;
		}catch(Exception e) {
			System.out.println("erro ao setar dados no arquivo de propriedas, erro: " + e.getMessage());
			return false;
		}
		
	}
	public CadastroBaseArquivos getPastaRaiz() {
		String servidor, unidade;
		
		CadastroBaseArquivos servidor_arquivos = new CadastroBaseArquivos();
		
		servidor = propriedades_local.getProperty("prop.server.baseDados");
		unidade = propriedades_local.getProperty("prop.server.unidade"); 
		servidor_arquivos.setServidor(servidor);
		servidor_arquivos.setUnidade(unidade);
		
		System.out.println("Servidor da base de arquivos: " + servidor_arquivos.getUnidade());
		System.out.println("Unidade da base de arquivos: " + servidor_arquivos.getUnidade());
		
        return servidor_arquivos;
		
	}
	
	public boolean setPastaRaiz(CadastroBaseArquivos base_arquivos) {
		
		try {
			propriedades_local.setProperty("prop.server.baseDados", base_arquivos.getServidor());
			propriedades_local.setProperty("prop.server.unidade", base_arquivos.getUnidade());

			return true;
			
		}catch(Exception e) {
			return false;
		}
		
		
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
		CadastroBaseArquivos config_pasta_raiz = getPastaRaiz();
		CadastroZapMessenger zap_zap = getConfigsZapMessenger();
		CadastroNuvem nuvem = getConfigsNuvem();
		String pasta_romaneio = getPastaRomaneios();
		String ip_relogio = getIpRelogio();
		
		boolean retorno_positivo = false;
		
		if(config_pasta_raiz != null ) {
			configs_globais.setServidor_arquivos(config_pasta_raiz);
			

			 if(getCodigoSequencial() != -1) {
                 configs_globais.setCodigoSequencial(getCodigoSequencial());

				    CadastroBaseDados cad = getDadosBaseDados();
                    if(cad != null)	{			 
                     
                    	//testa as configuracoes de zapmessenger
                    	if(zap_zap != null) {
                    		configs_globais.setZap_zap(zap_zap);
                    		
                    		if(nuvem != null) {
                    			configs_globais.setNuvem(nuvem);
                    			configs_globais.setBaseDados(cad);
                                DadosGlobais dados = DadosGlobais.getInstance();

                                if(pasta_romaneio != null) {
                                	configs_globais.setPasta_romaneios(pasta_romaneio);
                                	
                                	dados.setConfigs_globais(configs_globais);	 
                                	
                                	if(ip_relogio != null) {
                                    	configs_globais.setIp_relogio(ip_relogio);

                                    	dados.setConfigs_globais(configs_globais);	 
                               			retorno_positivo = true;

                                	}
                                }else
                        			return false;
                   			 
                    		}else
                    			return false;
                    	}
                    	
                    
                    }
                    else {
                    	return false;
                    }
			 }else {
					retorno_positivo = false;

			 }
		}else {
			retorno_positivo = false;

		}
		
		return retorno_positivo;
		
	}
	
	
	
	
	
}
