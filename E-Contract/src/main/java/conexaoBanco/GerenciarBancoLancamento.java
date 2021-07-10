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

import main.java.cadastros.DreSimples;
import main.java.cadastros.Lancamento;

public class GerenciarBancoLancamento {

	public String sql_dado(Lancamento dado) {
		/*
		 * CREATE TABLE `lancamento` ( `id_lancamento` int(5) NOT NULL AUTO_INCREMENT,
		 * `tipo_lancamento` int(3) DEFAULT NULL, prioridade int(3), `data_lancamento`
		 * varchar(40) DEFAULT NULL, `id_conta` int(5) DEFAULT NULL,
		 * `id_instituicao_bancaria` varchar(30) DEFAULT NULL, `id_centro_custo`
		 * varchar(30) DEFAULT NULL, `id_cliente_fornecedor` text DEFAULT NULL,
		 * `valor_total` varchar(40) DEFAULT NULL, numero_parcelas int (3),
		 * `data_primeiro_vencimento` varchar(40) DEFAULT NULL, intervalo int(3),
		 * gerar_parcelas int(3), `status` int(3) DEFAULT NULL, `observacao` text
		 * DEFAULT NULL, `descricao` text DEFAULT NULL, PRIMARY KEY (`id_lancamento`) )
		 * ENGINE=InnoDB DEFAULT CHARSET=utf8
		 */
		return "insert into lancamento (tipo_lancamento, prioridade, data_lancamento,"
				+ " id_conta, id_centro_custo,id_cliente_fornecedor, identificacao,"
				+ "valor_total, numero_parcelas, data_primeiro_vencimento, intervalo, gerar_parcelas, status,observacao,descricao, caminho_arquivo, diretorio_lancamento, contador) values ('"
				+ dado.getTipo_lancamento() + "','" + dado.getPrioridade() + "','" + dado.getData_lancamento() + "','"
				+ dado.getId_conta() + "','" + dado.getId_centro_custo() + "','" + dado.getId_cliente_fornecedor()
				+ "','" + dado.getIdentificacao() + "','" + dado.getValor() + "','" + dado.getNumero_parcelas() + "','"
				+ dado.getData_vencimento() + "','" + dado.getIntervalo() + "','" + dado.getGerar_parcelas() + "','"
				+ dado.getStatus() + "','" + dado.getObservacao() + "','" + dado.getDescricao() + "','"
				+ dado.getDiretorio_lancamento() + "','" + dado.getCaminho_arquivo() + "','" + 0 + "')";
	}

