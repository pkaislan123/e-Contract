package cadastros;

import java.util.ArrayList;

public class CadastroCliente {

	private int id;
	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}




	private String apelido;
    private int armazem;
	
	
	public int getArmazem() {
		return armazem;
	}





	public void setArmazem(int armazem) {
		this.armazem = armazem;
	}




	private int  tipo_pessoa;
	
	public int getTipo_pessoa() {
		return tipo_pessoa;
	}





	public void setTipo_pessoa(int tipo_pessoa) {
		this.tipo_pessoa = tipo_pessoa;
	}




	//dados de pessoa juridica
	private String razao_social;
	private String nome_fantaia;
	private String cnpj;
	private String  descricao;
	private String at_primaria;
	private String at_secundaria;
	
	
	//dados de pessoas fisicas
	private String cpf;
	private String nome_empresarial;
	private String nome;
	private String sobrenome;
	private String nascimento;
	private String  rg;
	private String  ocupacao;
	private String porte;
	private String atividade;
	
	//dados de inscricao estadual
	private String ie;
	private String status;
	private String status_ie;
	
	
	public String getStatus_ie() {
		return status_ie;
	}





	public void setStatus_ie(String status_ie) {
		this.status_ie = status_ie;
	}




	//dados de endereco
	private String rua;
	private String bairro;
	private String cep;
	private String cidade;
	private String numero;
	private String uf;

	
	//dados bancarios
	private ArrayList<ContaBancaria> contas = new ArrayList<>();

	
	
	public ArrayList<ContaBancaria> getContas() {
		return contas;
	}





	public void setContas(ArrayList<ContaBancaria> contas) {
		this.contas = contas;
	}




	//dados sefaz
	private String tipo_identificacao;
	private String identificacao_sefaz;
	private String cpf_responsavel;
	private String senha;
	
	//array de cliente
	private ArrayList<Contato> contatos = new ArrayList<>();
	

	
	
	
	



	public String getApelido() {
		return apelido;
	}





	public void setApelido(String apelido) {
		this.apelido = apelido;
	}





	public String getRazao_social() {
		return razao_social;
	}





	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}





	public String getNome_fantaia() {
		return nome_fantaia;
	}





	public void setNome_fantaia(String nome_fantaia) {
		this.nome_fantaia = nome_fantaia;
	}





	public String getCnpj() {
		return cnpj;
	}





	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}





	public String getDescricao() {
		return descricao;
	}





	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}





	public String getAt_primaria() {
		return at_primaria;
	}





	public void setAt_primaria(String at_primaria) {
		this.at_primaria = at_primaria;
	}





	public String getAt_secundaria() {
		return at_secundaria;
	}





	public void setAt_secundaria(String at_secundaria) {
		this.at_secundaria = at_secundaria;
	}





	public String getCpf() {
		return cpf;
	}





	public void setCpf(String cpf) {
		this.cpf = cpf;
	}





	public String getNome_empresarial() {
		return nome_empresarial;
	}





	public void setNome_empresarial(String nome_empresarial) {
		this.nome_empresarial = nome_empresarial;
	}





	public String getNome() {
		return nome;
	}





	public void setNome(String nome) {
		this.nome = nome;
	}





	public String getSobrenome() {
		return sobrenome;
	}





	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}





	public String getNascimento() {
		return nascimento;
	}





	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}





	public String getRg() {
		return rg;
	}





	public void setRg(String rg) {
		this.rg = rg;
	}





	public String getOcupacao() {
		return ocupacao;
	}





	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}





	public String getPorte() {
		return porte;
	}





	public void setPorte(String porte) {
		this.porte = porte;
	}





	public String getAtividade() {
		return atividade;
	}





	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}





	public String getIe() {
		return ie;
	}





	public void setIe(String ie) {
		this.ie = ie;
	}





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public String getRua() {
		return rua;
	}





	public void setRua(String rua) {
		this.rua = rua;
	}





	public String getBairro() {
		return bairro;
	}





	public void setBairro(String bairro) {
		this.bairro = bairro;
	}





	public String getCep() {
		return cep;
	}





	public void setCep(String cep) {
		this.cep = cep;
	}





	public String getCidade() {
		return cidade;
	}





	public void setCidade(String cidade) {
		this.cidade = cidade;
	}





	public String getNumero() {
		return numero;
	}





	public void setNumero(String numero) {
		this.numero = numero;
	}





	public String getUf() {
		return uf;
	}





	public void setUf(String uf) {
		this.uf = uf;
	}








	public String getTipo_identificacao() {
		return tipo_identificacao;
	}





	public void setTipo_identificacao(String tipo_identificacao) {
		this.tipo_identificacao = tipo_identificacao;
	}





	public String getIdentificacao_sefaz() {
		return identificacao_sefaz;
	}





	public void setIdentificacao_sefaz(String identificacao_sefaz) {
		this.identificacao_sefaz = identificacao_sefaz;
	}





	public String getCpf_responsavel() {
		return cpf_responsavel;
	}





	public void setCpf_responsavel(String cpf_responsavel) {
		this.cpf_responsavel = cpf_responsavel;
	}





	public String getSenha() {
		return senha;
	}





	public void setSenha(String senha) {
		this.senha = senha;
	}





	public ArrayList<Contato> getContatos() {
		return contatos;
	}





	public void setContatos(ArrayList<Contato> contatos) {
		this.contatos = contatos;
	}
	
	
	








	






	
	
}


