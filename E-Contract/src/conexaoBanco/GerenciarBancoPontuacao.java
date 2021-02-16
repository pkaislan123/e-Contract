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
import cadastros.CadastroPontuacao;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;

public class GerenciarBancoPontuacao {

	
	
	public String sql_pontuacao(CadastroPontuacao pontuacao) {
		return "insert into pontuacao (id_contrato, id_cliente, pontos, motivo, descricao) values ('"
				+ pontuacao.getId_contrato() + "','"
				+ pontuacao.getId_cliente() + "','"
				+ pontuacao.getPontos() + "','"
			    + pontuacao.getMotivo() + "','"
				+ pontuacao.getDescricao() +  "')";
	}
	
	public int inserirPontuacao(CadastroPontuacao pontuacao) {
		int result = -1;
		int id_cliente = -1;
		if (pontuacao != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_pontuacao(pontuacao);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id pontuacao inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir a pontuacao no banco de " + "dados "  );
				
				return -1;
			}
		} else {
			System.out.println("A pontuacao enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroPontuacao> getPontuacaoPorContrato(int id_contrato) {
		String selectPontuacao = "select * from pontuacao where id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroPontuacao> lista_pontuacao = new ArrayList<CadastroPontuacao>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPontuacao);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroPontuacao pontuacao = new CadastroPontuacao();
			
			    pontuacao.setId_pontuacao(rs.getInt("id_pontuacao"));
			    pontuacao.setId_contrato(rs.getInt("id_contrato"));
			    pontuacao.setId_cliente(rs.getInt("id_cliente"));
			    pontuacao.setPontos(rs.getInt("pontos"));
			    pontuacao.setDescricao(rs.getString("descricao"));
			    pontuacao.setMotivo(rs.getInt("motivo"));
				
				lista_pontuacao.add(pontuacao);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar pontuacao"  );
		}
		return lista_pontuacao;

	}
	
	
	public ArrayList<CadastroPontuacao> getPontuacaoPorCliente(int id_cliente) {
		String selectPontuacao = "select * from pontuacao where id_cliente = ?;";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroPontuacao> lista_pontuacao = new ArrayList<>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPontuacao);
			pstm.setInt(1, id_cliente);

			rs = pstm.executeQuery();
			while (rs.next()) {
				if (rs != null) {
				CadastroPontuacao pontuacao = new CadastroPontuacao();
			
			    pontuacao.setId_pontuacao(rs.getInt("id_pontuacao"));
			    pontuacao.setId_contrato(rs.getInt("id_contrato"));
			    pontuacao.setId_cliente(rs.getInt("id_cliente"));
			    pontuacao.setPontos(rs.getInt("pontos"));
			    pontuacao.setDescricao(rs.getString("descricao"));
			    pontuacao.setMotivo(rs.getInt("motivo"));

			    
				lista_pontuacao.add(pontuacao);
				}

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			
			

			return lista_pontuacao;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar pontuacao"  );
			return null;
		}
		
	
	}
	
	public ArrayList<CadastroPontuacao> getPontuacaoPorContratoCliente(int id_contrato, int id_cliente) {
		String selectPontuacao = "select * from pontuacao where id_contrato = ? and id_cliente = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroPontuacao> lista_pontuacao = new ArrayList<CadastroPontuacao>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectPontuacao);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_contrato);
			pstm.setInt(2, id_cliente);


			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroPontuacao pontuacao = new CadastroPontuacao();
			
			    pontuacao.setId_pontuacao(rs.getInt("id_pontuacao"));
			    pontuacao.setId_contrato(rs.getInt("id_contrato"));
			    pontuacao.setId_cliente(rs.getInt("id_cliente"));
			    pontuacao.setPontos(rs.getInt("pontos"));
			    pontuacao.setDescricao(rs.getString("descricao"));
			    pontuacao.setMotivo(rs.getInt("motivo"));

				lista_pontuacao.add(pontuacao);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar pontuacao"  );
		}
		return lista_pontuacao;

	}
	
	
	public boolean removerPontuacao(int id_pontuacao) {
		String sql_delete_pontuacao = "DELETE FROM pontuacao WHERE id_pontuacao = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_pontuacao);

			pstm.setInt(1, id_pontuacao);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Pontuacao excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir a pontuacao do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	
	
	
}
