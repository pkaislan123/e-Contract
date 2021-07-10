
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioBancoHoras;
import main.java.cadastros.CadastroFuncionarioBancoHoras;
import main.java.cadastros.CalculoCompleto;

public class GerenciarBancoFuncionarioBancoHoras {
	/*
	 * 
	 * CREATE TABLE `tabela_auxiliar_banco_horas` ( `id_banco` int(10) NOT NULL
	 * AUTO_INCREMENT, `id_funcionario` int(3) NOT NULL , `quantidade_horas` text
	 * not null, `mes` int(3) not null,
	 * 
	 * PRIMARY KEY (id_banco, id_funcionario) ) ENGINE=InnoDB AUTO_INCREMENT=0
	 * DEFAULT CHARSET=utf8
	 * 
	 */

	public String sql_banco_horas(CadastroFuncionarioBancoHoras banco_horas) {
		return "insert into tabela_auxiliar_banco_horas (id_funcionario, quantidade_horas, mes, tipo_banco) values ('"
				+ banco_horas.getId_funcionario() + "','" + banco_horas.getQuantidade_horas() + "','"
				+ banco_horas.getMes_referencia() + "','" + banco_horas.getTipo_banco() + "')";
	}

	public int inserirbanco_horas(CadastroFuncionarioBancoHoras banco_horas) {
		int result = -1;
		int id_cliente = -1;
		if (banco_horas != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_banco_horas(banco_horas);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id banco_horas inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir o banco_horas no banco de dados");

				return -1;
			}
		} else {
			System.out.println("O banco_horas enviado por parametro esta vazio");
			return -1;
		}
	}

	public ArrayList<CadastroFuncionarioBancoHoras> getBancoHoras() {
		String selectAdivitos = "select * from tabela_auxiliar_banco_horas";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioBancoHoras> lista_banco_horass = new ArrayList<CadastroFuncionarioBancoHoras>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioBancoHoras banco_horas = new CadastroFuncionarioBancoHoras();

				banco_horas.setId_banco(rs.getInt("id_banco"));
				banco_horas.setId_funcionario(rs.getInt("id_funcionario"));
				banco_horas.setMes_referencia(rs.getInt("mes"));
				banco_horas.setQuantidade_horas(rs.getString("quantidade_horas"));
				banco_horas.setTipo_banco(rs.getInt("tipo_banco"));

				lista_banco_horass.add(banco_horas);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar banco_horass");// );
		}
		return lista_banco_horass;

	}

	public ArrayList<CadastroFuncionarioBancoHoras> getBancoHorasPorFuncionario(int id_func) {

		String selectbanco_horas = "select * from tabela_auxiliar_banco_horas where id_funcionario = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioBancoHoras> lista_banco_horas = new ArrayList<>();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectbanco_horas);
			pstm.setInt(1, id_func);

			rs = pstm.executeQuery();
			while (rs.next()) {

				CadastroFuncionarioBancoHoras banco = new CadastroFuncionarioBancoHoras();

				banco.setId_banco(rs.getInt("id_banco"));
				banco.setId_funcionario(rs.getInt("id_funcionario"));
				banco.setMes_referencia(rs.getInt("mes"));
				banco.setQuantidade_horas(rs.getString("quantidade_horas"));
				banco.setTipo_banco(rs.getInt("tipo_banco"));

				lista_banco_horas.add(banco);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);

			return lista_banco_horas;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o banco_horas");// );
			System.out.println("Erro ao listar banco_horas id: erro: " + "\ncausa: ");
			return null;
		}

	}
	
	
	

	public CadastroFuncionarioBancoHoras getBancoHorasPorFuncionarioPorMes(int id_func, int mes) {

		String selectbanco_horas = "select * from tabela_auxiliar_banco_horas where id_funcionario = ? and mes = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioBancoHoras banco_horas = null;

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectbanco_horas);
			pstm.setInt(1, id_func);
			pstm.setInt(2, mes);

			rs = pstm.executeQuery();
			
			if(rs.next()) {
				banco_horas = new CadastroFuncionarioBancoHoras();

			banco_horas.setId_banco(rs.getInt("id_banco"));
			banco_horas.setId_funcionario(rs.getInt("id_funcionario"));
			banco_horas.setQuantidade_horas(rs.getString("quantidade_horas"));
			banco_horas.setMes_referencia(rs.getInt("mes"));
			banco_horas.setTipo_banco(rs.getInt("tipo_banco"));
			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);

			return banco_horas;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao listar o banco_horas, erro: " + e.getMessage() + "\nCausa: " + e.getCause());// );
			return null;
		}

	}

	public CadastroFuncionarioBancoHoras getBancoHorasPorId(int id_banco) {

		String selectbanco_horas = "select * from tabela_auxiliar_banco_horas where id_banco = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioBancoHoras banco_horas = new CadastroFuncionarioBancoHoras();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectbanco_horas);
			pstm.setInt(1, id_banco);

			rs = pstm.executeQuery();
			rs.next();

			banco_horas.setId_banco(rs.getInt("id_banco"));
			banco_horas.setId_funcionario(rs.getInt("id_funcionario"));
			banco_horas.setMes_referencia(rs.getInt("mes"));
			banco_horas.setQuantidade_horas(rs.getString("quantidade_horas"));
			banco_horas.setTipo_banco(rs.getInt("tipo_banco"));
			ConexaoBanco.fechaConexao(conn, pstm, rs);

			return banco_horas;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o banco_horas");// );
			System.out.println("Erro ao listar banco_horas id: erro: " + "\ncausa: ");
			return null;
		}

	}

	public boolean removerbanco_horas(int id_banco_horas) {
		String sql_delete_banco_horas = "DELETE FROM tabela_auxiliar_banco_horas WHERE id_banco = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_banco_horas);

			pstm.setInt(1, id_banco_horas);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "banco_horas excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o banco_horas do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}

	public boolean atualizarBancoHoras(CadastroFuncionarioBancoHoras banco_horas) {
		if (banco_horas != null) {
			Connection conn = null;
			String atualizar = null;
			PreparedStatement pstm;

			try {

				atualizar = "update tabela_auxiliar_banco_horas set mes = ?, quantidade_horas = ?, tipo_banco = ? where id_banco = ?";

				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(atualizar);
				pstm.setInt(1, banco_horas.getMes_referencia());

				pstm.setString(2, banco_horas.getQuantidade_horas());
				pstm.setInt(3, banco_horas.getTipo_banco());

				pstm.setInt(4, banco_horas.getId_banco());

				pstm.execute();
				// JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
				System.out.println("Banco Horas Atualizado com sucesso");
				ConexaoBanco.fechaConexao(conn);
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar o banco_horas no banco de" + "dados ");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Os dados do banco_horas estão vazios");
			return false;
		}

	}

}
