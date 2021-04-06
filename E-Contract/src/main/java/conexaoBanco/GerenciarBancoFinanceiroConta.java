package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.FinanceiroConta;


public class GerenciarBancoFinanceiroConta {

	public String sql_dado (FinanceiroConta dado) {
		return "insert into financeiro_conta (nome_conta, id_grupo_contas, tipo_conta, observacao, descricao) values ('"
				+ dado.getNome() + "','" 
				+ dado.getId_grupo_contas() + "','" 
			    + dado.getTipo_conta() + "','" 
				+ dado.getObservacao() + "','"
				+ dado.getDescricao() + "')";
	}

	public int inserirFinanceiroConta(FinanceiroConta dado) {
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
					System.out.println("Id da Conta inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a Conta no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<FinanceiroConta> getFinanceiroContas() {
		String select = "select * from financeiro_conta";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroConta> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroConta dado = new FinanceiroConta();

				dado.setId(rs.getInt("id_conta"));
				dado.setNome(rs.getString("nome_conta"));
				dado.setId_grupo_contas(rs.getInt("id_grupo_contas"));
				dado.setTipo_conta(rs.getInt("tipo_conta"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setDescricao(rs.getString("descricao"));
				
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a Conta");// );
		}
		return lista;

	}

	public FinanceiroConta getFinanceiroConta(int id) {

		String select = "select * from financeiro_conta where id_conta = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FinanceiroConta dado = new FinanceiroConta();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId(rs.getInt("id_conta"));
			dado.setNome(rs.getString("nome_conta"));
			dado.setId_grupo_contas(rs.getInt("id_grupo_contas"));
			dado.setTipo_conta(rs.getInt("tipo_conta"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setDescricao(rs.getString("descricao"));
			
			return dado;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id);// );
			JOptionPane.showMessageDialog(null, "Erro ao listar a Conta id: " + id + " erro: " + e.getCause() + "\ncausa: " + e.getMessage());
			return null;
		}

	}

	public boolean removerFinanceiroConta(int id) {
		String delete = "DELETE FROM financeiro_conta WHERE id_conta = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Conta excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir a Conta do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarFinanceiroConta(FinanceiroConta dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update financeiro_conta set nome_conta = ?, id_grupo_contas = ?,  tipo_conta = ?, observacao = ?,descricao = ? where id_conta = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getNome());
				pstm.setInt(2, dado.getId_grupo_contas());
				pstm.setInt(3, dado.getTipo_conta());
				pstm.setString(4, dado.getObservacao());
				pstm.setString(5, dado.getDescricao());
				
				pstm.setInt(6, dado.getId());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Conta Atualizada com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar a Conta no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	
}
