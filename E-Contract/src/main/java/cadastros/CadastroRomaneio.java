package main.java.cadastros;

import java.util.Date;

public class CadastroRomaneio {

	
	double umidade, impureza, ardidos, avariados;
	double umidade2, impureza2, ardidos2, avariados2;

	String classificador;
	
	public double getUmidade2() {
		return umidade2;
	}

	public void setUmidade2(double umidade2) {
		this.umidade2 = umidade2;
	}

	public double getImpureza2() {
		return impureza2;
	}

	public void setImpureza2(double impureza2) {
		this.impureza2 = impureza2;
	}

	public double getArdidos2() {
		return ardidos2;
	}

	public void setArdidos2(double ardidos2) {
		this.ardidos2 = ardidos2;
	}

	public double getAvariados2() {
		return avariados2;
	}

	public void setAvariados2(double avariados2) {
		this.avariados2 = avariados2;
	}

	public String getClassificador() {
		return classificador;
	}

	public void setClassificador(String classificador) {
		this.classificador = classificador;
	}

	double peso_bruto, tara, peso_liquido, peso_liquido_sem_descontos, despesa_recepcao;
	
	String doc_entrada, amostra, silo, transgenia;
	
	
	public double getDespesa_recepcao() {
		return despesa_recepcao;
	}

	public void setDespesa_recepcao(double despesa_recepcao) {
		this.despesa_recepcao = despesa_recepcao;
	}

	public String getDoc_entrada() {
		return doc_entrada;
	}

	public void setDoc_entrada(String doc_entrada) {
		this.doc_entrada = doc_entrada;
	}

	public String getAmostra() {
		return amostra;
	}

	public void setAmostra(String amostra) {
		this.amostra = amostra;
	}

	public String getSilo() {
		return silo;
	}

	public void setSilo(String silo) {
		this.silo = silo;
	}

	public String getTransgenia() {
		return transgenia;
	}

	public void setTransgenia(String transgenia) {
		this.transgenia = transgenia;
	}

	public double getPeso_liquido_sem_descontos() {
		return peso_liquido_sem_descontos;
	}

	public void setPeso_liquido_sem_descontos(double peso_liquido_sem_descontos) {
		this.peso_liquido_sem_descontos = peso_liquido_sem_descontos;
	}

	private CadastroCliente motorista;
	
	private String cfop, descricao_cfop, operacao;
	private String nome_motorista, cpf_motorista, placa;
	private Date  data;
    private int numero_romaneio, id_romaneio;
	private CadastroSafra safra;
	
	private Double peso_desconto_umidade, peso_desconto_impureza, peso_desconto_avariados, peso_desconto_total;
	public Double getPeso_desconto_umidade() {
		return peso_desconto_umidade;
	}

	public void setPeso_desconto_umidade(Double peso_desconto_umidade) {
		this.peso_desconto_umidade = peso_desconto_umidade;
	}

	public Double getPeso_desconto_impureza() {
		return peso_desconto_impureza;
	}

	public void setPeso_desconto_impureza(Double peso_desconto_impureza) {
		this.peso_desconto_impureza = peso_desconto_impureza;
	}

	public Double getPeso_desconto_avariados() {
		return peso_desconto_avariados;
	}

	public void setPeso_desconto_avariados(Double peso_desconto_avariados) {
		this.peso_desconto_avariados = peso_desconto_avariados;
	}

	public Double getPeso_desconto_total() {
		return peso_desconto_total;
	}

	public void setPeso_desconto_total(Double peso_desconto_total) {
		this.peso_desconto_total = peso_desconto_total;
	}

	public String getNome_motorista() {
		return nome_motorista;
	}

	public void setNome_motorista(String nome_motorista) {
		this.nome_motorista = nome_motorista;
	}

	public String getCpf_motorista() {
		return cpf_motorista;
	}

	public void setCpf_motorista(String cpf_motorista) {
		this.cpf_motorista = cpf_motorista;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	private CadastroProduto produto;
	private CadastroCliente remetente, destinatario;
	private String caminho_arquivo;
   
	public int getId_romaneio() {
		return id_romaneio;
	}

	public void setId_romaneio(int id_romaneio) {
		this.id_romaneio = id_romaneio;
	}


	public String getCaminho_arquivo() {
		return caminho_arquivo;
	}

	public void setCaminho_arquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}

	

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getDescricao_cfop() {
		return descricao_cfop;
	}

	public void setDescricao_cfop(String descricao_cfop) {
		this.descricao_cfop = descricao_cfop;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getNumero_romaneio() {
		return numero_romaneio;
	}

	public void setNumero_romaneio(int numero_romaneio) {
		this.numero_romaneio = numero_romaneio;
	}

	public CadastroSafra getSafra() {
		return safra;
	}

	public void setSafra(CadastroSafra safra) {
		this.safra = safra;
	}

	public CadastroProduto getProduto() {
		return produto;
	}

	public void setProduto(CadastroProduto produto) {
		this.produto = produto;
	}

	public CadastroCliente getRemetente() {
		return remetente;
	}

	public void setRemetente(CadastroCliente remetente) {
		this.remetente = remetente;
	}

	public CadastroCliente getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(CadastroCliente destinatario) {
		this.destinatario = destinatario;
	}

	public double getUmidade() {
		return umidade;
	}

	public void setUmidade(double umidade) {
		this.umidade = umidade;
	}

	public double getInpureza() {
		return impureza;
	}

	public void setInpureza(double inpureza) {
		this.impureza = inpureza;
	}

	public double getArdidos() {
		return ardidos;
	}

	public void setArdidos(double ardidos) {
		this.ardidos = ardidos;
	}

	public double getAvariados() {
		return avariados;
	}

	public void setAvariados(double avariados) {
		this.avariados = avariados;
	}

	public double getPeso_bruto() {
		return peso_bruto;
	}

	public void setPeso_bruto(double peso_bruto) {
		this.peso_bruto = peso_bruto;
	}

	public double getTara() {
		return tara;
	}

	public void setTara(double tara) {
		this.tara = tara;
	}

	public double getPeso_liquido() {
		return peso_liquido;
	}

	public void setPeso_liquido(double peso_liquido) {
		this.peso_liquido = peso_liquido;
	}

	public CadastroCliente getMotorista() {
		return motorista;
	}

	public void setMotorista(CadastroCliente motorista) {
		this.motorista = motorista;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
