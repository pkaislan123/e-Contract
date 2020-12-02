package cadastros;

public class CadastroModelo {

	String nome_modelo;
	String descricao_modelo;
	int id_modelo;
    byte[] arquivo;
	public String getNome_modelo() {
		return nome_modelo;
	}
	public void setNome_modelo(String nome_modelo) {
		this.nome_modelo = nome_modelo;
	}
	public String getDescricao_modelo() {
		return descricao_modelo;
	}
	public void setDescricao_modelo(String descricao_modelo) {
		this.descricao_modelo = descricao_modelo;
	}
	public int getId_modelo() {
		return id_modelo;
	}
	public void setId_modelo(int id_modelo) {
		this.id_modelo = id_modelo;
	}
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
    
    
	
	
}
