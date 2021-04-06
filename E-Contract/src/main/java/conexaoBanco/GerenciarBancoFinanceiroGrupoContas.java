package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.FinanceiroGrupoContas;

public class GerenciarBancoFinanceiroGrupoContas {

	public String sql_dado (FinanceiroGrupoContas dado) {
		return "insert into financeiro_grupo_contas (nome_grupo_contas, observacao, descricao) values ('"
				+ dado.getNome() + "','" 
				+ dado.getObservacao() + "','"
				+ dado.getDescricao() + "')";
	}

	public int inserirFinanceiroGrupoContas(FinanceiroGrupoContas dado) {
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
					System.out.println("Id Grupo de Contas inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o Grupo de Contas no banco de dados, Erro: " + e.getMessage() + "\nCausa: " + e.getCause()) ;

				return -1;
			}
		} else {
			System.out.println("O parametro enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<FinanceiroGrupoContas> getFinanceiroGrupoContass() {
		String select = "select * from financeiro_grupo_contas";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FinanceiroGrupoContas> lista = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);

			rs = pstm.executeQuery();
			while (rs.next()) {
				FinanceiroGrupoContas dado = new FinanceiroGrupoContas();

				dado.setId_grupo_contas(rs.getInt("id_grupo_contas"));
				dado.setNome(rs.getString("nome_grupo_contas"));
				dado.setObservacao(rs.getString("observacao"));
				dado.setDescricao(rs.getString("descricao"));
				
			

				lista.add(dado);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o Grupo de Contas");// );
		}
		return lista;

	}

	public FinanceiroGrupoContas getFinanceiroGrupoContas(int id) {

		String select = "select * from financeiro_grupo_contas where id_grupo_contas = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FinanceiroGrupoContas dado = new FinanceiroGrupoContas();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(select);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			dado.setId_grupo_contas(rs.getInt("id_grupo_contas"));
			dado.setNome(rs.getString("nome_grupo_contas"));
			dado.setObservacao(rs.getString("observacao"));
			dado.setDescricao(rs.getString("descricao"));
			
			return dado;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar Grupo de Contas id: " + id);// );
			System.out.println("Erro ao listar Grupo de Contas id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}

	public boolean removerFinanceiroGrupoContas(int id) {
		String delete = "DELETE FROM financeiro_grupo_contas WHERE id_grupo_contas = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(delete);

			pstm.setInt(1, id);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Grupo de Contas excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o Grupo de Contas do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarFinanceiroGrupoContas(FinanceiroGrupoContas dado) {
		if (dado != null) {
			try {
				Connection conn = null;
				String atualizar = null;
				PreparedStatement pstm;

				atualizar = "update financeiro_grupo_contas set nome_grupo_contas = ?, observacao = ?,descricao = ? where id_grupo_contas = ? ";
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setString(1, dado.getNome());
				pstm.setString(2, dado.getObservacao());
				pstm.setString(3, dado.getDescricao());
				
				pstm.setInt(4, dado.getId_grupo_contas());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				System.out.println("Grupo de Contas Atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o Grupo de Contas no banco de dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os parametros estão vazios");
			return false;
		}
	}

	
}
