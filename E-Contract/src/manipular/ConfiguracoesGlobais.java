package manipular;

import cadastros.CadastroBaseDados;

public class ConfiguracoesGlobais {

	
	private String caminhoPastaRaiz = "";
	private int codigoSequencial;
	private CadastroBaseDados baseDados;
	
	public ConfiguracoesGlobais() {
		
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
	
	
	public void setRaiz(String path_raiz)
	{
		this.caminhoPastaRaiz = path_raiz;
	}
	
	
	public String getRaiz() {
		return caminhoPastaRaiz;
	}
}
