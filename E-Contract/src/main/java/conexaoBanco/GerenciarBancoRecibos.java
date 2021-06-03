package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroRecibo;

public class GerenciarBancoRecibos {

	
	public String sql_recibo(CadastroRecibo recibo) {
		return "insert into recibo (tipo_recibo, status_recibo, data_recibo,nome_arquivo, id_pai, id_lancamento_pai) values ('"
				+ recibo.getTipo_recibo() + "','"
						+ recibo.getStatus_recibo()+ "','"

				+ recibo.getData_recibo() + "','"
				+ recibo.getNome_arquivo() + "','"
						+ recibo.getId_pai() + "','"
				+ recibo.getId_lancamento_pai()+ "')";
	}
	
	public int inserirrecibo(CadastroRecibo recibo) {
		int result = -1;
		int id_cliente = -1;
		if (recibo != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_recibo(recibo);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id recibo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o recibo no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O recibo enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroRecibo> getRecibosPorLancamentoPai(int id_contrato_pai) {
		String selectAdivitos = "select * from recibo where id_lancamento_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroRecibo> lista_recibos = new ArrayList<CadastroRecibo>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_pai);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroRecibo recibo = new CadastroRecibo();
			
				
				recibo.setId_recibo(rs.getInt("id_recibo"));
				recibo.setTipo_recibo(rs.getInt("tipo_recibo"));
				recibo.setData_recibo(rs.getString("data_recibo"));
				recibo.setNome_arquivo(rs.getString("nome_arquivo"));
				recibo.setStatus_recibo(rs.getInt("status_recibo"));
				recibo.setId_pai(rs.getInt("id_pai"));
				recibo.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));

				lista_recibos.add(recibo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar recibos");//  );
		}
		return lista_recibos;

	}
	
	
	public CadastroRecibo getrecibo(int id) {

		String selectrecibo = "select * from recibo where id_recibo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroRecibo recibo = new CadastroRecibo();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectrecibo);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			recibo.setId_recibo(rs.getInt("id_recibo"));
			recibo.setTipo_recibo(rs.getInt("tipo_recibo"));
			recibo.setData_recibo(rs.getString("data_recibo"));
			recibo.setNome_arquivo(rs.getString("nome_arquivo"));
			recibo.setStatus_recibo(rs.getInt("status_recibo"));
			recibo.setId_pai(rs.getInt("id_pai"));
			recibo.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));

			

			

		    return recibo;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o recibo id: " + id );// );
			System.out.println(
					"Erro ao listar recibo id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerRecibo(int id_recibo) {
		String sql_delete_recibo = "DELETE FROM recibo WHERE id_recibo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_recibo);

			pstm.setInt(1, id_recibo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o recibo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarStatusrecibo(int id_recibo, int status) {
		
		String sql_update_recibo = "update recibo set status_recibo = ? where id_recibo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_recibo);

			pstm.setInt(1, status);
			pstm.setInt(2, id_recibo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "status do recibo atualizado, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o  status do recibo no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}
	
	
}
