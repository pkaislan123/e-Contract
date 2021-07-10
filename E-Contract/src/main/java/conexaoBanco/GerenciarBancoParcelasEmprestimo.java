package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.Parcela;
import main.java.cadastros.ParcelaEmprestimo;

public class GerenciarBancoParcelasEmprestimo {

	public String sql_dado(ParcelaEmprestimo dado) {
		/*
		 * 
		 * CREATE TABLE `parcela_emprestimo` ( `id_parcela` int(5) NOT NULL
		 * AUTO_INCREMENT, `id_lancamento_pai` int(5) DEFAULT NULL, `identificador` text
		 * DEFAULT NULL, `data_vencimento` varchar(40) DEFAULT NULL, `objeto` int(3),
		 * `especie` varchar(100) DEFAULT NULL, `quantidade` double, `unidade_medida`
		 * varchar(40) DEFAULT NULL, `valor_unitario` varchar(60) DEFAULT NULL,
		 * `valor_total` varchar(40) DEFAULT NULL, `status` int(3) DEFAULT NULL,
		 * `observacao` text DEFAULT NULL, `descricao` text DEFAULT NULL,
		 * `caminho_arquivo` text DEFAULT NULL, PRIMARY KEY (`id_parcela`) )
		 * ENGINE=InnoDB AUTO_INCREMENT=600 DEFAULT CHARSET=utf8
		 * 
		 */
		return "insert into parcela_emprestimo (id_lancamento_pai, identificador, data_vencimento,fluxo_caixa,"
				+ "objeto, especie, quantidade, unidade_medida, valor_unitario, valor_total, status, observacao, descricao,caminho_arquivo, criar_pagamento ) values ('"
				+ dado.getId_lancamento_pai() + "','" + dado.getIdentificador() + "','" + dado.getData_vencimento()
				+ "','" + dado.getFluxo_caixa() + "','"

				+ dado.getObjeto() + "','" + dado.getEspecie() + "','" + dado.getQuantidade() + "','"
				+ dado.getUnidade_medida() + "','" + dado.getValor_unitario() + "','" + dado.getValor() + "','"
				+ dado.getStatus() + "','" + dado.getObservacao() + "','" + dado.getDescricao() + "','"
				+ dado.getCaminho_arquivo() + "','" + dado.getCriar_pagamento() + "')";
	}

