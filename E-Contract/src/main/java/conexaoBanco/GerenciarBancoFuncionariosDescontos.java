package main.java.conexaoBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.java.cadastros.CadastroFuncionario;
import main.java.cadastros.CadastroFuncionarioDescontos;
import main.java.cadastros.DescontoCompleto;


public class GerenciarBancoFuncionariosDescontos {
/*
 * CREATE TABLE `tabela_auxiliar_desconto` (
  `id_desconto` int(0) NOT NULL AUTO_INCREMENT,
   `descricao` text DEFAULT NULL,
  `referencia` text DEFAULT NULL,
  `porcentagem` double DEFAULT NULL,
    `valor` double DEFAULT NULL,
  PRIMARY KEY (`id_desconto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
	

	public String sql_desconto(CadastroFuncionarioDescontos desconto) {
		return "insert into tabela_auxiliar_desconto (descricao, referencia, porcentagem,valor) values ('"
				+ desconto.getDescricao() + "','"
				+ desconto.getReferencia() + "','"
				+ desconto.getPorcentagem() + "','"
				+ desconto.getValor() + "')";
	}
	
	public int inserirdesconto(CadastroFuncionarioDescontos desconto) {
		int result = -1;
		int id_cliente = -1;
		if (desconto != null) {
			Connection conn = null;
			try {

				conn = ConexaoBanco.getConexao();

				String query = sql_desconto(desconto);
				Statement stmt = (Statement) conn.createStatement();
				int numero = stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					result = rs.getInt(1);
					System.out.println("Id desconto inserido: " + result);
				}
				rs.close();
				stmt.close();

				return result;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Erro ao inserir o desconto no banco de dados");
				
				return -1;
			}
		} else {
			System.out.println("O desconto enviado por parametro esta vazio");
			return -1;
		}
	}
	
	
	public ArrayList<CadastroFuncionarioDescontos> getdescontos() {
		String selectAdivitos = "select * from tabela_auxiliar_desconto";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<CadastroFuncionarioDescontos> lista_descontos = new ArrayList<CadastroFuncionarioDescontos>();
		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectAdivitos);
			// pstm.setString(1, chave);

			rs = pstm.executeQuery();
			while (rs.next()) {
				CadastroFuncionarioDescontos desconto = new CadastroFuncionarioDescontos();
			
				
				desconto.setId_desconto(rs.getInt("id_desconto"));
				desconto.setDescricao(rs.getString("descricao"));
				desconto.setReferencia(rs.getString("referencia"));
				desconto.setPorcentagem(rs.getDouble("porcentagem"));
				desconto.setValor(rs.getDouble("valor"));
				
				lista_descontos.add(desconto);

			}
			ConexaoBanco.fechaConexao(conn, pstm, rs);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar descontos");//  );
		}
		return lista_descontos;

	}
	
	
	public CadastroFuncionarioDescontos getdesconto(int id) {

		String selectdesconto = "select * from tabela_auxiliar_desconto where id_desconto = ?";
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CadastroFuncionarioDescontos desconto = new CadastroFuncionarioDescontos();

		try {
			conn = ConexaoBanco.getConexao();
			pstm = conn.prepareStatement(selectdesconto);
			pstm.setInt(1, id);

			rs = pstm.executeQuery();
			rs.next();

			
			desconto.setId_desconto(rs.getInt("id_desconto"));
			desconto.setDescricao(rs.getString("descricao"));
			desconto.setReferencia(rs.getString("referencia"));
			desconto.setPorcentagem(rs.getDouble("porcentagem"));
			desconto.setValor(rs.getDouble("valor"));
			

			

		    return desconto;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar o desconto id: " + id );// );
			System.out.println(
					"Erro ao listar desconto id: " + id + " erro: "   + "\ncausa: "  );
			return null;
		}

	}
	
	
	public boolean removerdesconto( int id_desconto) {
		String sql_delete_desconto = "DELETE FROM tabela_auxiliar_desconto WHERE id_desconto = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_desconto);

			pstm.setInt(1, id_desconto);

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "desconto excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o desconto do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	
	
	public boolean removerRelacaoContratoDesconto(DescontoCompleto desc) {
		String sql_delete_desconto = "DELETE FROM funcionario_contrato_trabalho_descontos WHERE id_contrato = ? and id_desconto = ?";
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = ConexaoBanco.getConexao();
			PreparedStatement pstm;
			pstm = conn.prepareStatement(sql_delete_desconto);

			pstm.setInt(1, desc.getId_contrato_trabalho());
			pstm.setInt(2, desc.getId_desconto());

			pstm.execute();
			ConexaoBanco.fechaConexao(conn, pstm);
			JOptionPane.showMessageDialog(null, "desconto excluido, banco normalizado ");
			return true;

		} catch (Exception f) {
			JOptionPane.showMessageDialog(null,
					"Erro ao exlcuir o desconto do banco de dados\nBanco de dados corrompido!\nConsulte o administrador do sistema"
							+ "dados " + f.getMessage());
			return false;
		}

	}
	

	  public boolean atualizarDesconto(CadastroFuncionarioDescontos desconto) {
	        if (desconto != null) {
	            Connection conn = null;
	            String atualizar = null;
              PreparedStatement pstm;

	           
	            try {
	        
	            	atualizar =  "update tabela_auxiliar_desconto set descricao = ?, referencia = ?, porcentagem = ?,"
	            			+ "valor = ? where id_desconto = ?";
	            	
	          		  		conn = ConexaoBanco.getConexao();
	            	 pstm = conn.prepareStatement(atualizar);

		             pstm.setString(1, desconto.getDescricao());
		             pstm.setString(2, desconto.getReferencia());
		             pstm.setDouble(3, desconto.getPorcentagem());
		             pstm.setDouble(4, desconto.getValor());
		             pstm.setInt(5, desconto.getId_desconto());
		          
		             
	             
	                pstm.execute();
	                //JOptionPane.showMessageDialog(null, "funcionario atualizado com sucesso");
	                System.out.println("Desconto Atualizado com sucesso");
	                ConexaoBanco.fechaConexao(conn);
	              return true;
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(null, "Erro ao atualizar o desconto no banco de"
	                        + "dados "  );
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Os dados do desconto estão vazios");
	            return false;
	        }
	 
	 
	    }

	
	
	  public String sql_relacao_contrato_desconto(DescontoCompleto desc) {
			return "insert into funcionario_contrato_trabalho_descontos (id_contrato, id_desconto) values ('"
					+ desc.getId_contrato_trabalho() + "','"
					+ desc.getId_desconto()  + "')";
		}
		
		public boolean inserirRelacaoContratoDesconto(DescontoCompleto desc) {
			if (desc != null) {
				try {

					   Connection conn = null;
		              PreparedStatement pstm;
        		  		conn = ConexaoBanco.getConexao();

					String query = sql_relacao_contrato_desconto(desc);
					Statement stmt = (Statement) conn.createStatement();

	            	 pstm = conn.prepareStatement(query);

	                pstm.execute();

					
	                ConexaoBanco.fechaConexao(conn);

					return true;

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao inserir a relacao contrato desconto no banco de dados, erro: " + e.getMessage() + "\nCausa: " + e.getCause());
					
					return false;
				}
			} else {
				System.out.println("O desconto enviado por parametro esta vazio");
				return false;
			}
		}
		
		
		public ArrayList<DescontoCompleto> getdescontosPorContratoTrabalho(int id_contrato_trabalho) {
			String selectAdivitos = "select * from tabela_auxiliar_desconto\r\n"
					+ "left join funcionario_contrato_trabalho_descontos fctd on fctd.id_desconto = tabela_auxiliar_desconto.id_desconto\r\n"
					+ "where fctd.id_contrato = ?";
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			ArrayList<DescontoCompleto> lista_descontos = new ArrayList<DescontoCompleto>();
			try {
				conn = ConexaoBanco.getConexao();
				pstm = conn.prepareStatement(selectAdivitos);
				pstm.setInt(1, id_contrato_trabalho);

				rs = pstm.executeQuery();
				while (rs.next()) {
					DescontoCompleto desconto = new DescontoCompleto();
				
					
					desconto.setId_desconto(rs.getInt("id_desconto"));
					desconto.setDescricao(rs.getString("descricao"));
					desconto.setReferencia(rs.getString("referencia"));
					desconto.setPorcentagem(rs.getDouble("porcentagem"));
					desconto.setValor(rs.getDouble("valor"));
					desconto.setId_contrato_trabalho(id_contrato_trabalho);
					
					lista_descontos.add(desconto);

				}
				ConexaoBanco.fechaConexao(conn, pstm, rs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar descontos");//  );
			}
			return lista_descontos;

		}
		
		

}
