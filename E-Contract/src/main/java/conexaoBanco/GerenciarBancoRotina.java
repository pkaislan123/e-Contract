
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroProduto;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;
import main.java.cadastros.FinanceiroConta;
import main.java.cadastros.CadastroFuncionarioRotinaTrabalho;

public class GerenciarBancoRotina {

	/*
	 * CREATE TABLE `rotina_trabalho` ( `id_rotina` int(250) NOT NULL
	 * AUTO_INCREMENT, `id_ct_trabalho` int(3) NOT NULL, `tipo_rotina` int(3) NOT
	 * NULL, `id_funcionario` int(5) NOT NULL, `dia_semana` int(3) DEFAULT NULL,
	 * `hora_entrada1` varchar(30) DEFAULT NULL, `hora_saida1` varchar(30) DEFAULT
	 * NULL, `hora_entrada2` varchar(30) DEFAULT NULL, `hora_saida2` varchar(30)
	 * DEFAULT NULL, PRIMARY KEY (`id_rotina`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 */

	public int inserir_rotina_trabalho(CadastroFuncionarioRotinaTrabalho rotina_trabalho) {
		if (rotina_trabalho != null) {
			Connection conn = null;
			try {
				conn = ConexaoBanco.getConexao();
				String sql = "insert into rotina_trabalho\r\n"
						+ "( id_ct_trabalho, tipo_rotina, id_funcionario,dia_da_semana, hora_entrada1,hora_saida1 , hora_entrada2, hora_saida2) values ('"
						+ rotina_trabalho.getId_ct_trabalho() + "','" + rotina_trabalho.getTipo_rotina() + "','"
						+ rotina_trabalho.getId_funcionario() + "','" + rotina_trabalho.getDia_da_semana() + "','"
						+ rotina_trabalho.getHora_entrada1() + "','" + rotina_trabalho.getHora_saida1() + "','"
						+ rotina_trabalho.getHora_entrada2() + "','" + rotina_trabalho.getHora_saida2() + "')";

				PreparedStatement grava = (PreparedStatement) conn.prepareStatement(sql);
				grava.execute();
				// JOptionPane.showMessageDialog(null, "Rotina Trabalho Cadastrado");
				ConexaoBanco.fechaConexao(conn, grava);
				return 1;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir rotina no bd, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
				return 0;
			}
		} else {
			System.out.println("Dados enviado por parametro esta vazia");
			return 0;
		}
	}

