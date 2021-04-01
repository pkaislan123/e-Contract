package main.java.cadastros;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class CadastroContrato {

	/*
	 * 
	 * localizacao text 
bruto_livre text 
fertilizante text 
penhor int(3) 
status_penhor text 
optante_folha int(3) 
status_optante_folha text
	 * 
	 */
	
	private String descricao, observacao;
	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getObservacao() {
		return observacao;
	}



	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	private String localizacao, bruto_livre, fertilizante, status_penhor, status_optante_folha;
	private int optante_folha;
	
	
	public String getLocalizacao() {
		return localizacao;
	}



	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}



	public String getBruto_livre() {
		return bruto_livre;
	}



	public void setBruto_livre(String bruto_livre) {
		this.bruto_livre = bruto_livre;
	}



	public String getFertilizante() {
		return fertilizante;
	}



	public void setFertilizante(String fertilizante) {
		this.fertilizante = fertilizante;
	}



	public String getStatus_penhor() {
		return status_penhor;
	}



	public void setStatus_penhor(String status_penhor) {
		this.status_penhor = status_penhor;
	}



	public String getStatus_optante_folha() {
		return status_optante_folha;
	}



	public void setStatus_optante_folha(String status_optante_folha) {
		this.status_optante_folha = status_optante_folha;
	}




	public int getOptante_folha() {
		return optante_folha;
	}



	public void setOptante_folha(int optante_folha) {
		this.optante_folha = optante_folha;
	}
	private String texto_clausulas;
    private ArrayList<CadastroContrato.CadastroTarefa> lista_tarefas;
    private int status_aprovacao;
    
    
    public int getStatus_aprovacao() {
		return status_aprovacao;
	}



	public void setStatus_aprovacao(int status_aprovacao) {
		this.status_aprovacao = status_aprovacao;
	}
	private CadastroCliente cliente_retirada;
    
    
    private int tipo_entrega;
    private String frete;
    private String clausula_frete;
    private String armazenagem;
    private String clausula_armazenagem;
    private int id_local_retirada;
    
    
    
    
    
	public int getId_local_retirada() {
		return id_local_retirada;
	}



	public void setId_local_retirada(int id_local_retirada) {
		this.id_local_retirada = id_local_retirada;
	}



	public int getTipo_entrega() {
		return tipo_entrega;
	}



	public void setTipo_entrega(int tipo_entrega) {
		this.tipo_entrega = tipo_entrega;
	}



	public String getClausula_frete() {
		return clausula_frete;
	}



	public void setClausula_frete(String clausula_frete) {
		this.clausula_frete = clausula_frete;
	}



	public String getClausula_armazenagem() {
		return clausula_armazenagem;
	}



	public void setClausula_armazenagem(String clausula_armazenagem) {
		this.clausula_armazenagem = clausula_armazenagem;
	}



	public String getFrete() {
		return frete;
	}



	public void setFrete(String frete) {
		this.frete = frete;
	}



	public String getArmazenagem() {
		return armazenagem;
	}



	public void setArmazenagem(String armazenagem) {
		this.armazenagem = armazenagem;
	}



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

    private String caminho_diretorio_contrato2,  caminho_arquivo2, nome_arquivo2;
    
    
    
    

	public String getCaminho_diretorio_contrato2() {
		return caminho_diretorio_contrato2;
	}



	public void setCaminho_diretorio_contrato2(String caminho_diretorio_contrato2) {
		this.caminho_diretorio_contrato2 = caminho_diretorio_contrato2;
	}



	public String getCaminho_arquivo2() {
		return caminho_arquivo2;
	}



	public void setCaminho_arquivo2(String caminho_arquivo2) {
		this.caminho_arquivo2 = caminho_arquivo2;
	}



	public String getNome_arquivo2() {
		return nome_arquivo2;
	}



	public void setNome_arquivo2(String nome_arquivo2) {
		this.nome_arquivo2 = nome_arquivo2;
	}



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
		compradores = new CadastroCliente[3];
		vendedores = new CadastroCliente[3];
		corretores = new CadastroCliente[3];
	
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
		private String resposta;
		
		public String getResposta() {
			return resposta;
		}
		public void setResposta(String resposta) {
			this.resposta = resposta;
		}
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
		
		int id_carregamento, id_cliente, id_transportador, id_veiculo, id_contrato, id_produto, id_vendedor;
		int nf_venda1_aplicavel, nf_complemento_aplicavel, nf_interna_aplicavel;
		String codigo_nota_fiscal, data, caminho_nota_fiscal;
		String nome_remetente_nf_venda1, nome_destinatario_nf_venda1,nome_remetente_nf_complemento, nome_destinatario_nf_complemento;
		
		
		public String getNome_remetente_nf_venda1() {
			return nome_remetente_nf_venda1;
		}
		public void setNome_remetente_nf_venda1(String nome_remetente_nf_venda1) {
			this.nome_remetente_nf_venda1 = nome_remetente_nf_venda1;
		}
		public String getNome_destinatario_nf_venda1() {
			return nome_destinatario_nf_venda1;
		}
		public void setNome_destinatario_nf_venda1(String nome_destinatario_nf_venda1) {
			this.nome_destinatario_nf_venda1 = nome_destinatario_nf_venda1;
		}
		public String getNome_remetente_nf_complemento() {
			return nome_remetente_nf_complemento;
		}
		public void setNome_remetente_nf_complemento(String nome_remetente_nf_complemento) {
			this.nome_remetente_nf_complemento = nome_remetente_nf_complemento;
		}
		public String getNome_destinatario_nf_complemento() {
			return nome_destinatario_nf_complemento;
		}
		public void setNome_destinatario_nf_complemento(String nome_destinatario_nf_complemento) {
			this.nome_destinatario_nf_complemento = nome_destinatario_nf_complemento;
		}
		public int getNf_venda1_aplicavel() {
			return nf_venda1_aplicavel;
		}
		public void setNf_venda1_aplicavel(int nf_venda1_aplicavel) {
			this.nf_venda1_aplicavel = nf_venda1_aplicavel;
		}
		public int getNf_complemento_aplicavel() {
			return nf_complemento_aplicavel;
		}
		public void setNf_complemento_aplicavel(int nf_complemento_aplicavel) {
			this.nf_complemento_aplicavel = nf_complemento_aplicavel;
		}
		public int getNf_interna_aplicavel() {
			return nf_interna_aplicavel;
		}
		public void setNf_interna_aplicavel(int nf_interna_aplicavel) {
			this.nf_interna_aplicavel = nf_interna_aplicavel;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		private String descricao, codigo_romaneio ,caminho_romaneio ,codigo_nf_venda1 , caminho_nf_venda1,codigo_nf_complemento ,caminho_nf_complemento ,codigo_nf_interna ,caminho_nf_interna ,observacao; 
        double peso_romaneio, peso_nf_venda1, peso_nf_complemento,peso_nf_interna;
        
        BigDecimal valor_nf_venda1, valor_nf_complemento;
		
        
        
		public BigDecimal getValor_nf_venda1() {
			return valor_nf_venda1;
		}
		public void setValor_nf_venda1(BigDecimal valor_nf_venda1) {
			this.valor_nf_venda1 = valor_nf_venda1;
		}
		public BigDecimal getValor_nf_complemento() {
			return valor_nf_complemento;
		}
		public void setValor_nf_complemento(BigDecimal valor_nf_complemento) {
			this.valor_nf_complemento = valor_nf_complemento;
		}
		public String getCodigo_romaneio() {
			return codigo_romaneio;
		}
		public void setCodigo_romaneio(String codigo_romaneio) {
			this.codigo_romaneio = codigo_romaneio;
		}
		public String getCaminho_romaneio() {
			return caminho_romaneio;
		}
		public void setCaminho_romaneio(String caminho_romaneio) {
			this.caminho_romaneio = caminho_romaneio;
		}
		public String getCodigo_nf_venda1() {
			return codigo_nf_venda1;
		}
		public void setCodigo_nf_venda1(String codigo_nf_venda1) {
			this.codigo_nf_venda1 = codigo_nf_venda1;
		}
		public String getCaminho_nf_venda1() {
			return caminho_nf_venda1;
		}
		public void setCaminho_nf_venda1(String caminho_nf_venda1) {
			this.caminho_nf_venda1 = caminho_nf_venda1;
		}
		public String getCodigo_nf_complemento() {
			return codigo_nf_complemento;
		}
		public void setCodigo_nf_complemento(String codigo_nf_complemento) {
			this.codigo_nf_complemento = codigo_nf_complemento;
		}
		public String getCaminho_nf_complemento() {
			return caminho_nf_complemento;
		}
		public void setCaminho_nf_complemento(String caminho_nf_complemento) {
			this.caminho_nf_complemento = caminho_nf_complemento;
		}
		public String getCodigo_nf_interna() {
			return codigo_nf_interna;
		}
		public void setCodigo_nf_interna(String codigo_nf_interna) {
			this.codigo_nf_interna = codigo_nf_interna;
		}
		public String getCaminho_nf_interna() {
			return caminho_nf_interna;
		}
		public void setCaminho_nf_interna(String caminho_nf_interna) {
			this.caminho_nf_interna = caminho_nf_interna;
		}
		public String getObservacao() {
			return observacao;
		}
		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}
		public double getPeso_romaneio() {
			return peso_romaneio;
		}
		public void setPeso_romaneio(double peso_romaneio) {
			this.peso_romaneio = peso_romaneio;
		}
		public double getPeso_nf_venda1() {
			return peso_nf_venda1;
		}
		public void setPeso_nf_venda1(double peso_nf_venda1) {
			this.peso_nf_venda1 = peso_nf_venda1;
		}
		public double getPeso_nf_complemento() {
			return peso_nf_complemento;
		}
		public void setPeso_nf_complemento(double peso_nf_complemento) {
			this.peso_nf_complemento = peso_nf_complemento;
		}
		public double getPeso_nf_interna() {
			return peso_nf_interna;
		}
		public void setPeso_nf_interna(double peso_nf_interna) {
			this.peso_nf_interna = peso_nf_interna;
		}
		public String getCaminho_nota_fiscal() {
			return caminho_nota_fiscal;
		}
		public void setCaminho_nota_fiscal(String caminho_nota_fiscal) {
			this.caminho_nota_fiscal = caminho_nota_fiscal;
		}
		double peso_real_carga;
		
		public int getId_vendedor() {
			return id_vendedor;
		}
		public void setId_vendedor(int id_vendedor) {
			this.id_vendedor = id_vendedor;
		}
	
		
		
		
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
		
		
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public int getTipo() {
			return tipo;
		}
		public void setTipo(int tipo) {
			this.tipo = tipo;
		}
		String data_pagamento, descricao;
		double valor_pagamento;
		int tipo,id_pagamento, id_depositante, id_conta_depositante, id_favorecido, id_conta_favorecido;
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
	
	public static class CadastroTransferenciaPagamentoContratual{
		int id_transferencia, id_contrato_remetente, id_contrato_destinatario;
		
		String data, descricao, valor;

		public int getId_transferencia() {
			return id_transferencia;
		}

		public void setId_transferencia(int id_transferencia) {
			this.id_transferencia = id_transferencia;
		}

		public int getId_contrato_remetente() {
			return id_contrato_remetente;
		}

		public void setId_contrato_remetente(int id_contrato_remetente) {
			this.id_contrato_remetente = id_contrato_remetente;
		}

		public int getId_contrato_destinatario() {
			return id_contrato_destinatario;
		}

		public void setId_contrato_destinatario(int id_contrato_destinatario) {
			this.id_contrato_destinatario = id_contrato_destinatario;
		}

		
		
		public String getValor() {
			return valor;
		}

		public void setValor(String valor) {
			this.valor = valor;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		
		
		
		
	}
	
	
	public static class CadastroTransferenciaCarga{
	    int id_transferencia, id_contrato_remetente, id_contrato_destinatario, id_carregamento_remetente;
		
		String data, descricao, quantidade;

		public int getId_transferencia() {
			return id_transferencia;
		}

		public void setId_transferencia(int id_transferencia) {
			this.id_transferencia = id_transferencia;
		}

		public int getId_contrato_remetente() {
			return id_contrato_remetente;
		}

		public void setId_contrato_remetente(int id_contrato_remetente) {
			this.id_contrato_remetente = id_contrato_remetente;
		}

		public int getId_contrato_destinatario() {
			return id_contrato_destinatario;
		}

		public void setId_contrato_destinatario(int id_contrato_destinatario) {
			this.id_contrato_destinatario = id_contrato_destinatario;
		}

		public int getId_carregamento_remetente() {
			return id_carregamento_remetente;
		}

		public void setId_carregamento_remetente(int id_carregamento_remetente) {
			this.id_carregamento_remetente = id_carregamento_remetente;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(String quantidade) {
			this.quantidade = quantidade;
		}
		
		
		
	}
	
	
	public static class Recebimento{
		/*
		 * id_recebimento int(10) not null auto_increment,
data_recebimento varchar(40),
id_contrato_recebimento int(10),
id_cliente int(10),
id_transportador int(10),
id_veiculo int(10),
codigo_romaneio varchar(100),
peso_romaneio double,
caminho_romaneio text,
nf_venda varchar (40),
peso_nf_venda double,
caminho_nf_venda text,
nf_remessa varchar(40),
peso_nf_remessa double,
caminho_nf_remessa text,
		 */
		int id_recebimento, id_contrato_recebimento, id_cliente, id_transportador, id_veiculo, id_vendedor, nf_venda_aplicavel, nf_remessa_aplicavel;
		public int getId_vendedor() {
			return id_vendedor;
		}
		public int getNf_venda_aplicavel() {
			return nf_venda_aplicavel;
		}
		public void setNf_venda_aplicavel(int nf_venda_aplicavel) {
			this.nf_venda_aplicavel = nf_venda_aplicavel;
		}
		public int getNf_remessa_aplicavel() {
			return nf_remessa_aplicavel;
		}
		public void setNf_remessa_aplicavel(int nf_remessa_aplicavel) {
			this.nf_remessa_aplicavel = nf_remessa_aplicavel;
		}
		public void setId_vendedor(int id_vendedor) {
			this.id_vendedor = id_vendedor;
		}
		String data_recebimento, codigo_romaneio, caminho_romaneio, codigo_nf_venda, caminho_nf_venda, codigo_nf_remessa, caminho_nf_remessa;
		String nome_remetente_nf_remessa, nome_destinatario_nf_remessa;
		String nome_remetente_nf_venda, nome_destinatario_nf_venda;

		
        public String getNome_remetente_nf_remessa() {
			return nome_remetente_nf_remessa;
		}
		public void setNome_remetente_nf_remessa(String nome_remetente_nf_remessa) {
			this.nome_remetente_nf_remessa = nome_remetente_nf_remessa;
		}
		public String getNome_destinatario_nf_remessa() {
			return nome_destinatario_nf_remessa;
		}
		public void setNome_destinatario_nf_remessa(String nome_destinatario_nf_remessa) {
			this.nome_destinatario_nf_remessa = nome_destinatario_nf_remessa;
		}
		public String getNome_remetente_nf_venda() {
			return nome_remetente_nf_venda;
		}
		public void setNome_remetente_nf_venda(String nome_remetente_nf_venda) {
			this.nome_remetente_nf_venda = nome_remetente_nf_venda;
		}
		public String getNome_destinatario_nf_venda() {
			return nome_destinatario_nf_venda;
		}
		public void setNome_destinatario_nf_venda(String nome_destinatario_nf_venda) {
			this.nome_destinatario_nf_venda = nome_destinatario_nf_venda;
		}
		BigDecimal valor_nf_venda, valor_nf_remessa;
        

		public BigDecimal getValor_nf_venda() {
			return valor_nf_venda;
		}
		public void setValor_nf_venda(BigDecimal valor_nf_venda) {
			this.valor_nf_venda = valor_nf_venda;
		}
		public BigDecimal getValor_nf_remessa() {
			return valor_nf_remessa;
		}
		public void setValor_nf_remessa(BigDecimal valor_nf_remessa) {
			this.valor_nf_remessa = valor_nf_remessa;
		}
		double peso_romaneio, peso_nf_venda, peso_nf_remessa;
		
		
		public int getId_recebimento() {
			return id_recebimento;
		}
		public void setId_recebimento(int id_recebimento) {
			this.id_recebimento = id_recebimento;
		}
		public int getId_contrato_recebimento() {
			return id_contrato_recebimento;
		}
		public void setId_contrato_recebimento(int id_contrato_recebimento) {
			this.id_contrato_recebimento = id_contrato_recebimento;
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
		public String getData_recebimento() {
			return data_recebimento;
		}
		public void setData_recebimento(String data_recebimento) {
			this.data_recebimento = data_recebimento;
		}
		public String getCodigo_romaneio() {
			return codigo_romaneio;
		}
		public void setCodigo_romaneio(String codigo_romaneio) {
			this.codigo_romaneio = codigo_romaneio;
		}
		public String getCaminho_romaneio() {
			return caminho_romaneio;
		}
		public void setCaminho_romaneio(String caminho_romaneio) {
			this.caminho_romaneio = caminho_romaneio;
		}
		public String getCodigo_nf_venda() {
			return codigo_nf_venda;
		}
		public void setCodigo_nf_venda(String codigo_nf_venda) {
			this.codigo_nf_venda = codigo_nf_venda;
		}
		public String getCaminho_nf_venda() {
			return caminho_nf_venda;
		}
		public void setCaminho_nf_venda(String caminho_nf_venda) {
			this.caminho_nf_venda = caminho_nf_venda;
		}
		public String getCodigo_nf_remessa() {
			return codigo_nf_remessa;
		}
		public void setCodigo_nf_remessa(String codigo_nf_remessa) {
			this.codigo_nf_remessa = codigo_nf_remessa;
		}
		public String getCaminho_nf_remessa() {
			return caminho_nf_remessa;
		}
		public void setCaminho_nf_remessa(String caminho_nf_remessa) {
			this.caminho_nf_remessa = caminho_nf_remessa;
		}
		public double getPeso_romaneio() {
			return peso_romaneio;
		}
		public void setPeso_romaneio(double peso_romaneio) {
			this.peso_romaneio = peso_romaneio;
		}
		public double getPeso_nf_venda() {
			return peso_nf_venda;
		}
		public void setPeso_nf_venda(double peso_nf_venda) {
			this.peso_nf_venda = peso_nf_venda;
		}
		public double getPeso_nf_remessa() {
			return peso_nf_remessa;
		}
		public void setPeso_nf_remessa(double peso_nf_remessa) {
			this.peso_nf_remessa = peso_nf_remessa;
		}
		
		
		
		
	}
	
	
}
