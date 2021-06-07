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

import main.java.cadastros.FinanceiroPagamento;
import main.java.cadastros.FinanceiroPagamentoCompleto;
import main.java.cadastros.Lancamento;
import main.java.cadastros.SaldoInstituicaoBancaria;


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
		return "insert into financeiro_pagamento (id_lancamento_pai, identificador,tipo_pagador, id_pagador, tipo_recebedor,id_recebedor, id_forma_pagamento, status_condicao_pagamento, valor, data_pagamento,fluxo_caixa,"
				+ "observacao, descricao,caminho_arquivo ) values ('"
				+ dado.getId_lancamento() + "','"
				+ dado.getIdentificador() + "','"
			    + dado.getTipo_pagador() + "','"
				+ dado.getId_pagador() + "','"
					    + dado.getTipo_recebedor() + "','"

				+ dado.getId_recebedor() + "','"
				+ dado.getId_condicao_pagamento() + "','"
				+ dado.getStatus_pagamento() + "','"
				+ dado.getValor() + "','"
				+ dado.getData_pagamento() + "','" 
				+ dado.getFluxo_caixa() + "','" 
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
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setId_documento(rs.getInt("id_documento"));

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
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));

				dado.setId_recebedor(rs.getInt("id_recebedor"));
				dado.setId_documento(rs.getInt("id_documento"));

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
			dado.setTipo_pagador(rs.getInt("tipo_pagador"));
			dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
			dado.setId_pagador(rs.getInt("id_pagador"));
			dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));

			dado.setId_recebedor(rs.getInt("id_recebedor"));
			dado.setId_documento(rs.getInt("id_documento"));

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
				atualizar = "update financeiro_pagamento set identificador = ? , tipo_pagador = ?, id_pagador = ?, tipo_recebedor = ?, id_recebedor = ?, id_forma_pagamento = ?, status_condicao_pagamento = ?, "
						+ "valor = ?, data_pagamento = ?, fluxo_caixa = ?,"
						+ " observacao = ?, descricao = ? where id_pagamento =?";				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				
				pstm.setString(1, dado.getIdentificador());
				pstm.setInt(2, dado.getTipo_pagador());

				pstm.setInt(3, dado.getId_pagador());
				pstm.setInt(4, dado.getTipo_recebedor());

				pstm.setInt(5, dado.getId_recebedor());

				pstm.setInt(6, dado.getId_condicao_pagamento());
				pstm.setInt(7, dado.getStatus_pagamento());

				pstm.setString(8, dado.getValor().toString());
				pstm.setString(9, dado.getData_pagamento());
				pstm.setInt(10, dado.getFluxo_caixa());

				pstm.setString(11, dado.getObservacao());
				pstm.setString(12, dado.getDescricao());
				pstm.setInt(13, dado.getId_pagamento());

			

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
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
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
	
	
	public ArrayList<FinanceiroPagamentoCompleto> getFinanceiroPagamentosLancamentosPorCaixa( int id_instituicao_bancaria) {
	/*	String select = "select * from lancamento\r\n"
				+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento "
				+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )";
		*/
	/*	String select = "select lancamento.*,fpag.*,\r\n"
				+ "date_format(str_to_date(fpag.data_pagamento, '%d/%m/%Y'), '%Y-%m-%d') as data_pg\r\n"
				+ " from lancamento\r\n"
				+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento \r\n"
				+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )\r\n"
				+ " order BY STR_TO_DATE(data_pg, '%d/%m/%Y') ASC";
		*/
		/*String select  = "select lancamento.*,fpag.*,cp.*\r\n"
				+ " from lancamento\r\n"
				+ "left join financeiro_pagamento fpag on fpag.id_lancamento_pai = lancamento.id_lancamento \r\n"
				+ "left join condicao_pagamento cp on cp.id_condicao_pagamento = fpag.id_forma_pagamento\r\n"
				+ " where  (tipo_pagador = 0 and id_pagador = ?) or (tipo_recebedor = 0 and id_recebedor = ? )\r\n"
				+ " order BY STR_TO_DATE(fpag.data_pagamento, '%d/%m/%Y') ASC";
		*/
		String select = "call consulta_extrato_por_caixa(?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroPagamentoCompleto> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_instituicao_bancaria);

			rs = pstm.executeQuery();
			while (rs.next()) {
				
				if(rs.getInt("id_lancamento") > 0) {
				
				FinanceiroPagamento dado = new FinanceiroPagamento();
				
				dado.setId_pagamento(rs.getInt("id_pagamento"));
				dado.setId_lancamento(rs.getInt("id_lancamento_pai"));
				dado.setIdentificador(rs.getString("identificador"));
				dado.setId_condicao_pagamento(rs.getInt("id_forma_pagamento"));
				dado.setStatus_pagamento(rs.getInt("status_condicao_pagamento"));
				dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
				dado.setTipo_pagador(rs.getInt("tipo_pagador"));
				dado.setId_pagador(rs.getInt("id_pagador"));
				dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
				dado.setId_recebedor(rs.getInt("id_recebedor"));
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
				financeiro_pagamento_lancamento.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
				financeiro_pagamento_lancamento.setNome_pagador(rs.getString("nome_pagador"));
				financeiro_pagamento_lancamento.setNome_recebedor(rs.getString("nome_recebedor"));

				lista.add(financeiro_pagamento_lancamento);
				
				
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a FinanceiroPagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
		}
		return lista;

	}
	
	public ArrayList<FinanceiroPagamentoCompleto> getTodosFinanceiroPagamentos() {
		
			String select = "call consulta_extrato_todos_caixa()";
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
					dado.setFluxo_caixa(rs.getInt("fluxo_caixa"));
					dado.setTipo_pagador(rs.getInt("tipo_pagador"));
					dado.setId_pagador(rs.getInt("id_pagador"));
					dado.setTipo_recebedor(rs.getInt("tipo_recebedor"));
					dado.setId_recebedor(rs.getInt("id_recebedor"));
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
					financeiro_pagamento_lancamento.setNome_forma_pagamento(rs.getString("nome_condicao_pagamento"));
					financeiro_pagamento_lancamento.setNome_pagador(rs.getString("nome_pagador"));
					financeiro_pagamento_lancamento.setNome_recebedor(rs.getString("nome_recebedor"));

					lista.add(financeiro_pagamento_lancamento);
					
					
					}

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar a Financeiro Pagamento \nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
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
	
	
	public boolean atualizarArquivoDoPagamento(String caminho_arquivo, int id_pagamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update financeiro_pagamento set caminho_arquivo = ?  where id_pagamento = ?";
			
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setString(1, caminho_arquivo);
			pstm.setInt(2, id_pagamento);

		

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			JOptionPane.showMessageDialog(null,"Arquivo do Pagamento Atualizado");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	
}

	public boolean atualizarIdDoDocumento(int id_documento, int id_pagamento) {

		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
			atualizar = "update financeiro_pagamento set id_documento = ?  where id_pagamento = ?";
			
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);
			pstm.setInt(1, id_documento);
			pstm.setInt(2, id_pagamento);

		

			pstm.execute();
			// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
			JOptionPane.showMessageDialog(null,"Arquivo do Pagamento Atualizado");
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar o pagamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
			return false;
		}
	
}
	

	

	public SaldoInstituicaoBancaria getSaldoPorPeriodo(int id_instituicao_bancaria, String data) {
		String select = "call busca_saldo_periodo(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		SaldoInstituicaoBancaria saldo = new SaldoInstituicaoBancaria();
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_instituicao_bancaria);
			pstm.setString(2, data);

			rs = pstm.executeQuery();
		   rs.next();
		   saldo.setTotal_despesa(rs.getDouble("total_despesa"));
		   saldo.setTotal_receita(rs.getDouble("total_receita"));

		   
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return saldo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Saldo!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
			return null;

		}

	}
	
	
	public  Map<String,String> pegarDatasPagamento(int id_caixa) {
       
		String select = "call busca_datas_pagamento_por_ib (?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Lancamento dado = new Lancamento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_caixa);
			rs = pstm.executeQuery();
			rs.next();
			 Map<String,String> example = new HashMap<String,String>();
			 example.put( "menor_data_pagamento", new String(rs.getString("menor_data_pagamento")));
			  example.put( "maior_data_pagamento", new String(rs.getString("maior_data_pagamento")));

			
			return example;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a maior data de vencimento do banco de dados" );
			return null;
		}
	}
	
	public  Map<String,String> pegarDatasPagamento() {
	       
		String select = "call busca_datas_pagamento()";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Lancamento dado = new Lancamento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			rs = pstm.executeQuery();
			rs.next();
			 Map<String,String> example = new HashMap<String,String>();
			 example.put( "menor_data_pagamento", new String(rs.getString("menor_data_pagamento")));
			  example.put( "maior_data_pagamento", new String(rs.getString("maior_data_pagamento")));

			
			return example;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a maior data de pagamento do banco de dados" );
			return null;
		}
	}
	
	public SaldoInstituicaoBancaria getTotalPagamentosDespesa(int id_instituicao_bancaria) {
		String select = "select\r\n"
				+ "\r\n"
				+ "(select sum(valor) as total_receita\r\n"
				+ "from(\r\n"
				+ "select  fin_pg.valor\r\n"
				+ "from financeiro_pagamento fin_pg \r\n"
				+ "left join instituicao_bancaria ib on ib.id_instituicao_bancaria = fin_pg.id_pagador\r\n"
				+ "left join lancamento la on la.id_lancamento = fin_pg.id_lancamento_pai\r\n"
				+ "where tipo_recebedor = 0 and id_recebedor = ? and la.tipo_lancamento = 1\r\n"
				+ ")  a) as total_receita\r\n"
				+ ", \r\n"
				+ "(select sum( valor) as total_despesa\r\n"
				+ " from\r\n"
				+ "(select fin_pg.valor\r\n"
				+ "from financeiro_pagamento fin_pg \r\n"
				+ "left join instituicao_bancaria ib on ib.id_instituicao_bancaria = fin_pg.id_pagador\r\n"
				+ "left join lancamento la on la.id_lancamento = fin_pg.id_lancamento_pai\r\n"
				+ "where tipo_pagador = 0 and id_pagador = ? and la.tipo_lancamento = 0\r\n"
				+ ") a) as total_despesa\r\n";
		Connection conn = null;
		PreparedStatement pstm = null;
		SaldoInstituicaoBancaria saldo = new SaldoInstituicaoBancaria();
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id_instituicao_bancaria);
			pstm.setInt(2, id_instituicao_bancaria);

			rs = pstm.executeQuery();
		   rs.next();
		   saldo.setTotal_despesa(rs.getDouble("total_despesa"));
		   saldo.setTotal_receita(rs.getDouble("total_receita"));

		   
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return saldo;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Saldo!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
			return null;

		}

	}
	
}
