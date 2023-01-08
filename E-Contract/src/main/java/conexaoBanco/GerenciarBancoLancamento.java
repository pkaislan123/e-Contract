/*
 * Decompiled with CFR 0.151.
 */
package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import main.java.cadastros.DreAgrupado;
import main.java.cadastros.DreSimples;
import main.java.cadastros.Lancamento;
import main.java.conexaoBanco.ConexaoBanco;

public class GerenciarBancoLancamento {
    public String sql_dado(Lancamento dado) {
        return "insert into lancamento (tipo_lancamento, prioridade, data_lancamento, id_conta, id_centro_custo,id_cliente_fornecedor, identificacao,valor_total, numero_parcelas, data_primeiro_vencimento, intervalo, gerar_parcelas, status,observacao,descricao, caminho_arquivo, diretorio_lancamento, contador) values ('" + dado.getTipo_lancamento() + "','" + dado.getPrioridade() + "','" + dado.getData_lancamento() + "','" + dado.getId_conta() + "','" + dado.getId_centro_custo() + "','" + dado.getId_cliente_fornecedor() + "','" + dado.getIdentificacao() + "','" + dado.getValor() + "','" + dado.getNumero_parcelas() + "','" + dado.getData_vencimento() + "','" + dado.getIntervalo() + "','" + dado.getGerar_parcelas() + "','" + dado.getStatus() + "','" + dado.getObservacao() + "','" + dado.getDescricao() + "','" + dado.getDiretorio_lancamento() + "','" + dado.getCaminho_arquivo() + "','" + 0 + "')";
    }

