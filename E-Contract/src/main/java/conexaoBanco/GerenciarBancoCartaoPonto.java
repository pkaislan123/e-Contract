

package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CartaoPonto;
import main.java.cadastros.CartaoPontoCompleto;
import main.java.cadastros.CalculoCompleto;


public class GerenciarBancoCartaoPonto {
/*
 * CREATE TABLE `tabela_auxiliar_cartao` (
  `id_cartao` int(0) NOT NULL AUTO_INCREMENT,
   `descricao` text DEFAULT NULL,
  `referencia` text DEFAULT NULL,
  `porcentagem` double DEFAULT NULL,
    `valor` double DEFAULT NULL,
  PRIMARY KEY (`id_cartao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
	

	public String sql_cartao(CartaoPonto cartao) {
		return "insert into cartao_ponto (uid) values ('"
				+ cartao.getUid() + "')";
	}
	
	public int inserircartao(CartaoPonto cartao) {
		int result = -1;
		int id_cliente = -1;
		if (cartao != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_cartao(cartao);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id cartao inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o cartao no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O cartao enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CartaoPonto> getcartaos() {
		String selectAdivitos = "select * from cartao_ponto";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CartaoPonto> lista_cartaos = new ArrayList<CartaoPonto>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CartaoPonto cartao = new CartaoPonto();
			
				
				cartao.setId_cartao(rs.getInt("id_cartao"));
				cartao.setUid(rs.getString("uid"));
				
				lista_cartaos.add(cartao);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar cartões");//  );
		}
		return lista_cartaos;

	}
	
	
	public CartaoPonto getcartao(int id) {

		String selectcartao = "select * from cartao_ponto where id_cartao = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CartaoPonto cartao = new CartaoPonto();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectcartao);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			
			cartao.setId_cartao(rs.getInt("id_cartao"));
			cartao.setUid(rs.getString("uid"));

			

		    return cartao;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o cartao id: " + id );// );
			System.out.println(
					"Erro ao listar cartao id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removercartao( int id_cartao) {
		String sql_delete_cartao = "DELETE FROM cartao_ponto WHERE id_cartao = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_cartao);

			pstm.setInt(1, id_cartao);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "Cartão excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o cartao do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	

	 public String sql_relacao_funcionario_cartao_ponto(CartaoPontoCompleto cartao) {
			return "insert into funcionario_cartao_ponto (id_funcionario, id_cartao) values ('"
					+ cartao.getId_funcionario() + "','"
					+ cartao.getId_cartao() + "')";
		}
		
		public boolean inserirRelacaoFuncionarioCartaoPonto(CartaoPontoCompleto cartao) {
			if (cartao != null) {
				try {

					   Connection conn = null;
		              PreparedStatement pstm;
     		  		conn = ConexaoBanco.getConexao();

					String query = sql_relacao_funcionario_cartao_ponto(cartao);
					Statement stmt = (Statement) conn.createStatement();

	            	 pstm = conn.prepareStatement(query);

	                pstm.execute();

					
	                ConexaoBanco.fechaConexao(conn);

					return true;

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao inserir a relacao funcionario cartao de ponto no banco de dados, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
					
					return false;
				}
			} else {
				System.out.println("O desconto enviado por parametro esta vazio");
				return false;
			}
		}
		
		
		public boolean removerRelacaoFuncionarioCartaoPonto(int id_func, int id_cartao) {
			String sql_delete= "DELETE FROM funcionario_cartao_ponto WHERE id_funcionario = ? and id_cartao = ?";
			Connection conn = null;
			ResultSet rs = null;
			try {
				conn = ConexaoBanco.getConexao();
				PreparedStatement pstm;
				pstm = conn.prepareStatement(sql_delete);

				pstm.setInt(1, id_func);
				pstm.setInt(2, id_cartao);

				pstm.execute();
				ConexaoBanco.fechaConexao(conn, pstm);
				JOptionPane.showMessageDialog(null, "Relação Colaborador Cartão de Ponto excluída!");
				return true;

			} catch (Exception f) {
				JOptionPane.showMessageDialog(null,
						"Erro ao excluir a  Relação Funcionario Cartao  do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
								+ "dados " + f.getMessage());
				return false;
			}

		}
	 
		
		public ArrayList<CartaoPontoCompleto> getCartoesPorFuncionario(int id_funcionario) {
			String selectAdivitos = "select cartao_ponto.*,\r\n"
					+ "fc.id_funcionario, CONCAT(fc.nome, \" \", fc.sobrenome) as nome_funcionario\r\n"
					+ " from cartao_ponto\r\n"
					+ "left join funcionario_cartao_ponto fctd on fctd.id_cartao = cartao_ponto.id_cartao \r\n"
					+ "left join funcionario fc on fc.id_funcionario = fctd.id_funcionario\r\n"
					+ "where fctd.id_funcionario = ?";
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			ArrayList<CartaoPontoCompleto> lista_cartoes = new ArrayList<CartaoPontoCompleto>();
			try {
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(selectAdivitos);
				pstm.setInt(1, id_funcionario);

				rs = pstm.executeQuery();
				while (rs.next()) {
					CartaoPontoCompleto cartao_completo = new CartaoPontoCompleto();
				
					cartao_completo.setId_cartao(rs.getInt("id_cartao"));
					cartao_completo.setUid(rs.getString("uid"));
					cartao_completo.setId_funcionario(rs.getInt("id_funcionario"));
					cartao_completo.setNome_funcionario(rs.getString("nome_funcionario"));

					lista_cartoes.add(cartao_completo);

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar cartões, erro: " + e.getMessage() + "\nCausa: " + e.getCause());//  );
			}
			return lista_cartoes;

		}
		
		
		public boolean verificarAssosiacaoPorCartao(int id_cartao) {
			String selectAdivitos = "select cartao_ponto.*,\r\n"
					+ "fc.id_funcionario, CONCAT(fc.nome, \"\", fc.sobrenome) as nome_funcionario\r\n"
					+ "from cartao_ponto\r\n"
					+ "left join funcionario_cartao_ponto fctd on fctd.id_cartao = cartao_ponto.id_cartao \r\n"
					+ "left join funcionario fc on fc.id_funcionario = fctd.id_funcionario\r\n"
					+ "where fctd.id_cartao = ?";
			
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			ArrayList<CartaoPontoCompleto> lista_cartoes = new ArrayList<CartaoPontoCompleto>();
			try {
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(selectAdivitos);
				pstm.setInt(1, id_cartao);

				rs = pstm.executeQuery();
				while (rs.next()) {
					CartaoPontoCompleto cartao_completo = new CartaoPontoCompleto();
				
					cartao_completo.setId_cartao(rs.getInt("id_cartao"));
					cartao_completo.setUid(rs.getString("uid"));
					cartao_completo.setId_funcionario(rs.getInt("id_funcionario"));
					cartao_completo.setNome_funcionario(rs.getString("nome_funcionario"));

					lista_cartoes.add(cartao_completo);

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar cartões, erro: " + e.getMessage() + "\nCausa: " + e.getCause());//  );
			}
			
			if(lista_cartoes.size() > 0) {
				return true;
			}else {
				return false;
			}
			
		}

	

}
