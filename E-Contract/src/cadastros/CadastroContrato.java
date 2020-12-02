package cadastros;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CadastroContrato {

	
	
	
	private CadastroCliente [] compradores ;
	private  CadastroCliente [] vendedores ;
	private CadastroCliente [] corretores;
	private BigDecimal valor_a_pagar;
	private double quantidade, valor_produto;
	private String produto, data_contrato, codigo, data_entrega, ctc, ctv, safra, medida, status_contrato;
	private int assinatura_comprador, assinatura_vendedor;
	
	private CadastroProduto modelo_produto;
	public String getStatus_contrato() {
		return status_contrato;
	}


	public void setStatus_contrato(String status_contrato) {
		this.status_contrato = status_contrato;
	}


	public CadastroProduto getModelo_produto() {
		return modelo_produto;
	}


	public void setModelo_produto(CadastroProduto modelo_produto) {
		this.modelo_produto = modelo_produto;
	}


	public CadastroSafra getModelo_safra() {
		return modelo_safra;
	}


	public void setModelo_safra(CadastroSafra modelo_safra) {
		this.modelo_safra = modelo_safra;
	}
	private CadastroSafra modelo_safra;
	
	
    private String clausulas_adicionais [];

	
	public  CadastroContrato()
	{
		compradores = new CadastroCliente[1];
		vendedores = new CadastroCliente[2];
		corretores = new CadastroCliente[2];
		clausulas_adicionais = new String[5];
		
		for(int i = 0; i < clausulas_adicionais.length; i++)
	     {
			clausulas_adicionais[i] = null;
	     }
		
		 for(int i = 0; i < corretores.length; i++)
	     {
	    	 corretores[i] = null;
	     }
		 for(int i = 0; i < compradores.length; i++)
	     {
	    	 compradores[i] = null;
	     }
		 for(int i = 0; i < vendedores.length; i++)
	     {
	    	 vendedores[i] = null;
	     }
	     
		
	    
		
	}
	
	
	public String [] listaClausulasAdicionais()
	{
		return clausulas_adicionais;
	}
	
	public void adicionar_clausula(int posicao, String texto)
	{
		clausulas_adicionais[posicao] = texto;
	}
	
	public CadastroCliente [] listaCorretores()
	{
		return corretores;
	}
	
	public CadastroCliente [] listaCompradores()
	{
		return compradores;
	}
	
	public CadastroCliente [] listaVendedores()
	{
		return vendedores;
	}
	
	public void adicionarComprador(int posicao, CadastroCliente comprador)
	{
		compradores[posicao] = comprador;
	}
	
	public void adicionarVendedor(int posicao, CadastroCliente vendedor)
	{
		vendedores[posicao] = vendedor;
	}
	
	public void adicionarCorretor(int posicao, CadastroCliente corretor)
	{
		corretores[posicao] = corretor;
	}
	
	
	
	
	public String getCtc() {
		return ctc;
	}
	public void setCtc(String ctc) {
		this.ctc = ctc;
	}
	public String getCtv() {
		return ctv;
	}
	public void setCtv(String ctv) {
		this.ctv = ctv;
	}
	public String getData_entrega() {
		return data_entrega;
	}
	public void setData_entrega(String data_entrega) {
		this.data_entrega = data_entrega;
	}


	public String getSafra() {
		return safra;
	}
	public String getData_contrato() {
		return data_contrato;
	}
	public void setData_contrato(String data_contrato) {
		this.data_contrato = data_contrato;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setSafra(String safra) {
		this.safra = safra;
	}
	public double getValor_produto() {
		return valor_produto;
	}
	public void setValor_produto(double valor_produto) {
		this.valor_produto = valor_produto;
	}
	public BigDecimal getValor_a_pagar() {
		return valor_a_pagar;
	}
	public void setValor_a_pagar(BigDecimal valor_a_pagar) {
		this.valor_a_pagar = valor_a_pagar;
	}


	public String getData_pagamento() {
		return data_pagamento;
	}
	public void setData_pagamento(String data_pagamento) {
		this.data_pagamento = data_pagamento;
	}
	public String getLocal_retirada() {
		return local_retirada;
	}
	public void setLocal_retirada(String local_retirada) {
		this.local_retirada = local_retirada;
	}
	
	
	public String getMedida() {
		return medida;
	}
	
	
	
	public void setMedida(String medida) {
		this.medida = medida;
	}
	private String data_pagamento;
	private String local_retirada;
	
	/*
	 * produto 16 1
safra 17 1
quantidade 17 5
tipo 17 6
valor do protudo 22 2
valor a pagar: 22 7
data pagamento: 23 2
local retirada: 24 2


	 */
	
	
	
	
	
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	
	
	private ArrayList<CadastroPagamento> pagamentos = new ArrayList<>();
	
	
	
	public ArrayList<CadastroPagamento> getPagamentos() {
		return pagamentos;
	}
	public void setPagamentos(ArrayList<CadastroPagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}



	public static class CadastroPagamento
	{
		
		ContaBancaria conta;
		BigDecimal valor;
		String data_pagamento;
		public ContaBancaria getConta() {
			return conta;
		}
		public void setConta(ContaBancaria conta) {
			this.conta = conta;
		}
		public BigDecimal getValor() {
			return valor;
		}
		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}
		public String getData_pagamento() {
			return data_pagamento;
		}
		public void setData_pagamento(String data_pagamento) {
			this.data_pagamento = data_pagamento;
		}
		
		
		
		
	}
	
	
}
