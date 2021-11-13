package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroAnotacaoGeral;
import main.java.cadastros.CadastroNota;

public class GerenciarBancoAnotacaoGerais {

	public String sql_anotacao( CadastroAnotacaoGeral nota) {
		
		
		return "insert into anotacao_geral (texto, id_cliente) values ('"
				+ nota.getTexto() + "','"
				+ nota.getId_cliente() + "')";
	}
	
	
	
	public int inserirnota(CadastroAnotacaoGeral nota) {
		int result = -1;
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_anotacao(nota);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id nota inserida: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir a anotação no banco de dados!\nErro: " + e.getMessage() + "\nCausa: " + e.getCause()  );
				
				return -1;
			}
		
	}
	
	
	public ArrayList<CadastroAnotacaoGeral> getnotas(int id_cliente) {
		String selectAnotacoes = "select * from anotacao_geral where id_cliente = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroAnotacaoGeral> lista_anotacoes = new ArrayList<CadastroAnotacaoGeral>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAnotacoes);
			pstm.setInt(1, id_cliente);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroAnotacaoGeral nota = new CadastroAnotacaoGeral();
			
				nota.setId_anotacao(rs.getInt("id_anotacao"));
				nota.setId_cliente(rs.getInt("id_cliente"));
				nota.setTexto(rs.getString("texto"));
			
				lista_anotacoes.add(nota);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar notas"  );
		}
		return lista_anotacoes;

	}
	
	
	
	
	public CadastroAnotacaoGeral getnota(int id) {

		String selectnota = "select * from anotacao_geral where id_anotacao = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroAnotacaoGeral nota = new CadastroAnotacaoGeral();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectnota);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			nota.setId_cliente(rs.getInt("id_cliente"));
			nota.setId_anotacao(rs.getInt("id_anotacao"));
			nota.setTexto(rs.getString("texto"));

		    return nota;

		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro ao listar o nota id: " + id + " erro: "  );
			System.out.println(
					"Erro ao listar nota id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removernota( int id_nota) {
		String sql_delete_nota = "DELETE FROM anotacao_geral WHERE id_anotacao = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_nota);

			pstm.setInt(1, id_nota);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Anotação Excluída, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir a anotação do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	
	public boolean atualizarNota(CadastroAnotacaoGeral nota) {
		
		String sql_update_nota = "update anotacao_geral set texto = ? where id_anotacao = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_nota);

			pstm.setString(1, nota.getTexto());
		
			pstm.setInt(2, nota.getId_anotacao());


			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar a anotação no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}
	
	
	
}
