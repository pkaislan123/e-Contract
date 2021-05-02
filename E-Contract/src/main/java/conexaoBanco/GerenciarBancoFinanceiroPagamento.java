package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;


public class GerenciarBancoFinanceiroPagamento {

	public String sql_dado (FinanceiroPagamento dado) {
		/*
		 * CREATE TABLE `financeiro_pagamento` (
	  `id_pagamento` int(5) NOT NULL AUTO_INCREMENT,
	  `identificador` text DEFAULT NULL, 
	  `id_forma_pagamento` text DEFAULT NULL,
	  `valor` varchar(40) DEFAULT NULL,
	  `data_pagamento` varchar(40) DEFAULT NULL,
	  `observacao` text DEFAULT NULL,
	  `descricao` text DEFAULT NULL,
	  `caminho_arquivo` text DEFAULT NULL,
	  PRIMARY KEY (`id_pagamento`)
	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8
		 */
		return "insert into financeiro_pagamento (id_lancamento_pai, identificador,id_pagador, id_forma_pagamento, status_condicao_pagamento, valor, data_pagamento,"
				+ "observacao, descricao,caminho_arquivo ) values ('"
				+ dado.getId_lancamento() + "','"
				+ dado.getIdentificador() + "','"
				+ dado.getId_pagador() + "','"
				+ dado.getId_condicao_pagamento() + "','"
				+ dado.getStatus_pagamento() + "','"
				+ dado.getValor() + "','"
				+ dado.getData_pagamento() + "','" 
			    + dado.getObservacao() + "','" 
			    + dado.getDescricao() + "','" 
			    
			    + dado.getCaminho_arquivo() + "')";
	}
	


	public int inserirFinanceiroPagamento(FinanceiroPagamento dado) {
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
					System.out.println("Id do Pagamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o Pagamento Financeiro no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<FinanceiroPagamento> getFinanceiroPagamentos() {
		String select = "select * from financeiro_pagamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamento> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamento dado = new FinanceiroPagamento();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public ArrayList<FinanceiroPagamento> getFinanceiroPagamentosPorLancamento(int id_lancamento) {
		String select = "select * from financeiro_pagamento where id_lancamento_pai = ? ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamento> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_lancamento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroPagamento dado = new FinanceiroPagamento();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setId_pagador(rs.getInt("id_pagador"));

				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}


	public FinanceiroPagamento getFinanceiroPagamento(int id) {

		String select = "select * from financeiro_pagamento where id_pagamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FinanceiroPagamento dado = new FinanceiroPagamento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_pagamento(rs.getInt("id_pagamento"));
			dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
			dado.setIdentificador(rs.getString("identificador"));
			dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
			dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
			dado.setId_pagador(rs.getInt("id_pagador"));

			try{
				dado.setValor(new BigDecimal(rs.getString("valor")));
			}catch(Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}
			dado.setData_pagamento(rs.getString("data_pagamento"));
			dado.setDescricao(rs.getString("descricao"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
		
			
			return dado;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar o Financeiro Pagamento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerFinanceiroPagamento(int id) {
		String delete = "DELETE FROM financeiro_pagamento WHERE id_pagamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Financeiro Pagamento Excluída, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o Financeiro Pagamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarFinanceiroPagamento(FinanceiroPagamento dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update financeiro_pagamento set identificador = ? , id_pagador = ?, id_forma_pagamento = ?, status_condicao_pagamento = ?, "
						+ "valor = ?, data_pagamento = ?,"
						+ " observacao = ?, descricao = ?,caminho_arquivo = ?  where id_pagamento =?";				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				
				pstm.setString(1, dado.getIdentificador());
				pstm.setInt(2, dado.getId_pagador());
				pstm.setInt(3, dado.getId_condicao_pagamento());
				pstm.setInt(4, dado.getStatus_pagamento());

				pstm.setString(5, dado.getValor().toString());
				pstm.setString(6, dado.getData_pagamento());
				pstm.setString(7, dado.getObservacao());
				pstm.setString(8, dado.getDescricao());
				pstm.setString(9, dado.getCaminho_arquivo());
				pstm.setInt(10, dado.getId_pagamento());

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Financeiro Pagamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Financeiro Pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	
	public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentosLancamentos() {
		String select = "select * from lancamento\r\n"
				+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				
				if(rs.getInt("id_lancamento") > 0) {
				
				FinanceiroPagamento dado = new FinanceiroPagamento();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setDescricao(rs.getString("descricao"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setCaminho_arquivo(rs.getString("caminho_arquivo"));
			
				Lancamento lancamento = new Lancamento();
				lancamento.setId_lancamento(rs.getInt("id_lancamento"));
				lancamento.setPrioridade(rs.getInt("prioridade"));
				lancamento.setTipo_lancamento(rs.getInt("tipo_lancamento"));
				lancamento.setData_lancamento(rs.getString("data_lancamento"));
				lancamento.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				lancamento.setId_conta(rs.getInt("id_conta"));
				lancamento.setId_centro_custo(rs.getInt("id_centro_custo"));
				lancamento.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				lancamento.setGerar_parcelas(rs.getInt("gerar_parcelas"));
				lancamento.setIntervalo(rs.getInt("intervalo"));
				lancamento.setNumero_parcelas(rs.getInt("numero_parcelas"));
				
				FinanceiroPagamentoCompleto financeiro_pagamento_lancamento = new FinanceiroPagamentoCompleto();
				financeiro_pagamento_lancamento.setLancamento(lancamento);
				financeiro_pagamento_lancamento.setFpag(dado);
				
				lista.add(financeiro_pagamento_lancamento);
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public boolean atualizarTeste(FinanceiroPagamento dado, int id_instituicao_bancaria) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update financeiro_pagamento set id_pagador = ?\r\n"
						+ "where id_pagamento = ?";				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				
				pstm.setInt(1, id_instituicao_bancaria);
				pstm.setInt(2, dado.getId_pagamento());
				

			

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Financeiro Pagamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Financeiro Pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}
}