	public int inserirParcela(ParcelaEmprestimo dado) {
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
					System.out.println("Id da parcela inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a parcela no banco de dados, Erro: "
						+ e.getMessage() + "\nCausa: " + e.getCause());

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<ParcelaEmprestimo> getParcelas() {
		String select = "select * from parcela_emprestimo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<ParcelaEmprestimo> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				ParcelaEmprestimo dado = new ParcelaEmprestimo();

				dado.setId_parcela(rs.getInt("id_parcela"));
				dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setData_vencimento(rs.getString("data_vencimento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setQuantidade(rs.getDouble("quantidade"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));
				dado.setId_documento(rs.getInt("id_documento"));
				dado.setCriar_pagamento(rs.getInt("criar_pagamento"));
				dado.setId_pagamento(rs.getInt("id_pagamento"));

				try {
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				} catch (Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try {
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setStatus(rs.getInt("status"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a parcela \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ArrayList<ParcelaEmprestimo> getParcelasPorLancamento(int id_lancamento) {
		String select = "select * from parcela_emprestimo where id_lancamento_pai = ? ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<ParcelaEmprestimo> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				ParcelaEmprestimo dado = new ParcelaEmprestimo();

				dado.setId_parcela(rs.getInt("id_parcela"));
				dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setData_vencimento(rs.getString("data_vencimento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));

				dado.setObjeto(rs.getInt("objeto"));
				dado.setEspecie(rs.getString("especie"));
				dado.setUnidade_medida(rs.getString("unidade_medida"));
				dado.setId_documento(rs.getInt("id_documento"));
				dado.setCriar_pagamento(rs.getInt("criar_pagamento"));
				dado.setId_pagamento(rs.getInt("id_pagamento"));

				dado.setQuantidade(rs.getDouble("quantidade"));
				try {
					dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
				} catch (Exception e) {
					dado.setValor_unitario(BigDecimal.ZERO);
				}
				try {
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				} catch (Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setStatus(rs.getInt("status"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));

				lista.add(dado);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a parcela \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}

	public ParcelaEmprestimo getParcela(int id) {

		String select = "select * from parcela_emprestimo where id_parcela = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			ParcelaEmprestimo dado = new ParcelaEmprestimo();

			dado.setId_parcela(rs.getInt("id_parcela"));
			dado.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));
			dado.setIdentificador(rs.getString("identificador"));
			dado.setData_vencimento(rs.getString("data_vencimento"));
			dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
			dado.setId_documento(rs.getInt("id_documento"));

			dado.setObjeto(rs.getInt("objeto"));
			dado.setEspecie(rs.getString("especie"));
			dado.setQuantidade(rs.getDouble("quantidade"));
			dado.setUnidade_medida(rs.getString("unidade_medida"));
			dado.setCriar_pagamento(rs.getInt("criar_pagamento"));
			dado.setId_pagamento(rs.getInt("id_pagamento"));

			try {
				dado.setValor_unitario(new BigDecimal(rs.getString("valor_unitario")));
			} catch (Exception e) {
				dado.setValor_unitario(BigDecimal.ZERO);
			}
			try {
				dado.setValor(new BigDecimal(rs.getString("valor_total")));
			} catch (Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}
			dado.setStatus(rs.getInt("status"));
			dado.setDescricao(rs.getString("descricao"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));

			return dado;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null,
					"Erro ao listar a parcela id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerParcela(int id) {
		String delete = "DELETE FROM parcela_emprestimo WHERE id_parcela = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Parcela Excluída, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a parcela do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarParcela(ParcelaEmprestimo dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
				// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update parcela_emprestimo set identificador = ?, "
						+ "data_vencimento = ?, fluxo_caixa = ?, objeto = ?, especie = ?, quantidade = ?,"
						+ "unidade_medida = ?, valor_unitario = ?, valor_total = ?, status = ?,"
						+ " observacao = ?, descricao = ? , criar_pagamento = ? where id_parcela = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				pstm.setString(1, dado.getIdentificador());
				pstm.setString(2, dado.getData_vencimento());
				pstm.setInt(3, dado.getFluxo_caixa());

				pstm.setInt(4, dado.getObjeto());

				pstm.setString(5, dado.getEspecie());

				pstm.setDouble(6, dado.getQuantidade());

				pstm.setString(7, dado.getUnidade_medida());
				try {
					pstm.setString(8, dado.getValor_unitario().toString());
				} catch (Exception e) {
					pstm.setString(8, "0");

				}
				pstm.setString(9, dado.getValor().toString());

				pstm.setInt(10, dado.getStatus());
				pstm.setString(11, dado.getObservacao());
				pstm.setString(12, dado.getDescricao());
				pstm.setInt(13, dado.getCriar_pagamento());

				pstm.setInt(14, dado.getId_parcela());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Parcela Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: "
						+ e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	public boolean atualizarArquivoDaParcela(String caminho_arquivo, int id_parcela) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update parcela_emprestimo set caminho_arquivo = ?  where id_parcela = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setString(1, caminho_arquivo);
			pstm.setInt(2, id_parcela);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage()
					+ "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarStatusParcela(int status, int id_parcela) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update parcela_emprestimo set status = ?  where id_parcela = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setInt(1, status);
			pstm.setInt(2, id_parcela);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Parcela Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage()
					+ "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarIdDocumento(int id_documento, int id_parcela) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update parcela_emprestimo set id_documento = ?  where id_parcela = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setInt(1, id_documento);
			pstm.setInt(2, id_parcela);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Parcela Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar a parcela no banco de dados\nErro: " + e.getMessage()
					+ "\nCausa: " + e.getCause());
			return false;
		}

	}

	public boolean atualizarIdPagamento(int id_parcela, int id_pagamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			// atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,
			// tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update parcela_emprestimo set id_pagamento = ?  where id_parcela = ?";

			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setInt(1, id_pagamento);
			pstm.setInt(2, id_parcela);

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			System.out.println("Id do Pagamento Atualizada com sucesso");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o id do pagamento de parcela de emprestimo no banco de dados\nErro: "
							+ e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}

	}

}
