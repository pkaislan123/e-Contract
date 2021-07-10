
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAdmissao;
import main.java.cadastros.CadastroFuncionarioAdmissao;

public class GerenciarBancoFuncionariosContratoTrabalho {
	/*
	 * CREATE TABLE `funcionario_contrato_trabalho` ( `id_contrato` int(3) NOT NULL
	 * AUTO_INCREMENT, `status` int(3) not null, `data_admissao` varchar(40) DEFAULT
	 * NULL, `tipo_contrato` text DEFAULT NULL, `cargo` text DEFAULT NULL, `funcao`
	 * text DEFAULT NULL, `salario` double DEFAULT NULL,
	 * 
	 * PRIMARY KEY (`id_contrato`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8
	 */

	public String sql_ContratodeTrabalho(CadastroFuncionarioAdmissao contrato) {
		return "insert into funcionario_contrato_trabalho (id_colaborador, status, data_admissao, tipo_contrato,cargo,funcao,salario) values ('"
				+ contrato.getId_colaborador() + "','" + contrato.getStatus() + "','" + contrato.getData_admissao()
				+ "','" + contrato.getTipo_contrato() + "','" + contrato.getCargo() + "','" + contrato.getFuncao()
				+ "','" + contrato.getSalario() + "')";
	}

	public int inserircontrato(CadastroFuncionarioAdmissao contrato) {
		int result = -1;
		int id_cliente = -1;
		if (contrato != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_ContratodeTrabalho(contrato);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id contrato inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o contrato no banco de dados");

				return -1;
			}
		} else {
			System.out.println("O contrato enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<CadastroFuncionarioAdmissao> getcontratos() {
		String selectAdivitos = "select * from funcionario_contrato_trabalho";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioAdmissao> lista_contratos = new ArrayList<CadastroFuncionarioAdmissao>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

				contrato.setId_contrato(rs.getInt("id_contrato"));
				contrato.setId_colaborador(rs.getInt("id_colaborador"));

				contrato.setStatus(rs.getInt("status"));
				contrato.setData_admissao(rs.getString("data_admissao"));
				contrato.setTipo_contrato(rs.getString("tipo_contrato"));
				contrato.setCargo(rs.getString("cargo"));
				contrato.setFuncao(rs.getString("funcao"));
				contrato.setSalario(rs.getDouble("salario"));

				lista_contratos.add(contrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contratos");// );
		}
		return lista_contratos;

	}

	public ArrayList<CadastroFuncionarioAdmissao> getcontratosPorColaborador(int id_coloborador) {
		String selectAdivitos = "select * from funcionario_contrato_trabalho where id_colaborador = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioAdmissao> lista_contratos = new ArrayList<CadastroFuncionarioAdmissao>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			pstm.setInt(1, id_coloborador);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

				contrato.setId_contrato(rs.getInt("id_contrato"));
				contrato.setId_colaborador(rs.getInt("id_colaborador"));

				contrato.setStatus(rs.getInt("status"));
				contrato.setData_admissao(rs.getString("data_admissao"));
				contrato.setTipo_contrato(rs.getString("tipo_contrato"));
				contrato.setCargo(rs.getString("cargo"));
				contrato.setFuncao(rs.getString("funcao"));
				contrato.setSalario(rs.getDouble("salario"));

				lista_contratos.add(contrato);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar contratos");// );
		}
		return lista_contratos;

	}

