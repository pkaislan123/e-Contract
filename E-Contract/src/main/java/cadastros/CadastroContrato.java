/*
 * Decompiled with CFR 0.151.
 */
package main.java.cadastros;

import java.math.BigDecimal;
import java.util.ArrayList;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.ContaBancaria;

public class CadastroContrato {
    private double valor_total_armazenamento_a_receber;
    private double valor_total_armazenamento_pago;
    private int filho;
    private double valor_total_comissao_receber;
    private String nome_local_retirada;
    private String ids_clientes_compradores_contrato_pai;
    private String ids_clientes_compradores;
    private String descricao;
    private String observacao;
    private double total_pago;
    private double total_comissao;
    private String localizacao;
    private String bruto_livre;
    private String fertilizante;
    private String status_penhor;
    private String status_optante_folha;
    private int optante_folha;
    private String fundo_rural;
    private String clausula_fundo_rural;
    private String clausula_comissao;
    private int criar_clausula_comissao;
    private int criar_clausula_1;
    private int criar_clausula_2;
    private String clausula1;
    private String clausula2;
    private int comissao;
    private int grupo_particular;
    private String texto_clausulas;
    private ArrayList<CadastroTarefa> lista_tarefas;
    private int status_aprovacao;
    private CadastroCliente cliente_retirada;
    private int tipo_entrega;
    private String frete;
    private String clausula_frete;
    private String armazenagem;
    private String clausula_armazenagem;
    private int id_local_retirada;
    private int armazenamento;
    private BigDecimal valor_a_receber;
    private double quantidade_recebida;
    private double quantidade_carregada;
    private String caminho_diretorio_contrato;
    private String caminho_diretorio_contrato2;
    private String caminho_arquivo2;
    private String nome_arquivo2;
    private ArrayList<String> clausulas;
    private CadastroCliente[] compradores;
    private CadastroCliente[] vendedores;
    private CadastroCliente[] corretores;
    private BigDecimal valor_a_pagar;
    private BigDecimal valor_comissao;
    private BigDecimal valor_armazenamento;
    private BigDecimal valor_armazenamento_por_unidade;
    private double quantidade;
    private double valor_produto;
    private String produto;
    private String data_contrato;
    private String codigo;
    private String data_entrega;
    private String ctc;
    private String ctv;
    private String safra;
    private String medida;
    private String caminho_arquivo;
    private String nome_arquivo;
    private int sub_contrato;
    private int assinatura_comprador;
    private int assinatura_vendedor;
    private int id;
    private int status_contrato;
    private String nomes_compradores;
    private String nomes_vendedores;
    private String nomes_corretores;
    private CadastroProduto modelo_produto;
    private CadastroSafra modelo_safra;
    private String data_pagamento;
    private String local_retirada;
    private ArrayList<CadastroPagamento> pagamentos = new ArrayList();

    public double getValor_total_armazenamento_pago() {
        return this.valor_total_armazenamento_pago;
    }

    public void setValor_total_armazenamento_pago(double valor_total_armazenamento_pago) {
        this.valor_total_armazenamento_pago = valor_total_armazenamento_pago;
    }

    public double getValor_total_armazenamento_a_receber() {
        return this.valor_total_armazenamento_a_receber;
    }

    public void setValor_total_armazenamento_a_receber(double valor_total_armazenamento_a_receber) {
        this.valor_total_armazenamento_a_receber = valor_total_armazenamento_a_receber;
    }

    public int getFilho() {
        return this.filho;
    }

    public double getValor_total_comissao_receber() {
        return this.valor_total_comissao_receber;
    }

    public void setValor_total_comissao_receber(double valor_total_comissao_receber) {
        this.valor_total_comissao_receber = valor_total_comissao_receber;
    }

    public String getNome_local_retirada() {
        return this.nome_local_retirada;
    }

    public void setNome_local_retirada(String nome_local_retirada) {
        this.nome_local_retirada = nome_local_retirada;
    }

    public String getIds_clientes_compradores_contrato_pai() {
        return this.ids_clientes_compradores_contrato_pai;
    }

    public void setIds_clientes_compradores_contrato_pai(String ids_clientes_compradores_contrato_pai) {
        this.ids_clientes_compradores_contrato_pai = ids_clientes_compradores_contrato_pai;
    }

    public String getIds_clientes_compradores() {
        return this.ids_clientes_compradores;
    }

    public void setIds_clientes_compradores(String ids_clientes_compradores) {
        this.ids_clientes_compradores = ids_clientes_compradores;
    }

