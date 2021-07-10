package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroContrato;
import main.java.cadastros.CadastroTarefaGeral;

public class GerenciarBancoTarefaGeral {

	
	public String sql_tarefa(CadastroTarefaGeral tarefa) {

		String query = "insert into tarefa_geral (status_tarefa, nome_tarefa, descricao_tarefa , mensagem, hora, data_tarefa, id_usuario_criador, id_usuario_executor, hora_agendada, data_agendada, prioridade, tipo, id_lancamento_pai, id_funcionario_pai) values ('"
				+ tarefa.getStatus_tarefa() + "','" + tarefa.getNome_tarefa() + "','" + tarefa.getDescricao_tarefa()
				+ "','" + tarefa.getMensagem() + "','" + tarefa.getHora() + "','" + tarefa.getData() + "','"
				+ tarefa.getCriador().getId()

				+ "','" + tarefa.getExecutor().getId() + "','" + tarefa.getHora_agendada()
				+ "','" + tarefa.getData_agendada() 
				+ "','" + tarefa.getPrioridade()
				+ "','" + tarefa.getTipo()
				+ "','" + tarefa.getId_lancamento_pai()
				+ "','" + tarefa.getId_funcionario_pai() + "')";
		
		return query;

	}

	
	public boolean inserirTarefaGeral(CadastroTarefaGeral tarefa) {
		int result = -1;
		if (tarefa != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_tarefa(tarefa);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
				}
				rs.close();
				stmt.close();

				return true;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir a tarefa no banco de dados\nErro: " + e.getMessage() + "\n" + e.getCause());
				return false;
			}
		} else {
			System.out.println("A tarefa enviado por parametro esta vazio");
			return false;
		}
	}

	
	public ArrayList<CadastroTarefaGeral> consultaTarefasRomaneios() {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select tf.*,\n"
				+ "CONCAT(criador.nome, \" \", criador.sobrenome) as criador,\n"
				+ "CONCAT(executor.nome, \" \", executor.sobrenome) as executor\n"
				+ " from tarefa_geral tf \n"
				+ "join usuarios criador on criador.id_usuario = tf.id_usuario_criador\n"
				+ "join usuarios executor on executor.id_usuario = tf.id_usuario_criador where tipo = 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);

			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroTarefaGeral tarefa = new CadastroTarefaGeral();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));
					tarefa.setNome_executor(rs.getString("executor"));
					tarefa.setNome_criador(rs.getString("criador"));
				
					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));
					tarefa.setTipo(rs.getInt("tipo"));


					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}

	public ArrayList<CadastroTarefaGeral> consultaTarefasLancamentos(int id_lancamento_pai) {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select tf.*,\n"
				+ "CONCAT(criador.nome, \" \", criador.sobrenome) as criador,\n"
				+ "CONCAT(executor.nome, \" \", executor.sobrenome) as executor\n"
				+ " from tarefa_geral tf \n"
				+ "join usuarios criador on criador.id_usuario = tf.id_usuario_criador\n"
				+ "join usuarios executor on executor.id_usuario = tf.id_usuario_criador where tipo = 2 and id_lancamento_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_lancamento_pai);
			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroTarefaGeral tarefa = new CadastroTarefaGeral();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));
					tarefa.setNome_executor(rs.getString("executor"));
					tarefa.setNome_criador(rs.getString("criador"));

				
					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));
					tarefa.setTipo(rs.getInt("tipo"));
					tarefa.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}
	
	
	public ArrayList<CadastroTarefaGeral> consultaTarefasFuncionarios(int id_funcionario_pai) {
		System.out.println("Lista tarefas foi chamado!");
		String selectTarefas = "select tf.*,\n"
				+ "CONCAT(criador.nome, \" \", criador.sobrenome) as criador,\n"
				+ "CONCAT(executor.nome, \" \", executor.sobrenome) as executor\n"
				+ " from tarefa_geral tf \n"
				+ "join usuarios criador on criador.id_usuario = tf.id_usuario_criador\n"
				+ "join usuarios executor on executor.id_usuario = tf.id_usuario_criador where tipo = 5 and id_funcionario_pai = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroTarefaGeral> lista_tarefas = new ArrayList<>();;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectTarefas);
			pstm.setInt(1, id_funcionario_pai);
			rs = pstm.executeQuery();
			int i = 0;

			while (rs.next()) {

				if (rs != null) {
					System.out.print("tarefa não e nula!");

					CadastroTarefaGeral tarefa = new CadastroTarefaGeral();

					tarefa.setId_tarefa(rs.getInt("id_tarefa"));
					tarefa.setStatus_tarefa(rs.getInt("status_tarefa"));
					tarefa.setNome_tarefa(rs.getString("nome_tarefa"));
					tarefa.setDescricao_tarefa(rs.getString("descricao_tarefa"));
					tarefa.setMensagem(rs.getString("mensagem"));
					tarefa.setHora(rs.getString("hora"));
					tarefa.setData(rs.getString("data_tarefa"));
					tarefa.setResposta(rs.getString("resposta"));
					tarefa.setNome_executor(rs.getString("executor"));
					tarefa.setNome_criador(rs.getString("criador"));

					tarefa.setId_funcionario_pai(rs.getInt("id_funcionario_pai"));
					tarefa.setHora_agendada(rs.getString("hora_agendada"));
					tarefa.setData_agendada(rs.getString("data_agendada"));
					tarefa.setPrioridade(rs.getInt("prioridade"));
					tarefa.setTipo(rs.getInt("tipo"));
					tarefa.setId_lancamento_pai(rs.getInt("id_lancamento_pai"));

					lista_tarefas.add(tarefa);

				}
			}

			ConexaoBanco.fechaConexao(conn, pstm, rs);
			System.out.println("Tarefas foram listadas com sucesso!");
			return lista_tarefas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar todas as tarefas \n erro: " + e.getMessage() + "causa: " + e.getCause());
			return null;
		}

	}
	
}
