

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioEvento;
import main.java.cadastros.CadastroFuncionarioEvento;


public class GerenciarBancoFuncionariosEventos {
/*
CREATE TABLE `funcionario_evento` (
  `id_evento` int(5) NOT NULL AUTO_INCREMENT,
    `id_colaborador` int(5) ,
  `id_evento` int(5) ,
  `tipo_evento` int(5) ,
  `data_evento` varchar(40) ,
  `data_folga` varchar(40) ,
  `data_ferias_ida` varchar(40) ,
  `data_ferias_volta` varchar(40) ,
  `novo_valor_salarial` varchar(40) ,
  `movitvo_demissao`  int(5)  ,



  PRIMARY KEY (`id_evento`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
 */
	

	public String sql_ContratodeTrabalho(CadastroFuncionarioEvento evento) {
		return "insert into funcionario_evento (id_colaborador, id_contrato, tipo_evento, data_evento,data_folga,data_ferias_ida,data_ferias_volta, novo_valor_salarial, motivo_demissao) values ('"
				+ evento.getId_colaborador() + "','"
				+ evento.getId_contrato() + "','"
				+ evento.getTipo_evento() + "','"
				+ evento.getData_evento() + "','"
				+ evento.getData_folga() + "','"
                + evento.getData_ferias_ida() + "','"
                        + evento.getData_ferias_volta() + "','"

                + evento.getNovo_valor_salarial() + "','"

                + evento.getMotivo_demissao() + "')";
	}
	
	public int inserirevento(CadastroFuncionarioEvento evento) {
		int result = -1;
		int id_cliente = -1;
		if (evento != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_ContratodeTrabalho(evento);
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
						"Erro ao inserir o evento no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O evento enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroFuncionarioEvento> geteventos() {
		String selectAdivitos = "select * from funcionario_evento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioEvento> lista_eventos = new ArrayList<CadastroFuncionarioEvento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioEvento evento = new CadastroFuncionarioEvento();
			
				
				evento.setId_evento(rs.getInt("id_evento"));
				evento.setId_colaborador(rs.getInt("id_colaborador"));
                evento.setTipo_evento(rs.getInt("tipo_evento"));
				evento.setData_evento(rs.getString("data_evento"));
				evento.setData_folga(rs.getString("data_folga"));
				evento.setData_ferias_ida(rs.getString("data_ferias_ida"));
				evento.setData_ferias_volta(rs.getString("data_ferias_volta"));
				evento.setNovo_valor_salarial(rs.getDouble("novo_valor_salarial"));

				evento.setMotivo_demissao(rs.getInt("motivo_demissao"));


				lista_eventos.add(evento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar eventos");//  );
		}
		return lista_eventos;

	}
	
	
	public ArrayList<CadastroFuncionarioEvento> getEventosPorColaborador(int id_coloborador) {
		String selectAdivitos = "select * from funcionario_evento where id_colaborador = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioEvento> lista_eventos = new ArrayList<CadastroFuncionarioEvento>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			 pstm.setInt(1, id_coloborador);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioEvento evento = new CadastroFuncionarioEvento();
			
				


				evento.setId_evento(rs.getInt("id_evento"));
				evento.setId_colaborador(rs.getInt("id_colaborador"));
                evento.setTipo_evento(rs.getInt("tipo_evento"));
				evento.setData_evento(rs.getString("data_evento"));
				evento.setData_folga(rs.getString("data_folga"));
				evento.setData_ferias_ida(rs.getString("data_ferias_ida"));
				evento.setData_ferias_volta(rs.getString("data_ferias_volta"));
				evento.setNovo_valor_salarial(rs.getDouble("novo_valor_salarial"));

				evento.setMotivo_demissao(rs.getInt("motivo_demissao"));




				lista_eventos.add(evento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar eventos");//  );
		}
		return lista_eventos;

	}
	
	
	/*
	 select novo_valor_salarial from funcionario_evento 
where id_colaborador = 554 and tipo_evento = 1
ORDER BY  id_evento DESC LIMIT 1;
	 */
	public double getUltimoValorSalarial(int id_func) {
		String sql_ContratodeTrabalho = "select novo_valor_salarial from funcionario_evento \r\n"
				+ "where id_colaborador = ? and tipo_evento = 1\r\n"
				+ "ORDER BY  id_evento DESC LIMIT 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioEvento evento = new CadastroFuncionarioEvento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id_func);

			rs = pstm.executeQuery();
			rs.next();

		
			double valor = rs.getDouble("novo_valor_salarial");
			

		    return valor;

		} catch (Exception e) {
			
			return -1;
		}
	}
	
	
	public CadastroFuncionarioEvento getevento(int id) {

		String sql_ContratodeTrabalho = "select * from funcionario_evento where id_evento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioEvento evento = new CadastroFuncionarioEvento();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			


			evento.setId_evento(rs.getInt("id_evento"));
			evento.setId_colaborador(rs.getInt("id_colaborador"));
            evento.setTipo_evento(rs.getInt("tipo_evento"));
			evento.setData_evento(rs.getString("data_evento"));
			evento.setData_folga(rs.getString("data_folga"));
			evento.setData_ferias_ida(rs.getString("data_ferias_ida"));
			evento.setData_ferias_volta(rs.getString("data_ferias_volta"));
			evento.setNovo_valor_salarial(rs.getDouble("novo_valor_salarial"));

			evento.setMotivo_demissao(rs.getInt("motivo_demissao"));



			

		    return evento;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o evento id: " + id );// );
			System.out.println(
					"Erro ao listar evento id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerevento( int id_evento) {
		String sql_ContratodeTrabalho = "DELETE FROM funcionario_evento WHERE id_evento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);

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
