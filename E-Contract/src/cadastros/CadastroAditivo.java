package cadastros;

public class CadastroAditivo {

	
	private int id_aditivo , status, id_contrato_pai;
	private String texto,  data, nome_arquivo;
	public String getNome_arquivo() {
		return nome_arquivo;
	}
	public void setNome_arquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}
	public int getId_aditivo() {
		return id_aditivo;
	}
	public void setId_aditivo(int id_aditivo) {
		this.id_aditivo = id_aditivo;
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
	
	 
	
	
	
}
