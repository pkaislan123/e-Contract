package cadastros;

public class ContaBancaria
{
	private String cpf_titular;
	private String banco;
	private String codigo;
	private String agencia;
	private String conta;
	private int id_conta;
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getId_conta() {
		return id_conta;
	}
	public void setId_conta(int id_conta) {
		this.id_conta = id_conta;
	}
	public String getCpf_titular() {
		return cpf_titular;
	}
	public void setCpf_titular(String cpf_titular) {
		this.cpf_titular = cpf_titular;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	
	
	
	
}