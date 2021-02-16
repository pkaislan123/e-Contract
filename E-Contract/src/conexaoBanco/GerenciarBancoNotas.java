package conexaoBanco;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

import cadastros.CadastroNota;
import cadastros.CadastroCliente;
import cadastros.CadastroContrato;
import cadastros.CadastroProduto;
import cadastros.CadastroSafra;
import java.time.format.DateTimeFormatter;
public class GerenciarBancoNotas {

	
	
	public String sql_nota( CadastroNota nota) {
		
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");


		

		String strDate = dateFormat1.format(nota.getData_nota());

		String strDateLembrete = "";
		String strHoraLembrete = "";

		if(nota.getLembrar() == 0) {
			strDateLembrete = "2021-01-01";
			strHoraLembrete = "00:00:00";
		}else {

			strDateLembrete = dateFormat1.format(nota.getData_lembrete());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			strHoraLembrete  = nota.getHora_lembrete().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		}
		return "insert into nota (nome, descricao, texto, tipo, data_nota, id_usuario_pai, lembrar, data_lembrete, hora_lembrete, notificar, uni_tempo,tempo_notificacao) values ('"
				+ nota.getNome() + "','"
				+ nota.getDescricao() + "','"
				+ nota.getTexto() + "','"
				+ nota.getTipo() + "','"
				+ strDate+ "','"
				+ nota.getId_usuario_pai() + "','"
				+ nota.getLembrar() + "','"
				+ strDateLembrete + "','"
				+ strHoraLembrete + "','"
				+ nota.getNotificar() + "','"
				+ nota.getUni_tempo() + "','"
						
				+ nota.getTempo_notificacao() + "')";
	}
	
	
	
	
	
	
	public int inserirnota(CadastroNota nota) {
		int result = -1;
		int id_cliente = -1;
		if (nota != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_nota(nota);
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
						"Erro ao inserir o nota no banco de " + "dados "  );
				
				return -1;
			}
		} else {
			System.out.println("O nota enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroNota> getnotas(int id_usuario_pai) {
		String selectAdivitos = "select * from nota where id_usuario_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroNota> lista_notas = new ArrayList<CadastroNota>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);
			pstm.setInt(1, id_usuario_pai);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroNota nota = new CadastroNota();
			
				
				nota.setId(rs.getInt("id_nota"));
				nota.setNome(rs.getString("nome"));
				nota.setDescricao(rs.getString("descricao"));
				nota.setTexto(rs.getString("texto"));
				nota.setData_nota(rs.getDate("data_nota"));
				nota.setId_usuario_pai(rs.getInt("id_usuario_pai"));
				nota.setLembrar(rs.getInt("lembrar"));
				nota.setTipo(rs.getInt("tipo"));
				nota.setData_lembrete(rs.getDate("data_lembrete"));
				nota.setHora_lembrete(rs.getTime("hora_lembrete").toLocalTime());
				nota.setNotificar(rs.getInt("notificar"));
				nota.setUni_tempo(rs.getInt("uni_tempo"));
				nota.setTempo_notificacao(rs.getInt("tempo_notificacao"));

				lista_notas.add(nota);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar notas"  );
		}
		return lista_notas;

	}
	
	public ArrayList<CadastroNota> getTodasNotas() {
		String selectAdivitos = "select * from nota";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroNota> lista_notas = new ArrayList<CadastroNota>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroNota nota = new CadastroNota();
			
				
				nota.setId(rs.getInt("id_nota"));
				nota.setNome(rs.getString("nome"));
				nota.setDescricao(rs.getString("descricao"));
				nota.setTexto(rs.getString("texto"));
				nota.setData_nota(rs.getDate("data_nota"));
				nota.setId_usuario_pai(rs.getInt("id_usuario_pai"));
				nota.setLembrar(rs.getInt("lembrar"));
				nota.setTipo(rs.getInt("tipo"));
				nota.setData_lembrete(rs.getDate("data_lembrete"));
				nota.setHora_lembrete(rs.getTime("hora_lembrete").toLocalTime());
				nota.setNotificar(rs.getInt("notificar"));
				nota.setUni_tempo(rs.getInt("uni_tempo"));
				nota.setTempo_notificacao(rs.getInt("tempo_notificacao"));

				lista_notas.add(nota);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar notas"  );
		}
		return lista_notas;

	}
	
	public CadastroNota getnota(int id) {

		String selectnota = "select * from nota where id_nota = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroNota nota = new CadastroNota();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectnota);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			nota.setId(rs.getInt("id_nota"));
			nota.setNome(rs.getString("nome"));
			nota.setDescricao(rs.getString("descricao"));
			nota.setTexto(rs.getString("texto"));
			nota.setData_nota(rs.getDate("data_nota"));
			nota.setId_usuario_pai(rs.getInt("id_usuario_pai"));
			nota.setLembrar(rs.getInt("lembrar"));
			nota.setTipo(rs.getInt("tipo"));
			nota.setData_lembrete(rs.getDate("data_lembrete"));
			nota.setHora_lembrete(rs.getTime("hora_lembrete").toLocalTime());
			nota.setNotificar(rs.getInt("notificar"));
			nota.setUni_tempo(rs.getInt("uni_tempo"));
			nota.setTempo_notificacao(rs.getInt("tempo_notificacao"));
			

		    return nota;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o nota id: " + id + " erro: "  );
			System.out.println(
					"Erro ao listar nota id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removernota(int id_usuario_pai, int id_nota) {
		String sql_delete_nota = "DELETE FROM nota WHERE id_usuario_pai = ? and id_nota = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_nota);

			pstm.setInt(1, id_usuario_pai);
			pstm.setInt(2, id_nota);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "nota excluida, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o nota do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarStatusnota(CadastroNota nota) {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");


		

		String strDate = dateFormat1.format(nota.getData_nota());

		String strDateLembrete = "";
		String strHoraLembrete = "";

		if(nota.getLembrar() == 0) {
			strDateLembrete = "2021-01-01";
			strHoraLembrete = "00:00:00";
		}else {

			strDateLembrete = dateFormat1.format(nota.getData_lembrete());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			strHoraLembrete  = nota.getHora_lembrete().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		}
		String sql_update_nota = "update nota set nome = ?, descricao = ?, texto = ?, tipo = ?, data_nota = ?, id_usuario_pai = ?, lembrar = ?,"
				+ "data_lembrete = ?, hora_lembrete = ?, notificar = ?, uni_tempo = ?, tempo_notificacao = ? where id_nota = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_update_nota);

			pstm.setString(1, nota.getNome());
			pstm.setString(2, nota.getDescricao());
			pstm.setString(3, nota.getTexto());
			pstm.setInt(4, nota.getTipo());
			pstm.setString(5, strDate);
			pstm.setInt(6, nota.getId_usuario_pai());
			pstm.setInt(7, nota.getLembrar());
			pstm.setString(8, strDateLembrete);
			pstm.setString(9, strHoraLembrete);
			pstm.setInt(10, nota.getNotificar());
			pstm.setInt(11,nota.getUni_tempo());
			pstm.setInt(12,nota.getTempo_notificacao());
			pstm.setInt(13, nota.getId());


			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "status do nota atualizado, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao atualizar o  status do nota no banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}
	}
	
	
	
}
