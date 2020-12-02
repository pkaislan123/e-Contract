package tratamento_proprio;

import java.io.File;

import cadastros.CadastroLogin;
import manipular.ManipularTxt;
import outros.DadosGlobais;
import outros.GetData;

public class Log {
	
   //Classe que implementa logger
	
	//Necessario usuario logado no momento 
	//Necessario tempo corrente
	//necessario erro
	private GetData dados_temporais;
	private CadastroLogin login;
	ManipularTxt arquivos = new ManipularTxt();

	public Log() {
		
		dados_temporais = new GetData();
		
		
	}
	
	
	public boolean registrarLogDiario(String nivel, String msg) {
		getDadosGlobais();
		 if(criarDiretorioLogs()) {
			 File arquivo_para_escrever = criarArquivoLogs();
			 if( arquivo_para_escrever != null) {
				 
				 String mensagemCompleta =  "Level:" + nivel;
				 mensagemCompleta += ("Msg:" + msg) ;
				 mensagemCompleta +=  ("Hora:" + dados_temporais.getHoraLog()) ;
				try { 
				 mensagemCompleta +=  ("user:" + login.getLogin() + "/" );
				 mensagemCompleta +=  ("user:" + login.getNome());
				}catch(Exception v)
				{
					 mensagemCompleta +=  ("user: não logado" );
				}
				 
				 escreverArquivoLog(arquivo_para_escrever, mensagemCompleta);
				 registrarLogGlobal(nivel, msg);
				 
				 return true;
			 }else {
				 return false;
			 }
		 }else {
			 return false;
		 }
	}
	
	
	public boolean registrarLogGlobal(String nivel, String msg) {
		getDadosGlobais();

		 if(criarDiretorioLogs()) {
			 File arquivo_para_escrever = criarArquivoLogsGlobais();
			 if( arquivo_para_escrever != null) {
				 try {
				 escreverArquivoLog(arquivo_para_escrever, "Level:" + nivel + "Msg:" + msg + "Data:" + dados_temporais.getDataLog() + "Hora:" 
						 + dados_temporais.getHoraLog() + "user:" + login.getLogin() + "/" + login.getNome());
				 }catch(Exception e) {
					 escreverArquivoLog(arquivo_para_escrever, "Level:" + nivel + "Msg:" + msg + "Data:" + dados_temporais.getDataLog() + "Hora:" 
							 + dados_temporais.getHoraLog() + "user: não logado");
				 }
				 
				 return true;
			 }else {
				 return false;
			 }
		 }else {
			 return false;
		 }
	}
	
	public boolean criarDiretorioLogs() {


		return arquivos.criarDiretorio("C:\\ProgramData\\E-Contract\\logs");
		
	}
	
	public File criarArquivoLogs() {
		String nomeArquivo = ("logs_" + dados_temporais.getData()).replaceAll("/", "-");
		return arquivos.criarArquivoLog("C:\\ProgramData\\E-Contract\\logs", nomeArquivo.toString(), "txt");

	}
	
	public File criarArquivoLogsGlobais() {
		String nomeArquivo = ("logsGlobais");
		return arquivos.criarArquivoLog("C:\\ProgramData\\E-Contract\\logs", nomeArquivo.toString(), "txt");

	}
	
	public boolean escreverArquivoLog(File file, String mensagem) {
		return arquivos.escreverArquivo(file, mensagem);
	}

	
	public void getDadosGlobais() {
		DadosGlobais dados = DadosGlobais.getInstance();
		login = dados.getLogin();
	}
}
