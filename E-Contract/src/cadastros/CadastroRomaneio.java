package cadastros;

import java.util.Date;

public class CadastroRomaneio {

	
	
	private Date  data;
    private int numero_romaneio;
	private CadastroSafra safra;
	private CadastroProduto produto;
	private CadastroCliente remetente, destinatario;
	private String caminho_arquivo;
	
	
	public String getCaminho_arquivo() {
		return caminho_arquivo;
	}

	public void setCaminho_arquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}

	double umidade, impureza, ardidos, avariados;
	
	double peso_bruto, tara, peso_liquido;
	
	private CadastroCliente motorista;

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