	public CadastroFuncionarioAdmissao getcontrato(int id) {

		String sql_ContratodeTrabalho = "select * from funcionario_contrato_trabalho where id_contrato = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId_contrato(rs.getInt("id_contrato"));
			contrato.setId_colaborador(rs.getInt("id_colaborador"));

			contrato.setStatus(rs.getInt("status"));
			contrato.setData_admissao(rs.getString("data_admissao"));
			contrato.setTipo_contrato(rs.getString("tipo_contrato"));
			contrato.setCargo(rs.getString("cargo"));
			contrato.setFuncao(rs.getString("funcao"));
			contrato.setSalario(rs.getDouble("salario"));

			return contrato;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o contrato id: " + id);// );
			System.out.println("Erro ao listar contrato id: " + id + " erro: " + "\ncausa: ");
			return null;
		}

	}
	
	

	public CadastroFuncionarioAdmissao getcontratoAtivoPorFuncionario(int id_func) {

		String sql_ContratodeTrabalho = "select * from funcionario_contrato_trabalho where id_colaborador = ? and status = 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id_func);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId_contrato(rs.getInt("id_contrato"));
			contrato.setId_colaborador(rs.getInt("id_colaborador"));

			contrato.setStatus(rs.getInt("status"));
			contrato.setData_admissao(rs.getString("data_admissao"));
			contrato.setTipo_contrato(rs.getString("tipo_contrato"));
			contrato.setCargo(rs.getString("cargo"));
			contrato.setFuncao(rs.getString("funcao"));
			contrato.setSalario(rs.getDouble("salario"));

			return contrato;

		} catch (Exception e) {
			return null;
		}

	}

	
	public CadastroFuncionarioAdmissao getcontratoMesPagamento(int id_func, String data_pri_dia_paga) {

		String sql_ContratodeTrabalho = "call buscar_contrato_mes_pagamento_salario(?, ?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id_func);
			pstm.setString(2, data_pri_dia_paga);

			rs = pstm.executeQuery();
			rs.next();

			contrato.setId_contrato(rs.getInt("id_contrato"));
			contrato.setId_colaborador(rs.getInt("id_colaborador"));

			contrato.setStatus(rs.getInt("status"));
			contrato.setData_admissao(rs.getString("data_admissao"));
			contrato.setTipo_contrato(rs.getString("tipo_contrato"));
			contrato.setCargo(rs.getString("cargo"));
			contrato.setFuncao(rs.getString("funcao"));
			contrato.setSalario(rs.getDouble("salario"));
			contrato.setData_encerramento_contrato(rs.getString("data_encerramento"));
			contrato.setUltimo_salario(rs.getDouble("novo_valor_salarial"));

			return contrato;

		} catch (Exception e) {
			return null;
		}

	}
	
	
	public boolean removercontrato(int id_contrato) {
		String sql_ContratodeTrabalho = "DELETE FROM funcionario_contrato_trabalho WHERE id_contrato = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);

			pstm.setInt(1, id_contrato);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "contrato excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o contrato do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarcontrato(CadastroFuncionarioAdmissao contrato) {
		if (contrato != null) {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			try {

				atualizar = "update funcionario_contrato_trabalho set status = ?, data_admissao = ?, tipo_contrato = ?,"
						+ "cargo = ? , funcao = ?, salario = ? where id_contrato = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setInt(1, contrato.getStatus());
				pstm.setString(2, contrato.getData_admissao());
				pstm.setString(3, contrato.getTipo_contrato());
				pstm.setString(4, contrato.getCargo());
				pstm.setString(5, contrato.getFuncao());
				pstm.setDouble(6, contrato.getSalario());

				pstm.setInt(7, contrato.getId_contrato());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
				System.out.println("Contrato de Trabalho Atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o contrato no banco de" + "dados ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os dados do contrato estão vazios");
			return false;
		}

	}

	public boolean atualizarStatusContrato(int id_ct_contrato) {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			try {

				atualizar = "update funcionario_contrato_trabalho set status = 0  where id_contrato = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);

				pstm.setInt(1, id_ct_contrato);
				

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
				System.out.println("Contrato de Trabalho Atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o contrato no banco de" + "dados ");
				return false;
			}
		

	}

	public int getNumContratosAtivos(int id) {

		String sql_ContratodeTrabalho = "select count(*) as contratos_ativos from funcionario_contrato_trabalho where id_colaborador = ? and status = 1";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioAdmissao contrato = new CadastroFuncionarioAdmissao();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			int num_contratos_ativos = rs.getInt("contratos_ativos");

			return num_contratos_ativos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar a quantidade de contratos ativos para o colaborador, id: " + id);// );
			System.out.println("Erro ao listar contrato id: " + id + " erro: " + "\ncausa: ");
			return -1;
		}

	}

}
