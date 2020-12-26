package classesExtras;

import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroNFe;
import cadastros.CadastroProduto;

public class Carregamento {
	
	private String data;
	private CadastroContrato contrato_proprietario;
	
	public CadastroContrato getContrato_proprietario() {
		return contrato_proprietario;
	}
	public void setContrato_proprietario(CadastroContrato contrato_proprietario) {
		this.contrato_proprietario = contrato_proprietario;
	}
	private CadastroContrato contrato_destinado;
	private CadastroCliente cliente;
	private CadastroProduto produto;
	private CadastroNFe nfe;
	private CadastroCliente transportador;
	private CadastroCliente.Veiculo veiculo;
	private double peso_real;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}


	public CadastroContrato getContrato() {
		return contrato_destinado;
	}
	public void setContrato(CadastroContrato contrato) {
		this.contrato_destinado = contrato;
	}
	public CadastroCliente getCliente() {
		return cliente;
	}
	public void setCliente(CadastroCliente cliente) {
		this.cliente = cliente;
	}
	public CadastroProduto getProduto() {
		return produto;
	}
	public void setProduto(CadastroProduto produto) {
		this.produto = produto;
	}
	public CadastroNFe getNfe() {
		return nfe;
	}
	public void setNfe(CadastroNFe nfe) {
		this.nfe = nfe;
	}
	public CadastroCliente getTransportador() {
		return transportador;
	}
	public void setTransportador(CadastroCliente transportador) {
		this.transportador = transportador;
	}
	public CadastroCliente.Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(CadastroCliente.Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public double getPeso_real() {
		return peso_real;
	}
	public void setPeso_real(double peso_real) {
		this.peso_real = peso_real;
	}
	
	
	

}