	public ArrayList<CadastroFuncionarioRotinaTrabalho> getRotinas(int id_ct_trabalho, int id_funcionario) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas = new ArrayList<CadastroFuncionarioRotinaTrabalho>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn
					.prepareStatement("select * from rotina_trabalho where id_ct_trabalho = ? and id_funcionario = ?");
			pstm.setInt(1, id_ct_trabalho);
			pstm.setInt(2, id_funcionario);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioRotinaTrabalho rotina_trabalho = new CadastroFuncionarioRotinaTrabalho();
				rotina_trabalho.setId_rotina(rs.getInt("id_rotina"));
				rotina_trabalho.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));

				rotina_trabalho.setTipo_rotina(rs.getInt("tipo_rotina"));

				rotina_trabalho.setId_funcionario(rs.getInt("id_funcionario"));
				rotina_trabalho.setDia_da_semana(rs.getInt("dia_da_semana"));
				rotina_trabalho.setHora_entrada1(rs.getString("hora_entrada1"));
				rotina_trabalho.setHora_saida1(rs.getString("hora_saida1"));
				rotina_trabalho.setHora_entrada2(rs.getString("hora_entrada2"));
				rotina_trabalho.setHora_saida2(rs.getString("hora_saida2"));

				rotinas.add(rotina_trabalho);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar safras" );
		}
		return rotinas;
	}

	public CadastroFuncionarioRotinaTrabalho getRotina(int id) {
		String selectSalarioMinimo = "select * from rotina_trabalho where id_rotina = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioRotinaTrabalho rotina_trabalho = new CadastroFuncionarioRotinaTrabalho();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectSalarioMinimo);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			rs.next();

			rotina_trabalho.setId_rotina(rs.getInt("id_rotina"));
			rotina_trabalho.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));

			rotina_trabalho.setTipo_rotina(rs.getInt("tipo_rotina"));

			rotina_trabalho.setId_funcionario(rs.getInt("id_funcionario"));
			rotina_trabalho.setDia_da_semana(rs.getInt("dia_da_semana"));
			rotina_trabalho.setHora_entrada1(rs.getString("hora_entrada1"));
			rotina_trabalho.setHora_saida1(rs.getString("hora_saida1"));
			rotina_trabalho.setHora_entrada2(rs.getString("hora_entrada2"));
			rotina_trabalho.setHora_saida2(rs.getString("hora_saida2"));

			return rotina_trabalho;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + "
			// erro: " );
			return null;
		}

	}

	public CadastroFuncionarioRotinaTrabalho getRotinaDiaSemana(int id_func, int dia_semana) {
		String selectSalarioMinimo = "select * from rotina_trabalho where id_funcionario = ? and dia_da_semana = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioRotinaTrabalho rotina_trabalho = new CadastroFuncionarioRotinaTrabalho();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectSalarioMinimo);
			pstm.setInt(1, id_func);
			pstm.setInt(2, dia_semana);

			rs = pstm.executeQuery();
			rs.next();

			rotina_trabalho.setId_rotina(rs.getInt("id_rotina"));

			rotina_trabalho.setTipo_rotina(rs.getInt("tipo_rotina"));

			rotina_trabalho.setId_funcionario(rs.getInt("id_funcionario"));
			rotina_trabalho.setDia_da_semana(rs.getInt("dia_da_semana"));
			rotina_trabalho.setHora_entrada1(rs.getString("hora_entrada1"));
			rotina_trabalho.setHora_saida1(rs.getString("hora_saida1"));
			rotina_trabalho.setHora_entrada2(rs.getString("hora_entrada2"));
			rotina_trabalho.setHora_saida2(rs.getString("hora_saida2"));

			return rotina_trabalho;

		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao buscar por safra id: " + id + "
			// erro: " );
			return null;
		}

	}

	public ArrayList<CadastroFuncionarioRotinaTrabalho> getRotinas(int id_funcionario) {

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas = new ArrayList<CadastroFuncionarioRotinaTrabalho>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("select * from rotina_trabalho where  id_funcionario = ?");
			pstm.setInt(1, id_funcionario);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioRotinaTrabalho rotina_trabalho = new CadastroFuncionarioRotinaTrabalho();
				rotina_trabalho.setId_rotina(rs.getInt("id_rotina"));
				rotina_trabalho.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));

				rotina_trabalho.setTipo_rotina(rs.getInt("tipo_rotina"));

				rotina_trabalho.setId_funcionario(rs.getInt("id_funcionario"));
				rotina_trabalho.setDia_da_semana(rs.getInt("dia_da_semana"));
				rotina_trabalho.setHora_entrada1(rs.getString("hora_entrada1"));
				rotina_trabalho.setHora_saida1(rs.getString("hora_saida1"));
				rotina_trabalho.setHora_entrada2(rs.getString("hora_entrada2"));
				rotina_trabalho.setHora_saida2(rs.getString("hora_saida2"));

				rotinas.add(rotina_trabalho);
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			// JOptionPane.showMessageDialog(null, "Erro ao listar safras" );
		}
		return rotinas;
	}

	public int getNumRotinas(int ct_ativo) {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioRotinaTrabalho> rotinas = new ArrayList<CadastroFuncionarioRotinaTrabalho>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement("select count(*) as num_rotinas from rotina_trabalho where id_ct_trabalho = ?");
			pstm.setInt(1, ct_ativo);
			rs = pstm.executeQuery();

			rs.next();
			int num_rotinas = rs.getInt("num_rotinas");
			
			ConexaoBanco.fechaConexao(conn, pstm, rs);
			return num_rotinas;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar rotinas, erro: " + e.getMessage() + "\nCausa: " + e.getCause() );
			return -1;

		}
	}

}
