
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioSalario;
import main.java.cadastros.CadastroFuncionarioSalario;
import main.java.cadastros.CalculoCompleto;


public class GerenciarBancoFuncionarioSalarios {
/*
CREATE TABLE `funcionario_salario` (
    `id_salario` int(10) NOT NULL AUTO_INCREMENT,
  `id_funcionario` int(3) NOT NULL ,
   `id_ct_trabalho` int(3) NOT NULL ,
   `mes` int(3) NOT NULL ,
   `ano` int(5) NOT NULL ,
    salario_base double,
    total_descontos double,
    total_acrescimos double, 
    total_hora_extras double,

  PRIMARY KEY (id_salario)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8


 */
	

	public String sql_salario(CadastroFuncionarioSalario salario) {
		return "insert into funcionario_salario (id_funcionario, id_ct_trabalho, mes, ano, salario_base,total_descontos,total_acrescimos, total_hora_extras) values ('"
				+ salario.getId_funcionario()+ "','"
				+ salario.getId_ct_trabalho() + "','"
				+ salario.getMes() + "','"
				+ salario.getAno() + "','"
				+ salario.getSalario_base() + "','"
				+ salario.getTotal_descontos() + "','"
			    + salario.getTotal_acrescimos() + "','"
				+ salario.getTotal_hora_extras()+ "')";
	}
	
	public int inserirsalario(CadastroFuncionarioSalario salario) {
		int result = -1;
		int id_cliente = -1;
		if (salario != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_salario(salario);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id salario inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o salario no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O salario enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroFuncionarioSalario> getsalarios() {
		String selectAdivitos = "select * from funcionario_salario";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioSalario> lista_salarios = new ArrayList<CadastroFuncionarioSalario>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioSalario salario = new CadastroFuncionarioSalario();
			
				salario.setId_salario(rs.getInt("id_salario"));
				salario.setId_funcionario(rs.getInt("id_funcionario"));
				salario.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));
				salario.setMes(rs.getInt("mes"));
				salario.setAno(rs.getInt("ano"));
				salario.setSalario_base(rs.getDouble("salario_base"));
				salario.setTotal_descontos(rs.getDouble("total_descontos"));
				salario.setTotal_acrescimos(rs.getDouble("total_acrescimos"));
				salario.setTotal_hora_extras(rs.getDouble("total_hora_extras"));

				lista_salarios.add(salario);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar salarios");//  );
		}
		return lista_salarios;

	}
	
	

	
	
	
	public CadastroFuncionarioSalario getSalario(int id) {

		String selectsalario = "select * from funcionario_salario where id_salario = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioSalario salario = new CadastroFuncionarioSalario();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectsalario);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();


			salario.setId_salario(rs.getInt("id_salario"));
			salario.setId_funcionario(rs.getInt("id_funcionario"));
			salario.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));
			salario.setMes(rs.getInt("mes"));
			salario.setAno(rs.getInt("ano"));
			salario.setSalario_base(rs.getDouble("salario_base"));
			salario.setTotal_descontos(rs.getDouble("total_descontos"));
			salario.setTotal_acrescimos(rs.getDouble("total_acrescimos"));
			salario.setTotal_hora_extras(rs.getDouble("total_hora_extras"));

		    return salario;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o salario id: " + id );// );
			System.out.println(
					"Erro ao listar salario id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}

	
	public ArrayList<CadastroFuncionarioSalario> getSalariosPorFuncionario( int id_func) {
		String selectAdivitos = "select * from funcionario_salario where id_funcionario = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioSalario> lista_salarios = new ArrayList<CadastroFuncionarioSalario>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			 pstm.setInt(1, id_func);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioSalario salario = new CadastroFuncionarioSalario();
			
				salario.setId_salario(rs.getInt("id_salario"));
				salario.setId_funcionario(rs.getInt("id_funcionario"));
				salario.setId_ct_trabalho(rs.getInt("id_ct_trabalho"));
				salario.setMes(rs.getInt("mes"));
				salario.setAno(rs.getInt("ano"));
				salario.setSalario_base(rs.getDouble("salario_base"));
				salario.setTotal_descontos(rs.getDouble("total_descontos"));
				salario.setTotal_acrescimos(rs.getDouble("total_acrescimos"));
				salario.setTotal_hora_extras(rs.getDouble("total_hora_extras"));

				lista_salarios.add(salario);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar salarios");//  );
		}
		return lista_salarios;

	}
	
	
	public boolean removersalario( int id_salario) {
		String sql_delete_salario = "DELETE FROM funcionario_salario WHERE id_salario = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_salario);

			pstm.setInt(1, id_salario);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "salario excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o salario do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	
	

}
