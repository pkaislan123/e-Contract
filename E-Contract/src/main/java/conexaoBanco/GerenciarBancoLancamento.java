package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.Lancamento;


public class GerenciarBancoLancamento {

	public String sql_dado (Lancamento dado) {
		return "insert into lancamento (data_lancamento, id_conta, id_condicao_pagamento, id_instituicao_bancaria, id_centro_custo,id_cliente_fornecedor,valor,data_vencimento,data_pagamento,status,observacao,descricao) values ('"
				+ dado.getData_lancamento() + "','" 
				+ dado.getId_conta() + "','" 
			    + dado.getId_condicao_pagamento() + "','"
			    + dado.getId_instituicao_bancaria() + "','" 
			    + dado.getId_centro_custo() + "','" 
			    + dado.getId_cliente_fornecedor() + "','" 
			    + dado.getValor()+ "','" 
			    + dado.getData_vencimento()+ "','"
			    + dado.getData_pagamento() + "','"
			    + dado.getStatus() + "','"
				+ dado.getObservacao() + "','"
				+ dado.getDescricao() + "')";
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
				JOptionPane.showMessageDialog(null, "Erro ao inserir o lançamento no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

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
				dado.setData_lancamento(rs.getString("data_lancamento"));
				dado.setId_conta(rs.getInt("id_conta"));
				dado.setId_condicao_pagamento(rs.getInt("id_condicao_pagamento"));
				dado.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				dado.setId_centro_custo(rs.getInt("id_centro_custo"));
				dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				try{
					dado.setValor(new BigDecimal(rs.getString("valor")));
				}catch(Exception e) {
					dado.setValor(BigDecimal.ZERO);
				}
				dado.setData_vencimento(rs.getString("data_vencimento"));
				dado.setData_pagamento(rs.getString("data_pagamento"));
				dado.setStatus(rs.getString("status"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setDescricao(rs.getString("descricao"));
				
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o lançamento");// );
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
			dado.setData_lancamento(rs.getString("data_lancamento"));
			dado.setId_conta(rs.getInt("id_conta"));
			dado.setId_condicao_pagamento(rs.getInt("id_condicao_pagamento"));
			dado.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
			dado.setId_centro_custo(rs.getInt("id_centro_custo"));
			dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
			try{
				dado.setValor(new BigDecimal(rs.getString("valor")));
			}catch(Exception e) {
				dado.setValor(BigDecimal.ZERO);
			}
			dado.setData_vencimento(rs.getString("data_vencimento"));
			dado.setData_pagamento(rs.getString("data_pagamento"));
			dado.setStatus(rs.getString("status"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setDescricao(rs.getString("descricao"));
			
			return dado;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar o lançamento id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
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

	public boolean atualizarLancamento(Lancamento dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update lancamento set data_lancamento = ?, id_conta = ?, id_condicao_pagamento = ?,"
						+ " id_instituicao_bancaria = ?, id_centro_custo = ?,id_cliente_fornecedor = ?,"
						+ "valor = ?,data_vencimento = ?,data_pagamento = ?,"
						+ "status = ?,observacao = ?,descricao = ? where id_lancamento = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				
				pstm.setString(1, dado.getData_lancamento());
				pstm.setInt(2, dado.getId_conta());
				pstm.setInt(3, dado.getId_condicao_pagamento());
				pstm.setInt(4, dado.getId_instituicao_bancaria());
				pstm.setInt(5, dado.getId_centro_custo());
				pstm.setInt(6, dado.getId_cliente_fornecedor());
				pstm.setString(7, dado.getValor().toString());
				pstm.setString(8, dado.getData_vencimento());
				pstm.setString(9, dado.getData_pagamento());
				pstm.setString(10, dado.getStatus());
				pstm.setString(11, dado.getObservacao());
				pstm.setString(12, dado.getDescricao());
				
				pstm.setInt(13, dado.getId_lancamento());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Lançamento Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	
}
