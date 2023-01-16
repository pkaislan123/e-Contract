package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroItem;

public class GerenciarBancoTipoItem {

	private final String selectTipoItems = "select * from tipo_item";

	public String sql_item(CadastroItem.Tipo item) {
		return "insert into tipo_item (nome, descricao) values ('" + item.getNome() + "','" + item.getDescricao()
				+ "')";
	}

	public int inserirTipoItem(CadastroItem.Tipo tipoItem) {
		int result = -1;
		if (tipoItem != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_item(tipoItem);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o tipo item no banco de dados", "Tipo Item",
						JOptionPane.ERROR_MESSAGE);
				return -1;
			}
		} else {
			System.out.println("O tipo item enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<CadastroItem.Tipo> getTipoItens() {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroItem.Tipo> listaTipoItem = new ArrayList<CadastroItem.Tipo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTipoItems);
			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroItem.Tipo tipoItem = new CadastroItem.Tipo();
				tipoItem.setId_tipo_item(rs.getInt("id_tipo_item"));
				tipoItem.setNome(rs.getString("nome"));
				tipoItem.setDescricao(rs.getString("descricao"));

				listaTipoItem.add(tipoItem);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar", "Tipo Itens", JOptionPane.ERROR_MESSAGE);
		}
		return listaTipoItem;
	}

	public boolean atualizarTipoItens(CadastroItem.Tipo tipoItem) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update tipo_item set nome = ?, descricao = ? where id_tipo_item = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, tipoItem.getNome());
			pstm.setString(2, tipoItem.getDescricao());
			pstm.setInt(3, tipoItem.getId_tipo_item());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao atualizar os dados dados\nErro: "
					+ e.getMessage() + "\nCausa: " + e.getCause(),"Tipo Item",JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}
	public static boolean removerTipoItem(int idTipoItem) {
		String sql_delete_grupo = "DELETE FROM tipo_item WHERE id_tipo_item = ?";
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_grupo);

			pstm.setInt(1, idTipoItem);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o cadastro do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage(),"Tipo Item",JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

}
