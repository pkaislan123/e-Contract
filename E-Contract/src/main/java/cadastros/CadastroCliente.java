package main.java.cadastros;

import java.util.ArrayList;


public class CadastroCliente {

	


	private int id;
	private int transportador;
	
	public int getTransportador() {
		return transportador;
	}





	public void setTransportador(int transportador) {
		this.transportador = transportador;
	}





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
	
	
	


	String rntrc, status_cadastro;
	ArrayList<Veiculo> veiculos;
	
	
	
	
	
	

	

    public String getRntrc() {
		return rntrc;
	}









	public void setRntrc(String rntrc) {
		this.rntrc = rntrc;
	}









	public String getStatus_cadastro() {
		return status_cadastro;
	}









	public void setStatus_cadastro(String status_cadastro) {
		this.status_cadastro = status_cadastro;
	}









	public ArrayList<Veiculo> getVeiculos() {
		return veiculos;
	}









	public void setVeiculos(ArrayList<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}














static public class Veiculo{
		
		int id_veiculo;
    	String registro_trator, placa_trator, eixos_trator, tipo_trator, cidade_trator, uf_trator;
 		String registro_reboque1, placa_reboque1, eixos_reboque1, tipo_reboque1, cidade_reboque1, uf_reboque1;
 		String registro_reboque2, placa_reboque2, eixos_reboque2, tipo_reboque2, cidade_reboque2, uf_reboque2;
		
 		public int getId_veiculo() {
			return id_veiculo;
		}
		public void setId_veiculo(int id_veiculo) {
			this.id_veiculo = id_veiculo;
		}
		public String getRegistro_trator() {
			return registro_trator;
		}
		public void setRegistro_trator(String registro_trator) {
			this.registro_trator = registro_trator;
		}
		public String getPlaca_trator() {
			return placa_trator;
		}
		public void setPlaca_trator(String placa_trator) {
			this.placa_trator = placa_trator;
		}
		public String getEixos_trator() {
			return eixos_trator;
		}
		public void setEixos_trator(String eixos_trator) {
			this.eixos_trator = eixos_trator;
		}
		public String getTipo_trator() {
			return tipo_trator;
		}
		public void setTipo_trator(String tipo_trator) {
			this.tipo_trator = tipo_trator;
		}
		public String getCidade_trator() {
			return cidade_trator;
		}
		public void setCidade_trator(String cidade_trator) {
			this.cidade_trator = cidade_trator;
		}
		public String getUf_trator() {
			return uf_trator;
		}
		public void setUf_trator(String uf_trator) {
			this.uf_trator = uf_trator;
		}
		public String getRegistro_reboque1() {
			return registro_reboque1;
		}
		public void setRegistro_reboque1(String registro_reboque1) {
			this.registro_reboque1 = registro_reboque1;
		}
		public String getPlaca_reboque1() {
			return placa_reboque1;
		}
		public void setPlaca_reboque1(String placa_reboque1) {
			this.placa_reboque1 = placa_reboque1;
		}
		public String getEixos_reboque1() {
			return eixos_reboque1;
		}
		public void setEixos_reboque1(String eixos_reboque1) {
			this.eixos_reboque1 = eixos_reboque1;
		}
		public String getTipo_reboque1() {
			return tipo_reboque1;
		}
		public void setTipo_reboque1(String tipo_reboque1) {
			this.tipo_reboque1 = tipo_reboque1;
		}
		public String getCidade_reboque1() {
			return cidade_reboque1;
		}
		public void setCidade_reboque1(String cidade_reboque1) {
			this.cidade_reboque1 = cidade_reboque1;
		}
		public String getUf_reboque1() {
			return uf_reboque1;
		}
		public void setUf_reboque1(String uf_reboque1) {
			this.uf_reboque1 = uf_reboque1;
		}
		public String getRegistro_reboque2() {
			return registro_reboque2;
		}
		public void setRegistro_reboque2(String registro_reboque2) {
			this.registro_reboque2 = registro_reboque2;
		}
		public String getPlaca_reboque2() {
			return placa_reboque2;
		}
		public void setPlaca_reboque2(String placa_reboque2) {
			this.placa_reboque2 = placa_reboque2;
		}
		public String getEixos_reboque2() {
			return eixos_reboque2;
		}
		public void setEixos_reboque2(String eixos_reboque2) {
			this.eixos_reboque2 = eixos_reboque2;
		}
		public String getTipo_reboque2() {
			return tipo_reboque2;
		}
		public void setTipo_reboque2(String tipo_reboque2) {
			this.tipo_reboque2 = tipo_reboque2;
		}
		public String getCidade_reboque2() {
			return cidade_reboque2;
		}
		public void setCidade_reboque2(String cidade_reboque2) {
			this.cidade_reboque2 = cidade_reboque2;
		}
		public String getUf_reboque2() {
			return uf_reboque2;
		}
		public void setUf_reboque2(String uf_reboque2) {
			this.uf_reboque2 = uf_reboque2;
		}
 		
 		
    	
	}

	





	
	
}