	public int inserirLancamento(Lancamento dado) {
		int result = -1;
		int id_cliente = -1;
		if (dado != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_dado(dado);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id do lançamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o lançamento no banco de dados, Erro: "
						+ e.getMessage() + "\nCausa: " + e.getCause());

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<Lancamento> getLancamentos() {
		String select = "select * from lancamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentosCompletos() {

		String select = "call busca_lancamentos()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
					dado.setValor_ja_pago(BigDecimal.ZERO);
				}

				try {
					dado.setValor_proximo_pagamento_a_vencer(
							new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	
	public ArrayList<Lancamento> buscaLancamentosCompletosPorCliente( int id_func, int mes, int ano) {

		String select = "call busca_lancamentos_por_cliente(?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
					dado.setValor_ja_pago(BigDecimal.ZERO);
				}

				try {
					dado.setValor_proximo_pagamento_a_vencer(
							new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentosSimples() {

		String select = "call busca_lancamentos_mais_rapidos()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
					dado.setValor_proximo_pagamento_a_vencer(
							new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
				} catch (Exception e) {
					dado.setValor_proximo_pagamento_a_vencer(BigDecimal.ZERO);
				}

				try {
					dado.setValor(new BigDecimal(rs.getDouble("valor_total")));
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
					dado.setValor_ja_pago(BigDecimal.ZERO);
				}

				dado.setStatus(rs.getInt("status"));
				dado.setIdentificacao(rs.getString("identificacao"));
				dado.setDescricao(rs.getString("descricao"));

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentosCompletosFiltrados(int cc, int mes, int ano, int tipo) {

		String select = "call busca_lancamentos_filtrados(?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
					dado.setValor_ja_pago(BigDecimal.ZERO);
				}

				try {
					dado.setValor_proximo_pagamento_a_vencer(
							new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentosCompletosPaginados(int inicio, int fim) {

		String select = "call busca_lancamentos_paginados(?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
					dado.setValor_ja_pago(BigDecimal.ZERO);
				}

				try {
					dado.setValor_proximo_pagamento_a_vencer(
							new BigDecimal(rs.getDouble("valor_proximo_pagamento_a_vencer")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentosCompletosGrafico(int flag_despesa_receita, int flag_conta_grupo_contas,
			int flag_status) {

		String select = "call busca_lancamentos_grafico(?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<Lancamento> buscaLancamentos() {/*
														 * String select =
														 * "select id_lancamento, tipo_lancamento, la.identificacao, la.descricao,prioridade, data_lancamento, valor_total, data_primeiro_vencimento,la.status,\r\n"
														 * + "fc.nome_conta,\r\n" + "fgc.nome_grupo_contas,\r\n" +
														 * "ib.nome_instituicao_bancaria,\r\n" +
														 * "cc.nome_centro_custo,\r\n" + "case\r\n" +
														 * "when cliente_fornecedor.tipo_cliente = '0' then cliente_fornecedor.nome_empresarial \r\n"
														 * +
														 * " when cliente_fornecedor.tipo_cliente = '1' then cliente_fornecedor.nome_fantasia\r\n"
														 * + "end as cliente_fornecedor\r\n" + "from lancamento la\r\n"
														 * +
														 * "LEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\n"
														 * +
														 * "LEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\n"
														 * +
														 * "LEFT JOIN instituicao_bancaria ib on ib.id_instituicao_bancaria = la.id_instituicao_bancaria\r\n"
														 * +
														 * "LEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\n"
														 * +
														 * "LEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\n"
														 * + "";
														 */
		/*
		 * ultimo funcional String select = "\r\n" +
		 * "select id_lancamento, tipo_lancamento, la.identificacao, la.descricao,prioridade, data_lancamento, valor_total,\r\n"
		 * +
		 * "(select sum(valor) from financeiro_pagamento where id_lancamento = la.id_lancamento\r\n"
		 * + "group by id_lancamento\r\n" +
		 * "order by id_lancamento) as valor_ja_pago,\r\n" +
		 * "(select data_vencimento from parcela where parcela.id_lancamento_pai = la.id_lancamento and status = 0 limit 1)\r\n"
		 * + "as data_proximo_vencimento,\r\n" + "la.status,\r\n" + "fc.nome_conta,\r\n"
		 * + "fgc.nome_grupo_contas,\r\n" + "ib.nome_instituicao_bancaria,\r\n" +
		 * "cc.nome_centro_custo,\r\n" + "case\r\n" +
		 * "when cliente_fornecedor.tipo_cliente = '0' then cliente_fornecedor.nome_empresarial \r\n"
		 * +
		 * " when cliente_fornecedor.tipo_cliente = '1' then cliente_fornecedor.nome_fantasia\r\n"
		 * + "end as cliente_fornecedor\r\n" + "\r\n" + "from lancamento la\r\n" +
		 * "\r\n" + "LEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\n" +
		 * "LEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\n"
		 * +
		 * "LEFT JOIN instituicao_bancaria ib on ib.id_instituicao_bancaria = la.id_instituicao_bancaria\r\n"
		 * + "LEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\n"
		 * +
		 * "LEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\n"
		 * + "\r\n" + "order by id_lancamento;\r\n" + "";
		 */
		String select = "\r\n"
				+ "select id_lancamento, tipo_lancamento, la.contador, la.id_destinatario_nf, la.identificacao, la.descricao,prioridade, data_lancamento, valor_total,\r\n"
				+ "(select sum(valor) from financeiro_pagamento where id_lancamento_pai = la.id_lancamento\r\n"
				+ "group by id_lancamento\r\n" + "order by id_lancamento) as valor_ja_pago,\r\n"
				+ "(select data_vencimento from parcela where parcela.id_lancamento_pai = la.id_lancamento and status = 0 limit 1)\r\n"
				+ "as data_proximo_vencimento,\r\n" + "(select DATE_FORMAT(\r\n"
				+ "(select str_to_date(data_pagamento, '%d/%m/%Y') as data\r\n" + "from financeiro_pagamento\r\n"
				+ "where data_pagamento != '' and financeiro_pagamento.id_lancamento_pai = la.id_lancamento\r\n"
				+ "order by data DESC\r\n"
				+ "limit 1), '%d/%m/%Y') as maior_data_pagamento ) as data_ultimo_pagamento,\r\n" + "la.status,\r\n"
				+ "fc.nome_conta,\r\n" + "fgc.nome_grupo_contas,\r\n" + "cc.nome_centro_custo,\r\n" + "case\r\n"
				+ "when cliente_fornecedor.tipo_cliente = '0' then cliente_fornecedor.nome_empresarial \r\n"
				+ " when cliente_fornecedor.tipo_cliente = '1' then cliente_fornecedor.nome_fantasia\r\n"
				+ "end as cliente_fornecedor\r\n" + "\r\n" + "from lancamento la\r\n" + "\r\n"
				+ "LEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\n"
				+ "LEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\n"
				+ "LEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\n"
				+ "LEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\n"
				+ "\r\n" + "order by id_lancamento;\r\n" + "";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<Lancamento> lista = new ArrayList<>();
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
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}

				try {
					dado.setValor_ja_pago(new BigDecimal(rs.getDouble("valor_ja_pago")));
				} catch (Exception e) {
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
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
			} catch (Exception e) {
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

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public Lancamento getLancamentoParaRelatorio(int id) {
		/*
		 * select id_lancamento, tipo_lancamento, la.contador, la.id_destinatario_nf, (
		 * case when destinatario_nf.tipo_cliente = '0' then
		 * destinatario_nf.nome_empresarial when destinatario_nf.tipo_cliente = '1' then
		 * destinatario_nf.nome_fantasia end
		 * 
		 * ) as nome_destinatario_nf, la.identificacao, la.descricao,prioridade,
		 * data_lancamento, la.data_primeiro_vencimento, la.observacao,
		 * la.numero_parcelas, la.intervalo, ( select ids_condicao_pagamento from (
		 * SELECT id_lancamento_pai, group_concat(`id_forma_pagamento`) as
		 * ids_condicao_pagamento FROM financeiro_pagamento GROUP BY id_lancamento_pai )
		 * as teste where id_lancamento_pai = la.id_lancamento ) as
		 * ids_condicao_pagamento, ( select status_condicao_pagamento from ( SELECT
		 * id_lancamento_pai, group_concat(`status_condicao_pagamento`) as
		 * status_condicao_pagamento FROM financeiro_pagamento GROUP BY
		 * id_lancamento_pai ) as teste where id_lancamento_pai = la.id_lancamento ) as
		 * status_condicao_pagamento, la.status, fc.nome_conta, fgc.nome_grupo_contas,
		 * case when la.tipo_lancamento != 2 then cc.nome_centro_custo when
		 * la.tipo_lancamento = 2 then remetente.nome_instituicao_bancaria end as
		 * nome_centro_custo, case when cliente_fornecedor.tipo_cliente = '0' and
		 * la.tipo_lancamento != 2 then cliente_fornecedor.nome_empresarial when
		 * cliente_fornecedor.tipo_cliente = '1' and la.tipo_lancamento != 2 then
		 * cliente_fornecedor.nome_fantasia when la.tipo_lancamento = 2 then
		 * destinatario.nome_instituicao_bancaria
		 * 
		 * end as cliente_fornecedor from lancamento la LEFT JOIN financeiro_conta fc on
		 * fc.id_conta = la.id_conta LEFT JOIN financeiro_grupo_contas fgc on
		 * fgc.id_grupo_contas = fc.id_grupo_contas LEFT JOIN centro_custo cc on
		 * cc.id_centro_custo = la.id_centro_custo LEFT JOIN cliente cliente_fornecedor
		 * on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor LEFT JOIN cliente
		 * destinatario_nf on destinatario_nf.id_cliente = la.id_destinatario_nf left
		 * join instituicao_bancaria remetente on remetente.id_instituicao_bancaria =
		 * la.id_centro_custo left join instituicao_bancaria destinatario on
		 * destinatario.id_instituicao_bancaria = la.id_cliente_fornecedor
		 * 
		 * where id_lancamento = 12
		 */
		String select = "select id_lancamento, tipo_lancamento, la.contador, la.id_destinatario_nf,\r\n" + "(\r\n"
				+ "case\r\n" + "when destinatario_nf.tipo_cliente = '0' then destinatario_nf.nome_empresarial \r\n"
				+ " when destinatario_nf.tipo_cliente = '1' then destinatario_nf.nome_fantasia\r\n" + "end\r\n" + "\r\n"
				+ ") as nome_destinatario_nf,\r\n"
				+ " la.identificacao, la.descricao,prioridade, data_lancamento, la.data_primeiro_vencimento,la.id_centro_custo, la.id_cliente_fornecedor,\r\n"
				+ " la.observacao, la.numero_parcelas, la.intervalo, la.valor_total,\r\n" + " (\r\n"
				+ "select ids_condicao_pagamento from\r\n" + "(\r\n"
				+ "SELECT id_lancamento_pai, group_concat(`id_forma_pagamento`) as ids_condicao_pagamento\r\n"
				+ "FROM financeiro_pagamento  \r\n" + "GROUP BY id_lancamento_pai\r\n"
				+ ") as teste where id_lancamento_pai = la.id_lancamento\r\n" + ") as ids_condicao_pagamento,\r\n"
				+ "(\r\n" + "select status_condicao_pagamento from\r\n" + "(\r\n"
				+ "SELECT id_lancamento_pai, group_concat(`status_condicao_pagamento`) as status_condicao_pagamento\r\n"
				+ "FROM financeiro_pagamento  \r\n" + "GROUP BY id_lancamento_pai\r\n"
				+ ") as teste where id_lancamento_pai = la.id_lancamento\r\n" + ") as status_condicao_pagamento,\r\n"
				+ "la.status,\r\n" + "fc.nome_conta,\r\n" + "fgc.nome_grupo_contas,\r\n" + "case\r\n"
				+ "when la.tipo_lancamento != 2 then cc.nome_centro_custo\r\n"
				+ "when la.tipo_lancamento = 2 then remetente.nome_instituicao_bancaria\r\n"
				+ "end as nome_centro_custo,\r\n" + "case\r\n"
				+ "when cliente_fornecedor.tipo_cliente = '0' and la.tipo_lancamento != 2 then cliente_fornecedor.nome_empresarial \r\n"
				+ "when cliente_fornecedor.tipo_cliente = '1' and la.tipo_lancamento != 2 then cliente_fornecedor.nome_fantasia\r\n"
				+ "when la.tipo_lancamento = 2 then destinatario.nome_instituicao_bancaria\r\n" + "\r\n"
				+ "end as cliente_fornecedor\r\n" + " from lancamento la\r\n"
				+ "LEFT JOIN financeiro_conta fc on fc.id_conta = la.id_conta\r\n"
				+ "LEFT JOIN financeiro_grupo_contas fgc on fgc.id_grupo_contas = fc.id_grupo_contas\r\n"
				+ "LEFT JOIN centro_custo cc on cc.id_centro_custo = la.id_centro_custo\r\n"
				+ "LEFT JOIN cliente cliente_fornecedor on cliente_fornecedor.id_cliente = la.id_cliente_fornecedor\r\n"
				+ "LEFT JOIN cliente destinatario_nf on destinatario_nf.id_cliente = la.id_destinatario_nf\r\n"
				+ "left join instituicao_bancaria remetente on remetente.id_instituicao_bancaria = la.id_centro_custo\r\n"
				+ "left join instituicao_bancaria destinatario on destinatario.id_instituicao_bancaria = la.id_cliente_fornecedor\r\n"
				+ "\r\n" + "where id_lancamento = ?";
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
			} catch (Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}

			dado.setData_vencimento(rs.getString("data_primeiro_vencimento"));
			dado.setStatus(rs.getInt("status"));
			dado.setIdentificacao(rs.getString("identificacao"));
			dado.setDescricao(rs.getString("descricao"));

			return dado;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o lançamento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerLancamento(int id) {
		String delete = "DELETE FROM lancamento WHERE id_lancamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Lançamento Excluído, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o lançamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarCaminhoLancamento(String caminho_arquivo, int id_lancamento) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set caminho_arquivo = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, caminho_arquivo);
			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarIdDocuemnto(int id_documento, int id_lancamento) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set id_documento = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, id_documento);
			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarDiretorioLancamento(String diretorio_lancamento, int id_lancamento) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set diretorio_lancamento = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, diretorio_lancamento);
			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarLancamento(Lancamento dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
				// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update lancamento set tipo_lancamento = ? , prioridade = ?, data_lancamento = ?, id_conta = ?,"
						+ "id_centro_custo = ?,id_cliente_fornecedor = ?, identificacao = ?,"
						+ "valor_total = ?, numero_parcelas = ?,data_primeiro_vencimento = ?,"
						+ "intervalo = ?, status = ?,observacao = ?,descricao = ?  where id_lancamento = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

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
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Lançamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
						+ e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	public boolean atualizarContadorLancamento(int status_contador, int id_destinatario_nf, int id_lancamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set contador = ?, id_destinatario_nf = ?  where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, status_contador);
			pstm.setInt(2, id_destinatario_nf);

			pstm.setInt(3, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Lançamento Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarValorLancamento(String valor, int id_lancamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set valor_total = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, valor);
			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Valor total do Lançamento!");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarStatusLancamento(int status, int id_lancamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set status = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, status);
			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Valor total do Lançamento!");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public Map<String, String> pegarDatas() {

		/*
		 * String select = "select \r\n" + " DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_lancamento, '%d/%m/%Y') as data\r\n" +
		 * "from lancamento\r\n" + "where data_lancamento != ''\r\n" +
		 * "order by data \r\n" + "limit 1), '%d/%m/%Y') as menor_data_lancamento \r\n"
		 * + ",\r\n" + " DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_lancamento, '%d/%m/%Y') as data\r\n" +
		 * "from lancamento\r\n" + "where data_lancamento != ''\r\n" +
		 * "order by data DESC\r\n" +
		 * "limit 1), '%d/%m/%Y') as maior_data_lancamento \r\n" + ",\r\n" +
		 * "DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_primeiro_vencimento, '%d/%m/%Y') as data\r\n" +
		 * "from lancamento\r\n" + "where data_primeiro_vencimento != ''\r\n" +
		 * "order by data\r\n" + "limit 1), '%d/%m/%Y') as menor_data_vencimento \r\n" +
		 * ",\r\n" + "DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_primeiro_vencimento, '%d/%m/%Y') as data\r\n" +
		 * "from lancamento\r\n" + "where data_primeiro_vencimento != ''\r\n" +
		 * "order by data DESC\r\n" +
		 * "limit 1), '%d/%m/%Y') as maior_data_vencimento \r\n" + ",\r\n" +
		 * "DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_pagamento, '%d/%m/%Y') as data\r\n" +
		 * "from financeiro_pagamento\r\n" + "where data_pagamento != ''\r\n" +
		 * "order by data\r\n" + "limit 1), '%d/%m/%Y') as menor_data_pagamento \r\n" +
		 * ",\r\n" + "DATE_FORMAT(\r\n" +
		 * "(select str_to_date(data_pagamento, '%d/%m/%Y') as data\r\n" +
		 * "from financeiro_pagamento\r\n" + "where data_pagamento != ''\r\n" +
		 * "order by data DESC\r\n" +
		 * "limit 1), '%d/%m/%Y') as maior_data_pagamento \r\n" + "\r\n" + "\r\n" +
		 * "\r\n" + "\r\n" + "\r\n" + "";
		 */
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
			Map<String, String> example = new HashMap<String, String>();
			example.put("menor_data_lancamento", new String(rs.getString("menor_data_lancamento")));
			example.put("maior_data_lancamento", new String(rs.getString("maior_data_lancamento")));
			example.put("menor_data_vencimento", new String(rs.getString("menor_data_vencimento")));
			example.put("maior_data_vencimento", new String(rs.getString("maior_data_vencimento")));
			example.put("menor_data_pagamento", new String(rs.getString("menor_data_pagamento")));
			example.put("maior_data_pagamento", new String(rs.getString("maior_data_pagamento")));

			return example;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a maior data de vencimento do banco de dados");
			return null;
		}
	}

	public Map<Integer, Double> busca_lancamentos_grafico_linha(int flag_despesa_receita, int status) {

		String select = "call busca_lancamentos_grafico_linha(?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Map<Integer, Double> lista = new HashMap<Integer, Double>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, flag_despesa_receita);
			pstm.setInt(2, status);

			rs = pstm.executeQuery();
			while (rs.next()) {

				String mes_lancamento = rs.getString("data_proximo_vencimento");
				try {
					lista.put(Integer.parseInt(mes_lancamento), rs.getDouble("valor_total"));
				} catch (Exception e) {

				}

			}

			return lista;
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a maior data de vencimento do banco de dados");
			return null;
		}
	}

	public boolean atualizarStatusContadorLancamento(int status_contador, int id_lancamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update lancamento set contador = ? where id_lancamento = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setInt(1, status_contador);

			pstm.setInt(2, id_lancamento);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Lançamento Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

	public ArrayList<DreSimples> getDreSimplesCC(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();

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
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return list;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<DreSimples> getDreSimplesCCRc(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc_regime_caixa(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();

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
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return list;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<DreSimples> getDreSimplesCCRp(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc_regime_parcela(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();

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
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return list;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return null;
		}

	}

	public double getSaldoDreSimplesCC(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();
		double saldo = 0;

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

				if (dre.getReceitas() < 0)
					dre.setReceitas(0);

				if (dre.getDespesas() < 0)
					dre.setDespesas(0);

				double total = dre.getReceitas() - dre.getDespesas();

				saldo += total;

				list.add(dre);
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return saldo;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return 0;
		}

	}

	public double getSaldoDreSimplesCCRc(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc_regime_caixa(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();
		double saldo = 0;

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

				if (dre.getReceitas() < 0)
					dre.setReceitas(0);

				if (dre.getDespesas() < 0)
					dre.setDespesas(0);

				double total = dre.getReceitas() - dre.getDespesas();

				saldo += total;

				list.add(dre);
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return saldo;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return 0;
		}

	}

	public double getSaldoDreSimplesCCRp(int ano, int id_centro_custo) {

		String select = "call consulta_dre_simples_por_cc_regime_parcela(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<DreSimples> list = new ArrayList<>();
		double saldo = 0;

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

				if (dre.getReceitas() < 0)
					dre.setReceitas(0);

				if (dre.getDespesas() < 0)
					dre.setDespesas(0);

				double total = dre.getReceitas() - dre.getDespesas();

				saldo += total;

				list.add(dre);
				mes++;

			}
			ConexaoBanco.fechaConexao(conn);

			return saldo;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar Saldo DRE, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return 0;
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

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar o numero de lançamentos");
			return -1;
		}

	}

}
