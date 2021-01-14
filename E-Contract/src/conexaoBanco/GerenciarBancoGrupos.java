package conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroAditivo;
import cadastros.CadastroGrupo;

public class GerenciarBancoGrupos {

	
	
	public String sql_grupo(CadastroGrupo grupo) {
		return "insert into grupo (nome_grupo, descricao_grupo, integrantes) values ('"
				+ grupo.getNome_grupo() + "','"
				+ grupo.getDescricao_grupo() + "','"
				+ grupo.getIntegrantes()+ "')";
	}
	
	public int inserirGrupo(CadastroGrupo grupo) {
		int result = -1;
		int id_cliente = -1;
		if (grupo != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_grupo(grupo);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id grupo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o grupo no banco de " + "dados " + e.getMessage());
				
				return -1;
			}
		} else {
			System.out.println("O grupo enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroGrupo> getGrupos() {
		String selectGrupos = "select * from grupo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroGrupo> lista_grupos = new ArrayList<CadastroGrupo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectGrupos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroGrupo grupo = new CadastroGrupo();
			
				grupo.setId_grupo(rs.getInt("id_grupo"));
				grupo.setNome_grupo(rs.getString("nome_grupo"));
				grupo.setDescricao_grupo(rs.getString("descricao_grupo"));
				grupo.setIntegrantes(rs.getString("integrantes"));
				
				
				lista_grupos.add(grupo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar grupos" + e.getMessage());
		}
		return lista_grupos;

	}
	
	
	public CadastroGrupo getGrupo(int id_grupo) {

		String selectgrupo = "select * from grupo where id_grupo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroGrupo grupo = new CadastroGrupo();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectgrupo);
			pstm.setInt(1,id_grupo);

			rs = pstm.executeQuery();
			rs.next();

			grupo.setId_grupo(rs.getInt("id_grupo"));
			grupo.setNome_grupo(rs.getString("nome_grupo"));
			grupo.setDescricao_grupo(rs.getString("descricao_grupo"));
			grupo.setIntegrantes(rs.getString("integrantes"));
			

		    return grupo;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o grupo de id: " + id_grupo + " erro: " + e.getMessage());
			System.out.println(
					"Erro ao listar grupo id: " + id_grupo + " erro: " + e.getMessage() + "\ncausa: " + e.getCause());
			return null;
		}

	}
	
	
	public boolean removerGrupo(int id_grupo) {
		String sql_delete_grupo = "DELETE FROM grupo WHERE id_grupo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_grupo);

			pstm.setInt(1, id_grupo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Grupo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o grupo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	
	public boolean atualizarGrupo(CadastroGrupo grupo) {
		String sql_update_grupo = "update grupo set nome_grupo = ?, descricao_grupo = ?, integrantes =  ? where id_grupo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_grupo);

			pstm.setString(1, grupo.getNome_grupo());
			pstm.setString(2, grupo.getDescricao_grupo());
			pstm.setString(3, grupo.getIntegrantes());
			pstm.setInt(4, grupo.getId_grupo());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Grupo atualizado, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o  grupo do aditivo no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
		
	}
	
}