    public void setFilho(int filho) {
        this.filho = filho;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public double getTotal_pago() {
        return this.total_pago;
    }

    public void setTotal_pago(double total_pago) {
        this.total_pago = total_pago;
    }

    public double getTotal_comissao() {
        return this.total_comissao;
    }

    public void setTotal_comissao(double total_comissao) {
        this.total_comissao = total_comissao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getCriar_clausula_1() {
        return this.criar_clausula_1;
    }

    public void setCriar_clausula_1(int criar_clausula_1) {
        this.criar_clausula_1 = criar_clausula_1;
    }

    public int getCriar_clausula_2() {
        return this.criar_clausula_2;
    }

    public void setCriar_clausula_2(int criar_clausula_2) {
        this.criar_clausula_2 = criar_clausula_2;
    }

    public String getClausula1() {
        return this.clausula1;
    }

    public void setClausula1(String clausula1) {
        this.clausula1 = clausula1;
    }

    public String getClausula2() {
        return this.clausula2;
    }

    public void setClausula2(String clausula2) {
        this.clausula2 = clausula2;
    }

    public int getComissao() {
        return this.comissao;
    }

    public void setComissao(int comissao) {
        this.comissao = comissao;
    }

    public int getCriar_clausula_comissao() {
        return this.criar_clausula_comissao;
    }

    public void setCriar_clausula_comissao(int criar_clausula_comissao) {
        this.criar_clausula_comissao = criar_clausula_comissao;
    }

    public String getClausula_comissao() {
        return this.clausula_comissao;
    }

    public void setClausula_comissao(String clausula_comissao) {
        this.clausula_comissao = clausula_comissao;
    }

    public String getFundo_rural() {
        return this.fundo_rural;
    }

    public void setFundo_rural(String fundo_rural) {
        this.fundo_rural = fundo_rural;
    }

    public String getClausula_fundo_rural() {
        return this.clausula_fundo_rural;
    }

    public void setClausula_fundo_rural(String clausula_fundo_rural) {
        this.clausula_fundo_rural = clausula_fundo_rural;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getBruto_livre() {
        return this.bruto_livre;
    }

    public void setBruto_livre(String bruto_livre) {
        this.bruto_livre = bruto_livre;
    }

    public String getFertilizante() {
        return this.fertilizante;
    }

    public void setFertilizante(String fertilizante) {
        this.fertilizante = fertilizante;
    }

    public String getStatus_penhor() {
        return this.status_penhor;
    }

    public void setStatus_penhor(String status_penhor) {
        this.status_penhor = status_penhor;
    }

    public String getStatus_optante_folha() {
        return this.status_optante_folha;
    }

    public void setStatus_optante_folha(String status_optante_folha) {
        this.status_optante_folha = status_optante_folha;
    }

    public int getOptante_folha() {
        return this.optante_folha;
    }

    public int getGrupo_particular() {
        return this.grupo_particular;
    }

    public void setGrupo_particular(int grupo_particular) {
        this.grupo_particular = grupo_particular;
    }

    public void setOptante_folha(int optante_folha) {
        this.optante_folha = optante_folha;
    }

    public int getStatus_aprovacao() {
        return this.status_aprovacao;
    }

    public void setStatus_aprovacao(int status_aprovacao) {
        this.status_aprovacao = status_aprovacao;
    }

    public int getArmazenamento() {
        return this.armazenamento;
    }

    public void setArmazenamento(int armazenamento) {
        this.armazenamento = armazenamento;
    }

    public BigDecimal getValor_a_receber() {
        return this.valor_a_receber;
    }

    public void setValor_a_receber(BigDecimal valor_a_receber) {
        this.valor_a_receber = valor_a_receber;
    }

    public double getQuantidade_recebida() {
        return this.quantidade_recebida;
    }

    public void setQuantidade_recebida(double quantidade_recebida) {
        this.quantidade_recebida = quantidade_recebida;
    }

    public double getQuantidade_carregada() {
        return this.quantidade_carregada;
    }

    public void setQuantidade_carregada(double quantidade_carregada) {
        this.quantidade_carregada = quantidade_carregada;
    }

    public int getId_local_retirada() {
        return this.id_local_retirada;
    }

    public void setId_local_retirada(int id_local_retirada) {
        this.id_local_retirada = id_local_retirada;
    }

    public int getTipo_entrega() {
        return this.tipo_entrega;
    }

    public void setTipo_entrega(int tipo_entrega) {
        this.tipo_entrega = tipo_entrega;
    }

    public String getClausula_frete() {
        return this.clausula_frete;
    }

    public void setClausula_frete(String clausula_frete) {
        this.clausula_frete = clausula_frete;
    }

    public String getClausula_armazenagem() {
        return this.clausula_armazenagem;
    }

    public void setClausula_armazenagem(String clausula_armazenagem) {
        this.clausula_armazenagem = clausula_armazenagem;
    }

    public String getFrete() {
        return this.frete;
    }

    public void setFrete(String frete) {
        this.frete = frete;
    }

    public String getArmazenagem() {
        return this.armazenagem;
    }

    public void setArmazenagem(String armazenagem) {
        this.armazenagem = armazenagem;
    }

    public CadastroCliente getCliente_retirada() {
        return this.cliente_retirada;
    }

    public void setCliente_retirada(CadastroCliente cliente_retirada) {
        this.cliente_retirada = cliente_retirada;
    }

    public ArrayList<CadastroTarefa> getLista_tarefas() {
        return this.lista_tarefas;
    }

    public void setLista_tarefas(ArrayList<CadastroTarefa> lista_tarefas) {
        this.lista_tarefas = lista_tarefas;
    }

    public String getCaminho_diretorio_contrato2() {
        return this.caminho_diretorio_contrato2;
    }

    public void setCaminho_diretorio_contrato2(String caminho_diretorio_contrato2) {
        this.caminho_diretorio_contrato2 = caminho_diretorio_contrato2;
    }

    public String getCaminho_arquivo2() {
        return this.caminho_arquivo2;
    }

    public void setCaminho_arquivo2(String caminho_arquivo2) {
        this.caminho_arquivo2 = caminho_arquivo2;
    }

    public String getNome_arquivo2() {
        return this.nome_arquivo2;
    }

    public void setNome_arquivo2(String nome_arquivo2) {
        this.nome_arquivo2 = nome_arquivo2;
    }

    public String getCaminho_diretorio_contrato() {
        return this.caminho_diretorio_contrato;
    }

    public void setCaminho_diretorio_contrato(String caminho_diretorio_contrato) {
        this.caminho_diretorio_contrato = caminho_diretorio_contrato;
    }

    public int getAssinatura_comprador() {
        return this.assinatura_comprador;
    }

    public void setAssinatura_comprador(int assinatura_comprador) {
        this.assinatura_comprador = assinatura_comprador;
    }

    public int getAssinatura_vendedor() {
        return this.assinatura_vendedor;
    }

    public void setAssinatura_vendedor(int assinatura_vendedor) {
        this.assinatura_vendedor = assinatura_vendedor;
    }

    public String getTexto_clausulas() {
        return this.texto_clausulas;
    }

    public void setTexto_clausulas(String texto_clausulas) {
        this.texto_clausulas = texto_clausulas;
    }

    public ArrayList<String> getClausulas() {
        return this.clausulas;
    }

    public void setClausulas(ArrayList<String> clausulas) {
        this.clausulas = clausulas;
    }

    public CadastroCliente[] getCompradores() {
        return this.compradores;
    }

    public void setCompradores(CadastroCliente[] compradores) {
        this.compradores = compradores;
    }

    public CadastroCliente[] getVendedores() {
        return this.vendedores;
    }

    public void setVendedores(CadastroCliente[] vendedores) {
        this.vendedores = vendedores;
    }

    public CadastroCliente[] getCorretores() {
        return this.corretores;
    }

    public void setCorretores(CadastroCliente[] corretores) {
        this.corretores = corretores;
    }

    public BigDecimal getValor_armazenamento_por_unidade() {
        return this.valor_armazenamento_por_unidade;
    }

    public void setValor_armazenamento_por_unidade(BigDecimal valor_armazenamento_por_unidade) {
        this.valor_armazenamento_por_unidade = valor_armazenamento_por_unidade;
    }

    public BigDecimal getValor_armazenamento() {
        return this.valor_armazenamento;
    }

    public void setValor_armazenamento(BigDecimal valor_armazenamento) {
        this.valor_armazenamento = valor_armazenamento;
    }

    public BigDecimal getValor_comissao() {
        return this.valor_comissao;
    }

    public void setValor_comissao(BigDecimal valor_comissao) {
        this.valor_comissao = valor_comissao;
    }

    public int getSub_contrato() {
        return this.sub_contrato;
    }

    public void setSub_contrato(int sub_contrato) {
        this.sub_contrato = sub_contrato;
    }

    public String getCaminho_arquivo() {
        return this.caminho_arquivo;
    }

    public String getNome_arquivo() {
        return this.nome_arquivo;
    }

    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
    }

    public void setCaminho_arquivo(String caminho_arquivo) {
        this.caminho_arquivo = caminho_arquivo;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomes_compradores() {
        return this.nomes_compradores;
    }

    public void setNomes_compradores(String nomes_compradores) {
        this.nomes_compradores = nomes_compradores;
    }

    public String getNomes_vendedores() {
        return this.nomes_vendedores;
    }

    public void setNomes_vendedores(String nomes_vendedores) {
        this.nomes_vendedores = nomes_vendedores;
    }

    public String getNomes_corretores() {
        return this.nomes_corretores;
    }

    public void setNomes_corretores(String nomes_corretores) {
        this.nomes_corretores = nomes_corretores;
    }

    public int getStatus_contrato() {
        return this.status_contrato;
    }

    public void setStatus_contrato(int status_contrato) {
        this.status_contrato = status_contrato;
    }

    public CadastroProduto getModelo_produto() {
        return this.modelo_produto;
    }

    public void setModelo_produto(CadastroProduto modelo_produto) {
        this.modelo_produto = modelo_produto;
    }

    public CadastroSafra getModelo_safra() {
        return this.modelo_safra;
    }

    public void setModelo_safra(CadastroSafra modelo_safra) {
        this.modelo_safra = modelo_safra;
    }

    public CadastroContrato() {
        this.compradores = new CadastroCliente[3];
        this.vendedores = new CadastroCliente[3];
        this.corretores = new CadastroCliente[3];
        int i = 0;
        while (i < this.corretores.length) {
            this.corretores[i] = null;
            ++i;
        }
        i = 0;
        while (i < this.compradores.length) {
            this.compradores[i] = null;
            ++i;
        }
        i = 0;
        while (i < this.vendedores.length) {
            this.vendedores[i] = null;
            ++i;
        }
        this.clausulas = new ArrayList();
    }

    public CadastroCliente[] listaCorretores() {
        return this.corretores;
    }

    public CadastroCliente[] listaCompradores() {
        return this.compradores;
    }

    public CadastroCliente[] listaVendedores() {
        return this.vendedores;
    }

    public void adicionarComprador(int posicao, CadastroCliente comprador) {
        this.compradores[posicao] = comprador;
    }

    public void adicionarVendedor(int posicao, CadastroCliente vendedor) {
        this.vendedores[posicao] = vendedor;
    }

    public void adicionarCorretor(int posicao, CadastroCliente corretor) {
        this.corretores[posicao] = corretor;
    }

    public String getCtc() {
        return this.ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getCtv() {
        return this.ctv;
    }

    public void setCtv(String ctv) {
        this.ctv = ctv;
    }

    public String getData_entrega() {
        return this.data_entrega;
    }

    public void setData_entrega(String data_entrega) {
        this.data_entrega = data_entrega;
    }

    public String getSafra() {
        return this.safra;
    }

    public String getData_contrato() {
        return this.data_contrato;
    }

    public void setData_contrato(String data_contrato) {
        this.data_contrato = data_contrato;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setSafra(String safra) {
        this.safra = safra;
    }

    public double getValor_produto() {
        return this.valor_produto;
    }

    public void setValor_produto(double valor_produto) {
        this.valor_produto = valor_produto;
    }

    public BigDecimal getValor_a_pagar() {
        return this.valor_a_pagar;
    }

    public void setValor_a_pagar(BigDecimal valor_a_pagar) {
        this.valor_a_pagar = valor_a_pagar;
    }

    public String getData_pagamento() {
        return this.data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public String getLocal_retirada() {
        return this.local_retirada;
    }

    public void setLocal_retirada(String local_retirada) {
        this.local_retirada = local_retirada;
    }

    public String getMedida() {
        return this.medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public String getProduto() {
        return this.produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public ArrayList<CadastroPagamento> getPagamentos() {
        return this.pagamentos;
    }

    public void setPagamentos(ArrayList<CadastroPagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public static class CadastroPagamento {
        int id;
        int pagamento_adiantado;
        ContaBancaria conta;
        BigDecimal valor = new BigDecimal("0");
        String valor_string;
        String data_pagamento;
        String descricao_pagamento;

        public int getPagamento_adiantado() {
            return this.pagamento_adiantado;
        }

        public void setPagamento_adiantado(int pagamento_adiantado) {
            this.pagamento_adiantado = pagamento_adiantado;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValor_string() {
            return this.valor_string;
        }

        public void setValor_string(String valor_string) {
            this.valor_string = valor_string;
        }

        public String getDescricao_pagamento() {
            return this.descricao_pagamento;
        }

        public void setDescricao_pagamento(String descricao_pagamento) {
            this.descricao_pagamento = descricao_pagamento;
        }

        public ContaBancaria getConta() {
            return this.conta;
        }

        public void setConta(ContaBancaria conta) {
            this.conta = conta;
        }

        public BigDecimal getValor() {
            return this.valor;
        }

        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }

        public String getData_pagamento() {
            return this.data_pagamento;
        }

        public void setData_pagamento(String data_pagamento) {
            this.data_pagamento = data_pagamento;
        }
    }

    public static class CadastroPagamentoContratual {
        String data_pagamento;
        String descricao;
        String conta_depositante;
        String conta_favorecido;
        double valor_pagamento;
        int tipo;
        int id_pagamento;
        int id_depositante;
        int id_contrato_destinatario;
        int id_contrato_remetente;
        int id_conta_depositante;
        int id_favorecido;
        int id_conta_favorecido;

        public String getDescricao() {
            return this.descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public int getTipo() {
            return this.tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public String getConta_depositante() {
            return this.conta_depositante;
        }

        public void setConta_depositante(String conta_depositante) {
            this.conta_depositante = conta_depositante;
        }

        public String getConta_favorecido() {
            return this.conta_favorecido;
        }

        public void setConta_favorecido(String conta_favorecido) {
            this.conta_favorecido = conta_favorecido;
        }

        public int getId_contrato_destinatario() {
            return this.id_contrato_destinatario;
        }

        public void setId_contrato_destinatario(int id_contrato_destinatario) {
            this.id_contrato_destinatario = id_contrato_destinatario;
        }

        public int getId_contrato_remetente() {
            return this.id_contrato_remetente;
        }

        public void setId_contrato_remetente(int id_contrato_remetente) {
            this.id_contrato_remetente = id_contrato_remetente;
        }

        public String getData_pagamento() {
            return this.data_pagamento;
        }

        public void setData_pagamento(String data_pagamento) {
            this.data_pagamento = data_pagamento;
        }

        public double getValor_pagamento() {
            return this.valor_pagamento;
        }

        public void setValor_pagamento(double valor_pagamento) {
            this.valor_pagamento = valor_pagamento;
        }

        public int getId_pagamento() {
            return this.id_pagamento;
        }

        public void setId_pagamento(int id_pagamento) {
            this.id_pagamento = id_pagamento;
        }

        public int getId_depositante() {
            return this.id_depositante;
        }

        public void setId_depositante(int id_depositante) {
            this.id_depositante = id_depositante;
        }

        public int getId_conta_depositante() {
            return this.id_conta_depositante;
        }

        public void setId_conta_depositante(int id_conta_depositante) {
            this.id_conta_depositante = id_conta_depositante;
        }

        public int getId_favorecido() {
            return this.id_favorecido;
        }

        public void setId_favorecido(int id_favorecido) {
            this.id_favorecido = id_favorecido;
        }

        public int getId_conta_favorecido() {
            return this.id_conta_favorecido;
        }

        public void setId_conta_favorecido(int id_conta_favorecido) {
            this.id_conta_favorecido = id_conta_favorecido;
        }
    }

    public static class CadastroTarefa {
        private int id_tarefa;
        private int status_tarefa;
        private String descricao_tarefa;
        private String mensagem;
        private String hora;
        private String data;
        private String nome_tarefa;
        private CadastroLogin criador;
        private String resposta;
        private String nome_criador;
        private String nome_executor;
        private String hora_agendada;
        private String data_agendada;
        private int prioridade;
        private CadastroLogin executor;

        public String getNome_criador() {
            return this.nome_criador;
        }

        public void setNome_criador(String nome_criador) {
            this.nome_criador = nome_criador;
        }

        public String getNome_executor() {
            return this.nome_executor;
        }

        public void setNome_executor(String nome_executor) {
            this.nome_executor = nome_executor;
        }

        public String getResposta() {
            return this.resposta;
        }

        public void setResposta(String resposta) {
            this.resposta = resposta;
        }

        public String getHora_agendada() {
            return this.hora_agendada;
        }

        public void setHora_agendada(String hora_agendada) {
            this.hora_agendada = hora_agendada;
        }

        public String getData_agendada() {
            return this.data_agendada;
        }

        public void setData_agendada(String data_agendada) {
            this.data_agendada = data_agendada;
        }

        public int getPrioridade() {
            return this.prioridade;
        }

        public void setPrioridade(int prioridade) {
            this.prioridade = prioridade;
        }

        public CadastroLogin getCriador() {
            return this.criador;
        }

        public void setCriador(CadastroLogin criador) {
            this.criador = criador;
        }

        public CadastroLogin getExecutor() {
            return this.executor;
        }

        public void setExecutor(CadastroLogin executor) {
            this.executor = executor;
        }

        public int getId_tarefa() {
            return this.id_tarefa;
        }

        public void setId_tarefa(int id_tarefa) {
            this.id_tarefa = id_tarefa;
        }

        public int getStatus_tarefa() {
            return this.status_tarefa;
        }

        public void setStatus_tarefa(int status_tarefa) {
            this.status_tarefa = status_tarefa;
        }

        public String getDescricao_tarefa() {
            return this.descricao_tarefa;
        }

        public void setDescricao_tarefa(String descricao_tarefa) {
            this.descricao_tarefa = descricao_tarefa;
        }

        public String getMensagem() {
            return this.mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getHora() {
            return this.hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getNome_tarefa() {
            return this.nome_tarefa;
        }

        public void setNome_tarefa(String nome_tarefa) {
            this.nome_tarefa = nome_tarefa;
        }
    }

    public static class CadastroTransferenciaCarga {
        int id_transferencia;
        int id_contrato_remetente;
        int id_contrato_destinatario;
        int id_carregamento_remetente;
        String data;
        String descricao;
        String quantidade;
        String codigo_remetente;
        String codigo_destinatario;

        public String getCodigo_remetente() {
            return this.codigo_remetente;
        }

        public void setCodigo_remetente(String codigo_remetente) {
            this.codigo_remetente = codigo_remetente;
        }

        public String getCodigo_destinatario() {
            return this.codigo_destinatario;
        }

        public void setCodigo_destinatario(String codigo_destinatario) {
            this.codigo_destinatario = codigo_destinatario;
        }

        public int getId_transferencia() {
            return this.id_transferencia;
        }

        public void setId_transferencia(int id_transferencia) {
            this.id_transferencia = id_transferencia;
        }

        public int getId_contrato_remetente() {
            return this.id_contrato_remetente;
        }

        public void setId_contrato_remetente(int id_contrato_remetente) {
            this.id_contrato_remetente = id_contrato_remetente;
        }

        public int getId_contrato_destinatario() {
            return this.id_contrato_destinatario;
        }

        public void setId_contrato_destinatario(int id_contrato_destinatario) {
            this.id_contrato_destinatario = id_contrato_destinatario;
        }

        public int getId_carregamento_remetente() {
            return this.id_carregamento_remetente;
        }

        public void setId_carregamento_remetente(int id_carregamento_remetente) {
            this.id_carregamento_remetente = id_carregamento_remetente;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getQuantidade() {
            return this.quantidade;
        }

        public void setQuantidade(String quantidade) {
            this.quantidade = quantidade;
        }
    }

    public static class CadastroTransferenciaPagamentoContratual {
        int id_transferencia;
        int id_contrato_remetente;
        int id_contrato_destinatario;
        String data;
        String descricao;
        String valor;

        public int getId_transferencia() {
            return this.id_transferencia;
        }

        public void setId_transferencia(int id_transferencia) {
            this.id_transferencia = id_transferencia;
        }

        public int getId_contrato_remetente() {
            return this.id_contrato_remetente;
        }

        public void setId_contrato_remetente(int id_contrato_remetente) {
            this.id_contrato_remetente = id_contrato_remetente;
        }

        public int getId_contrato_destinatario() {
            return this.id_contrato_destinatario;
        }

        public void setId_contrato_destinatario(int id_contrato_destinatario) {
            this.id_contrato_destinatario = id_contrato_destinatario;
        }

        public String getValor() {
            return this.valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    public static class CadastroTransferenciaRecebimento {
        int id_transferencia;
        int id_contrato_remetente;
        int id_contrato_destinatario;
        String data;
        String descricao;
        double quantidade;

        public int getId_transferencia() {
            return this.id_transferencia;
        }

        public void setId_transferencia(int id_transferencia) {
            this.id_transferencia = id_transferencia;
        }

        public int getId_contrato_remetente() {
            return this.id_contrato_remetente;
        }

        public void setId_contrato_remetente(int id_contrato_remetente) {
            this.id_contrato_remetente = id_contrato_remetente;
        }

        public int getId_contrato_destinatario() {
            return this.id_contrato_destinatario;
        }

        public void setId_contrato_destinatario(int id_contrato_destinatario) {
            this.id_contrato_destinatario = id_contrato_destinatario;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public double getQuantidade() {
            return this.quantidade;
        }

        public void setQuantidade(double quantidade) {
            this.quantidade = quantidade;
        }
    }

    public static class Carregamento {
        int id_carregamento;
        int id_cliente;
        int id_transportador;
        int id_veiculo;
        int id_contrato;
        int id_produto;
        int id_vendedor;
        int nf_venda1_aplicavel;
        int nf_complemento_aplicavel;
        int nf_interna_aplicavel;
        String codigo_nota_fiscal;
        String data;
        String caminho_nota_fiscal;
        String nome_remetente_nf_venda1;
        String nome_destinatario_nf_venda1;
        String nome_remetente_nf_complemento;
        String nome_destinatario_nf_complemento;
        String nome_transportador;
        String nome_comprador;
        String nome_vendedor;
        String nome_produto;
        String placa_veiculo;
        String codigo_contrato;
        private String descricao;
        private String codigo_romaneio;
        private String caminho_romaneio;
        private String codigo_nf_venda1;
        private String caminho_nf_venda1;
        private String codigo_nf_complemento;
        private String caminho_nf_complemento;
        private String codigo_nf_interna;
        private String caminho_nf_interna;
        private String observacao;
        double peso_romaneio;
        double peso_nf_venda1;
        double peso_nf_complemento;
        double peso_nf_interna;
        BigDecimal valor_nf_venda1;
        BigDecimal valor_nf_complemento;
        double peso_real_carga;

        public String toString() {
            return "Carregamento [id_carregamento=" + this.id_carregamento + ", id_cliente=" + this.id_cliente + ", id_transportador=" + this.id_transportador + ", id_veiculo=" + this.id_veiculo + ", id_contrato=" + this.id_contrato + ", id_produto=" + this.id_produto + ", id_vendedor=" + this.id_vendedor + ", nf_venda1_aplicavel=" + this.nf_venda1_aplicavel + ", nf_complemento_aplicavel=" + this.nf_complemento_aplicavel + ", nf_interna_aplicavel=" + this.nf_interna_aplicavel + ", codigo_nota_fiscal=" + this.codigo_nota_fiscal + ", data=" + this.data + ", caminho_nota_fiscal=" + this.caminho_nota_fiscal + ", nome_remetente_nf_venda1=" + this.nome_remetente_nf_venda1 + ", nome_destinatario_nf_venda1=" + this.nome_destinatario_nf_venda1 + ", nome_remetente_nf_complemento=" + this.nome_remetente_nf_complemento + ", nome_destinatario_nf_complemento=" + this.nome_destinatario_nf_complemento + ", nome_transportador=" + this.nome_transportador + ", nome_comprador=" + this.nome_comprador + ", nome_vendedor=" + this.nome_vendedor + ", nome_produto=" + this.nome_produto + ", placa_veiculo=" + this.placa_veiculo + ", codigo_contrato=" + this.codigo_contrato + ", descricao=" + this.descricao + ", codigo_romaneio=" + this.codigo_romaneio + ", caminho_romaneio=" + this.caminho_romaneio + ", codigo_nf_venda1=" + this.codigo_nf_venda1 + ", caminho_nf_venda1=" + this.caminho_nf_venda1 + ", codigo_nf_complemento=" + this.codigo_nf_complemento + ", caminho_nf_complemento=" + this.caminho_nf_complemento + ", codigo_nf_interna=" + this.codigo_nf_interna + ", caminho_nf_interna=" + this.caminho_nf_interna + ", observacao=" + this.observacao + ", peso_romaneio=" + this.peso_romaneio + ", peso_nf_venda1=" + this.peso_nf_venda1 + ", peso_nf_complemento=" + this.peso_nf_complemento + ", peso_nf_interna=" + this.peso_nf_interna + ", valor_nf_venda1=" + this.valor_nf_venda1 + ", valor_nf_complemento=" + this.valor_nf_complemento + ", peso_real_carga=" + this.peso_real_carga + "]";
        }

        public String getCodigo_contrato() {
            return this.codigo_contrato;
        }

        public void setCodigo_contrato(String codigo_contrato) {
            this.codigo_contrato = codigo_contrato;
        }

        public String getNome_transportador() {
            return this.nome_transportador;
        }

        public void setNome_transportador(String nome_transportador) {
            this.nome_transportador = nome_transportador;
        }

        public String getNome_comprador() {
            return this.nome_comprador;
        }

        public void setNome_comprador(String nome_comprador) {
            this.nome_comprador = nome_comprador;
        }

        public String getNome_vendedor() {
            return this.nome_vendedor;
        }

        public void setNome_vendedor(String nome_vendedor) {
            this.nome_vendedor = nome_vendedor;
        }

        public String getNome_produto() {
            return this.nome_produto;
        }

        public void setNome_produto(String nome_produto) {
            this.nome_produto = nome_produto;
        }

        public String getPlaca_veiculo() {
            return this.placa_veiculo;
        }

        public void setPlaca_veiculo(String placa_veiculo) {
            this.placa_veiculo = placa_veiculo;
        }

        public String getNome_remetente_nf_venda1() {
            return this.nome_remetente_nf_venda1;
        }

        public void setNome_remetente_nf_venda1(String nome_remetente_nf_venda1) {
            this.nome_remetente_nf_venda1 = nome_remetente_nf_venda1;
        }

        public String getNome_destinatario_nf_venda1() {
            return this.nome_destinatario_nf_venda1;
        }

        public void setNome_destinatario_nf_venda1(String nome_destinatario_nf_venda1) {
            this.nome_destinatario_nf_venda1 = nome_destinatario_nf_venda1;
        }

        public String getNome_remetente_nf_complemento() {
            return this.nome_remetente_nf_complemento;
        }

        public void setNome_remetente_nf_complemento(String nome_remetente_nf_complemento) {
            this.nome_remetente_nf_complemento = nome_remetente_nf_complemento;
        }

        public String getNome_destinatario_nf_complemento() {
            return this.nome_destinatario_nf_complemento;
        }

        public void setNome_destinatario_nf_complemento(String nome_destinatario_nf_complemento) {
            this.nome_destinatario_nf_complemento = nome_destinatario_nf_complemento;
        }

        public int getNf_venda1_aplicavel() {
            return this.nf_venda1_aplicavel;
        }

        public void setNf_venda1_aplicavel(int nf_venda1_aplicavel) {
            this.nf_venda1_aplicavel = nf_venda1_aplicavel;
        }

        public int getNf_complemento_aplicavel() {
            return this.nf_complemento_aplicavel;
        }

        public void setNf_complemento_aplicavel(int nf_complemento_aplicavel) {
            this.nf_complemento_aplicavel = nf_complemento_aplicavel;
        }

        public int getNf_interna_aplicavel() {
            return this.nf_interna_aplicavel;
        }

        public void setNf_interna_aplicavel(int nf_interna_aplicavel) {
            this.nf_interna_aplicavel = nf_interna_aplicavel;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public BigDecimal getValor_nf_venda1() {
            return this.valor_nf_venda1;
        }

        public void setValor_nf_venda1(BigDecimal valor_nf_venda1) {
            this.valor_nf_venda1 = valor_nf_venda1;
        }

        public BigDecimal getValor_nf_complemento() {
            return this.valor_nf_complemento;
        }

        public void setValor_nf_complemento(BigDecimal valor_nf_complemento) {
            this.valor_nf_complemento = valor_nf_complemento;
        }

        public String getCodigo_romaneio() {
            return this.codigo_romaneio;
        }

        public void setCodigo_romaneio(String codigo_romaneio) {
            this.codigo_romaneio = codigo_romaneio;
        }

        public String getCaminho_romaneio() {
            return this.caminho_romaneio;
        }

        public void setCaminho_romaneio(String caminho_romaneio) {
            this.caminho_romaneio = caminho_romaneio;
        }

        public String getCodigo_nf_venda1() {
            return this.codigo_nf_venda1;
        }

        public void setCodigo_nf_venda1(String codigo_nf_venda1) {
            this.codigo_nf_venda1 = codigo_nf_venda1;
        }

        public String getCaminho_nf_venda1() {
            return this.caminho_nf_venda1;
        }

        public void setCaminho_nf_venda1(String caminho_nf_venda1) {
            this.caminho_nf_venda1 = caminho_nf_venda1;
        }

        public String getCodigo_nf_complemento() {
            return this.codigo_nf_complemento;
        }

        public void setCodigo_nf_complemento(String codigo_nf_complemento) {
            this.codigo_nf_complemento = codigo_nf_complemento;
        }

        public String getCaminho_nf_complemento() {
            return this.caminho_nf_complemento;
        }

        public void setCaminho_nf_complemento(String caminho_nf_complemento) {
            this.caminho_nf_complemento = caminho_nf_complemento;
        }

        public String getCodigo_nf_interna() {
            return this.codigo_nf_interna;
        }

        public void setCodigo_nf_interna(String codigo_nf_interna) {
            this.codigo_nf_interna = codigo_nf_interna;
        }

        public String getCaminho_nf_interna() {
            return this.caminho_nf_interna;
        }

        public void setCaminho_nf_interna(String caminho_nf_interna) {
            this.caminho_nf_interna = caminho_nf_interna;
        }

        public String getObservacao() {
            return this.observacao;
        }

        public void setObservacao(String observacao) {
            this.observacao = observacao;
        }

        public double getPeso_romaneio() {
            return this.peso_romaneio;
        }

        public void setPeso_romaneio(double peso_romaneio) {
            this.peso_romaneio = peso_romaneio;
        }

        public double getPeso_nf_venda1() {
            return this.peso_nf_venda1;
        }

        public void setPeso_nf_venda1(double peso_nf_venda1) {
            this.peso_nf_venda1 = peso_nf_venda1;
        }

        public double getPeso_nf_complemento() {
            return this.peso_nf_complemento;
        }

        public void setPeso_nf_complemento(double peso_nf_complemento) {
            this.peso_nf_complemento = peso_nf_complemento;
        }

        public double getPeso_nf_interna() {
            return this.peso_nf_interna;
        }

        public void setPeso_nf_interna(double peso_nf_interna) {
            this.peso_nf_interna = peso_nf_interna;
        }

        public String getCaminho_nota_fiscal() {
            return this.caminho_nota_fiscal;
        }

        public void setCaminho_nota_fiscal(String caminho_nota_fiscal) {
            this.caminho_nota_fiscal = caminho_nota_fiscal;
        }

        public int getId_vendedor() {
            return this.id_vendedor;
        }

        public void setId_vendedor(int id_vendedor) {
            this.id_vendedor = id_vendedor;
        }

        public int getId_produto() {
            return this.id_produto;
        }

        public void setId_produto(int id_produto) {
            this.id_produto = id_produto;
        }

        public int getId_carregamento() {
            return this.id_carregamento;
        }

        public void setId_carregamento(int id_carregamento) {
            this.id_carregamento = id_carregamento;
        }

        public int getId_cliente() {
            return this.id_cliente;
        }

        public void setId_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
        }

        public int getId_transportador() {
            return this.id_transportador;
        }

        public void setId_transportador(int id_transportador) {
            this.id_transportador = id_transportador;
        }

        public int getId_veiculo() {
            return this.id_veiculo;
        }

        public void setId_veiculo(int id_veiculo) {
            this.id_veiculo = id_veiculo;
        }

        public int getId_contrato() {
            return this.id_contrato;
        }

        public void setId_contrato(int id_contrato) {
            this.id_contrato = id_contrato;
        }

        public String getCodigo_nota_fiscal() {
            return this.codigo_nota_fiscal;
        }

        public void setCodigo_nota_fiscal(String codigo_nota_fiscal) {
            this.codigo_nota_fiscal = codigo_nota_fiscal;
        }

        public String getData() {
            return this.data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public double getPeso_real_carga() {
            return this.peso_real_carga;
        }

        public void setPeso_real_carga(double peso_real_carga) {
            this.peso_real_carga = peso_real_carga;
        }
    }

    public static class Recebimento {
        String nome_transportador;
        String placa_veiculo;
        String nome_cliente;
        String nome_vendedor;
        int id_recebimento;
        int id_contrato_recebimento;
        int id_cliente;
        int id_transportador;
        int id_veiculo;
        int id_vendedor;
        int nf_venda_aplicavel;
        int nf_remessa_aplicavel;
        String data_recebimento;
        String codigo_romaneio;
        String caminho_romaneio;
        String codigo_nf_venda;
        String caminho_nf_venda;
        String codigo_nf_remessa;
        String caminho_nf_remessa;
        String nome_remetente_nf_remessa;
        String nome_destinatario_nf_remessa;
        String nome_remetente_nf_venda;
        String nome_destinatario_nf_venda;
        BigDecimal valor_nf_venda;
        BigDecimal valor_nf_remessa;
        double peso_romaneio;
        double peso_nf_venda;
        double peso_nf_remessa;

        public String getNome_cliente() {
            return this.nome_cliente;
        }

        public void setNome_cliente(String nome_cliente) {
            this.nome_cliente = nome_cliente;
        }

        public String getNome_vendedor() {
            return this.nome_vendedor;
        }

        public void setNome_vendedor(String nome_vendedor) {
            this.nome_vendedor = nome_vendedor;
        }

        public String getNome_transportador() {
            return this.nome_transportador;
        }

        public void setNome_transportador(String nome_transportador) {
            this.nome_transportador = nome_transportador;
        }

        public String getPlaca_veiculo() {
            return this.placa_veiculo;
        }

        public void setPlaca_veiculo(String placa_veiculo) {
            this.placa_veiculo = placa_veiculo;
        }

        public int getId_vendedor() {
            return this.id_vendedor;
        }

        public int getNf_venda_aplicavel() {
            return this.nf_venda_aplicavel;
        }

        public void setNf_venda_aplicavel(int nf_venda_aplicavel) {
            this.nf_venda_aplicavel = nf_venda_aplicavel;
        }

        public int getNf_remessa_aplicavel() {
            return this.nf_remessa_aplicavel;
        }

        public void setNf_remessa_aplicavel(int nf_remessa_aplicavel) {
            this.nf_remessa_aplicavel = nf_remessa_aplicavel;
        }

        public void setId_vendedor(int id_vendedor) {
            this.id_vendedor = id_vendedor;
        }

        public String getNome_remetente_nf_remessa() {
            return this.nome_remetente_nf_remessa;
        }

        public void setNome_remetente_nf_remessa(String nome_remetente_nf_remessa) {
            this.nome_remetente_nf_remessa = nome_remetente_nf_remessa;
        }

        public String getNome_destinatario_nf_remessa() {
            return this.nome_destinatario_nf_remessa;
        }

        public void setNome_destinatario_nf_remessa(String nome_destinatario_nf_remessa) {
            this.nome_destinatario_nf_remessa = nome_destinatario_nf_remessa;
        }

        public String getNome_remetente_nf_venda() {
            return this.nome_remetente_nf_venda;
        }

        public void setNome_remetente_nf_venda(String nome_remetente_nf_venda) {
            this.nome_remetente_nf_venda = nome_remetente_nf_venda;
        }

        public String getNome_destinatario_nf_venda() {
            return this.nome_destinatario_nf_venda;
        }

        public void setNome_destinatario_nf_venda(String nome_destinatario_nf_venda) {
            this.nome_destinatario_nf_venda = nome_destinatario_nf_venda;
        }

        public BigDecimal getValor_nf_venda() {
            return this.valor_nf_venda;
        }

        public void setValor_nf_venda(BigDecimal valor_nf_venda) {
            this.valor_nf_venda = valor_nf_venda;
        }

        public BigDecimal getValor_nf_remessa() {
            return this.valor_nf_remessa;
        }

        public void setValor_nf_remessa(BigDecimal valor_nf_remessa) {
            this.valor_nf_remessa = valor_nf_remessa;
        }

        public int getId_recebimento() {
            return this.id_recebimento;
        }

        public void setId_recebimento(int id_recebimento) {
            this.id_recebimento = id_recebimento;
        }

        public int getId_contrato_recebimento() {
            return this.id_contrato_recebimento;
        }

        public void setId_contrato_recebimento(int id_contrato_recebimento) {
            this.id_contrato_recebimento = id_contrato_recebimento;
        }

        public int getId_cliente() {
            return this.id_cliente;
        }

        public void setId_cliente(int id_cliente) {
            this.id_cliente = id_cliente;
        }

        public int getId_transportador() {
            return this.id_transportador;
        }

        public void setId_transportador(int id_transportador) {
            this.id_transportador = id_transportador;
        }

        public int getId_veiculo() {
            return this.id_veiculo;
        }

        public void setId_veiculo(int id_veiculo) {
            this.id_veiculo = id_veiculo;
        }

        public String getData_recebimento() {
            return this.data_recebimento;
        }

        public void setData_recebimento(String data_recebimento) {
            this.data_recebimento = data_recebimento;
        }

        public String getCodigo_romaneio() {
            return this.codigo_romaneio;
        }

        public void setCodigo_romaneio(String codigo_romaneio) {
            this.codigo_romaneio = codigo_romaneio;
        }

        public String getCaminho_romaneio() {
            return this.caminho_romaneio;
        }

        public void setCaminho_romaneio(String caminho_romaneio) {
            this.caminho_romaneio = caminho_romaneio;
        }

        public String getCodigo_nf_venda() {
            return this.codigo_nf_venda;
        }

        public void setCodigo_nf_venda(String codigo_nf_venda) {
            this.codigo_nf_venda = codigo_nf_venda;
        }

        public String getCaminho_nf_venda() {
            return this.caminho_nf_venda;
        }

        public void setCaminho_nf_venda(String caminho_nf_venda) {
            this.caminho_nf_venda = caminho_nf_venda;
        }

        public String getCodigo_nf_remessa() {
            return this.codigo_nf_remessa;
        }

        public void setCodigo_nf_remessa(String codigo_nf_remessa) {
            this.codigo_nf_remessa = codigo_nf_remessa;
        }

        public String getCaminho_nf_remessa() {
            return this.caminho_nf_remessa;
        }

        public void setCaminho_nf_remessa(String caminho_nf_remessa) {
            this.caminho_nf_remessa = caminho_nf_remessa;
        }

        public double getPeso_romaneio() {
            return this.peso_romaneio;
        }

        public void setPeso_romaneio(double peso_romaneio) {
            this.peso_romaneio = peso_romaneio;
        }

        public double getPeso_nf_venda() {
            return this.peso_nf_venda;
        }

        public void setPeso_nf_venda(double peso_nf_venda) {
            this.peso_nf_venda = peso_nf_venda;
        }

        public double getPeso_nf_remessa() {
            return this.peso_nf_remessa;
        }

        public void setPeso_nf_remessa(double peso_nf_remessa) {
            this.peso_nf_remessa = peso_nf_remessa;
        }
    }
}

