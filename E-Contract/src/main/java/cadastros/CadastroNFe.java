package main.java.cadastros;

import java.time.LocalDate;
import java.util.Date;

public class CadastroNFe {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String nfe;
	private String medida;
	private String serie;
	private String nome_remetente;
	private String inscricao_remetente;
	private String protocolo;
	private Date  data;
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getMedida() {
		return medida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}
	private String natureza;
	private String nome_destinatario;
	private String inscricao_destinatario;
	private String produto;
	private String quantidade;
	private String valor;
	private String caminho_arquivo;
	private Double quantidade_double;
	private CadastroContrato contrato;
	
	
	public CadastroContrato getContrato() {
		return contrato;
	}
	public void setContrato(CadastroContrato contrato) {
		this.contrato = contrato;
	}
	public Double getQuantidade_double() {
		return quantidade_double;
	}
	public void setQuantidade_double(Double quantidade_double) {
		this.quantidade_double = quantidade_double;
	}
	public String getNfe() {
		return nfe;
	}
	public void setNfe(String nfe) {
		this.nfe = nfe;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNome_remetente() {
		return nome_remetente;
	}
	public void setNome_remetente(String nome_remetente) {
		this.nome_remetente = nome_remetente;
	}
	public String getInscricao_remetente() {
		return inscricao_remetente;
	}
	public void setInscricao_remetente(String inscricao_remetente) {
		this.inscricao_remetente = inscricao_remetente;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	


	
	
	public String getNatureza() {
		return natureza;
	}
	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	public String getNome_destinatario() {
		return nome_destinatario;
	}
	public void setNome_destinatario(String nome_destinatario) {
		this.nome_destinatario = nome_destinatario;
	}
	public String getInscricao_destinatario() {
		return inscricao_destinatario;
	}
	public void setInscricao_destinatario(String inscricao_destinatario) {
		this.inscricao_destinatario = inscricao_destinatario;
	}
	public String getProduto() {
		return produto;
	}
	public String getCaminho_arquivo() {
		return caminho_arquivo;
	}
	public void setCaminho_arquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	
}
