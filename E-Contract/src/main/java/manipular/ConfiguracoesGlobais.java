package main.java.manipular;

import main.java.cadastros.CadastroBaseArquivos;
import main.java.cadastros.CadastroBaseDados;
import main.java.cadastros.CadastroNuvem;
import main.java.cadastros.CadastroZapMessenger;

public class ConfiguracoesGlobais {

	
	private CadastroBaseArquivos servidor_arquivos;
	private int codigoSequencial;
	private CadastroBaseDados baseDados;
	private CadastroZapMessenger zap_zap;
	private CadastroNuvem nuvem;
	private String pasta_romaneios;
	private String ip_relogio;
	
	
	
	
	
	
	public String getIp_relogio() {
		return ip_relogio;
	}





	public void setIp_relogio(String ip_relogio) {
		this.ip_relogio = ip_relogio;
	}





	public String getPasta_romaneios() {
		return pasta_romaneios;
	}





	public void setPasta_romaneios(String pasta_romaneios) {
		this.pasta_romaneios = pasta_romaneios;
	}





	public CadastroNuvem getNuvem() {
		return nuvem;
	}





	public void setNuvem(CadastroNuvem nuvem) {
		this.nuvem = nuvem;
	}





	public ConfiguracoesGlobais() {
		
	}
	
	
	
	

   public String getServidorUnidade() {
		return   "\\\\" + getServidor_arquivos().getServidor() + "\\" + getServidor_arquivos().getUnidade() + "\\";

   }


	public CadastroBaseDados getBaseDados() {
		return baseDados;
	}


	public void setBaseDados(CadastroBaseDados baseDados) {
		this.baseDados = baseDados;
	}








	public void setCodigoSequencial(int codigo) {
		this.codigoSequencial = codigo;
	}
	
	public int getCodigoSequencial() {
		return this.codigoSequencial;
	}








	public CadastroBaseArquivos getServidor_arquivos() {
		return servidor_arquivos;
	}








	public CadastroZapMessenger getZap_zap() {
		return zap_zap;
	}





	public void setZap_zap(CadastroZapMessenger zap_zap) {
		this.zap_zap = zap_zap;
	}





	public void setServidor_arquivos(CadastroBaseArquivos servidor_arquivos) {
		this.servidor_arquivos = servidor_arquivos;
	}
	
	
	
}
