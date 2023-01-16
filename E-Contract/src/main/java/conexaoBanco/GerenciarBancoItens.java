package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroItem;

public class GerenciarBancoItens {

	private String sql_item(CadastroItem item) {
		System.out.println(item.getNome() + " - " + item.getDescricao() + " - " + item.getTipo().getId_tipo_item());

		return "insert into itens_estoque (nome, descricao, id_tipo_item) values ('" + item.getNome() + "','"
				+ item.getDescricao() + "','" + item.getTipo().getId_tipo_item() + "')";
	}

	private String selectItens() {
		return "select ie.id_item, ie.nome as nome_item, ie.descricao as descricao_item, ie.id_tipo_item as fk_id_tipo_item, "
				+ "	   ti.id_tipo_item, ti.nome as nome_tipo_item, ti.descricao as descricao_tipo_item "
				+ "    from itens_estoque as ie LEFT join tipo_item as ti ON ie.id_tipo_item = ti.id_tipo_item group by ie.id_item";
	}

	public int inserirItem(CadastroItem item) {
		int result = -1;
		if (item != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_item(item);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id item inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o item no banco de dados");
				return -1;
			}
		} else {
			System.out.println("O item enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<CadastroItem> getItens() {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroItem> listaTipoItem = new ArrayList<CadastroItem>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectItens());
			rs = pstm.executeQuery();

			while (rs.next()) {
				CadastroItem item = new CadastroItem();
				if (rs.getString("fk_id_tipo_item") != null) {
					item.getTipo().setId_tipo_item(rs.getInt("id_tipo_item"));
					item.getTipo().setNome(rs.getString("nome_tipo_item"));
					item.getTipo().setDescricao(rs.getString("descricao_tipo_item"));
				}
				item.setId_item(rs.getInt("id_item"));
				item.setNome(rs.getString("nome_item"));
				item.setDescricao(rs.getString("descricao_item"));

				listaTipoItem.add(item);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "Erro ao listar", "Itens", JOptionPane.ERROR_MESSAGE);
		}
		return listaTipoItem;
	}

	public boolean atualizarItem(CadastroItem item) {
		try {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			atualizar = "update itens_estoque set nome = ?, descricao = ?, id_tipo_item = ? where id_item = ? ";
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(atualizar);

			pstm.setString(1, item.getNome());
			pstm.setString(2, item.getDescricao());
			pstm.setInt(3, item.getTipo().getId_tipo_item());
			pstm.setInt(4, item.getId_item());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn);
			return true;
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar os dados dados\nErro: " + e.getMessage() + "\nCausa: " + e.getCause(), "Item",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}
	
	public static boolean removerItem(int id_item) {
		String sql_delete_item = "DELETE FROM itens_estoque WHERE id_item = ?";
		Connection conn = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			
			pstm = conn.prepareStatement(sql_delete_item);
			pstm.setInt(1, id_item);
			pstm.execute();
			
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o cadastro do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage(),"Item",JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

}
