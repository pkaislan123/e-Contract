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
		/*
		 * CREATE TABLE `lancamento` (
  `id_lancamento` int(5) NOT NULL AUTO_INCREMENT,
  `tipo_lancamento` int(3) DEFAULT NULL,
  prioridade int(3),
  `data_lancamento` varchar(40) DEFAULT NULL,
  `id_conta` int(5) DEFAULT NULL,
  `id_instituicao_bancaria` varchar(30) DEFAULT NULL,
  `id_centro_custo` varchar(30) DEFAULT NULL,
  `id_cliente_fornecedor` text DEFAULT NULL,
  `valor_total` varchar(40) DEFAULT NULL,
  numero_parcelas int (3),
   `data_primeiro_vencimento` varchar(40) DEFAULT NULL,
  intervalo int(3),
  gerar_parcelas int(3),
  `status` int(3) DEFAULT NULL,
  `observacao` text DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  PRIMARY KEY (`id_lancamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
		 */
		return "insert into lancamento (tipo_lancamento, prioridade, data_lancamento,"
				+ " id_conta, id_instituicao_bancaria, id_centro_custo,id_cliente_fornecedor, identificacao,"
				+ "valor_total, numero_parcelas, data_primeiro_vencimento, intervalo, gerar_parcelas, status,observacao,descricao, caminho_arquivo, diretorio_lancamento) values ('"
				+ dado.getTipo_lancamento() + "','"
				+ dado.getPrioridade() + "','"
				+ dado.getData_lancamento() + "','" 
				+ dado.getId_conta() + "','" 
			    + dado.getId_instituicao_bancaria() + "','" 
			    + dado.getId_centro_custo() + "','" 
			    + dado.getId_cliente_fornecedor() + "','" 
			    + dado.getIdentificacao() + "','"
			    + dado.getValor()+ "','" 
			    + dado.getNumero_parcelas() + "','"
			    + dado.getData_vencimento() + "','"
			    + dado.getIntervalo() + "','"
			    + dado.getGerar_parcelas() + "','"
			    + dado.getStatus() + "','"
				+ dado.getObservacao() + "','"
				+ dado.getDescricao()  + "','"
			    + dado.getDiretorio_lancamento()  + "','"
				+ dado.getCaminho_arquivo() + "')";
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
				dado.setPrioridade(rs.getInt("prioridade"));
				dado.setTipo_lancamento(rs.getInt("tipo_lancamento"));
				dado.setData_lancamento(rs.getString("data_lancamento"));
				dado.setId_conta(rs.getInt("id_conta"));
				dado.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
				dado.setId_centro_custo(rs.getInt("id_centro_custo"));
				dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
				dado.setGerar_parcelas(rs.getInt("gerar_parcelas"));
				dado.setIntervalo(rs.getInt("intervalo"));
				dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
				
				try{
					dado.setValor(new BigDecimal(rs.getString("valor_total")));
				}catch(Exception e) {
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
			JOptionPane.showMessageDialog(null, "Erro ao listar o lançamento\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
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
			dado.setId_instituicao_bancaria(rs.getInt("id_instituicao_bancaria"));
			dado.setId_centro_custo(rs.getInt("id_centro_custo"));
			dado.setId_cliente_fornecedor(rs.getInt("id_cliente_fornecedor"));
			dado.setGerar_parcelas(rs.getInt("gerar_parcelas"));
			dado.setIntervalo(rs.getInt("intervalo"));
			dado.setNumero_parcelas(rs.getInt("numero_parcelas"));
			
			try{
				dado.setValor(new BigDecimal(rs.getString("valor_total")));
			}catch(Exception e) {
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
	
	public boolean atualizarLancamento(String caminho_arquivo, String diretorio_lancamento, int id_lancamento) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				//atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				atualizar = "update lancamento set caminho_arquivo = ?, diretorio_lancamento = ? where id_lancamento = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, caminho_arquivo);
				pstm.setString(2, diretorio_lancamento);

				pstm.setInt(3, id_lancamento);
				
			
				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o lançamento no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
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
				atualizar = "update lancamento set tipo_lancamento = ? , data_lancamento = ?, id_conta = ?, id_condicao_pagamento = ?,"
						+ " id_instituicao_bancaria = ?, id_centro_custo = ?,id_cliente_fornecedor = ?,"
						+ "valor = ?,data_vencimento = ?,data_pagamento = ?,"
						+ "status = ?,observacao = ?,descricao = ? where id_lancamento = ?";
				
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setInt(1, dado.getTipo_lancamento());
				pstm.setString(2, dado.getData_lancamento());
				pstm.setInt(3, dado.getId_conta());
				pstm.setInt(5, dado.getId_instituicao_bancaria());
				pstm.setInt(6, dado.getId_centro_custo());
				pstm.setInt(7, dado.getId_cliente_fornecedor());
				pstm.setString(8, dado.getValor().toString());
				pstm.setString(9, dado.getData_vencimento());
				pstm.setInt(11, dado.getStatus());
				pstm.setString(12, dado.getObservacao());
				pstm.setString(13, dado.getDescricao());
				
				pstm.setInt(14, dado.getId_lancamento());

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
