package cadastros;

public class CadastroAviso {

	
	/*Exemplo
	 * 
	 * Tipo: Aviso / Falha
	 * Setor: Importação Automatica de Romaneio
	 * Mensagem: Não foi possivel importar o romaneio codigo xxxx, cadastre o depositante 
	 * 
	 * 
	 */
	
	String tipo, setor, mensagem;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
}
