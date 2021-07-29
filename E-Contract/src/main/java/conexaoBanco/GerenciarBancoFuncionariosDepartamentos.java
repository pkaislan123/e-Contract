

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioDepartamentos;


public class GerenciarBancoFuncionariosDepartamentos {
/*
CREATE TABLE `departamento` (
  `id_departamento` int(3) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `descricao` text DEFAULT NULL,
  
  PRIMARY KEY (`id_departamento`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
 */
	

	public String sql_departamento(CadastroFuncionarioDepartamentos departamento) {
		return "insert into departamento (nome, descricao) values ('"
				+ departamento.getNome() + "','"
                + departamento.getDescricao() + "')";
	}
	
	public int inserirDepartamento(CadastroFuncionarioDepartamentos departamento) {
		int result = -1;
		int id_cliente = -1;
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_departamento(departamento);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id departamento inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o departamento no banco de dados");
				
				return -1;
			}
		
	}
	
	
	public ArrayList<CadastroFuncionarioDepartamentos> getDepartamentos() {
		String selectAdivitos = "select * from departamento";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioDepartamentos> lista_departamentos = new ArrayList<CadastroFuncionarioDepartamentos>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioDepartamentos departamento = new CadastroFuncionarioDepartamentos();
			
				
				departamento.setId(rs.getInt("id_departamento"));
				departamento.setNome(rs.getString("nome"));
				departamento.setDescricao(rs.getString("descricao"));


				lista_departamentos.add(departamento);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar departamentos");//  );
		}
		return lista_departamentos;

	}
	
	

	
	public CadastroFuncionarioDepartamentos getdepartamento(int id) {

		String sql_ContratodeTrabalho = "select * from departamento where id_departamento = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioDepartamentos departamento = new CadastroFuncionarioDepartamentos();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			departamento.setId(rs.getInt("id_departamento"));
			departamento.setNome(rs.getString("nome"));
			departamento.setDescricao(rs.getString("descricao"));


			

		    return departamento;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o departamento id: " + id );// );
			System.out.println(
					"Erro ao listar departamento id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerdepartamento( int id_departamento) {
		String sql_ContratodeTrabalho = "DELETE FROM departamento WHERE id_departamento = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_ContratodeTrabalho);

			pstm.setInt(1, id_departamento);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "departamento excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o departamento do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	

	

}
