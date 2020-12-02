package manipular;

public class ConfiguracoesGlobais {

	
	private String caminhoPastaRaiz = "";
	private int codigoSequencial;
	
	public ConfiguracoesGlobais() {
		
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
