package main.java.cadastros;

public class CadastroDocumento {

	
	int id_documento;
	int id_contrato_pai, tipo, id_pai, id_cliente, id_lancamento, id_ib, id_funcionario;
	public int getId_funcionario() {
		return id_funcionario;
	}
	public void setId_funcionario(int id_funcionario) {
		this.id_funcionario = id_funcionario;
	}
	public int getId_ib() {
		return id_ib;
	}
	public void setId_ib(int id_ib) {
		this.id_ib = id_ib;
	}
	public int getId_lancamento() {
		return id_lancamento;
	}
	public void setId_lancamento(int id_lancamento) {
		this.id_lancamento = id_lancamento;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getId_pai() {
		return id_pai;
	}
	public void setId_pai(int id_pai) {
		this.id_pai = id_pai;
	}
	private String nome, descricao, nome_arquivo;
	public int getId_documento() {
		return id_documento;
	}
	public void setId_documento(int id_documento) {
		this.id_documento = id_documento;
	}
	public int getId_contrato_pai() {
		return id_contrato_pai;
	}
	public void setId_contrato_pai(int id_contrato_pai) {
		this.id_contrato_pai = id_contrato_pai;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome_arquivo() {
		return nome_arquivo;
	}
	public void setNome_arquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}

	
	
	
	
}