    public int inserirLancamento(Lancamento dado) {
        int result = -1;
        int id_cliente = -1;
        if (dado != null) {
            Connection conn = null;
            try {
                conn = ConexaoBanco.getConexao();
                String query = this.sql_dado(dado);
                Statement stmt = conn.createStatement();
                int numero = stmt.executeUpdate(query, 1);
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                    System.out.println("Id do lan\u00e7amento inserido: " + result);
                }
                rs.close();
                stmt.close();
                return result;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir o lan\u00e7amento no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause());
                return -1;
            }
        }
        System.out.println("O parametro enviado por parametro esta vazio");
        return -1;
    }

    public ArrayList<Lancamento> getLancamentos() {
        String select = "select * from lancamento";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setId_conta(rs.getInt("id_conta"));
                dado.setId_centro_custo(rs.getInt("id_centro_custo"));
                dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
                dado.setGerar_parcelas(rs.getInt("gerar_parcelas"));
                dado.setIntervalo(rs.getInt("intervalo"));
                dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setId_documento(rs.getInt("id_documento"));
                try {
                    dado.setValor(new BigDecimal(rs.getString("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_primeiro_vencimento"));
                dado.setStatus(rs.getInt("status"));
                dado.setObservacao(rs.getString("observacao"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
                dado.setDiretorio_lancamento(rs.getString("diretorio_lancamento"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosCompletos() {
        String select = "call busca_lancamentos()";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setIds_forma_pagamento(rs.getString("ids_condicao_pagamento"));
                dado.setStatus_forma_pagamento(rs.getString("status_condicao_pagamento"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_proximo_pagamento_a_vencer(new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
                }
                catch (Exception e) {
                    dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosCompletosPorCliente(int id_func, int mes, int ano) {
        String select = "call busca_lancamentos_por_cliente(?,?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, id_func);
            pstm.setInt(2, mes);
            pstm.setInt(3, ano);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setIds_forma_pagamento(rs.getString("ids_condicao_pagamento"));
                dado.setStatus_forma_pagamento(rs.getString("status_condicao_pagamento"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_proximo_pagamento_a_vencer(new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
                }
                catch (Exception e) {
                    dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosSimples() {
        String select = "call busca_lancamentos_mais_rapidos()";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
                try {
                    dado.setValor_proximo_pagamento_a_vencer(new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
                }
                catch (Exception e) {
                    dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
                }
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosCompletosFiltrados(int cc, int mes, int ano, int tipo) {
        String select = "call busca_lancamentos_filtrados(?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, cc);
            pstm.setInt(2, mes);
            pstm.setInt(3, ano);
            pstm.setInt(4, tipo);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setIds_forma_pagamento(rs.getString("ids_condicao_pagamento"));
                dado.setStatus_forma_pagamento(rs.getString("status_condicao_pagamento"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_proximo_pagamento_a_vencer(new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
                }
                catch (Exception e) {
                    dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosCompletosPaginados(int inicio, int fim) {
        String select = "call busca_lancamentos_paginados(?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, inicio);
            pstm.setInt(2, fim);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setIds_forma_pagamento(rs.getString("ids_condicao_pagamento"));
                dado.setStatus_forma_pagamento(rs.getString("status_condicao_pagamento"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_proximo_pagamento_a_vencer(new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
                }
                catch (Exception e) {
                    dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentosCompletosGrafico(int flag_despesa_receita, int flag_conta_grupo_contas, int flag_status) {
        String select = "call busca_lancamentos_grafico(?,?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, flag_despesa_receita);
            pstm.setInt(2, flag_conta_grupo_contas);
            pstm.setInt(3, flag_status);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setIds_forma_pagamento(rs.getString("ids_condicao_pagamento"));
                dado.setStatus_forma_pagamento(rs.getString("status_condicao_pagamento"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public ArrayList<Lancamento> buscaLancamentos() {
        String select = "\r\nselect id_lancamento, tipo_lancamento, la.contador, la.id_destinatario_nf, la.identificacao, la.descricao,prioridade, data_lancamento, valor_total,\r\n(select sum(valor) from financeiro_pagamento where id_lancamento_pai = la.id_lancamento\r\ngroup by id_lancamento\r\norder by id_lancamento) as valor_ja_pago,\r\n(select data_vencimento from parcela where parcela.id_lancamento_pai = la.id_lancamento and status = 0 limit 1)\r\nas data_proximo_vencimento,\r\n(select DATE_FORMAT(\r\n(select str_to_date(data_pagamento, '%d/%m/%Y') as data\r\nfrom financeiro_pagamento\r\nwhere data_pagamento != '' and financeiro_pagamento.id_lancamento_pai = la.id_lancamento\r\norder by data DESC\r\nlimit 1), '%d/%m/%Y') as maior_data_pagamento ) as data_ultimo_pagamento,\r\nla.status,\r\nfc.nome_conta,\r\nfgc.nome_grupo_contas,\r\ncc.nome_centro_custo,\r\ncase\r\nwhen cliente_fornecedor.tipo_cliente = '0' then cliente_fornecedor.nome_empresarial \r\n when cliente_fornecedor.tipo_cliente = '1' then cliente_fornecedor.nome_fantasia\r\nend as cliente_fornecedor\r\n\r\nfrom lancamento la\r\n\r\nLEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\nLEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\nLEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\nLEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\n\r\norder by id_lancamento;\r\n";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<Lancamento> lista = new ArrayList<Lancamento>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Lancamento dado = new Lancamento();
                dado.setId_lancamento(rs.getInt("id_lancamento"));
                dado.setPrioridade(rs.getInt("prioridade"));
                dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
                dado.setData_lancamento(rs.getString("data_lancamento"));
                dado.setNome_conta(rs.getString("nome_conta"));
                dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
                dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
                dado.setContador(rs.getInt("contador"));
                dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
                try {
                    dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
                }
                catch (Exception e) {
                    dado.setValor(BigDecimal.ZERO);
                }
                try {
                    dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
                }
                catch (Exception e) {
                    dado.setValor_ja_pago(BigDecimal.ZERO);
                }
                dado.setData_vencimento(rs.getString("data_proximo_vencimento"));
                dado.setData_pagamento(rs.getString("data_ultimo_pagamento"));
                dado.setStatus(rs.getInt("status"));
                dado.setIdentificacao(rs.getString("identificacao"));
                dado.setDescricao(rs.getString("descricao"));
                lista.add(dado);
            }
            ConexaoBanco.fechaConexao(conn, pstm, rs);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
        }
        return lista;
    }

    public Lancamento getLancamento(int id) {
        String select = "select * from lancamento where id_lancamento = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Lancamento dado = new Lancamento();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            rs.next();
            dado.setId_lancamento(rs.getInt("id_lancamento"));
            dado.setPrioridade(rs.getInt("prioridade"));
            dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
            dado.setData_lancamento(rs.getString("data_lancamento"));
            dado.setId_conta(rs.getInt("id_conta"));
            dado.setId_centro_custo(rs.getInt("id_centro_custo"));
            dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
            dado.setGerar_parcelas(rs.getInt("gerar_parcelas"));
            dado.setIntervalo(rs.getInt("intervalo"));
            dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
            dado.setContador(rs.getInt("contador"));
            dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
            dado.setId_documento(rs.getInt("id_documento"));
            try {
                dado.setValor(new BigDecimal(rs.getString("valor_total")));
            }
            catch (Exception e) {
                dado.setValor(BigDecimal.ZERO);
            }
            dado.setData_vencimento(rs.getString("data_primeiro_vencimento"));
            dado.setStatus(rs.getInt("status"));
            dado.setObservacao(rs.getString("observacao"));
            dado.setIdentificacao(rs.getString("identificacao"));
            dado.setDescricao(rs.getString("descricao"));
            dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
            dado.setDiretorio_lancamento(rs.getString("diretorio_lancamento"));
            return dado;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
            return null;
        }
    }

    public Lancamento getLancamentoParaRelatorio(int id) {
        String select = "select id_lancamento, tipo_lancamento, la.contador, la.id_destinatario_nf,\r\n(\r\ncase\r\nwhen destinatario_nf.tipo_cliente = '0' then destinatario_nf.nome_empresarial \r\n when destinatario_nf.tipo_cliente = '1' then destinatario_nf.nome_fantasia\r\nend\r\n\r\n) as nome_destinatario_nf,\r\n la.identificacao, la.descricao,prioridade, data_lancamento, la.data_primeiro_vencimento,la.id_centro_custo, la.id_cliente_fornecedor,\r\n la.observacao, la.numero_parcelas, la.intervalo, la.valor_total,\r\n (\r\nselect ids_condicao_pagamento from\r\n(\r\nSELECT id_lancamento_pai, group_concat(`id_forma_pagamento`) as ids_condicao_pagamento\r\nFROM financeiro_pagamento  \r\nGROUP BY id_lancamento_pai\r\n) as teste where id_lancamento_pai = la.id_lancamento\r\n) as ids_condicao_pagamento,\r\n(\r\nselect status_condicao_pagamento from\r\n(\r\nSELECT id_lancamento_pai, group_concat(`status_condicao_pagamento`) as status_condicao_pagamento\r\nFROM financeiro_pagamento  \r\nGROUP BY id_lancamento_pai\r\n) as teste where id_lancamento_pai = la.id_lancamento\r\n) as status_condicao_pagamento,\r\nla.status,\r\nfc.nome_conta,\r\nfgc.nome_grupo_contas,\r\ncase\r\nwhen la.tipo_lancamento != 2 then cc.nome_centro_custo\r\nwhen la.tipo_lancamento = 2 then remetente.nome_instituicao_bancaria\r\nend as nome_centro_custo,\r\ncase\r\nwhen cliente_fornecedor.tipo_cliente = '0' and la.tipo_lancamento != 2 then cliente_fornecedor.nome_empresarial \r\nwhen cliente_fornecedor.tipo_cliente = '1' and la.tipo_lancamento != 2 then cliente_fornecedor.nome_fantasia\r\nwhen la.tipo_lancamento = 2 then destinatario.nome_instituicao_bancaria\r\n\r\nend as cliente_fornecedor\r\n from lancamento la\r\nLEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\nLEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\nLEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\nLEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\nLEFT JOIN cliente destinatario_nf on destinatario_nf.id_cliente = la.id_destinatario_nf\r\nleft join instituicao_bancaria remetente on remetente.id_instituicao_bancaria = la.id_centro_custo\r\nleft join instituicao_bancaria destinatario on destinatario.id_instituicao_bancaria = la.id_cliente_fornecedor\r\n\r\nwhere id_lancamento = ?";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Lancamento dado = new Lancamento();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, id);
            rs = pstm.executeQuery();
            rs.next();
            dado.setId_lancamento(rs.getInt("id_lancamento"));
            dado.setPrioridade(rs.getInt("prioridade"));
            dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
            dado.setData_lancamento(rs.getString("data_lancamento"));
            dado.setId_centro_custo(rs.getInt("id_centro_custo"));
            dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
            dado.setNome_conta(rs.getString("nome_conta"));
            dado.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
            dado.setNome_centro_custo(rs.getString("nome_centro_custo"));
            dado.setNome_cliente_fornecedor(rs.getString("cliente_fornecedor"));
            dado.setContador(rs.getInt("contador"));
            dado.setId_detinatario_nf(rs.getInt("id_destinatario_nf"));
            dado.setNome_destinatario_nf(rs.getString("nome_destinatario_nf"));
            dado.setData_vencimento(rs.getString("data_primeiro_vencimento"));
            dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
            dado.setObservacao(rs.getString("observacao"));
            dado.setIntervalo(rs.getInt("intervalo"));
            try {
                dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
            }
            catch (Exception e) {
                dado.setValor(BigDecimal.ZERO);
            }
            dado.setData_vencimento(rs.getString("data_primeiro_vencimento"));
            dado.setStatus(rs.getInt("status"));
            dado.setIdentificacao(rs.getString("identificacao"));
            dado.setDescricao(rs.getString("descricao"));
            return dado;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o lan\u00e7amento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
            return null;
        }
    }

    public boolean removerLancamento(int id) {
        String delete = "DELETE FROM lancamento WHERE id_lancamento = ?";
        Connection conn = null;
        Object rs = null;
        try {
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(delete);
            pstm.setInt(1, id);
            pstm.execute();
            ConexaoBanco.fechaConexao(conn, pstm);
            JOptionPane.showMessageDialog(null, "Lan\u00e7amento Exclu\u00eddo, banco normalizado ");
            return true;
        }
        catch (Exception f) {
            JOptionPane.showMessageDialog(null, "Erro ao exlcuir o lan\u00e7amento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistemadados " + f.getMessage());
            return false;
        }
    }

    public boolean atualizarCaminhoLancamento(String caminho_arquivo, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set caminho_arquivo = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setString(1, caminho_arquivo);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public boolean atualizarIdDocuemnto(int id_documento, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set id_documento = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, id_documento);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public boolean atualizarDiretorioLancamento(String diretorio_lancamento, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set diretorio_lancamento = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setString(1, diretorio_lancamento);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public boolean atualizarLancamento(Lancamento dado) {
        if (dado != null) {
            try {
                Connection conn = null;
                String atualizar = null;
                atualizar = "update lancamento set tipo_lancamento = ? , prioridade = ?, data_lancamento = ?, id_conta = ?,id_centro_custo = ?,id_cliente_fornecedor = ?, identificacao = ?,valor_total = ?, numero_parcelas = ?,data_primeiro_vencimento = ?,intervalo = ?, status = ?,observacao = ?,descricao = ?  where id_lancamento = ?";
                conn = ConexaoBanco.getConexao();
                PreparedStatement pstm = conn.prepareStatement(atualizar);
                pstm.setInt(1, dado.getTipo_lancamento());
                pstm.setInt(2, dado.getPrioridade());
                pstm.setString(3, dado.getData_lancamento());
                pstm.setInt(4, dado.getId_conta());
                pstm.setInt(5, dado.getId_centro_custo());
                pstm.setInt(6, dado.getId_cliente_fornecedor());
                pstm.setString(7, dado.getIdentificacao());
                pstm.setString(8, dado.getValor().toString());
                pstm.setInt(9, dado.getNumero_parcelas());
                pstm.setString(10, dado.getData_vencimento());
                pstm.setInt(11, dado.getIntervalo());
                pstm.setInt(12, dado.getStatus());
                pstm.setString(13, dado.getObservacao());
                pstm.setString(14, dado.getDescricao());
                pstm.setInt(15, dado.getId_lancamento());
                pstm.execute();
                System.out.println("Lan\u00e7amento Atualizada com sucesso");
                ConexaoBanco.fechaConexao(conn);
                return true;
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
                return false;
            }
        }
        JOptionPane.showMessageDialog(null, "Os parametros est\u00e3o vazios");
        return false;
    }

    public boolean atualizarContadorLancamento(int status_contador, int id_destinatario_nf, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set contador = ?, id_destinatario_nf = ?  where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, status_contador);
            pstm.setInt(2, id_destinatario_nf);
            pstm.setInt(3, id_lancamento);
            pstm.execute();
            System.out.println("Lan\u00e7amento Atualizada com sucesso");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public boolean atualizarValorLancamento(String valor, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set valor_total = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setString(1, valor);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            System.out.println("Valor total do Lan\u00e7amento!");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public boolean atualizarStatusLancamento(int status, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set status = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, status);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            System.out.println("Valor total do Lan\u00e7amento!");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public Map<String, String> pegarDatas() {
        String select = "call getDatas()";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Lancamento dado = new Lancamento();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            rs.next();
            HashMap<String, String> example = new HashMap<String, String>();
            example.put("menor_data_lancamento", new String(rs.getString("menor_data_lancamento")));
            example.put("maior_data_lancamento", new String(rs.getString("maior_data_lancamento")));
            example.put("menor_data_vencimento", new String(rs.getString("menor_data_vencimento")));
            example.put("maior_data_vencimento", new String(rs.getString("maior_data_vencimento")));
            example.put("menor_data_pagamento", new String(rs.getString("menor_data_pagamento")));
            example.put("maior_data_pagamento", new String(rs.getString("maior_data_pagamento")));
            return example;
        }
        catch (Exception e) {
            return null;
        }
    }

    public Map<Integer, Double> busca_lancamentos_grafico_linha_despesa_receita(int flag_despesa_receita, int ano) {
        String select = "call busca_lancamentos_grafico_linha(?,?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        HashMap<Integer, Double> lista = new HashMap<Integer, Double>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, flag_despesa_receita);
            pstm.setInt(2, ano);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String mes_lancamento = rs.getString("mes_vencimento");
                try {
                    lista.put(Integer.parseInt(mes_lancamento), rs.getDouble("valor_total"));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return lista;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar a maior data de vencimento do banco de dados");
            return null;
        }
    }

    public boolean atualizarStatusContadorLancamento(int status_contador, int id_lancamento) {
        try {
            Connection conn = null;
            String atualizar = null;
            atualizar = "update lancamento set contador = ? where id_lancamento = ?";
            conn = ConexaoBanco.getConexao();
            PreparedStatement pstm = conn.prepareStatement(atualizar);
            pstm.setInt(1, status_contador);
            pstm.setInt(2, id_lancamento);
            pstm.execute();
            System.out.println("Lan\u00e7amento Atualizada com sucesso");
            ConexaoBanco.fechaConexao(conn);
            return true;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o lan\u00e7amento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return false;
        }
    }

    public ArrayList<DreSimples> getDreSimplesCC(int ano, int id_centro_custo) {
        String select = "call consulta_dre_simples_por_cc(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita"));
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public ArrayList<DreSimples> getDreSimplesCCRc(int ano, int id_centro_custo, int id_ib) {
        String select = "call consulta_dre_simples_por_cc_regime_caixa(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            pstm.setInt(3, id_ib);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita") + rs.getDouble("receita_emprestimo"));
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public ArrayList<DreSimples> getDreSimplesCCRp(int ano, int id_centro_custo) {
        String select = "call consulta_dre_simples_por_cc_regime_parcela(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita"));
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public double getSaldoDreSimplesCC(int ano, int id_centro_custo) {
        String select = "call consulta_dre_simples_por_cc(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        double saldo = 0.0;
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita"));
                if (dre.getReceitas() < 0.0) {
                    dre.setReceitas(0.0);
                }
                if (dre.getDespesas() < 0.0) {
                    dre.setDespesas(0.0);
                }
                double total = dre.getReceitas() - dre.getDespesas();
                saldo += total;
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return saldo;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return 0.0;
        }
    }

    public double getSaldoDreSimplesCCRc(int ano, int id_centro_custo, int id_ib) {
        String select = "call consulta_dre_simples_por_cc_regime_caixa(?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        double saldo = 0.0;
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            pstm.setInt(3, id_ib);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita") + rs.getDouble("receita_emprestimo"));
                if (dre.getReceitas() < 0.0) {
                    dre.setReceitas(0.0);
                }
                if (dre.getDespesas() < 0.0) {
                    dre.setDespesas(0.0);
                }
                double total = dre.getReceitas() - dre.getDespesas();
                saldo += total;
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return saldo;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return 0.0;
        }
    }

    public double getSaldoDreSimplesCCRp(int ano, int id_centro_custo) {
        String select = "call consulta_dre_simples_por_cc_regime_parcela(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreSimples> list = new ArrayList<DreSimples>();
        double saldo = 0.0;
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            int mes = 1;
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreSimples dre = new DreSimples();
                dre.setMes(mes);
                dre.setDespesas(rs.getDouble("despesa"));
                dre.setReceitas(rs.getDouble("receita"));
                if (dre.getReceitas() < 0.0) {
                    dre.setReceitas(0.0);
                }
                if (dre.getDespesas() < 0.0) {
                    dre.setDespesas(0.0);
                }
                double total = dre.getReceitas() - dre.getDespesas();
                saldo += total;
                list.add(dre);
                ++mes;
            }
            ConexaoBanco.fechaConexao(conn);
            return saldo;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return 0.0;
        }
    }

    public ArrayList<DreAgrupado> getDreAgrupadoCCRegimeLancamentoReceitas(int ano, int id_centro_custo) {
        String select = "call consulta_dre_agrupado_por_cc_receitas(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreAgrupado> list = new ArrayList<DreAgrupado>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreAgrupado dre = new DreAgrupado();
                dre.setFlag(1);
                dre.setNome_conta(rs.getString("nome_conta"));
                dre.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dre.setValor_receitas_janeiro(rs.getDouble("valor_receita_janeiro"));
                dre.setValor_receitas_fevereiro(rs.getDouble("valor_receita_fevereiro"));
                dre.setValor_receitas_marco(rs.getDouble("valor_receita_marco"));
                dre.setValor_receitas_abril(rs.getDouble("valor_receita_abril"));
                dre.setValor_receitas_maio(rs.getDouble("valor_receita_maio"));
                dre.setValor_receitas_junho(rs.getDouble("valor_receita_junho"));
                dre.setValor_receitas_julho(rs.getDouble("valor_receita_julho"));
                dre.setValor_receitas_agosto(rs.getDouble("valor_receita_agosto"));
                dre.setValor_receitas_setembro(rs.getDouble("valor_receita_setembro"));
                dre.setValor_receitas_outubro(rs.getDouble("valor_receita_outubro"));
                dre.setValor_receitas_novembro(rs.getDouble("valor_receita_novembro"));
                dre.setValor_receitas_dezembro(rs.getDouble("valor_receita_dezembro"));
                list.add(dre);
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE Agrupado Receitas Regime de Lan\u00e7amento, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public ArrayList<DreAgrupado> getDreAgrupadoCCRegimeLancamentoDespesas(int ano, int id_centro_custo) {
        String select = "call consulta_dre_agrupado_por_cc_despesas(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreAgrupado> list = new ArrayList<DreAgrupado>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreAgrupado dre = new DreAgrupado();
                dre.setFlag(0);
                dre.setNome_conta(rs.getString("nome_conta"));
                dre.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dre.setValor_despesas_janeiro(rs.getDouble("valor_despesa_janeiro"));
                dre.setValor_despesas_fevereiro(rs.getDouble("valor_despesa_fevereiro"));
                dre.setValor_despesas_marco(rs.getDouble("valor_despesa_marco"));
                dre.setValor_despesas_abril(rs.getDouble("valor_despesa_abril"));
                dre.setValor_despesas_maio(rs.getDouble("valor_despesa_maio"));
                dre.setValor_despesas_junho(rs.getDouble("valor_despesa_junho"));
                dre.setValor_despesas_julho(rs.getDouble("valor_despesa_julho"));
                dre.setValor_despesas_agosto(rs.getDouble("valor_despesa_agosto"));
                dre.setValor_despesas_setembro(rs.getDouble("valor_despesa_setembro"));
                dre.setValor_despesas_outubro(rs.getDouble("valor_despesa_outubro"));
                dre.setValor_despesas_novembro(rs.getDouble("valor_despesa_novembro"));
                dre.setValor_despesas_dezembro(rs.getDouble("valor_despesa_dezembro"));
                list.add(dre);
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE Agrupado Despesas Regime de Lan\u00e7amento, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public ArrayList<DreAgrupado> getDreAgrupadoCCRegimeParcelaReceitas(int ano, int id_centro_custo) {
        String select = "call consulta_dre_agrupado_por_cc_receitas_regime_parcela(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreAgrupado> list = new ArrayList<DreAgrupado>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreAgrupado dre = new DreAgrupado();
                dre.setFlag(1);
                dre.setNome_conta(rs.getString("nome_conta"));
                dre.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dre.setValor_receitas_janeiro(rs.getDouble("valor_receita_janeiro"));
                dre.setValor_receitas_fevereiro(rs.getDouble("valor_receita_fevereiro"));
                dre.setValor_receitas_marco(rs.getDouble("valor_receita_marco"));
                dre.setValor_receitas_abril(rs.getDouble("valor_receita_abril"));
                dre.setValor_receitas_maio(rs.getDouble("valor_receita_maio"));
                dre.setValor_receitas_junho(rs.getDouble("valor_receita_junho"));
                dre.setValor_receitas_julho(rs.getDouble("valor_receita_julho"));
                dre.setValor_receitas_agosto(rs.getDouble("valor_receita_agosto"));
                dre.setValor_receitas_setembro(rs.getDouble("valor_receita_setembro"));
                dre.setValor_receitas_outubro(rs.getDouble("valor_receita_outubro"));
                dre.setValor_receitas_novembro(rs.getDouble("valor_receita_novembro"));
                dre.setValor_receitas_dezembro(rs.getDouble("valor_receita_dezembro"));
                list.add(dre);
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE Agrupado Receitas Regime de Parcela, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public ArrayList<DreAgrupado> getDreAgrupadoCCRegimeParcelaDespesas(int ano, int id_centro_custo) {
        String select = "call consulta_dre_agrupado_por_cc_despesas_regime_parcela(?, ?)";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ArrayList<DreAgrupado> list = new ArrayList<DreAgrupado>();
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            pstm.setInt(1, ano);
            pstm.setInt(2, id_centro_custo);
            rs = pstm.executeQuery();
            while (rs.next()) {
                DreAgrupado dre = new DreAgrupado();
                dre.setFlag(0);
                dre.setNome_conta(rs.getString("nome_conta"));
                dre.setNome_grupo_contas(rs.getString("nome_grupo_contas"));
                dre.setValor_despesas_janeiro(rs.getDouble("valor_despesa_janeiro"));
                dre.setValor_despesas_fevereiro(rs.getDouble("valor_despesa_fevereiro"));
                dre.setValor_despesas_marco(rs.getDouble("valor_despesa_marco"));
                dre.setValor_despesas_abril(rs.getDouble("valor_despesa_abril"));
                dre.setValor_despesas_maio(rs.getDouble("valor_despesa_maio"));
                dre.setValor_despesas_junho(rs.getDouble("valor_despesa_junho"));
                dre.setValor_despesas_julho(rs.getDouble("valor_despesa_julho"));
                dre.setValor_despesas_agosto(rs.getDouble("valor_despesa_agosto"));
                dre.setValor_despesas_setembro(rs.getDouble("valor_despesa_setembro"));
                dre.setValor_despesas_outubro(rs.getDouble("valor_despesa_outubro"));
                dre.setValor_despesas_novembro(rs.getDouble("valor_despesa_novembro"));
                dre.setValor_despesas_dezembro(rs.getDouble("valor_despesa_dezembro"));
                list.add(dre);
            }
            ConexaoBanco.fechaConexao(conn);
            return list;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar DRE Agrupado Despesas Regime de Parcela, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
            return null;
        }
    }

    public int getNumLancamentos() {
        String select = "select id_lancamento from lancamento order by id_lancamento desc limit 1";
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int quantidade = 0;
        try {
            conn = ConexaoBanco.getConexao();
            pstm = conn.prepareStatement(select);
            rs = pstm.executeQuery();
            rs.next();
            quantidade = rs.getInt("id_lancamento");
            return quantidade;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar o numero de lan\u00e7amentos");
            return -1;
        }
    }
}

