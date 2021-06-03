
package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioAcrescimos;


public class GerenciarBancoFuncionariosAcrescimos {
/*
 * CREATE TABLE `tabela_auxiliar_acrescimo` (
  `id_acrescimo` int(0) NOT NULL AUTO_INCREMENT,
   `descricao` text DEFAULT NULL,
  `referencia` text DEFAULT NULL,
  `porcentagem` double DEFAULT NULL,
    `valor` double DEFAULT NULL,
  PRIMARY KEY (`id_acrescimo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
	

	public String sql_desconto(CadastroFuncionarioAcrescimos acrescimo) {
		return "insert into tabela_auxiliar_acrescimo (descricao, referencia, porcentagem,valor) values ('"
				+ acrescimo.getDescricao() + "','"
				+ acrescimo.getReferencia() + "','"
				+ acrescimo.getPorcentagem() + "','"
				+ acrescimo.getValor() + "')";
	}
	
	public int inserirAcrescimo(CadastroFuncionarioAcrescimos acrescimo) {
		int result = -1;
		int id_cliente = -1;
		if (acrescimo != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_desconto(acrescimo);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id acrescimo inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o acrescimo no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O acrescimo enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroFuncionarioAcrescimos> getAcrescimos() {
		String selectAdivitos = "select * from tabela_auxiliar_acrescimo";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioAcrescimos> lista_acrescimos = new ArrayList<CadastroFuncionarioAcrescimos>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioAcrescimos acrescimo = new CadastroFuncionarioAcrescimos();
			
				
				acrescimo.setId_acrescimo(rs.getInt("id_acrescimo"));
				acrescimo.setDescricao(rs.getString("descricao"));
				acrescimo.setReferencia(rs.getString("referencia"));
				acrescimo.setPorcentagem(rs.getDouble("porcentagem"));
				acrescimo.setValor(rs.getDouble("valor"));
				
				lista_acrescimos.add(acrescimo);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar acrescimos");//  );
		}
		return lista_acrescimos;

	}
	
	
	public CadastroFuncionarioAcrescimos getAcrescimo(int id) {

		String selectdesconto = "select * from tabela_auxiliar_acrescimo where id_acrescimo = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioAcrescimos acrescimo = new CadastroFuncionarioAcrescimos();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectdesconto);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			acrescimo.setId_acrescimo(rs.getInt("id_acrescimo"));
			acrescimo.setDescricao(rs.getString("descricao"));
			acrescimo.setReferencia(rs.getString("referencia"));
			acrescimo.setPorcentagem(rs.getDouble("porcentagem"));
			acrescimo.setValor(rs.getDouble("valor"));
			

			

		    return acrescimo;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o acrescimo id: " + id );// );
			System.out.println(
					"Erro ao listar acrescimo id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerAcrescimo( int id_acrescimo) {
		String sql_delete_desconto = "DELETE FROM tabela_auxiliar_acrescimo WHERE id_acrescimo = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_desconto);

			pstm.setInt(1, id_acrescimo);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Acrescimo excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao excluir o acrescimo do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	

	  public boolean atualizarAcrescimo(CadastroFuncionarioAcrescimos acrescimo) {
	        if (acrescimo != null) {
	            Connection conn = null;
	            String atualizar = null;
              PreparedStatement pstm;

	           
	            try {
	        
	            	atualizar =  "update tabela_auxiliar_acrescimo set descricao = ?, referencia = ?, porcentagem = ?,"
	            			+ "valor = ? where id_acrescimo = ?";
	            	
	          		  		conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, acrescimo.getDescricao());
		             pstm.setString(2, acrescimo.getReferencia());
		             pstm.setDouble(3, acrescimo.getPorcentagem());
		             pstm.setDouble(4, acrescimo.getValor());
		             pstm.setInt(5, acrescimo.getId_acrescimo());
		          
		             
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
	                System.out.println("Desconto Atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar o acrescimo no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados do acrescimo estão vazios");
	            return false;
	        }
	 
	 
	    }

	
	

}
