package cadastros;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CadastroContrato {

	private String texto_clausulas;
    private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas;
    
    private CadastroCliente cliente_retirada;
    
    

	public CadastroCliente getCliente_retirada() {
		return cliente_retirada;
	}



	public void setCliente_retirada(CadastroCliente cliente_retirada) {
		this.cliente_retirada = cliente_retirada;
	}



	public ArrayList<CadastroContrato.CadastroTarefa> getLista_tarefas() {
		return lista_tarefas;
	}



	public void setLista_tarefas(ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas) {
		this.lista_tarefas = lista_tarefas;
	}
	private String caminho_diretorio_contrato;



	public String getCaminho_diretorio_contrato() {
		return caminho_diretorio_contrato;
	}



	public void setCaminho_diretorio_contrato(String caminho_diretorio_contrato) {
		this.caminho_diretorio_contrato = caminho_diretorio_contrato;
	}



	public int getAssinatura_comprador() {
		return assinatura_comprador;
	}



	public void setAssinatura_comprador(int assinatura_comprador) {
		this.assinatura_comprador = assinatura_comprador;
	}



	public int getAssinatura_vendedor() {
		return assinatura_vendedor;
	}



	public void setAssinatura_vendedor(int assinatura_vendedor) {
		this.assinatura_vendedor = assinatura_vendedor;
	}



	public String getTexto_clausulas() {
		return texto_clausulas;
	}



	public void setTexto_clausulas(String texto_clausulas) {
		this.texto_clausulas = texto_clausulas;
	}
	private ArrayList<String> clausulas ;
	
	
	
	
	public ArrayList<String> getClausulas() {
		return clausulas;
	}



	public void setClausulas(ArrayList<String> clausulas) {
		this.clausulas = clausulas;
	}



	public CadastroCliente[] getCompradores() {
		return compradores;
	}

	

	public void setCompradores(CadastroCliente[] compradores) {
		this.compradores = compradores;
	}


	public CadastroCliente[] getVendedores() {
		return vendedores;
	}


	public void setVendedores(CadastroCliente[] vendedores) {
		this.vendedores = vendedores;
	}


	public CadastroCliente[] getCorretores() {
		return corretores;
	}


	public void setCorretores(CadastroCliente[] corretores) {
		this.corretores = corretores;
	}
	private CadastroCliente [] compradores ;
	private  CadastroCliente [] vendedores ;
	private CadastroCliente [] corretores;
	private BigDecimal valor_a_pagar , valor_comissao;
	
	
	public BigDecimal getValor_comissao() {
		return valor_comissao;
	}



	public void setValor_comissao(BigDecimal valor_comissao) {
		this.valor_comissao = valor_comissao;
	}



	public int getComissao() {
		return comissao;
	}







	public int getClausula_comissao() {
		return clausula_comissao;
	}



	public void setClausula_comissao(int clausula_comissao) {
		this.clausula_comissao = clausula_comissao;
	}



	public void setComissao(int comissao) {
		this.comissao = comissao;
	}
	private double quantidade, valor_produto;
	private String produto, data_contrato, codigo, data_entrega, ctc, ctv, safra, medida, caminho_arquivo, nome_arquivo;
	private int sub_contrato;
	private int comissao, clausula_comissao;
	
	
	public int getSub_contrato() {
		return sub_contrato;
	}



	public void setSub_contrato(int sub_contrato) {
		this.sub_contrato = sub_contrato;
	}



	public String getCaminho_arquivo() {
		return caminho_arquivo;
	}


	public String getNome_arquivo() {
		return nome_arquivo;
	}



	public void setNome_arquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}



	public void setCaminho_arquivo(String caminho_arquivo) {
		this.caminho_arquivo = caminho_arquivo;
	}
	private int assinatura_comprador, assinatura_vendedor, id, status_contrato;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	private String nomes_compradores, nomes_vendedores, nomes_corretores;
	
	public String getNomes_compradores() {
		return nomes_compradores;
	}


	public void setNomes_compradores(String nomes_compradores) {
		this.nomes_compradores = nomes_compradores;
	}


	public String getNomes_vendedores() {
		return nomes_vendedores;
	}


	public void setNomes_vendedores(String nomes_vendedores) {
		this.nomes_vendedores = nomes_vendedores;
	}


	public String getNomes_corretores() {
		return nomes_corretores;
	}


	public void setNomes_corretores(String nomes_corretores) {
		this.nomes_corretores = nomes_corretores;
	}
	private CadastroProduto modelo_produto;
	public int getStatus_contrato() {
		return status_contrato;
	}


	public void setStatus_contrato(int status_contrato) {
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
	
	
  
	
	public  CadastroContrato()
	{
		compradores = new CadastroCliente[1];
		vendedores = new CadastroCliente[2];
		corretores = new CadastroCliente[2];
	
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
	     
		 
		 clausulas = new ArrayList<>();
		
	    
		
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
		
		int id;
		int pagamento_adiantado;
		
		
		
		public int getPagamento_adiantado() {
			return pagamento_adiantado;
		}
		public void setPagamento_adiantado(int pagamento_adiantado) {
			this.pagamento_adiantado = pagamento_adiantado;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		ContaBancaria conta;
		BigDecimal valor = new BigDecimal("0");
		String valor_string;
		public String getValor_string() {
			return valor_string;
		}
		public void setValor_string(String valor_string) {
			this.valor_string = valor_string;
		}
		String data_pagamento;
		String descricao_pagamento;
		
		
		public String getDescricao_pagamento() {
			return descricao_pagamento;
		}
		public void setDescricao_pagamento(String descricao_pagamento) {
			this.descricao_pagamento = descricao_pagamento;
		}
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
	
	

	public static class CadastroTarefa
	{
		
		private int id_tarefa;
		private int status_tarefa;
		private String descricao_tarefa;
		private String mensagem;
		private String hora;
		private String data;
		private String nome_tarefa;
		private CadastroLogin criador;
		
		private String hora_agendada;
		private String data_agendada;
		private int prioridade;
		
		
		
		public String getHora_agendada() {
			return hora_agendada;
		}
		public void setHora_agendada(String hora_agendada) {
			this.hora_agendada = hora_agendada;
		}
		public String getData_agendada() {
			return data_agendada;
		}
		public void setData_agendada(String data_agendada) {
			this.data_agendada = data_agendada;
		}
		public int getPrioridade() {
			return prioridade;
		}
		public void setPrioridade(int prioridade) {
			this.prioridade = prioridade;
		}
		public CadastroLogin getCriador() {
			return criador;
		}
		public void setCriador(CadastroLogin criador) {
			this.criador = criador;
		}
		public CadastroLogin getExecutor() {
			return executor;
		}
		public void setExecutor(CadastroLogin executor) {
			this.executor = executor;
		}
		private CadastroLogin executor;
		
		
		
		public int getId_tarefa() {
			return id_tarefa;
		}
		public void setId_tarefa(int id_tarefa) {
			this.id_tarefa = id_tarefa;
		}
		
		
		public int getStatus_tarefa() {
			return status_tarefa;
		}
		public void setStatus_tarefa(int status_tarefa) {
			this.status_tarefa = status_tarefa;
		}
		public String getDescricao_tarefa() {
			return descricao_tarefa;
		}
		public void setDescricao_tarefa(String descricao_tarefa) {
			this.descricao_tarefa = descricao_tarefa;
		}
		public String getMensagem() {
			return mensagem;
		}
		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		public String getHora() {
			return hora;
		}
		public void setHora(String hora) {
			this.hora = hora;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public String getNome_tarefa() {
			return nome_tarefa;
		}
		public void setNome_tarefa(String nome_tarefa) {
			this.nome_tarefa = nome_tarefa;
		}
		
		
		
	}
	
	
	
	public static class Carregamento{
		
		int id_carregamento, id_cliente, id_transportador, id_veiculo, id_contrato, id_produto;
		String codigo_nota_fiscal, data;
		double peso_real_carga;
		
		
		
		public int getId_produto() {
			return id_produto;
		}
		public void setId_produto(int id_produto) {
			this.id_produto = id_produto;
		}
		public int getId_carregamento() {
			return id_carregamento;
		}
		public void setId_carregamento(int id_carregamento) {
			this.id_carregamento = id_carregamento;
		}
		public int getId_cliente() {
			return id_cliente;
		}
		public void setId_cliente(int id_cliente) {
			this.id_cliente = id_cliente;
		}
		public int getId_transportador() {
			return id_transportador;
		}
		public void setId_transportador(int id_transportador) {
			this.id_transportador = id_transportador;
		}
		public int getId_veiculo() {
			return id_veiculo;
		}
		public void setId_veiculo(int id_veiculo) {
			this.id_veiculo = id_veiculo;
		}
		public int getId_contrato() {
			return id_contrato;
		}
		public void setId_contrato(int id_contrato) {
			this.id_contrato = id_contrato;
		}
		public String getCodigo_nota_fiscal() {
			return codigo_nota_fiscal;
		}
		public void setCodigo_nota_fiscal(String codigo_nota_fiscal) {
			this.codigo_nota_fiscal = codigo_nota_fiscal;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public double getPeso_real_carga() {
			return peso_real_carga;
		}
		public void setPeso_real_carga(double peso_real_carga) {
			this.peso_real_carga = peso_real_carga;
		}
		
		
		
		
		
	}
	
	
	public static class CadastroPagamentoContratual{
		
		
		String data_pagamento;
		double valor_pagamento;
		int id_pagamento, id_depositante, id_conta_depositante, id_favorecido, id_conta_favorecido;
		public String getData_pagamento() {
			return data_pagamento;
		}
		public void setData_pagamento(String data_pagamento) {
			this.data_pagamento = data_pagamento;
		}
		public double getValor_pagamento() {
			return valor_pagamento;
		}
		public void setValor_pagamento(double valor_pagamento) {
			this.valor_pagamento = valor_pagamento;
		}
		public int getId_pagamento() {
			return id_pagamento;
		}
		public void setId_pagamento(int id_pagamento) {
			this.id_pagamento = id_pagamento;
		}
		public int getId_depositante() {
			return id_depositante;
		}
		public void setId_depositante(int id_depositante) {
			this.id_depositante = id_depositante;
		}
		public int getId_conta_depositante() {
			return id_conta_depositante;
		}
		public void setId_conta_depositante(int id_conta_depositante) {
			this.id_conta_depositante = id_conta_depositante;
		}
		public int getId_favorecido() {
			return id_favorecido;
		}
		public void setId_favorecido(int id_favorecido) {
			this.id_favorecido = id_favorecido;
		}
		public int getId_conta_favorecido() {
			return id_conta_favorecido;
		}
		public void setId_conta_favorecido(int id_conta_favorecido) {
			this.id_conta_favorecido = id_conta_favorecido;
		}
		
		
		
		
	}
	
	
}
