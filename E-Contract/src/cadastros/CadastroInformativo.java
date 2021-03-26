package cadastros;

public class CadastroInformativo {

	
	private int id_contrato, id_usuario, id_informativo;
    private String mensagem, hora_completa;
	
	public int getId_informativo() {
		return id_informativo;
	}
	


	public String getHora_completa() {
		return hora_completa;
	}



	public void setHora_completa(String hora_completa) {
		this.hora_completa = hora_completa;
	}



	public void setId_informativo(int id_informativo) {
		this.id_informativo = id_informativo;
	}

	public int getId_contrato() {
		return id_contrato;
	}

	public void setId_contrato(int id_contrato) {
		this.id_contrato = id_contrato;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	
	
	
	
}
