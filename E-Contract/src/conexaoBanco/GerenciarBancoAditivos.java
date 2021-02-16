package conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import cadastros.CadastroAditivo;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;

public class GerenciarBancoAditivos {

	
	
	public String sql_aditivo(CadastroAditivo aditivo) {
		return "insert into aditivo (status_aditivo, data_aditivo, texto,nome_arquivo, id_contrato_pai) values ('"
				+ aditivo.getStatus() + "','"
				+ aditivo.getData() + "','"
				+ aditivo.getTexto() + "','"
				+ aditivo.getNome_arquivo() + "','"

				+ aditivo.getId_contrato_pai() + "')";
	}
	
	public int inserirAditivo(CadastroAditivo aditivo) {
		int result = -1;
		int id_cliente = -1;
		if (aditivo != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_aditivo(aditivo);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id aditivo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o aditivo no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O aditivo enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroAditivo> getAditivos(int id_contrato_pai) {
		String selectAdivitos = "select * from aditivo where id_contrato_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroAditivo> lista_aditivos = new ArrayList<CadastroAditivo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_pai);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroAditivo aditivo = new CadastroAditivo();
			
				
				aditivo.setId_aditivo(rs.getInt("id_aditivo"));
				aditivo.setData(rs.getString("data_aditivo"));
				aditivo.setNome_arquivo(rs.getString("nome_arquivo"));
				aditivo.setStatus(rs.getInt("status_aditivo"));
				aditivo.setId_contrato_pai(rs.getInt("id_contrato_pai"));
				aditivo.setTexto(rs.getString("texto"));
				
				lista_aditivos.add(aditivo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar aditivos");//  );
		}
		return lista_aditivos;

	}
	
	
	public CadastroAditivo getAditivo(int id) {

		String selectAditivo = "select * from aditivo where id_aditivo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroAditivo aditivo = new CadastroAditivo();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAditivo);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			aditivo.setId_aditivo(rs.getInt("id_aditivo"));
			aditivo.setData(rs.getString("data_aditivo"));
			aditivo.setNome_arquivo(rs.getString("nome_arquivo"));
			aditivo.setStatus(rs.getInt("status_aditivo"));
			aditivo.setId_contrato_pai(rs.getInt("id_contrato_pai"));
			aditivo.setTexto(rs.getString("texto"));

			

		    return aditivo;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o aditivo id: " + id );// );
			System.out.println(
					"Erro ao listar aditivo id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerAditivo(int id_contrato, int id_aditivo) {
		String sql_delete_aditivo = "DELETE FROM aditivo WHERE id_contrato_pai = ? and id_aditivo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_aditivo);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_aditivo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Aditivo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o aditivo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarStatusAditivo(int id_aditivo, int status) {
		
		String sql_update_aditivo = "update aditivo set status_aditivo = ? where id_aditivo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_aditivo);

			pstm.setInt(1, status);
			pstm.setInt(2, id_aditivo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "status do aditivo atualizado, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o  status do aditivo no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}
	
	
	
}
