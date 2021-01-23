package cadastros;

public class CadastroPontuacao {

	private   int id_pontuacao, id_contrato, id_cliente, pontos, motivo;
	public int getMotivo() {
		return motivo;
	}
	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}
	private  String descricao;
	public int getId_pontuacao() {
		return id_pontuacao;
	}
	public void setId_pontuacao(int id_pontuacao) {
		this.id_pontuacao = id_pontuacao;
	}
	public int getId_contrato() {
		return id_contrato;
	}
	public void setId_contrato(int id_contrato) {
		this.id_contrato = id_contrato;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
	
}
