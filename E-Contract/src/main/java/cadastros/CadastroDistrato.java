package main.java.cadastros;
public class CadastroDistrato {

	
	private int id_distrato , status, id_contrato_pai;
	private String texto,  data, nome_arquivo;
	public int getId_distrato() {
		return id_distrato;
	}
	public void setId_distrato(int id_distrato) {
		this.id_distrato = id_distrato;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId_contrato_pai() {
		return id_contrato_pai;
	}
	public void setId_contrato_pai(int id_contrato_pai) {
		this.id_contrato_pai = id_contrato_pai;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNome_arquivo() {
		return nome_arquivo;
	}
	public void setNome_arquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}
	
	
	 
	
	
	
}
