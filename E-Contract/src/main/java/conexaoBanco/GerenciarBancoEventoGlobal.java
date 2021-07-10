

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.EventoGlobal;
import main.java.cadastros.EventoGlobal;


public class GerenciarBancoEventoGlobal {
/*

CREATE TABLE `evento_global` (
  `id_evento` int(5) NOT NULL AUTO_INCREMENT,
  `tipo_evento` int(5) DEFAULT NULL,
  `data_evento` varchar(40) DEFAULT NULL,
  `hora_evento` varchar(40) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  
  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8

*/
	

	public String sql_evento(EventoGlobal evento) {
		return "insert into evento_global (tipo_evento, data_evento, hora_evento, descricao) values ('"
				+ evento.getTipo_evento() + "','"
				+ evento.getData_evento() + "','"
				+ evento.getHora_evento() + "','"				
                + evento.getDescricao() + "')";
	}
	
	public int inserirevento(EventoGlobal evento) {
		int result = -1;
		int id_cliente = -1;
		if (evento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_evento(evento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id evento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o evento no banco de dados, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
				
				return -1;
			}
		} else {
			System.out.println("O evento enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<EventoGlobal> geteventos() {
		String selectAdivitos = "select * from evento_global";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<EventoGlobal> lista_eventos = new ArrayList<EventoGlobal>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				EventoGlobal evento = new EventoGlobal();
			
				
				evento.setId_evento(rs.getInt("id_evento"));
				evento.setTipo_evento(rs.getInt("tipo_evento"));
				evento.setData_evento(rs.getString("data_evento"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setHora_evento(rs.getString("hora_evento"));
				lista_eventos.add(evento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar eventos");//  );
		}
		return lista_eventos;

	}
	
	
	public ArrayList<EventoGlobal> getEventosPorId(int id_evento) {
		String selectAdivitos = "select * from evento_global where id_evento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<EventoGlobal> lista_eventos = new ArrayList<EventoGlobal>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			 pstm.setInt(1, id_evento);

			rs = pstm.executeQuery();
			while (rs.next()) {
				EventoGlobal evento = new EventoGlobal();
			
				


				
				evento.setId_evento(rs.getInt("id_evento"));
				evento.setTipo_evento(rs.getInt("tipo_evento"));
				evento.setData_evento(rs.getString("data_evento"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setHora_evento(rs.getString("hora_evento"));



				lista_eventos.add(evento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar eventos");//  );
		}
		return lista_eventos;

	}
	
	public ArrayList<EventoGlobal> getEventosPorData(String data) {
		String selectAdivitos = "select * from evento_global where data_evento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<EventoGlobal> lista_eventos = new ArrayList<EventoGlobal>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			 pstm.setString(1, data);

			rs = pstm.executeQuery();
			while (rs.next()) {
				EventoGlobal evento = new EventoGlobal();
			
				


				
				evento.setId_evento(rs.getInt("id_evento"));
				evento.setTipo_evento(rs.getInt("tipo_evento"));
				evento.setData_evento(rs.getString("data_evento"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setHora_evento(rs.getString("hora_evento"));



				lista_eventos.add(evento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar eventos");//  );
		}
		return lista_eventos;

	}
	
	

	
	public boolean removerevento( int id_evento) {
		String sql_excluir = "DELETE FROM evento_global WHERE id_evento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_excluir);

			pstm.setInt(1, id_evento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "evento excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o evento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	

	

}
