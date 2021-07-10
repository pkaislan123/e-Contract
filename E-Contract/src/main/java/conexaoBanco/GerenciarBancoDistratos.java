package main.java.conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import main.java.cadastros.CadastroCliente;
import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroContrato.CadastroPagamentoContratual;
import main.java.cadastros.CadastroDistrato;
import main.java.cadastros.CadastroLogin;
import main.java.cadastros.CadastroModelo;
import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroSafra;
import main.java.cadastros.RegistroQuantidade;
import main.java.cadastros.RegistroRecebimento;
import main.java.outros.DadosGlobais;
import main.java.tratamento_proprio.Log;

public class GerenciarBancoDistratos {

	
	
	public String sql_distrato(CadastroDistrato distrato) {
		return "insert into distrato (status_distrato, data_distrato, texto,nome_arquivo, id_contrato_pai) values ('"
				+ distrato.getStatus() + "','"
				+ distrato.getData() + "','"
				+ distrato.getTexto() + "','"
				+ distrato.getNome_arquivo() + "','"

				+ distrato.getId_contrato_pai() + "')";
	}
	
	public int inserirDistrato(CadastroDistrato distrato) {
		int result = -1;
		int id_cliente = -1;
		if (distrato != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_distrato(distrato);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id distrato inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o distrato no banco de " + "dados "  );
				
				return -1;
			}
		} else {
			System.out.println("O distrato enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroDistrato> getDistratos(int id_contrato_pai) {
		String selectAdivitos = "select * from distrato where id_contrato_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroDistrato> lista_distratos = new ArrayList<CadastroDistrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato_pai);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroDistrato distrato = new CadastroDistrato();
			
				
				distrato.setId_distrato(rs.getInt("id_distrato"));
				distrato.setData(rs.getString("data_distrato"));
				distrato.setNome_arquivo(rs.getString("nome_arquivo"));
				distrato.setStatus(rs.getInt("status_distrato"));
				distrato.setId_contrato_pai(rs.getInt("id_contrato_pai"));
				distrato.setTexto(rs.getString("texto"));
				
				lista_distratos.add(distrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar distratos"  );
		}
		return lista_distratos;

	}
	
	
	
	public ArrayList<CadastroDistrato> getDistratos( ) {
		String selectAdivitos = "select * from distrato ";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroDistrato> lista_distratos = new ArrayList<CadastroDistrato>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroDistrato distrato = new CadastroDistrato();
			
				
				distrato.setId_distrato(rs.getInt("id_distrato"));
				distrato.setData(rs.getString("data_distrato"));
				distrato.setNome_arquivo(rs.getString("nome_arquivo"));
				distrato.setStatus(rs.getInt("status_distrato"));
				distrato.setId_contrato_pai(rs.getInt("id_contrato_pai"));
				distrato.setTexto(rs.getString("texto"));
				
				lista_distratos.add(distrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar distratos"  );
		}
		return lista_distratos;

	}
	
	
	public CadastroDistrato getDistrato(int id) {

		String selectDistrato = "select * from distrato where id_distrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroDistrato distrato = new CadastroDistrato();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectDistrato);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			distrato.setId_distrato(rs.getInt("id_distrato"));
			distrato.setData(rs.getString("data_distrato"));
			distrato.setNome_arquivo(rs.getString("nome_arquivo"));
			distrato.setStatus(rs.getInt("status_distrato"));
			distrato.setId_contrato_pai(rs.getInt("id_contrato_pai"));
			distrato.setTexto(rs.getString("texto"));

			

		    return distrato;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o distrato id: " + id + " erro: "  );
			System.out.println(
					"Erro ao listar distrato id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerDistrato(int id_contrato, int id_distrato) {
		String sql_delete_distrato = "DELETE FROM distrato WHERE id_contrato_pai = ? and id_distrato = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_distrato);

			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_distrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Distrato excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o distrato do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarStatusDistrato( int status, int id_distrato) {
		
		String sql_update_distrato = "update distrato set status_distrato = ? where id_distrato = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_distrato);

			pstm.setInt(1, status);
			pstm.setInt(2, id_distrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "status do distrato atualizado, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o  status do distrato no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}
	
	
	
}
