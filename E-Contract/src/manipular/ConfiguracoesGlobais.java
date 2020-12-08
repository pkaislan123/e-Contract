package manipular;

import cadastros.CadastroBaseArquivos;
import cadastros.CadastroBaseDados;

public class ConfiguracoesGlobais {

	
	private CadastroBaseArquivos servidor_arquivos;
	private int codigoSequencial;
	private CadastroBaseDados baseDados;
	
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








	public void setServidor_arquivos(CadastroBaseArquivos servidor_arquivos) {
		this.servidor_arquivos = servidor_arquivos;
	}
	
	
	
}
